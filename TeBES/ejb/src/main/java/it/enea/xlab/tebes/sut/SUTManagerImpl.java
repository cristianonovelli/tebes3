package it.enea.xlab.tebes.sut;

import it.enea.xlab.tebes.common.Profile;
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

	@PersistenceContext(unitName="TeBESPersistenceLayer")
	private EntityManager eM; 
	
	// TODO 
	// 1. createSUT dovrebbe essere capace di "linkarsi" all'utente
	// 2. readSUTByName dovrebbe essere una readSUTByUserIdAndName ma non so come impostare la query
	
	
	/**
	 * CREATE SUT
	 * If there isn't SUT with this SUT name, it creates the new SUT
	 * @return 	sutID if created
	 * 			-1 otherwise
	 */
	public Long createSUT(SUT sut, User user) {

		// TODO qui ci vuole una readByNameAndUser
		SUT existingSUT = this.readSUTByName(sut.getName());
		
		if (existingSUT == null) {
			eM.persist(sut);		
			
			//sut.addToUser(user);
			//eM.persist(user);
			
			return sut.getId();
		}
		else 
			return new Long(-1);
	}
	
	
	/**
	 * READ SUT
	 */	
	public SUT readSUT(Long sutID) {
		
		return eM.find(SUT.class, sutID);
	}	
		
	/**
	 * READ SUT by Name
	 * @return the first SUT with that name, if the SUT is present 
	 * @return null, if the SUT is not present 
	 */	
	private SUT readSUTByName(String name) {
		
        String queryString = "SELECT s FROM SUT AS s WHERE s.name = ?1";
        
        Query query = eM.createQuery(queryString);
        query.setParameter(1, name);
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
