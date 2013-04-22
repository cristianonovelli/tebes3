package it.enea.xlab.tebes.test;

import javax.ejb.Remote;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.model.TAF;

@Remote
public interface TestManagerRemote {
	
	public TAF buildTAF(Action action);
	
	public Report executeTAF(TAF taf, Report report);
	
}
