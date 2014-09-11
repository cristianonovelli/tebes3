package it.enea.xlab.tebes.input;

import java.util.List;

import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TextStore;

import javax.ejb.Remote;

@Remote
public interface FileManagerRemote {

	
	// FILE 
	public Session fileUpload(String fileRefId, String fileName, String type, byte[] fileContent, Session session);

	public boolean isFileIdPresent(String fileIdRef);

	public List<FileStore> readFileListByType(String type);

	public FileStore readFilebyIdRef(String fileIdRef);

	
	
	// TEXT
	public Session textUpload(Input inputTemp, String textValue, Session session);
	
	public boolean isTextIdPresent(String idRef);

	public TextStore readTextbyIdRef(String idRef);
}
