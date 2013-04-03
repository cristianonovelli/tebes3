package it.enea.xlab.tebes.report;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReportManagerImpl implements ReportManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM; 
	
	// Logger
	private static Logger logger = Logger.getLogger(ReportManagerImpl.class);

	
	/**
	 * CREATE Report
	 */
	public Long createReport(Long sessionId) {
		
		
		
		return null;
	}
	


}
