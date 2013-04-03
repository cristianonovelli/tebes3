package it.enea.xlab.tebes.report;

import javax.ejb.Remote;

@Remote
public interface ReportManagerRemote {

	Long createReport(Long sessionId);

	
}
