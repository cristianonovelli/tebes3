package it.enea.xlab.tebes.controllers.file;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.FileStore;
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
	
	
	public FileManagerController() {

	}

	
	public void initContext() throws NotBoundException, NamingException {
		
		fileManagerService = JNDIServices.getFileManagerService();
	}


	public Session upload(String fileIdRef, String fileName, String type, String fileString, Session session) {

		return fileManagerService.upload(fileIdRef, fileName, type, fileString, session);
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
