package it.enea.xlab.tebes.test;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.model.TAF;

import javax.ejb.Remote;

@Remote
public interface TestManagerRemote {
	
	public TAF buildTAF(Action action);
	
	public Report executeTAF(TAF taf, Session session);
	
}
