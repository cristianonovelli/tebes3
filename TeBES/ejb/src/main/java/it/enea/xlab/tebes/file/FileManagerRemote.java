package it.enea.xlab.tebes.file;

import it.enea.xlab.tebes.entity.Session;

import javax.ejb.Remote;

@Remote
public interface FileManagerRemote {

	public Session upload(String fileName, String type, String fileString, Session session);

}
