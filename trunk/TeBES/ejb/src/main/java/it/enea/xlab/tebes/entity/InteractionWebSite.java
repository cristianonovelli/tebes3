package it.enea.xlab.tebes.entity;

import it.enea.xlab.tebes.common.SUTConstants;

import javax.persistence.Entity;

@Entity
public class InteractionWebSite extends Interaction {
	
	private static final long serialVersionUID = 1L;

	public InteractionWebSite() {
		super(SUTConstants.INTERACTION_WEBSITE);
	}

}
