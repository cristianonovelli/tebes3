package it.enea.xlab.tebes.controllers.file;

import java.rmi.NotBoundException;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.file.FileManagerRemote;
import it.enea.xlab.tebes.sut.SUTManagerRemote;

public class FileManagerController extends WebController<Report> {

	public static final String CONTROLLER_NAME = "FileManagerController";
	
	private FileManagerRemote fileManagerService;
	
	
	public FileManagerController() {

	}

	
	public void initContext() throws NotBoundException, NamingException {
		
		fileManagerService = JNDIServices.getFileManagerService();
	}


<<<<<<< .mine
	public Session upload(String fileName, String type, String fileString, Session session) {
=======
	public Session upload(String fileName, String fileString, Session session) {
>>>>>>> .r160

<<<<<<< .mine
		return fileManagerService.upload(fileName, type, fileString, session);
=======
		return fileManagerService.upload(fileName, fileString, session);
>>>>>>> .r160
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
