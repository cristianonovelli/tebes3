package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

public class SUTManagerController implements SUTManagerRemote {

	private SUTManagerRemote sutManagerBean;
	
	public SUTManagerController() throws Exception {

		sutManagerBean = JNDIServices.getSUTManagerService();
	}


	public Long createSUT(SUT sut, User user) {

		return sutManagerBean.createSUT(sut, user);
	}
	
	
	public SUT readSUT(Long sutId) {

		return sutManagerBean.readSUT(sutId);
	}
	
	public SUT readSUTByName(String sutName) {
		
		return sutManagerBean.readSUTByName(sutName);
	}	


	public Boolean updateSUT(SUT sut) {
		
		return sutManagerBean.updateSUT(sut);
	}


	public Boolean deleteSUT(Long idSUT) {
		
		return sutManagerBean.deleteSUT(idSUT);
	}

}
