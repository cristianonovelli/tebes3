package it.enea.xlab.tebes.controllers.file;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.file.FileManagerRemote;

import java.rmi.NotBoundException;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class FileManagerController extends WebController<Report> {

	private static final long serialVersionUID = 1L;

	public static final String CONTROLLER_NAME = "FileManagerController";
	
	private FileManagerRemote fileManagerService;
	private ActionManagerRemote actionManagerService;
	
	public FileManagerController() {

	}

	
	public void initContext() throws NotBoundException, NamingException {
		
		fileManagerService = JNDIServices.getFileManagerService();
		actionManagerService = JNDIServices.getActionManagerService();
	}


	public Session upload(Input input, String fileName, String type, byte[] fileContent, Session session) throws Exception {

		// Aggiorno l'input
		input.setInputSolved(true);
		actionManagerService.updateInput(input);
		
		Action action = input.getAction();
		boolean checking = actionManagerService.checkActionReady(action);
		
		if (checking)
			action.setStateToReady();
		
		Session result = fileManagerService.upload(input.getFileIdRef(), fileName, type, fileContent, session);

		return result;
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


	public boolean isFileIdPresent(String fileIdRef) {
		
		return fileManagerService.isFileIdPresent(fileIdRef);
	}


	public List<FileStore> getFileListByType(String type) {
		
		return fileManagerService. readFileListByType(type); 
	}
	
	

}
