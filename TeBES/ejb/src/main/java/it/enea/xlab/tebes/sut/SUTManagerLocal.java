package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

import javax.ejb.Local;

@Local
public interface SUTManagerLocal {
	
	// Create SUT
	public Long createSUT(SUT sut, User user);
	
	// Read SUT
	public SUT readSUT(Long idSUT);
	
	// la lista di SUT di uno User si ricava direttamente dall'oggetto user
	// questo metodo � quindi inutile
	// public List<SUT> readSUTListbyUserId(Long userId);
	
	// Update SUT
	public Boolean updateSUT(SUT sut);
	
	// Delete SUT
	public Boolean deleteSUT(Long idSUT);

}