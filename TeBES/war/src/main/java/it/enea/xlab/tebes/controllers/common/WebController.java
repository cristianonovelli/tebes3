package it.enea.xlab.tebes.controllers.common;

import java.rmi.NotBoundException;

import javax.naming.NamingException;

public abstract class WebController {

	public abstract void initContext() throws NotBoundException, NamingException;
}
