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
import it.enea.xlab.tebes.utils.Messages;

import java.rmi.NotBoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	private Long selectedTestPlan;
	private Long selectedSUT;
	private List<SelectItem> testPlanSelection; 
	private List<SelectItem> SUTSelection;
	private String sessionFormMessage;
	private boolean showSessionFormMessage;
	private Long selectedSession;
	private String logMessage;
	private boolean isLogEnabled;
	private Session viewCurrentSession;
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

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

	public Long createSession(Long userId, Long sutId, Long testPlanId) {
		
		Long result = sessionManagerBean.check(userId, sutId, testPlanId);
		System.out.println("check: " + result);
		
		
		if (result.intValue()>0)
			return sessionManagerBean.createSession(userId, sutId, testPlanId);
		else
			return new Long(-1);
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
		NestedCriterion userCriterion = new NestedCriterion("user", Restrictions.eq("eMail", FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()));
		criterions.add(userCriterion);
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
			testPlanSelection.add(new SelectItem(testPlan.getId(), testPlan.getName()));
		}
	}
	
	private void updateSUTs() {
		List<SUT> userSUTs = this.getCurrentUser().getSutList();
		SUTSelection = new ArrayList<SelectItem>();
		for (SUT sut : userSUTs) {
			SUTSelection.add(new SelectItem(sut.getId(), sut.getName()));
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
		this.selectedSUT = -1L;
		this.selectedTestPlan = -1L;
	}
	
	public String createSession() {
		this.updateDataModel();
		return "session_creation_success";
	}
	
	public String openCreateSessionView() {
		return "create_session";
	}
	
	public String enableLog() {
		this.isLogEnabled = true;
		startLog();
		return "";
	}
	
	public String startLog() {
		
		for (int i=0 ; i <10 ; i++){
            logMessage += "output <br/>";
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }   
        this.isLogEnabled = false;
        return "";
	}
	
	public String viewSession() {
		if(selectedSession != null)
			this.viewCurrentSession = this.sessionManagerBean.readSession(selectedSession);
		
		return "view_session";
	}
	
	public String createNewSession() {
		if(createSession(getCurrentUser().getId(), selectedSUT, selectedTestPlan) > 0) {
			this.updateDataModel();
			return "session_creation_success";
		}
		else {
			this.showSessionFormMessage = true;
			this.sessionFormMessage = Messages.FORM_SESSION_CREATION_FAIL;
			return "session_creation_fail";
		}
	}

	public Long getSelectedTestPlan() {
		return selectedTestPlan;
	}

	public void setSelectedTestPlan(Long selectedTestPlan) {
		this.selectedTestPlan = selectedTestPlan;
	}

	public Long getSelectedSUT() {
		return selectedSUT;
	}

	public void setSelectedSUT(Long selectedSUT) {
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

	public String getLogMessage() {
		if(logMessage == null)
			logMessage = "logMessage";
		return this.logMessage;
	}

	public boolean getIsLogEnabled() {
		return isLogEnabled;
	}

	public Session getViewCurrentSession() {
		return viewCurrentSession;
	}

	public void setViewCurrentSession(Session viewCurrentSession) {
		this.viewCurrentSession = viewCurrentSession;
	}
}

