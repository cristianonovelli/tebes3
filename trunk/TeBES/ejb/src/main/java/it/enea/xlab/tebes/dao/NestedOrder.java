package it.enea.xlab.tebes.dao;

import org.hibernate.criterion.Order;

public class NestedOrder extends Order{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String outerPropertyName;
	public String getOuterPropertyName() {
		return outerPropertyName;
	}

	public void setOuterPropertyName(String outerPropertyName) {
		this.outerPropertyName = outerPropertyName;
	}

	public String getInnerPropertyName() {
		return innerPropertyName;
	}

	public void setInnerPropertyName(String innerPropertyName) {
		this.innerPropertyName = innerPropertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	String innerPropertyName;
	boolean ascending;
	
	public NestedOrder(String outerPropertyName, String innerPropertyName, boolean ascending) {
		super(innerPropertyName, ascending);
		this.outerPropertyName = outerPropertyName;
		this.innerPropertyName=innerPropertyName;
		this.ascending = ascending;
	}

	
}
