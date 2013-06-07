package it.enea.xlab.tebes.report;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
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
				 
				 if (report != null)
					 result = true;
			 }
			 else
				 result = false;
			 
		} catch (Exception e2) {
			result = false;
		}
		 
		return result;
	}

	
	// TODO forse aggiornerei qui dentro l'XML
	// essendo parte della struttura
	public Report createReportForNewSession(Session session) {
		
		// Create Report by JPA
		Report report = new Report();
		Long reportId = this.createReport(report);				
		report = this.readReport(reportId);
		
		// Define report name as "TR-" + [reportId]
		report.setName(Report.getReportnamePrefix().concat(reportId.toString()));
		
		// Define report description as "Report " + [reportName]
		report.setDescription(Report.getReportdescription().concat(report.getName()));
		
		// Set sessionID
		report.setSessionID(session.getId());
		
		// Get and Set current Datetime
		report.setDatetime(XLabDates.getCurrentUTC());
		
		boolean updating = this.updateReport(report);
		
		if (updating) 
			report = this.readReport(reportId);			
		else
			report = null;
		
		return report;
	}

	
	
	public String getSystemXMLReportAbsPathName() {
		
		return PropertiesUtil.getSuperUserReportAbsPathName();	
	}
	
}



