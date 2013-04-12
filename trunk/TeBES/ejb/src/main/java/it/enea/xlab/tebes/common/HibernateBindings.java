package it.enea.xlab.tebes.common;

import javax.persistence.EntityManager;

import org.hibernate.Session;

public class HibernateBindings {

	public static void detach(EntityManager entityManager, Object entity) {
	    org.hibernate.Session session = (Session) entityManager.getDelegate();
	    session.evict(entity);
	}
}
