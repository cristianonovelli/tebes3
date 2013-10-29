package it.enea.xlab.tebes.controllers.authentication;

import java.rmi.NotBoundException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import it.enea.xlab.tebes.authentication.AuthenticationManager;
import it.enea.xlab.tebes.common.Constants;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.users.UserManagerRemote;

public class AuthenticationManagerController {

	public static final String CONTROLLER_NAME = "AuthenticationManagerController";
	
	private AuthenticationManager authenticationManager;
	
	private UserManagerRemote userManager;
	
	private User principal;
	
	private boolean isAdmin;
	
	public AuthenticationManagerController() throws Exception {
		
		this.authenticationManager = JNDIServices.getAuthenticationManagerService();
		this.userManager = JNDIServices.getUserManagerService();
	}

	public User getPrincipal() {
		
		if(this.principal == null)
			this.principal = this.authenticationManager.getUser(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
		return principal;
	}

	public void setPrincipal(User principal) {
		this.principal = principal;
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public boolean getIsAdmin() {
		
		if(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() == null || "".equals(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()))
			this.isAdmin = false;
		else {

			if(this.principal == null)
				this.principal = this.authenticationManager.getUser(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

			/*if(Constants.ADMIN_ROLE_NAME.equals(userManager.readRole(principal).getName()))
				this.isAdmin = true;
			else
				this.isAdmin = false;*/
			
			
			if (userManager.readRole(principal).getLevel() >=3)
				this.isAdmin = true;
			else
				this.isAdmin = false;
			
			
		}

		return isAdmin;
	}
	
	public String logout() {
		
		if(this.getRequest().getSession() == null)
			return "logout_fail";
		else {
			this.getRequest().getSession().invalidate();
			return "logout";
		}
	}
	
}
