package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.entity.SUT;
import javax.ejb.Local;

@Local
public interface SUTManagerRemote {
	
	// Create SUT
	public Long createSUT(SUT sut);
	
	// Read SUT
	public SUT readSUT(Long idSUT);
	
	// la lista di SUT di uno User si ricava direttamente dall'oggetto user
	// questo metodo è quindi inutile
	// public List<SUT> readSUTListbyUserId(Long userId);
	
	// Update SUT
	public Boolean updateSUT(Long idSUT);
	
	// Delete SUT
	public Boolean deleteSUT(Long idSUT);

}
