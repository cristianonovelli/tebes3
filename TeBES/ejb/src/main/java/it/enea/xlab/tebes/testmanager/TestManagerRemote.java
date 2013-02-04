package it.enea.xlab.tebes.testmanager;

import javax.ejb.Remote;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.model.TAF;

@Remote
public interface TestManagerRemote {
	
	public TAF buildTAF(Action action);
	
	public boolean executeTAF(TAF taf);
	
}
