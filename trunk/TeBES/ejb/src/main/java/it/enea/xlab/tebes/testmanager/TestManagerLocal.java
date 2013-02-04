package it.enea.xlab.tebes.testmanager;

import javax.ejb.Local;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.model.TAF;

@Local
public interface TestManagerLocal {
	
	public TAF buildTAF(Action action);
	
	public boolean executeTAF(TAF taf);
	
}
