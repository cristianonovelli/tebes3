package it.enea.xlab.tebes.file;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.xlab.utilities.XLabDates;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FileManagerImpl implements FileManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM;

	public Session upload(String fileName, String type, String language, String fileString, Session session) {
		
		String datetime = XLabDates.getCurrentUTC();
		
		FileStore uploadedFile = new FileStore(fileName, type, language, datetime, fileString);
		
		Long fileId = this.createFile(uploadedFile);
		
		System.out.println("fileId:" + fileId);
		
		// TODO avoid the replication
		// in the case the same file is already present
		
		// TODO Session e Report, aggiornamento
		// la Session deve sapere che deve prendere quel file
		

		return session;
	}

	private Long createFile(FileStore file) {

		try {
			eM.persist(file);
			return file.getId();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new Long(-1);
		}	
	} 

	
	private FileStore readFile(Long fileId) {
		
		return eM.find(FileStore.class, fileId);
	}
	
	
	public Boolean deleteFile(Long fileId) {

		FileStore file = this.readFile(fileId);
		
		if (file == null)
			return false;

		eM.remove(file);
		return true;
	}


	
}



