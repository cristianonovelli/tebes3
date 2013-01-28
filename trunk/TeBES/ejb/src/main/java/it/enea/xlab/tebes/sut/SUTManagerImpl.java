package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SUTManagerImpl implements SUTManagerRemote {

	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager eM; 
	
	// TODO 
	// 1. createSUT dovrebbe essere capace di "linkarsi" all'utente
	// 2. readSUTByName dovrebbe essere una readSUTByUserIdAndName ma non so come impostare la query
	
	
	/**
	 * CREATE SUT
	 * If there isn't SUT with this SUT name, it creates the new SUT
	 * @return 	sutID if created
	 * @return 	id of SUT if created
	 * 			-1 if already a SUT with that name exists
	 * 			-2 if an exception occurs
	 */
	public Long createSUT(SUT sut, User user) {

		// TODO qui ci vuole una readSUTByNameAndUser che sia gestito bene
		SUT existingSUT = this.readSUTByNameAndUser(sut.getName(), user);
		
		try {
			if (existingSUT == null) {
					
				
				//sut.addToUser(user);
				eM.persist(sut);	
				
				return sut.getId();
			}
			else 
				return new Long(-1);
		}
		catch(Exception e) {
			return new Long(-2);
		}
	}
	
	
	/**
	 * READ SUT
	 */	
	public SUT readSUT(Long sutID) {
		
		return eM.find(SUT.class, sutID);
	}	
	

	public SUT readSUTByName(String sutName) {
		
        String queryString = "SELECT s FROM SUT AS s WHERE s.name = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, sutName);
        @SuppressWarnings("unchecked")
		List<SUT> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return (SUT) resultList.get(0);
        else
        	return null;
	}
		
	/**
	 * READ SUT by Name
	 * @return the first SUT with that name, if the SUT is present 
	 * @return null, if the SUT is not present 
	 */	
	private SUT readSUTByNameAndUser(String name, User userId) {
		
        String queryString = "SELECT s FROM SUT AS s WHERE s.name = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, name);
        //query.setParameter(2, userId);
        @SuppressWarnings("unchecked")
		List<SUT> resultList = query.getResultList();
        if ((resultList != null) && (resultList.size() > 0))
        	return (SUT) resultList.get(0);
        else
        	return null;
	}

	
	/**
	 * UPDATE SUT
	 * TODO tocheck
	 */
	public Boolean updateSUT(SUT sut) {
		
		Boolean result = false;
		
		 try {
			 if ( (sut != null) && (sut.getId() != null) ) {
				 sut = eM.merge(sut);
				 
				 if (sut != null)
					 result = true;
			 }
			 
		} catch (IllegalArgumentException e) {
			result = false;
		} catch (Exception e2) {
			result = null;
		}
		 
		 return result;
	}
	
	
	/**
	 * DELETE SUT
	 */
	public Boolean deleteSUT(Long sutID) {

		SUT sut = this.readSUT(sutID);
		
		if (sut == null)
			return false;
		
		try {
			eM.remove(sut);
		} catch (IllegalArgumentException e) {
			return false;
		} catch (Exception e2) {
			return null;
		}
		
		return true;
	}


	
}
