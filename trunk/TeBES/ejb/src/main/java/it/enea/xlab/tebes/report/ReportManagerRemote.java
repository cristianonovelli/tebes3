package it.enea.xlab.tebes.report;

import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;

import javax.ejb.Remote;

@Remote
public interface ReportManagerRemote {

	Long createReport(Report report);

	Report readReport(Long reportId);

	boolean updateReport(Report report);
	
	Report createReportForNewSession(Session session) throws Exception;

	String getSystemXMLReportAbsPathName();
	
}
