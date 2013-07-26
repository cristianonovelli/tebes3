package it.enea.xlab.tebes.controllers.file;

import java.rmi.NotBoundException;

import javax.naming.NamingException;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.file.FileManagerRemote;
import it.enea.xlab.tebes.sut.SUTManagerRemote;

public class FileManagerController extends WebController {

	public static final String CONTROLLER_NAME = "FileManagerController";
	
	private FileManagerRemote fileManagerService;
	
	
	public FileManagerController() {

	}

	
	public void initContext() throws NotBoundException, NamingException {
		
		fileManagerService = JNDIServices.getFileManagerService();
	}


	public Session upload(String fileName, String type, String lg, String fileString, Session session) {

		return fileManagerService.upload(fileName, type, lg, fileString, session);
	}
	
	

}
