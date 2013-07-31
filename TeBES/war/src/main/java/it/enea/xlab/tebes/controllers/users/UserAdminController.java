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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class UserAdminController extends WebController<User> {

        public static final String CONTROLLER_NAME = "UserAdminController";
    	private static Logger logger = Logger.getLogger(UserAdminController.class);
        
        private UserManagerRemote userManagerBean;

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
        	userManagerBean = JNDIServices.getUserManagerService();
        }

        public void initContext() throws NotBoundException, NamingException {
                userManagerBean = JNDIServices.getUserManagerService();                 
        }
        
        
        // GET USER LIST
        public List<Long> getUserIdList() {  
                return userManagerBean.getUserIdList();
        }
        
        // CREATE -> Equivalente a UserProfileManager.signUp MA ASSEGNA ANCHE UN RUOLO DIVERSO DA QUELLO DI DEFAULT
        /**
         * Registration (CREATE) User
         * @return userId
         */
        public Long createUser(User user, Role role) {

                Long userId = userManagerBean.createUser(user);
                
                if (userId > 0) {
                        user = this.readUser(userId);
                        userManagerBean.setUserRole(user, role);
                }
                return userId;
        }       

        // READ User
        public User readUser(Long id) {
                
                return userManagerBean.readUser(id);
        }
        
        // UPDATE -> UserProfileManager.Update
        
        // UPDATEROLE
        
        // DELETE User
        public Boolean deleteUser(Long id) {
                return userManagerBean.deleteUser(id);
        }

        public Boolean deleteUserByEmail(String email) {
                return userManagerBean.deleteUserByEmail(email);
        }
        
        // GET Role LIST
        public List<Long> getRoleIdList() {
                
                // Se servono tutti i ruoli, 
                // allora creo in questa classe il metodo getRoleList 
                // che utilizza il presente metodo
                
                return userManagerBean.getRoleIdList();
        }
        
        // CREATE Role
        public Long createRole(Role role) {
                return userManagerBean.createRole(role);
        }

        // READ Role
        public Role readRole(Long roleId) {
                return userManagerBean.readRole(roleId);
        }

        // READ Role by level
        public Role readRoleByLevel(int level) {
                return userManagerBean.readRoleByLevel(level);
        }

        public void setUserRole(User user, Role role) {
                userManagerBean.setUserRole(user, role);
        }

        public Boolean deleteRole(Long id) {    
                return userManagerBean.deleteRole(id);
        }

        // CREATE Role
        public Long createGroup(Group group) {
                return userManagerBean.createGroup(group);
        }

        public List<Long> getGroupIdList() {
                return userManagerBean.getGroupIdList();
        }

        public List<Group> getGroupList() {
                return userManagerBean.getGroupList();
        }

        public Group readGroup(Long id) {
                return userManagerBean.readGroup(id);
        }

        public Long setUserGroup(User user, Group group) {
                return userManagerBean.setUserGroup(user, group);
        }

        public Boolean deleteGroup(Long id) {
                return userManagerBean.deleteGroup(id);
        }

        public List<Long> getActionIdList() {
                return userManagerBean.getActionIdList();
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
			userManagerBean.deleteUser(this.getItemToDelete());
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
					Role role = this.userManagerBean.readRole(this.selectedRole);
					if(role != null)
						user.setRole(role);
				}

				if(this.selectedGroup != null && !this.selectedGroup.equals("")) {
					Group group = this.userManagerBean.readGroup(this.selectedGroup);
					if(group != null)
						user.setGroup(group);
				}

				Long result = this.userManagerBean.createUser(user);
				if(result == -1) {
					this.userFormMessage = Messages.FORM_ERROR_USER_ALREADY_EXISTING;
					this.showUserFormMessage = true;
					return "creation_fail";
				} else if(result == -2) {
					this.userFormMessage = Messages.FORM_ERROR_USER_CREATION;
					this.showUserFormMessage = true;
					return "creation_fail";
				}
				
				this.updateDataModel();
				return "creation_success";
			} else {
				this.userFormMessage = message;
				this.showUserFormMessage = true;
				return "creation_fail";
			}

		}
		
		public String updateUser() {
			
			String message = UserUtils.checkUserFields(selectedUser.getName(), selectedUser.getSurname(), selectedUser.geteMail(), selectedUser.getPassword(), currentPasswordConfirm);
			
			if(message == null) {
				
				User user = this.userManagerBean.readUser(this.selectedUser.getId());
				
				if(user == null) {
					this.userFormMessage = Messages.FORM_USER_NOT_EXISTING;
					this.showUserFormMessage = true;
					return "update_fail";
				}
				
				user.setName(selectedUser.getName());
				user.setSurname(selectedUser.getSurname());
				user.seteMail(selectedUser.geteMail());
				user.setPassword(selectedUser.getPassword());

				if(this.selectedRole != null && !this.selectedRole.equals("")) {
					Role role = this.userManagerBean.readRole(this.selectedRole);
					if(role != null)
						user.setRole(role);
				}

				if(this.selectedGroup != null && !this.selectedGroup.equals("")) {
					Group group = this.userManagerBean.readGroup(this.selectedGroup);
					if(group != null)
						user.setGroup(group);
				}

				if(!this.userManagerBean.updateUser(user)) {
					this.userFormMessage = Messages.FORM_ERROR_USER_UPDATE;
					this.showUserFormMessage = true;
					return "update_fail";
				}
				
				this.updateDataModel();
				return "update_success";
			} else {
				this.userFormMessage = message;
				this.showUserFormMessage = true;
				return "update_fail";
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

}