package it.enea.xlab.tebes.controllers.users;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.Messages;
import it.enea.xlab.tebes.utils.UserUtils;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class UserAdminController extends WebController<User> {

		private static final long serialVersionUID = 1L;
		public static final String CONTROLLER_NAME = "UserAdminController";
    	//private static Logger logger = Logger.getLogger(UserAdminController.class);
        
        private UserManagerRemote userManagerService;

    	private Long itemToDelete;
    	private User selectedUser;
    	
    	private boolean newItemFormMessageRendered = false;
    	private String newItemFormMessage;
    	private boolean isUserEditMode = false;
    	
    	// view_edit_user page
    	private List<SelectItem> roleSelect; 
    	private List<SelectItem> groupSelect;
    	private String currentPasswordConfirm;
    	private String selectedRole;
    	private String selectedGroup;
    	private String userFormMessage;
    	private boolean showUserFormMessage;

    	
		public UserAdminController() throws NamingException {
        	userManagerService = JNDIServices.getUserManagerService();
        }

        public void initContext() throws NotBoundException, NamingException {
                userManagerService = JNDIServices.getUserManagerService();                 
        }
        
        
        // GET USER LIST
        public List<Long> getUserIdList(User superUser) {  
                return userManagerService.getUserIdList(superUser);
        }
        
        // CREATE -> Equivalente a UserProfileManager.signUp MA ASSEGNA ANCHE UN RUOLO DIVERSO DA QUELLO DI DEFAULT
        /**
         * Registration (CREATE) User
         * @return userId
         */
        public Long createUser(User user, Role role) {

                Long userId = userManagerService.createUser(user);
                
                if (userId > 0) {
                        user = this.readUser(userId);
                        userManagerService.setUserRole(user, role);
                }
                return userId;
        }       

        // READ User
        public User readUser(Long id) {
                
                return userManagerService.readUser(id);
        }
        
        // UPDATE -> UserProfileManager.Update
        
        // UPDATEROLE
        
        // DELETE User
        public Boolean deleteUser(Long id) {
                return userManagerService.deleteUser(id);
        }

        public Boolean deleteUserByEmail(String email) {
                return userManagerService.deleteUserByEmail(email);
        }
        
        // GET Role LIST
        public List<Long> getRoleIdList() {
                
                // Se servono tutti i ruoli, 
                // allora creo in questa classe il metodo getRoleList 
                // che utilizza il presente metodo
                
                return userManagerService.getRoleIdList();
        }
        
        // CREATE Role
        public Long createRole(Role role) {
                return userManagerService.createRole(role);
        }

        // READ Role
        public Role readRole(Long roleId) {
                return userManagerService.readRole(roleId);
        }

        // READ Role by level
        public Role readRoleByLevel(int level) {
                return userManagerService.readRoleByLevel(level);
        }

        public void setUserRole(User user, Role role) {
                userManagerService.setUserRole(user, role);
        }

        public Boolean deleteRole(Long id) {    
                return userManagerService.deleteRole(id);
        }

        // CREATE Role
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

        public List<Long> getActionIdList() {
                return userManagerService.getActionIdList();
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

		public String viewItemDetails() {
			this.selectedUser = (User) dataModel.getRowData();
			this.isUserEditMode = true;
			this.userFormMessage = "";
			this.showUserFormMessage = false;
			return "toItemDetails";
		}
		
		public String createItem() {
			this.selectedUser = new User();
			this.currentPasswordConfirm = "";
			this.userFormMessage = "";
			this.showUserFormMessage = false;
			this.isUserEditMode = false;
			return "toItemCreation";
		}
		
		public String deleteItem() {
			try {
			userManagerService.deleteUser(this.getItemToDelete());
			} catch (Exception e) {
				setNewItemFormMessage(Messages.FORM_ERROR_DELETE_USER);
				setNewItemFormMessageRendered(true);
			}
			updateDataModel();
			return "item_deleted";
		}

		@Override
		public void updateDataModel() {
			super.updateDataModel();
		}
		
		private void updateRoles() throws NamingException {
			
			List<Role> rolesFromDB = JNDIServices.getUserManagerService().readAllRoles();
			roleSelect = new ArrayList<SelectItem>();
			for (Role role : rolesFromDB) {
				roleSelect.add(new SelectItem(role.getName()));
			}
		}
		
		private void updateGroups() throws NamingException {
			
			List<Group> groupsFromDB = JNDIServices.getUserManagerService().getGroupList();
			groupSelect = new ArrayList<SelectItem>();
			for (Group group : groupsFromDB) {
				groupSelect.add(new SelectItem(group.getName()));
			}
		}
		
		public String createNewUser() {

			String message = UserUtils.checkUserFields(selectedUser.getName(), selectedUser.getSurname(), selectedUser.geteMail(), selectedUser.getPassword(), 
					currentPasswordConfirm);
			
			if(message == null) {
				
				User user = new User();
				user.setName(selectedUser.getName());
				user.setSurname(selectedUser.getSurname());
				user.seteMail(selectedUser.geteMail());
				user.setPassword(selectedUser.getPassword());

				if(this.selectedRole != null && !this.selectedRole.equals("")) {
					Role role = this.userManagerService.readRole(this.selectedRole);
					if(role != null)
						user.setRole(role);
				}

				if(this.selectedGroup != null && !this.selectedGroup.equals("")) {
					Group group = this.userManagerService.readGroup(this.selectedGroup);
					if(group != null)
						user.setGroup(group);
				}

				Long result = this.userManagerService.createUser(user);
				if(result == -1) {
					this.userFormMessage = Messages.FORM_ERROR_USER_ALREADY_EXISTING;
					this.showUserFormMessage = true;
					return "";
				} else if(result == -2) {
					this.userFormMessage = Messages.FORM_ERROR_USER_CREATION;
					this.showUserFormMessage = true;
					return "";
				}
				
				this.updateDataModel();
				return "creation_success";
			} else {
				this.userFormMessage = message;
				this.showUserFormMessage = true;
				return "";
			}

		}
		
		public String updateUser() {
			
			String message = UserUtils.checkUserFields(selectedUser.getName(), selectedUser.getSurname(), selectedUser.geteMail(), selectedUser.getPassword(), currentPasswordConfirm);
			
			if(message == null) {
				
				User user = this.userManagerService.readUser(this.selectedUser.getId());
				
				if(user == null) {
					this.userFormMessage = Messages.FORM_USER_NOT_EXISTING;
					this.showUserFormMessage = true;
					return "";
				}
				
				user.setName(selectedUser.getName());
				user.setSurname(selectedUser.getSurname());
				user.seteMail(selectedUser.geteMail());
				user.setPassword(selectedUser.getPassword());

				if(this.selectedRole != null && !this.selectedRole.equals("")) {
					Role role = this.userManagerService.readRole(this.selectedRole);
					if(role != null)
						user.setRole(role);
				}

				if(this.selectedGroup != null && !this.selectedGroup.equals("")) {
					Group group = this.userManagerService.readGroup(this.selectedGroup);
					if(group != null)
						user.setGroup(group);
				}

				if(!this.userManagerService.updateUser(user)) {
					this.userFormMessage = Messages.FORM_ERROR_USER_UPDATE;
					this.showUserFormMessage = true;
					return "";
				}
				
				this.updateDataModel();
				return "update_success";
			} else {
				this.userFormMessage = message;
				this.showUserFormMessage = true;
				return "";
			}
		}
		
		public String cancel() {
			this.updateDataModel();
			return "back";
		}
		
		public Long getItemToDelete() {
			return itemToDelete;
		}

		public void setItemToDelete(Long itemToDelete) {
			this.itemToDelete = itemToDelete;
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
		
		public User getSelectedUser() {
			return selectedUser;
		}

		public void setSelectedUser(User selectedUser) {
			this.selectedUser = selectedUser;
		}

		public boolean getIsUserEditMode() {
			return isUserEditMode;
		}

		public List<SelectItem> getRoleSelect() throws NamingException {
			if(roleSelect == null || roleSelect.size() == 0)
				this.updateRoles();
			return roleSelect;
		}
        
        public List<SelectItem> getGroupSelect() throws NamingException {
			if(groupSelect == null || groupSelect.size() == 0)
				this.updateGroups();
			return groupSelect;
		}

		public String getSelectedRole() {
			if(this.getSelectedUser() != null && this.selectedUser.getRole() != null)
				this.selectedRole = this.getSelectedUser().getRole().getName();
			return this.selectedRole;
		}

		public void setSelectedRole(String selectedRole) {
			this.selectedRole = selectedRole;
		}

		public String getSelectedGroup() {
			if(this.getSelectedUser() != null && this.selectedUser.getGroup() != null)
				this.selectedGroup = this.getSelectedUser().getGroup().getName();
			return this.selectedGroup;
		}

		public void setSelectedGroup(String selectedGroup) {
			this.selectedGroup = selectedGroup;
		}

		public String getCurrentPasswordConfirm() {
			if(this.isUserEditMode && this.getSelectedUser() != null)
				this.currentPasswordConfirm = this.getSelectedUser().getPassword();
			
			return currentPasswordConfirm;
		}

		public void setCurrentPasswordConfirm(String currentPasswordConfirm) {
			this.currentPasswordConfirm = currentPasswordConfirm;
		}

		public String getUserFormMessage() {
			return userFormMessage;
		}

		public boolean getShowUserFormMessage() {
			return showUserFormMessage;
		}

		public boolean checkValidation() {
				
			return true;
					//userManagerService.checkValidation();
		}

}