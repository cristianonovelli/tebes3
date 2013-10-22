package it.enea.xlab.tebes.controllers.sut;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.SUTConstants;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.SUTInteraction;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.Messages;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class SUTManagerController extends WebController<SUT> {

	public static final String CONTROLLER_NAME = "SUTManagerController";
	
	public static final String SELECT_TYPE_DEFAULT = "Seleziona il tipo";
	public static final String SELECT_INTERACTION_DEFAULT = "Seleziona l'interazione";
	
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
	private boolean showInteractionMenu = false;
	
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
				SUTInteraction interaction = new SUTInteraction();
				interaction.setType(this.selectedInteraction);
				sut.setType(this.selectedType);
				sut.setInteraction(interaction);
				this.createSUT(sut, currentUser);

				this.resetFields();
				this.updateDataModel();
				return "sut_creation_success";
			} else
				return "";

		} else {
			this.sutFormMessage = Messages.FORM_ERROR_SUT_CREATION;
			this.showSutFormMessage = true;
			return "";
		}
	}
	
	public String cancel() {
		this.resetFields();
		this.updateDataModel();
		return "back";
	}

	public void resetFields() {
		this.selectedInteraction = "";
		this.selectedType = "";
		this.showEndpointInput = false;
		this.showInteractionMenu = false;
		this.endpoint = "";
		sut = new SUT();
	}
	
	private void updateTypes() {
		
		if(typeSelection == null || typeSelection.size() == 0) {
			typeSelection = new ArrayList<SelectItem>();
			typeSelection.add(new SelectItem(SELECT_TYPE_DEFAULT));
			for (String type : this.getSUTTypeList()) {
				typeSelection.add(new SelectItem(type));
			}
		}
	}
	
	private void updateInteractions() {
		interactionSelection = new ArrayList<SelectItem>();
		List<SUTInteraction> interactions = getSUTInteractionList(this.selectedType);
		interactionSelection.add(new SelectItem(SELECT_INTERACTION_DEFAULT));
		for (SUTInteraction sutInteraction : interactions) {
			interactionSelection.add(new SelectItem(sutInteraction.getType()));
		}
	}
	
	private boolean checkSUTFields() {

		if(this.sut.getName() != null && !this.getSut().getName().equals("") && this.getSut().getDescription() != null && 
				!this.getSut().getDescription().equals("") &&
				this.selectedInteraction != null && !this.selectedInteraction.equals(SELECT_INTERACTION_DEFAULT) &&
				this.selectedInteraction != null && !this.selectedInteraction.equals(SELECT_TYPE_DEFAULT)) {
			
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
	
	public String onSelectedTypeValue() {
		this.showInteractionMenu = true;
		this.showEndpointInput = false;
		this.endpoint = "";
		return "";
	}
	
	public String openCreateSUTView() {
		this.sutFormMessage = "";
		this.showSutFormMessage = false;
		this.resetFields();
		return "create_sut";
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

	public List<SUTInteraction> getSUTInteractionList(String type) {
		return sutManagerBean.getSystemSUTInteractionListByType(type);
	}

	public boolean getShowInteractionMenu() {
		return showInteractionMenu;
	}

	public Vector<String> getSUTTypeList() {
		return sutManagerBean.getSUTTypeList();
	}

}
