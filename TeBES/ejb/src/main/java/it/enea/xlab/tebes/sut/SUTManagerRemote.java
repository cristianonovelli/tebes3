package it.enea.xlab.tebes.sut;

import java.util.List;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

import javax.ejb.Remote;

@Remote
public interface SUTManagerRemote {
	
	// Create SUT
	public Long createSUT(SUT sut, User user);
	
	// Read SUT
	public SUT readSUT(Long idSUT);
	public SUT readSUTByName(String sutName);
	
	// la lista di SUT di uno User si ricava direttamente dall'oggetto user
	// questo metodo è quindi inutile
	// public List<SUT> readSUTListbyUserId(Long userId);
	
	// Update SUT
	public Boolean updateSUT(SUT sut);
	
	// Delete SUT
	public Boolean deleteSUT(Long idSUT);

	public List<SUT> getSystemSUTSupported();

	

}
