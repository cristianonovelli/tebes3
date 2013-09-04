package it.enea.xlab.tebes.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import org.xlab.file.FileUtil;
import org.xlab.utilities.XLabDates;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FileManagerImpl implements FileManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM;

	public Session upload(String fileRefId, String fileName, String type, byte[] fileContent, Session session) {
		
		if (fileContent != null)
			System.out.println("FileStore3: upload: fileContent non null");
		else
			System.out.println("FileStore3: upload: fileContent NULL");
		
		String fileString = null;

		
		fileString = new String(fileContent);
		System.out.println("FileStore3: " + fileString);
		
		if (fileString != null) {
			if (!this.isFilePresent(fileString)) {
			
			
				String datetime = XLabDates.getCurrentUTC();
				
				FileStore uploadedFile = new FileStore(fileRefId, fileName, type, datetime, fileString);
				
				Long id = this.createFile(uploadedFile);
				
				System.out.println("FileStore3: id:" + id);
				System.out.println("FileStore3: fileRefId:" + fileRefId);
				// TODO avoid the replication
				// in the case the same file is already present
				
				// TODO Session e Report, aggiornamento
				// la Session deve sapere che deve prendere quel file
			
			}
			else {
				// TODO memorizzare esito e messaggio di risposta nella sessione
				System.out.println("FileStore3: file is already present");
			}
		}
		else
			System.out.println("FileStore3: filestring = null");
		
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

	
	// TODO manca il filtro per utente, cos� agisco su i file di TUTTI
	private Boolean isFilePresent(String source) {
		
        String queryString = "SELECT f FROM FileStore AS f WHERE f.source = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, source);
        @SuppressWarnings("unchecked")
		List<FileStore> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return true;
        else
        	return false;
	}
	
	public Boolean deleteFile(Long fileId) {

		FileStore file = this.readFile(fileId);
		
		if (file == null)
			return false;

		eM.remove(file);
		return true;
	}

	public boolean isFileIdPresent(String fileIdRef) {

        String queryString = "SELECT f FROM FileStore AS f WHERE f.fileRefId = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, fileIdRef);
        @SuppressWarnings("unchecked")
		List<FileStore> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return true;
        else
        	return false;
	}

	public List<FileStore> readFileListByType(String type) {

        String queryString = "SELECT f FROM FileStore AS f WHERE f.type = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, type);
        @SuppressWarnings("unchecked")
		List<FileStore> resultList = query.getResultList();

        return resultList;
	}


	
}



