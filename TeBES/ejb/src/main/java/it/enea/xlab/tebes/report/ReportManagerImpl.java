package it.enea.xlab.tebes.report;

import java.util.List;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.dao.TeBESDAO;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.xml.transform.TransformerFactoryConfigurationError;

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
		
		String firstString = "\ncreateReportForNewSession START";

		// Create Report by JPA
		Report report = new Report();
		

		Long reportId = this.createReport(report);		
		report = this.readReport(reportId);
	
		// Get User
		User user = session.getUser();
		
		firstString = firstString.concat("\ncreateReportForNewSession - reportId:" + reportId);
		
		// Define report name as "TR-" + [reportId]
		report.setName(Report.getReportnamePrefix().concat(reportId.toString()));
		firstString = firstString.concat("\ncreateReportForNewSession - name:" + report.getName());
	
		// Set Absolute Location and Publication
		String reportDirPath = PropertiesUtil.getUserReportsDirPath(user.getId());
		String absReportFilePath = reportDirPath.concat(report.getName().concat(Constants.XML_EXTENSION));
		report.setLocation(absReportFilePath);
		
		String publication = TeBESDAO.location2publication(absReportFilePath);
		report.setPublication(publication);
		
		
		// CREATE Report File		
		try {
			XLabFileManager.create(report.getLocation(), "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("\nCreated Report file: " + absReportFilePath);
		firstString = firstString.concat("Created Report file: " + absReportFilePath);
		
		// Define report description as "Report " + [reportName]
		report.setDescription(Report.getReportdescription().concat(report.getName()));
		firstString = firstString.concat("\ncreateReportForNewSession - description:" + report.getDescription());
		
		// Set sessionID
		report.setSessionID(session.getId());
		firstString = firstString.concat("\ncreateReportForNewSession - sessionId:" + report.getSessionID());
		
		// Get and Set current Datetime
		report.setDatetime(XLabDates.getCurrentUTC());
		firstString = firstString.concat("\ncreateReportForNewSession - datetime:" + report.getDatetime());

		
		// XML di Sistema da cui prendere il template
		String xmlReportPathName = PropertiesUtil.getSuperUserReportAbsFileName(); 
		firstString = firstString.concat("\ncreateReportForNewSession - xmlReportPathName:" + xmlReportPathName);
		
		ReportDOM reportDOM = null;

		// Get ReportDOM
		firstString = firstString.concat("\ncreateReportForNewSession - PRE-DOM");
		reportDOM = new ReportDOM(xmlReportPathName);
		firstString = firstString.concat("\ncreateReportForNewSession - POST-DOM");
		
		 
		Element rootElement = reportDOM.root;
		
		
		
		// Aggiorno XML
		if ( reportDOM.root != null ) {
			
			// Aggiorno XML Root
			reportDOM.setIdAttribute(rootElement, report.getId().toString());
			reportDOM.setNameAttribute(rootElement, report.getName());
			reportDOM.setDescriptionAttribute(rootElement, report.getDescription());
			//reportDOM.setSessionIDAttribute(rootElement, report.getSessionID().toString());
			reportDOM.setStateAttribute(rootElement, report.getState());
			//reportDOM.setDatetimeAttribute(rootElement, report.getDatetime());
			
			// Aggiorno XML Session
			reportDOM.setIdAttribute(reportDOM.getSessionElement(), report.getSessionID().toString());						
			reportDOM.setSessionCreationDateTime(session.getCreationDateTime());
			reportDOM.setSessionLastUpdateDateTime(session.getLastUpdateDateTime());
			
			// Aggiorno XML User			
			reportDOM.setIdAttribute(reportDOM.getUserElement(), user.getId().toString());
			reportDOM.setUserName(user.getName());
			reportDOM.setUserSurname(user.getSurname());

			// Aggiorno XML SUT
			SUT sut = session.getSut();
			reportDOM.setSUTId(sut.getId());
			reportDOM.setSUTName(sut.getName());
			reportDOM.setSUTType(sut.getType());
			reportDOM.setSUTInteraction(sut.getInteraction().getType());						
			reportDOM.setSUTDescription(sut.getDescription());
					
			// Aggiorno XML TestPlan	
			TestPlan testPlan = session.getTestPlan();
			reportDOM.setTestPlanId(testPlan.getId());
			reportDOM.setTestPlanName(testPlan.getName());
			reportDOM.setTestPlanDescription(testPlan.getDescription());
			reportDOM.setTestPlanCreationDatetime(testPlan.getCreationDatetime());
			reportDOM.setTestPlanLastUpdateDatetime(testPlan.getLastUpdateDatetime());
			reportDOM.setTestPlanState(testPlan.getState());			
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
				
				
				
				actionCounter++;
			}		
			

			// Set XML into the Report Bean
			report.setXml(reportDOM.getXMLString());
			
			// UPDATING
			boolean updating = this.updateReport(report);
			
			// READING
			if (updating) {
				report = this.readReport(reportId);
				firstString = firstString.concat("\ncreateReportForNewSession - UPDATING OK");
				

			}
			else
				firstString = firstString.concat("\ncreateReportForNewSession - UPDATING NO");
		}

		firstString = firstString.concat("\ncreateReportForNewSession END");
		
		System.out.println(firstString);
		
		report.addToFullDescription(firstString);
		

		
		return report;
	}

	
	
}



