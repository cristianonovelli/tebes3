package it.enea.xlab.tebes.report;

import it.enea.xlab.tebes.entity.Report;

import javax.ejb.Remote;

@Remote
public interface ReportManagerRemote {

	Long createReport(Report report);

	Report readReport(Long reportId);

	boolean updateReport(Report report);
	
}
