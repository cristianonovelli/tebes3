package it.enea.xlab.tebes.entity;

import javax.persistence.Entity;

@Entity
public class InteractionWebSite extends Interaction {
	
	private static final long serialVersionUID = 1L;

	public InteractionWebSite() {
		super(Interaction.INTERACTION_WEBSITE);
	}


	
}
