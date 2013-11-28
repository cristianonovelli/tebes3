package it.enea.xlab.tebes.paging;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.Profile;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.dao.NestedOrder;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

@Stateless(name="PagingManagerImpl")  
@Interceptors({Profile.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PagingManagerImpl implements PagingManager{


	protected static Logger logger = Logger.getLogger(PagingManager.class.getName());


	@PersistenceContext(unitName=Constants.PERSISTENCE_CONTEXT)
	private EntityManager em;

	public <T>List<T> findByCriteria(Class<T> clazz,int startRow, int pageSize, List<Criterion> restrictions, List<Order> ordersBy) {
		try {
			Session session = (Session)em.getDelegate();
			Criteria criteria = session.createCriteria(clazz);
			criteria.setMaxResults(pageSize);
			criteria.setFirstResult(startRow);
			if (restrictions != null)
				for (Criterion c : restrictions)
					configureCriterion(c,criteria);

			if (ordersBy != null){
				for (Order orderBy : ordersBy){
					if (orderBy instanceof NestedOrder){
						String outerProp = ((NestedOrder)orderBy).getOuterPropertyName();
						String innerProp = ((NestedOrder)orderBy).getInnerPropertyName();
						boolean asc = ((NestedOrder)orderBy).isAscending();
						criteria.createCriteria(outerProp).addOrder( (asc) ? Order.asc(innerProp) : Order.desc(innerProp));
					}
					else
						criteria.addOrder(orderBy);
				}
			}
			
			/*if (distinct) {
				criteria.setProjection(Projections.distinct(Projections.id()));
			}*/
			
			return criteria.list();	
		} catch (Exception e) {
			logger.warn("Error calling CrudManagerBean.findByCriteria()", e);
		}
		return new ArrayList();
	}

	public int countByCriteria(Class clazz, List<Criterion> restrictions) {
		try {
			org.hibernate.Session session = (Session)em.getDelegate();
			Criteria criteria = session.createCriteria(clazz);

			if (restrictions != null)
				for (Criterion c : restrictions)
					configureCriterion(c,criteria);

			criteria.setProjection( Projections.projectionList().add(Projections.rowCount()));
			if (criteria.list() == null || criteria.list().size() == 0)
				return 0;
			else 
				return (Integer)criteria.list().get(0);

		} catch (Exception e) {
			logger.warn("Error calling CrudManagerBean.countByCriteria()", e);
		}
		return 0;
	}


	protected void configureCriterion(Criterion c,Criteria criteria)
	{
		if (c instanceof NestedCriterion){
			String outerProp = ((NestedCriterion)c).getOuterPropertyName();
			Criterion inner = ((NestedCriterion)c).getInnerCriterion();

			configureCriterion(inner,criteria.createCriteria(outerProp));
		}
		else
			criteria.add(c);
	}
}
