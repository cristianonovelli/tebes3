package it.enea.xlab.tebes.report;

import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;

import javax.ejb.Remote;

@Remote
public interface ReportManagerRemote {

	Long createReport(Report report);

	Report readReport(Long reportId);

	boolean updateReport(Report report);
	
	Report createReportForNewSession(Session session) throws Exception;
	
	//Long createTestResult(TestResult tr, Long reportId);
	
	void saveLog(Report report, String methodName);
	
	Report adjustGlobalResultWithSpecificResult(Report report);
}
