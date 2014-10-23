package it.enea.xlab.tebes.report;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.TestPlanDescription;
import it.enea.xlab.tebes.entity.User;

import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xlab.file.XLabFileManager;
import org.xlab.utilities.XLabDates;

@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReportManagerImpl implements ReportManagerRemote {

	
	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM; 
	
	// Logger
	private static Logger logger = Logger.getLogger(ReportManagerImpl.class);

	
	/**
	 * CREATE Report
	 */
	public Long createReport(Report report) {
		
		try {
			eM.persist(report);

			return report.getId();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			return new Long(-1);
		}
	}
	
	/**
	 * READ Report
	 */
	public Report readReport(Long id) {

		Report report = null;
		
		try {
			
			report = eM.find(Report.class, id);
			
		} catch (EntityNotFoundException e) {
			report = null;
		}
		
		return report;
	}

	/**
	 * UPDATE Report
	 */
	public boolean updateReport(Report report) {
		
		boolean result = false;
		
		 try {
			 if ( (report != null) && (report.getId() != null) ) {

				 eM.merge(report);
				 
				 if (report != null) {
					 result = true; 
				 }
			 }
			 else
				 result = false;
			 
			 // Update Report File
			 if (result) {
				 
				 ReportDOM reportXML = new ReportDOM(report.getLocation());
				 if (reportXML != null) {
					 reportXML.setContent(report.getXml());
					 reportXML.save();
				 }
			 }
			 
		} catch (Exception e2) {
			result = false;
		}
		 
		return result;
	}

	

	public Report createReportForNewSession(Session session) throws Exception {
		
		// Create Report by JPA
		Report report = new Report();
		

		Long reportId = this.createReport(report);		
		report = this.readReport(reportId);
	
		// Get User
		User user = session.getUser();
		
		String fullDescription = "\nUser: " + user.getName() + " " + user.getSurname();
		fullDescription = fullDescription.concat("\nUser ID: " + user.getId());
		fullDescription = fullDescription.concat("\nCurrent DateTime: " + XLabDates.getCurrentUTC());
		fullDescription = fullDescription.concat("\nLocalization: " + session.getLocalization());
		
		fullDescription = fullDescription.concat("\n\nReport ID: " + reportId);
		
		// Define report name as "TR-" + [reportId]
		report.setName(Constants.REPORTNAME_PREFIX.concat(reportId.toString()));
	
		// Set Absolute Location and Publication
		String reportDirPath = PropertiesUtil.getUserReportsDirPath(user.getId());
		String absReportFilePath = reportDirPath.concat(report.getName().concat(Constants.XML_EXTENSION));
		report.setLocation(absReportFilePath);
		fullDescription = fullDescription.concat("\nReport Location: " + report.getLocation());
		
		String publication = TeBESDAO.location2publication(absReportFilePath);
		report.setPublication(publication);
		fullDescription = fullDescription.concat("\nReport Publication: " + report.getPublication());
		
		// Log
		String logsUserDirPath = PropertiesUtil.getUserLogsDirPath(user.getId());
		String logUserName = Constants.LOGNAME_PREFIX.concat(reportId.toString()).concat(Constants.LOG_EXTENSION);
		String absLogUserFilePath = logsUserDirPath.concat(logUserName);
		report.setLogLocation(absLogUserFilePath);		
		fullDescription = fullDescription.concat("\nThis Log File Location: " + report.getLogLocation());
		// CREATE Report and Log Files		
		try {
			XLabFileManager.create(report.getLocation(), "");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// Define report description as "Report " + [reportName]
		//report.setDescription(Report.getReportdescription().concat(report.getName()));
		
		
		// Set sessionID
		report.setSessionID(session.getId());
		
		// Get and Set current Datetime
		report.setDatetime(XLabDates.getCurrentUTC());
		
		// XML di Sistema da cui prendere il template
		String xmlReportPathName = PropertiesUtil.getSuperUserReportAbsFileName(); 
		fullDescription = fullDescription.concat("\nReport Template Location: " + xmlReportPathName);
		
		ReportDOM reportDOM = null;

		// Get ReportDOM
		reportDOM = new ReportDOM(xmlReportPathName);
		fullDescription = fullDescription.concat("\nReport DOM Creation");
		
		 
		Element rootElement = reportDOM.root;
		
		
		
		// Aggiorno XML
		if ( reportDOM.root != null ) {
			
			// Aggiorno blocco XML relativo a Root
			/*reportDOM.setIdAttribute(rootElement, report.getId().toString());
			reportDOM.setNameAttribute(rootElement, report.getName());
			//reportDOM.setDescriptionAttribute(rootElement, report.getDescription());
			//reportDOM.setSessionIDAttribute(rootElement, report.getSessionID().toString());
			reportDOM.setStateAttribute(rootElement, report.getState());
			//reportDOM.setDatetimeAttribute(rootElement, report.getDatetime());
			fullDescription = fullDescription.concat("\nReport DOM XML Root updated");*/
			
			// Aggiorno blocco XML relativo a Header
			reportDOM.setHeaderId(report.getId().toString());
			reportDOM.setHeaderName(report.getName());
			reportDOM.setHeaderState(report.getState());
			reportDOM.setHeaderDescription("it", report.getName() + " Report File");
			reportDOM.setHeaderDescription("en", "File Report " + report.getName());
			fullDescription = fullDescription.concat("\nReport HEADER updated in XML through DOM");
			
			// Aggiorno blocco XML relativo a Session 
			reportDOM.setIdAttribute(reportDOM.getSessionElement(), report.getSessionID().toString());						
			reportDOM.setSessionCreationDateTime(session.getCreationDateTime());
			reportDOM.setSessionLastUpdateDateTime(session.getLastUpdateDateTime());
			fullDescription = fullDescription.concat("\nReport DOM XML Session updated");
			
			// Aggiorno blocco XML relativo a User			
			reportDOM.setIdAttribute(reportDOM.getUserElement(), user.getId().toString());
			reportDOM.setUserName(user.getName());
			reportDOM.setUserSurname(user.getSurname());
			fullDescription = fullDescription.concat("\nReport DOM XML User updated");
			
			// Aggiorno blocco XML relativo a SUT
			SUT sut = session.getSut();
			reportDOM.setSUTId(sut.getId());
			Node sutNode = reportDOM.getSUTNodeById(sut.getId().toString());
			reportDOM.setSUTName(sutNode, sut.getName());
			reportDOM.setSUTType(sutNode, sut.getType());
			reportDOM.setSUTInteraction(sutNode, sut.getInteraction().getType());
			Node sutInteractionNode = reportDOM.getSUTInteractionNode(sutNode);
			if (sut.getInteraction().getEndpoint() != null)
				reportDOM.setSUTInteractionEndPoint(sutInteractionNode, sut.getInteraction().getEndpoint());
			if (sut.getInteraction().getOperation() != null)
				reportDOM.setSUTInteractionOperation(sutInteractionNode, sut.getInteraction().getOperation());
			if (sut.getInteraction().getPort()!= null)
				reportDOM.setSUTInteractionPort(sutInteractionNode, sut.getInteraction().getPort());
			if (sut.getInteraction().getTimeout()>=0)
				reportDOM.setSUTInteractionTimeout(sutInteractionNode, sut.getInteraction().getTimeout());			
			
			reportDOM.setSUTDescription(sutNode, sut.getDescription());
			fullDescription = fullDescription.concat("\nReport DOM XML SUT updated");
					
			// Aggiorno blocco XML relativo a TestPlan	
			TestPlan testPlan = session.getTestPlan();
			reportDOM.setTestPlanId(testPlan.getId());
			reportDOM.setTestPlanName(testPlan.getName());
			reportDOM.setTestPlanCreationDatetime(testPlan.getCreationDatetime());
			reportDOM.setTestPlanLastUpdateDatetime(testPlan.getLastUpdateDatetime());
			reportDOM.setTestPlanState(testPlan.getState());
			
			List<TestPlanDescription> table = testPlan.getTestPlanDescriptions();
			
			for (int i=0; i<table.size(); i++) {
				
				reportDOM.setTestPlanDescription(table.get(i).getLanguage(), table.get(i).getValue());
			}
			
			
			
			
			fullDescription = fullDescription.concat("\nReport DOM XML TestPlan updated");
			// TODO location deve contenere la posizione (relativa o assoluta o url pubblico del TP utente)
			// questo vorrebbe dire che deve essere stato salvato da qualche parte nel momento dell'importazione
			// valutare se ne vale la pena (import su file, creazione cartella utente ecc.)
			//reportDOM.setTestPlanReference(testPlan.getLocation());

			// recuper actions per creare nel report l'equivalente numero
			TestPlan tp = session.getTestPlan();
			List<Action> actionTPList = tp.getWorkflow().getActions();
			int actionCounter;
			Action actionTP = actionTPList.get(0);

			// Prendo l'unica action del nuovo Report
			NodeList actionNodeList = reportDOM.getTestActionNodeList();
			Node actionTRNode = actionNodeList.item(0);

			
			// Setto gli attributi della PRIMA action del report con quelli della action del TP
			reportDOM.setIdAttribute(actionTRNode, actionTP.getActionId());
			reportDOM.setNumberAttribute(actionTRNode, new Integer(actionTP.getActionNumber()).toString());
			fullDescription = fullDescription.concat("\nReport DOM XML Action 1 updated");
			
			Node actionTRNodeClone;	
			
			// Ciclo: clono actionList.size()-1 volte (poiché una è già presente)
			actionCounter=1;
			while (actionCounter < actionTPList.size()) {
				
				
				
				// Clono action del report
				actionTRNodeClone = actionTRNode.cloneNode(true);
				actionTRNodeClone = reportDOM.doc.importNode(actionTRNodeClone, true); 
				reportDOM.insertActionNode(actionTRNodeClone);
				
				// prendo la prossima action dal TP
				actionTP = actionTPList.get(actionCounter);
				
				// Setto id e number
				reportDOM.setIdAttribute(actionTRNodeClone, actionTP.getActionId());
				reportDOM.setNumberAttribute(actionTRNodeClone, new Integer(actionTP.getActionNumber()).toString());
				
				fullDescription = fullDescription.concat("\nReport DOM XML Action " + actionCounter+1 + " updated");
				
				actionCounter++;
			}		
			

			// Set XML into the Report Bean
			report.setXml(reportDOM.getXMLString());
			fullDescription = fullDescription.concat("\nSet XML into the Report Bean");
			
			// UPDATING
			boolean updating = this.updateReport(report);
			
			// READING
			if (updating) {
				report = this.readReport(reportId);
				fullDescription = fullDescription.concat("\nUpdating Report OK");
				

			}
			else
				fullDescription = fullDescription.concat("\nUpdating Report ERROR!");
		}

		fullDescription = fullDescription.concat("\nCreated Report for new Session.");	
		report.addLineToFullDescription(fullDescription);
		
		return report;
	}

	
/*	public Long createTestResult(TestResult tr, Long reportId) {
		
		try {
			eM.persist(tr);
							
			this.addTestResultToReport(tr.getId(), reportId);
							
			return tr.getId();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			return new Long(-1);
		}
	}

	
	
	private void addTestResultToReport(Long testResultId, Long reportId) {
		
		TestResult tr = this.readTestResult(testResultId);
		Report r = this.readReport(reportId);
		
		tr.setReport(r);
		
		eM.persist(r);
		
		return;
	}

	private TestResult readTestResult(Long testResultId) {
		
		return eM.find(TestResult.class, testResultId);
	}*/
	
	public void saveLog(Report report, String methodName) {
		
		String logContent = "***** LOG FILE *****\n";
		logContent = logContent.concat("generated from method: " + methodName + "\n\n");
		logContent = logContent.concat(report.getFullDescription());
		
		try {
			XLabFileManager.create(report.getLogLocation(), logContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	

    /*<!-- Global Result can take the following 5 values: 
		"undefined": result is undefined because no action has been executed; 
		"success": when every action finished with success result;
		"warning": when there is at least an action with warning result and no action with failure or error result;
		"failure": when there is at least an action with failure result and no action with error result; 
		"error": there is at least an action with error result, this means that a system error happened -->  */  
	@SuppressWarnings("finally")
	public Report adjustGlobalResultWithSpecificResult(Report report) {
		
		try {
			String tempResult = report.getAtomicResult();
			String globalResult = report.getGlobalResult();
			
			// 1. IF tempResult == "undefined" 
			// THEN nothing
			
			// 2. IF tempResult == "success"
			if ( tempResult.equals(Report.getSuccessfulResult()) 
					&& globalResult.equals(Report.getUndefinedResult()) )
				report.setGlobalResult(tempResult);
			
			// 3. IF tempResult == "warning" 
			if ( tempResult.equals(Report.getWarningResult()) 
					&& ( globalResult.equals(Report.getUndefinedResult()) || globalResult.equals(Report.getSuccessfulResult()) ) )
				report.setGlobalResult(tempResult);

			// 4. IF tempResult == "failure" 
			if ( tempResult.equals(Report.getFailureResult()) 
					&& 
					( globalResult.equals(Report.getUndefinedResult()) || 
					  globalResult.equals(Report.getSuccessfulResult()) || 
					  globalResult.equals(Report.getWarningResult()) ) )
				report.setGlobalResult(tempResult);
			
			// 5. IF tempResult == "error" 
			if ( tempResult.equals(Report.getErrorResult()) )
				report.setGlobalResult(tempResult);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			report.addLineToFullDescription("adjustGlobalResultWithSpecificResult:" + e.getMessage());
		}	
		finally {
			report.addLineToFullDescription("adjustGlobalResultWithSpecificResult: something");
			return report;			
		}

	} // End adjustGlobalResultWithSpecificResult
	
}



