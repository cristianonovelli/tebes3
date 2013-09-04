package it.enea.xlab.tebes.file;

import java.util.List;

import it.enea.xlab.tebes.entity.FileStore;
import it.enea.xlab.tebes.entity.Session;

import javax.ejb.Remote;

@Remote
public interface FileManagerRemote {

	public Session upload(String fileRefId, String fileName, String type, byte[] fileContent, Session session);

	public boolean isFileIdPresent(String fileIdRef);

	public List<FileStore> readFileListByType(String type);

}
