package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

import javax.naming.InitialContext;

public class SUTManagerController {

	private SUTManagerRemote sutManagerBean;
	
	
	
	public SUTManagerController() throws Exception {

		InitialContext ctx = new InitialContext();
		sutManagerBean = (SUTManagerRemote) ctx.lookup("TeBES-ear/SUTManagerImpl/remote");
	}


	public Long createSUT(SUT sut, User user) {

		return sutManagerBean.createSUT(sut, user);
	}
	
	
	public SUT readSUT(Long sutId) {

		return sutManagerBean.readSUT(sutId);
	}	
}
