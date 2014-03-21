package it.enea.xlab.tebes.controllers.users;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.controllers.localization.LocalizationController;
import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.FormMessages;
import it.enea.xlab.tebes.utils.UserUtils;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class GroupAdminController extends WebController<Group> {

		private static final long serialVersionUID = 1L;
		public static final String CONTROLLER_NAME = "GroupAdminController";
    	//private static Logger logger = Logger.getLogger(UserAdminController.class);
        
        private UserManagerRemote userManagerService;
        //private SUTManagerRemote sutManagerService;
        
        LocalizationController lc = new LocalizationController();
        
    	private Long groupToDelete;
    	private Group selectedGroup;
    	
    	private boolean newGroupFormMessageRendered = false;
    	private String newGroupFormMessage;
    	private boolean isGroupEditMode = false;
    	
    	// view_edit_user page
    	//private List<SelectItem> roleSelect; 
    	private List<SelectItem> groupSelect;
    	//private String currentPasswordConfirm;
    	//private String selectedRole;
    	private String groupFormMessage;
    	private boolean showGroupFormMessage;

    	
		public GroupAdminController() throws NamingException {
        	userManagerService = JNDIServices.getUserManagerService();
        	//sutManagerService = JNDIServices.getSUTManagerService();
        }

        public void initContext() throws NotBoundException, NamingException {
                userManagerService = JNDIServices.getUserManagerService();        
                //sutManagerService = JNDIServices.getSUTManagerService();
        }
              

        // CREATE Group
        public Long createGroup(Group group) {
                return userManagerService.createGroup(group);
        }

        public List<Long> getGroupIdList() {
                return userManagerService.getGroupIdList();
        }

        public List<Group> getGroupList() {
                return userManagerService.getGroupList();
        }

        public Group readGroup(Long id) {
                return userManagerService.readGroup(id);
        }

        public Long setUserGroup(User user, Group group) {
                return userManagerService.setUserGroup(user, group);
        }

        public Boolean deleteGroup(Long id) {
                return userManagerService.deleteGroup(id);
        }



		@Override
		public void resetSearchParameters() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected List<Criterion> determineRestrictions() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected List<Order> determineOrder() {
			// TODO Auto-generated method stub
			return null;
		}

		public String viewGroupDetails() {
			this.selectedGroup = (Group) dataModel.getRowData();
			this.isGroupEditMode = true;
			this.groupFormMessage = "";
			this.showGroupFormMessage = false;
			return "toGroupDetails";
		}
	

		public String createGroup() {
			this.selectedGroup = new Group();
			//this.currentPasswordConfirm = "";
			this.groupFormMessage = "";
			this.showGroupFormMessage = false;
			this.isGroupEditMode = false;
			return "toGroupCreation";
		}
		
		public String deleteGroup() {
			
			try {
				
				userManagerService.deleteGroup(this.getGroupToDelete());
			
			} catch (Exception e) {

				setNewGroupFormMessage(FormMessages.getErrorDeleteGroup());						
				setNewGroupFormMessageRendered(true);
			}
			updateDataModel();
			return "group_deleted";
		}

		@Override
		public void updateDataModel() {
			super.updateDataModel();
		}
		
		
		private void updateGroups() throws NamingException {
			
			List<Group> groupsFromDB = JNDIServices.getUserManagerService().getGroupList();
			groupSelect = new ArrayList<SelectItem>();
			for (Group group : groupsFromDB) {
				groupSelect.add(new SelectItem(group.getName()));
			}
		}
		
		public String createNewGroup() {

			String message = UserUtils.checkGroupFields(selectedGroup.getName(), selectedGroup.getDescription());
			
			if(message == null) {
				
				Group group = new Group(selectedGroup.getName(), selectedGroup.getDescription());

				// Create Group
				Long result = this.userManagerService.createGroup(group);
				
				if (result > 0) 				
					group = this.userManagerService.readGroup(result);					
				else {
					
					// TEMPORARY CODE
					this.groupFormMessage = "Creation Group returned negative Long [to handle in GroupAdminController]";
					this.showGroupFormMessage = true;
					return "";
				}
					
				/*else if(result == -1) {
					this.groupFormMessage = FormMessages.getErrorUserAlreadyExisting();
					this.showUserFormMessage = true;
					return "";
				} else if(result == -2) {
					this.userFormMessage = FormMessages.getErrorUserCreation();
					this.showUserFormMessage = true;
					return "";
				}*/
				
				this.updateDataModel();
				return "creationgroup_success";
			} else {
				this.groupFormMessage = message;
				this.showGroupFormMessage = true;
				return "";
			}

		}
		
		public String updateGroup() {
			
			String message = UserUtils.checkGroupFields(selectedGroup.getName(), selectedGroup.getDescription());
			
			if(message == null) {
				
				Group group = this.userManagerService.readGroup(this.selectedGroup.getId());
				
				if(group == null) {
					this.groupFormMessage = FormMessages.getGroupNotExisting();
					this.showGroupFormMessage = true;
					return "";
				}
				
				group.setName(selectedGroup.getName());
				group.setDescription(selectedGroup.getDescription());

				if(!this.userManagerService.updateGroup(group)) {
					this.groupFormMessage = FormMessages.getErrorUserUpdate();
					this.showGroupFormMessage = true;
					return "";
				}
				
				this.updateDataModel();
				return "updategroup_success";
			} else {
				this.groupFormMessage = message;
				this.showGroupFormMessage = true;
				return "";
			}
		}
		
		public String cancel() {
			this.updateDataModel();
			return "back";
		}
		
		public Long getGroupToDelete() {
			return groupToDelete;
		}

		public void setGroupToDelete(Long groupToDelete) {
			this.groupToDelete = groupToDelete;
		}

		public boolean isNewGroupFormMessageRendered() {
			return newGroupFormMessageRendered;
		}

		public void setNewGroupFormMessageRendered(boolean newGroupFormMessageRendered) {
			this.newGroupFormMessageRendered = newGroupFormMessageRendered;
		}

		public String getNewGroupFormMessage() {
			return newGroupFormMessage;
		}

		public void setNewGroupFormMessage(String newGroupFormMessage) {
			this.newGroupFormMessage = newGroupFormMessage;
		}
		
		public Group getSelectedGroup() {
			return selectedGroup;
		}

		public void setSelectedGroup(Group selectedGroup) {
			this.selectedGroup = selectedGroup;
		}

		public boolean getIsGroupEditMode() {
			return isGroupEditMode;
		}

        
       /* public List<SelectItem> getGroupSelect() throws NamingException {
			if(groupSelect == null || groupSelect.size() == 0)
				this.updateGroups();
			return groupSelect;
		}*/


		public String getGroupFormMessage() {
			return groupFormMessage;
		}

		public boolean getShowGroupFormMessage() {
			return showGroupFormMessage;
		}

		public boolean checkValidation() {
				
			return true;
		}

}
