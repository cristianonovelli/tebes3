package it.enea.xlab.tebes.report;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
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

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
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
			 
		} catch (Exception e2) {
			result = false;
		}
		 
		return result;
	}

	

	public Report createReportForNewSession(Session session) throws Exception {
		
		System.out.println("createReportForNewSession START");
		
		// Create Report by JPA
		Report report = new Report();
		Long reportId = this.createReport(report);		
		report = this.readReport(reportId);
		System.out.println("createReportForNewSession - reportId:" + reportId);
		
		// Define report name as "TR-" + [reportId]
		report.setName(Report.getReportnamePrefix().concat(reportId.toString()));
		System.out.println("createReportForNewSession - name:" + report.getName());
		
		// Define report description as "Report " + [reportName]
		report.setDescription(Report.getReportdescription().concat(report.getName()));
		System.out.println("createReportForNewSession - description:" + report.getDescription());
		
		// Set sessionID
		report.setSessionID(session.getId());
		System.out.println("createReportForNewSession - sessionId:" + report.getSessionID());
		
		// Get and Set current Datetime
		report.setDatetime(XLabDates.getCurrentUTC());
		System.out.println("createReportForNewSession - datetime:" + report.getDatetime());

		
		// XML di Sistema da cui prendere il template
		String xmlReportPathName = PropertiesUtil.getSuperUserReportAbsFileName(); 
		System.out.println("createReportForNewSession - xmlReportPathName:" + xmlReportPathName);
		
		ReportDOM reportDOM = null;

		// Get ReportDOM
		System.out.println("createReportForNewSession - PRE-DOM");
		reportDOM = new ReportDOM(xmlReportPathName);
		System.out.println("createReportForNewSession - POST-DOM");
		
		 
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
			User user = session.getUser();
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
			
			// Set XML into the Report Bean
			report.setXml(reportDOM.getXMLString());
		
			// UPDATING
			boolean updating = this.updateReport(report);
			
			// READING
			if (updating) {
				report = this.readReport(reportId);
				System.out.println("createReportForNewSession - UPDATING OK");
			}
			else
				System.out.println("createReportForNewSession - UPDATING NO");
		}

		System.out.println("createReportForNewSession END");
		
		return report;
	}

	
	
}



