package it.enea.xlab.tebes.file;

import it.enea.xlab.tebes.entity.Session;

import javax.ejb.Local;

@Local
public interface FileManagerLocal {

	public Session upload(String fileName, String fileString, Session session);

}
