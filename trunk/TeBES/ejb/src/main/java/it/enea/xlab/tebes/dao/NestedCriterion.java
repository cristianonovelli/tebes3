package it.enea.xlab.tebes.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.TypedValue;

public class NestedCriterion implements Criterion{

	String outerPropertyName;
	Criterion innerCriterion;

	
	public NestedCriterion(String outerPropertyName, Criterion innerCriterion) {
		super();
		this.outerPropertyName = outerPropertyName;
		this.innerCriterion = innerCriterion;
	}

	public String getOuterPropertyName() {
		return outerPropertyName;
	}

	public Criterion getInnerCriterion() {
		return innerCriterion;
	}

	public void setInnerCriterion(Criterion innerCriterion) {
		this.innerCriterion = innerCriterion;
	}

	public void setOuterPropertyName(String outerPropertyName) {
		this.outerPropertyName = outerPropertyName;
	}
	
	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		// TODO Auto-generated method stub
		return new TypedValue[]{};
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		// TODO Auto-generated method stub
		return "";
	}

	
}
