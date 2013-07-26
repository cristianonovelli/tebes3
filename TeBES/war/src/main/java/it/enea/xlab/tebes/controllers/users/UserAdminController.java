package it.enea.xlab.tebes.controllers.users;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.Group;
import it.enea.xlab.tebes.entity.Role;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.rmi.NotBoundException;
import java.util.List;

import javax.naming.NamingException;

public class UserAdminController extends WebController {

        public static final String CONTROLLER_NAME = "UserAdminController";
        
        private UserManagerRemote userManagerBean;
        
        
        public UserAdminController() {
        
        }

        public void initContext() throws NotBoundException, NamingException {
                
                // GET SERVICE
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
        
/*      public User readUserbyEmailAndPassword(String email) {
                
                return userManagerBean.readUserbyEmailAndPassword(userEmail, userPassword)
        }*/
        
        
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





}