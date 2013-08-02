package it.enea.xlab.tebes.controllers.sut;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.SUTConstants;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.Interaction;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.Messages;

public class SUTManagerController extends WebController<SUT> {

	public static final String CONTROLLER_NAME = "SUTManagerController";
	
	private SUTManagerRemote sutManagerBean;
	private UserManagerRemote userManagerBean;
	
	private SUT sut;
	private String sutFormMessage;
	private boolean showSutFormMessage;
	private List<SelectItem> typeSelection; 
	private List<SelectItem> interactionSelection;
	private String selectedType;
	private String selectedInteraction;
	private String endpoint;
	private boolean showEndpointInput = false;
	
	public SUTManagerController() throws NamingException {
		sutManagerBean = JNDIServices.getSUTManagerService();
		userManagerBean = JNDIServices.getUserManagerService();
	}

	public void initContext() throws NotBoundException, NamingException {
		sutManagerBean = JNDIServices.getSUTManagerService();
		userManagerBean = JNDIServices.getUserManagerService();
	}
	
	public Long createSUT(SUT sut, User user) {

		return sutManagerBean.createSUT(sut, user);
	}
		 
	public SUT readSUT(Long sutId) {

		return sutManagerBean.readSUT(sutId);
	}
	
	public SUT readSUTByName(String sutName) {
		
		return sutManagerBean.readSUTByName(sutName);
	}	

	public Boolean updateSUT(SUT sut) {
		
		return sutManagerBean.updateSUT(sut);
	}

	public Boolean deleteSUT(Long idSUT) {
		
		return sutManagerBean.deleteSUT(idSUT);
	}

	@Override
	public void updateDataModel() {
		super.updateDataModel();
	}

	@Override
	public void resetSearchParameters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Criterion> determineRestrictions() {
		List<Criterion> criterions = new ArrayList<Criterion>();
		NestedCriterion userCriterion = new NestedCriterion("user", Restrictions.eq("eMail", FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()));
        criterions.add(userCriterion);
		return criterions;
	}

	@Override
	protected List<Order> determineOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public String createNewSUT() {

		User currentUser = userManagerBean.readUsersByEmail(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()).get(0);

		if(currentUser != null && sut != null) {

			if(checkSUTFields()) {
				Interaction interaction = new Interaction();
				interaction.setType(this.selectedInteraction);
				sut.setType(this.selectedType);
				sut.setInteraction(interaction);
				this.createSUT(sut, currentUser);

				this.resetFields();
				this.updateDataModel();
				return "sut_creation_success";
			} else
				return "sut_creation_fail";

		} else {
			this.sutFormMessage = Messages.FORM_ERROR_SUT_CREATION;
			this.showSutFormMessage = true;
			return "sut_creation_fail";
		}
	}
	
	public String cancel() {
		this.updateDataModel();
		return "back";
	}

	public void resetFields() {
		sut = new SUT();
	}
	
	private void updateTypes() {
		
		typeSelection = new ArrayList<SelectItem>();
		typeSelection.add(new SelectItem(SUTConstants.SUT_TYPE1_DOCUMENT));
		typeSelection.add(new SelectItem(SUTConstants.SUT_TYPE2_TRANSPORT));
		typeSelection.add(new SelectItem(SUTConstants.SUT_TYPE3_PROCESS));
	}
	
	private void updateInteractions() {
		
		interactionSelection = new ArrayList<SelectItem>();
		interactionSelection.add(new SelectItem(SUTConstants.INTERACTION_EMAIL));
		interactionSelection.add(new SelectItem(SUTConstants.INTERACTION_WEBSITE));
		interactionSelection.add(new SelectItem(SUTConstants.INTERACTION_WS_CLIENT));
		interactionSelection.add(new SelectItem(SUTConstants.INTERACTION_WS_SERVER));
	}
	
	private boolean checkSUTFields() {

		if(this.sut.getName() != null && !this.getSut().getName().equals("") && this.getSut().getDescription() != null && 
				!this.getSut().getDescription().equals("")) {
			
			if(!this.selectedInteraction.equals(SUTConstants.INTERACTION_WEBSITE)) {
				if(this.endpoint == null || this.endpoint.equals("")) {
					
					this.sutFormMessage = Messages.FORM_ERROR_USER_NOT_COMPILED;
					this.showSutFormMessage = true;
					return false;
				}
			}
			
			return true;
		} else {
			this.sutFormMessage = Messages.FORM_ERROR_USER_NOT_COMPILED;
			this.showSutFormMessage = true;
			return false;
		}
	}
	
	public String onSelectedInteractionValue() {
		if(!this.selectedInteraction.equals(SUTConstants.INTERACTION_WEBSITE))
			this.showEndpointInput = true;
		else
			this.showEndpointInput = false;
		return "";
	}
	
	public SUT getSut() {
		if(sut == null)
			sut = new SUT();
		return sut;
	}

	public void setSut(SUT sut) {
		this.sut = sut;
	}

	public String getSutFormMessage() {
		return sutFormMessage;
	}

	public boolean getShowSutFormMessage() {
		return showSutFormMessage;
	}

	public List<SelectItem> getTypeSelection() {
		if(this.typeSelection == null || this.typeSelection.size() == 0)
			this.updateTypes();
		return typeSelection;
	}

	public List<SelectItem> getInteractionSelection() {
		if(this.interactionSelection == null || this.interactionSelection.size() == 0)
			this.updateInteractions();
		return interactionSelection;
	}

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public String getSelectedInteraction() {
		return selectedInteraction;
	}

	public void setSelectedInteraction(String selectedInteraction) {
		this.selectedInteraction = selectedInteraction;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public boolean getShowEndpointInput() {
		return showEndpointInput;
	}

}
