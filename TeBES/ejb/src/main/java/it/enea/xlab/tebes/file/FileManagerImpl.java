package it.enea.xlab.tebes.file;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.common.PropertiesUtil;
import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.report.ReportManagerRemote;
import it.enea.xlab.tebes.session.SessionManagerRemote;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.xlab.file.XLabFileManager;
import org.xlab.utilities.XLabDates;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FileManagerImpl implements FileManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM;

	@EJB
	private SessionManagerRemote sessionManager; 
	
	/**
	 * UPLOAD single file
	 */
	public Session upload(String fileIdRef, String fileName, String type, byte[] fileContent, Session session) {

		// Se il fileIdRef è già presente, allora il file è già stato caricato
		// TODO deve essere ritornato nella Session un messaggio congruo
		if ( !this.isFileIdPresent(fileIdRef) ) {
		
			// Se il il file content non è vuoto ...
			if  ( (fileContent != null) && (fileContent.length > 0) ) {
			
				// Get file string
				String fileString = new String(fileContent);
				
				// Get userId
				Long userId = session.getUser().getId();	
				

				// Generate datetime
				String datetime = XLabDates.getCurrentUTC();			
				String datetimeForFile = datetime.replace(":", "");
				datetimeForFile = datetimeForFile.replace("-", "");
				
				// Generate TeBES fileName
				String tebesFileName = FileStore.generateTeBESFileName(fileIdRef, userId.toString(), fileName, datetimeForFile);		
				
				// SAVE File in FileStore
				FileStore uploadedFile = new FileStore(fileIdRef, tebesFileName, type, datetime, fileString);
				
				Long id = this.createFile(uploadedFile);
				
                if (id > 0) {
                	uploadedFile = this.readFile(id);
                    this.setSessionOfFile(uploadedFile, session);
                }

				// SAVE File in TeBES FilyStem					
				String absGenericUserDocFilePath = PropertiesUtil.getUserDocsDirPath(userId);

				try {
					XLabFileManager.create(absGenericUserDocFilePath, tebesFileName, fileString);
					
				} catch (IOException e) {
					
					// TODO messaggio in Session
					e.printStackTrace();
				}
				
				// ADJUST SESSION
				
				// TODO avoid the replication
				// in the case the same file is already present
				
				// TODO Session e Report, aggiornamento
				// la Session deve sapere che deve prendere quel file
				// lo sa tramite gli input delle action
			
			} // if (fileString != null)
			
		} // if ( !this.isFileIdPresent(fileRefId) ) 
		
		
		sessionManager.updateSession(session);
		
		return session;
	}

	
	
	public FileStore readFilebyIdRef(String fileIdRef) {
		
        String queryString = "SELECT f FROM FileStore AS f WHERE f.fileRefId = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, fileIdRef);

        @SuppressWarnings("unchecked")
		List<FileStore> resultList = query.getResultList();
        
        if ((resultList != null) && (resultList.size() > 0))
        	return (FileStore) resultList.get(0);
        else
        	return null;
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

	
	// TODO manca il filtro per utente, così agisco su i file di TUTTI
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

	// TODO dovrei ottenere solo i file di una certa sessione e di un certo tipo per uno specifico utente
	public List<FileStore> readFileListByType(String type) {

        String queryString = "SELECT f FROM FileStore AS f WHERE f.type = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, type);
        @SuppressWarnings("unchecked")
		List<FileStore> resultList = query.getResultList();

        return resultList;
	}

	public List<FileStore> readFileList() {

        String queryString = "SELECT f FROM FileStore AS f";
        
        Query query = eM.createQuery(queryString);

        @SuppressWarnings("unchecked")
		List<FileStore> resultList = query.getResultList();

        return resultList;
	}

	public void setSessionOfFile(FileStore file, Session session) {
		
		FileStore f = this.readFile(file.getId());
		Session s = sessionManager.readSession(session.getId());
		
		f.setSession(s);
		eM.persist(f);	

		return;
	}
	
}



