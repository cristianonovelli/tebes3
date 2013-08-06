package it.enea.xlab.tebes.controllers.session;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.session.SessionManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;

import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class SessionManagerController extends WebController<Session> {

	public static final String CONTROLLER_NAME = "SessionManagerController";
	
	private SessionManagerRemote sessionManagerBean;
	private UserManagerRemote userManagerBean;
	private TestPlanManagerRemote testPlanManagerBean;
	
	private List<Session> sessionsList;
	
	private String selectedTestPlan;
	private String selectedSUT;
	private List<SelectItem> testPlanSelection; 
	private List<SelectItem> SUTSelection;
	private String sessionFormMessage;
	private boolean showSessionFormMessage;
	private Long selectedSession;

	public SessionManagerController() throws NamingException {
		sessionManagerBean = JNDIServices.getSessionManagerService();
		userManagerBean = JNDIServices.getUserManagerService();
		testPlanManagerBean = JNDIServices.getTestPlanManagerService();
	}

	public void initContext() throws NotBoundException, NamingException {
		sessionManagerBean = JNDIServices.getSessionManagerService();
	}
	
	public Session getSession(Long sessionId) {
		return sessionManagerBean.readSession(sessionId);
	}

//	public List<Session> getSessionsList() {
//		if(sessionsList == null)
//			
//		return sessionsList;
//	}

	public void setSessionsList(List<Session> sessionsList) {
		this.sessionsList = sessionsList;
	}
	
/*	public Session reactivateSession(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteSession(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}*/

	public Long run(Long userId, Long sutId, Long testPlanId) {
		
		return sessionManagerBean.run(userId, sutId, testPlanId);
	}

	public Report getReport(Long sessionId) {
		
		return this.getSession(sessionId).getReport();
	}

	public List<Long> getSessionIdList() {
		
		return sessionManagerBean.getSessionIdList();
	}

	public Boolean deleteSession(Long id) {
		
		return sessionManagerBean.deleteSession(id);
	}

	public Session readSession(Long id) {
		
		return sessionManagerBean.readSession(id);
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
		criterions.add(Restrictions.eq("userId", this.getCurrentUser().getId()));
		return criterions;
	}

	@Override
	protected List<Order> determineOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	private User getCurrentUser() {
		return userManagerBean.readUsersByEmail(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()).get(0);
	}
	
	private void updateTestPlans() {
		List<TestPlan> userTestPlans = this.testPlanManagerBean.readUserTestPlanList(this.getCurrentUser());
		testPlanSelection = new ArrayList<SelectItem>();
		for (TestPlan testPlan : userTestPlans) {
			testPlanSelection.add(new SelectItem(testPlan.getName()));
		}
	}
	
	private void updateSUTs() {
		List<SUT> userSUTs = this.getCurrentUser().getSutList();
		SUTSelection = new ArrayList<SelectItem>();
		for (SUT sut : userSUTs) {
			SUTSelection.add(new SelectItem(sut.getName()));
		}
	}
	
	public String cancel() {
		this.resetFields();
		return "backToSessionManager";
	}
	
	public String openCreateSessionViewFromTestPlan() {
		return "create_session";
	}
	
	private void resetFields() {
		this.sessionFormMessage = "";
		this.showSessionFormMessage = false;
		this.selectedSUT = "";
		this.selectedTestPlan = "";
	}
	
	public String createSession() {
		this.updateDataModel();
		return "session_creation_success";
	}
	
	public String openCreateSessionView() {
		return "create_session";
	}
	
	public String getSelectedTestPlan() {
		return selectedTestPlan;
	}

	public void setSelectedTestPlan(String selectedTestPlan) {
		this.selectedTestPlan = selectedTestPlan;
	}

	public String getSelectedSUT() {
		return selectedSUT;
	}

	public void setSelectedSUT(String selectedSUT) {
		this.selectedSUT = selectedSUT;
	}

	public List<SelectItem> getTestPlanSelection() {
		updateTestPlans();
		return testPlanSelection;
	}

	public void setTestPlanSelection(List<SelectItem> testPlanSelection) {
		this.testPlanSelection = testPlanSelection;
	}

	public List<SelectItem> getSUTSelection() {
		if(SUTSelection == null || SUTSelection.size() == 0)
			updateSUTs();
		return SUTSelection;
	}

	public void setSUTSelection(List<SelectItem> sUTSelection) {
		SUTSelection = sUTSelection;
	}

	public String getSessionFormMessage() {
		return sessionFormMessage;
	}

	public boolean getShowSessionFormMessage() {
		return showSessionFormMessage;
	}

	public Long getSelectedSession() {
		return selectedSession;
	}

	public void setSelectedSession(Long selectedSession) {
		this.selectedSession = selectedSession;
	}
}

