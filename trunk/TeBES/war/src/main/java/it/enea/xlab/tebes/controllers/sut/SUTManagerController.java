package it.enea.xlab.tebes.controllers.sut;

import java.rmi.NotBoundException;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerRemote;

public class SUTManagerController extends WebController<SUT> {

	public static final String CONTROLLER_NAME = "SUTManagerController";
	
	private SUTManagerRemote sutManagerBean;
	
	
	public SUTManagerController() {

	}

	
	public void initContext() throws NotBoundException, NamingException {
		
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


	@Override
	public void resetSearchParameters() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected List<Criterion> determineRestrictions() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected List<Order> determineOrder() {
		// TODO Auto-generated method stub
		return null;
	}

}
