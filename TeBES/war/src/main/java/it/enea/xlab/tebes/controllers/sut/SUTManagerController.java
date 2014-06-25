package it.enea.xlab.tebes.controllers.sut;

import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.common.SUTConstants;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.controllers.localization.LocalizationController;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.SUTInteraction;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.sut.SUTManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.FormMessages;
import it.enea.xlab.tebes.utils.UserUtils;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class SUTManagerController extends WebController<SUT> {

	private static final long serialVersionUID = 1L;

	public static final String CONTROLLER_NAME = "SUTManagerController";
	
	public static final String SELECT_TYPE_DEFAULT = "Select";
	public static final String SELECT_INTERACTION_DEFAULT = "Select";
	
	LocalizationController lc = new LocalizationController();
	
	private SUTManagerRemote sutManagerBean;
	private UserManagerRemote userManagerBean;
	
	private SUT sut;
	//private SUT selectedSUT;
	private String sutFormMessage;
	private boolean showSutFormMessage;
	private List<SelectItem> typeSelection; 
	private List<SelectItem> interactionList;
	private String selectedType;
	private String selectedInteraction;
	private String endpoint;
	private boolean showEndpointInput = false;
	private boolean showInteractionMenu = false;
	
	private boolean newItemFormMessageRendered = false;
	private Long sutToDelete;
	private String newItemFormMessage;
	private boolean isSUTEditMode = false;
	

	
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

		//this.setSUTEditMode(false);
		
		if(currentUser != null && this.sut != null) {

			if(checkSUTFields()) {
				SUTInteraction interaction = new SUTInteraction();
				interaction.setType(this.selectedInteraction);
				
				if ( (this.endpoint != null) && !this.endpoint.equals("") )
					interaction.setEndpoint(this.endpoint);
				
				this.sut.setType(this.selectedType);
				this.sut.setInteraction(interaction);
				this.createSUT(this.sut, currentUser);

				this.resetFields();
				this.updateDataModel();
				return "sut_creation_success";
			} else
				return "";

		} else {
			this.sutFormMessage = FormMessages.getErrorSutCreation();
			this.showSutFormMessage = true;
			return "";
		}
	}
	
	public String updateSUT() {
		
		String message = UserUtils.checkSUTFields(this.sut.getName(), this.sut.getDescription(), this.selectedType, this.selectedInteraction, this.endpoint);
		
		if(message == null) {
			
			if(sut.getId() == null) {
				this.sutFormMessage = FormMessages.getSutNotExisting();
				this.showSutFormMessage = true;
				return "";
			}			
			
			SUT sutToUpdate = this.sutManagerBean.readSUT(this.sut.getId());
			
			sutToUpdate.setName(sut.getName());
			sutToUpdate.setDescription(sut.getDescription());
			sutToUpdate.setType(this.selectedType);
			
			SUTInteraction tempInteraction = sutToUpdate.getInteraction();
			tempInteraction.setType(this.selectedInteraction);
					//new SUTInteraction(this.selectedInteraction);
			if (this.endpoint != null)
				tempInteraction.setEndpoint(endpoint);
			
			sutToUpdate.setInteraction(tempInteraction);

			if(!this.sutManagerBean.updateSUT(sutToUpdate)) {
				this.sutFormMessage = FormMessages.getErrorSutUpdate();
				this.showSutFormMessage = true;
				
				return "";
			}
			
			this.updateDataModel();
			this.sutFormMessage = FormMessages.getSutUpdate();
			return "sut_updating_success";
			
		} else {
			this.sutFormMessage = message;
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
		this.sut = new SUT();
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
		this.interactionList = new ArrayList<SelectItem>();
		List<SUTInteraction> interactions = getSUTInteractionList(this.selectedType);
		this.interactionList.add(new SelectItem(SELECT_INTERACTION_DEFAULT));
		for (SUTInteraction sutInteraction : interactions) {
			this.interactionList.add(new SelectItem(sutInteraction.getType()));
		}
	}
	
	private boolean checkSUTFields() {

		if(this.sut.getName() != null && !this.sut.getName().equals("") && this.sut.getDescription() != null && 
				!this.sut.getDescription().equals("") &&
				this.selectedInteraction != null && !this.selectedInteraction.equals(SELECT_INTERACTION_DEFAULT) &&
				this.selectedInteraction != null && !this.selectedInteraction.equals(SELECT_TYPE_DEFAULT)) {
			
			if(!this.selectedInteraction.equals(SUTConstants.INTERACTION_WEBSITE)) {
				if(this.endpoint == null || this.endpoint.equals("")) {
					
					this.sutFormMessage = FormMessages.getErrorUserNotCompiled();

					this.showSutFormMessage = true;
					return false;
				}
			}
			
			return true;
			
		} else {
			
			this.sutFormMessage = FormMessages.getErrorUserNotCompiled();
			this.showSutFormMessage = true;
			return false;
		}
	}
	
	public String onSelectedTypeValue() {
		this.showInteractionMenu = true;
		
		if ((this.endpoint == null) || (this.endpoint.equals(""))) 
			this.showEndpointInput = false;
		else
			this.showEndpointInput = true;
		
		//this.endpoint = "";
		return "";
	}	
	
	public String onSelectedInteractionValue() {
		
		
		if(!this.selectedInteraction.equals(SUTConstants.INTERACTION_WEBSITE))
			this.showEndpointInput = true;
		else
			this.showEndpointInput = false;
		return "";
	}
	

	
	public String openCreateSUTView() {
		this.setSUTEditMode(false);
		this.sutFormMessage = "";
		this.showSutFormMessage = false;
		this.resetFields();
		return "create_sut";
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

/*	public List<SelectItem> getInteractionSelection() {
		this.updateInteractions();
		return interactionLit;
	}*/

	public String getSelectedType() {
		
		if(this.getSut() != null && this.sut.getType() != null)
			this.selectedType = this.getSut().getType();
		return this.selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public String getSelectedInteraction() {
		
		if ( (this.selectedInteraction == null) || this.selectedInteraction.equals("") )  
			if(this.getSut() != null && this.getSut().getInteraction() != null)
				this.selectedInteraction = this.getSut().getInteraction().getType();
		
		return this.selectedInteraction;
	}

	public void setSelectedInteraction(String selectedInteraction) {
		this.selectedInteraction = selectedInteraction;
	}

	public String getEndpoint() {
		
		if ( (this.endpoint == null) || this.endpoint.equals("") )  
			if(this.getSut() != null && this.sut.getInteraction() != null)
				this.endpoint = this.getSut().getInteraction().getEndpoint();
		
		return this.endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public boolean getShowEndpointInput() {
		
		if (this.isSUTEditMode)
			if (this.sut != null)
				if (this.sut.getInteraction() != null)
					if (!this.sut.getInteraction().getType().equals(SUTConstants.INTERACTION_WEBSITE))
			this.showEndpointInput = true;
		
		return this.showEndpointInput;
	}

	public List<SUTInteraction> getSUTInteractionList(String type) {
		return sutManagerBean.getSystemSUTInteractionListByType(type);
	}

	public boolean getShowInteractionMenu() {
		
		if (this.isSUTEditMode)
			this.showInteractionMenu = true;
		
		return this.showInteractionMenu;
	}

	public ArrayList<String> getSUTTypeList() {
		return sutManagerBean.getSUTTypeList();
	}

	
	
	public String viewSutDetails() {
		
		this.resetFields();
		this.sut = (SUT) dataModel.getRowData();
		this.isSUTEditMode = true;
		this.sutFormMessage = "";
		this.showSutFormMessage = false;
		return "toSutDetails";
	}
	
	public boolean getIsSUTEditMode() {
		return isSUTEditMode;
	}
	
	public Long getSutToDelete() {
		return sutToDelete;
	}

	public void setSutToDelete(Long sutToDelete) {
		this.sutToDelete = sutToDelete;
	}
	
	public String deleteSut() {
		
		try {
			
			sutManagerBean.deleteSUT(this.getSutToDelete());
		
		} catch (Exception e) {

			setNewItemFormMessage(FormMessages.getErrorDeleteSUT());						
			setNewItemFormMessageRendered(true);
		}
		updateDataModel();
		return "sut_deleted";
	}
	
	public boolean isNewItemFormMessageRendered() {
		return newItemFormMessageRendered;
	}

	public void setNewItemFormMessageRendered(boolean newItemFormMessageRendered) {
		this.newItemFormMessageRendered = newItemFormMessageRendered;
	}
	
	public String getNewItemFormMessage() {
		return newItemFormMessage;
	}

	public void setNewItemFormMessage(String newItemFormMessage) {
		this.newItemFormMessage = newItemFormMessage;
	}

	public SUT getSut() {
		
		if(this.sut == null)
			this.sut = new SUT();
		
		return this.sut;
	}

	public void setSut(SUT sut) {
		this.sut = sut;
	}

	public void setSUTEditMode(boolean isSUTEditMode) {
		this.isSUTEditMode = isSUTEditMode;
	}

	public List<SelectItem> getInteractionList() {
		
		if(this.interactionList == null || this.typeSelection.size() == 0)
			this.updateInteractions();
		
		//if (!this.isSUTEditMode)
		//	this.updateInteractions();
		
		return interactionList;
	}

	public void setInteractionList(List<SelectItem> interactionList) {
		this.interactionList = interactionList;
	}

}
