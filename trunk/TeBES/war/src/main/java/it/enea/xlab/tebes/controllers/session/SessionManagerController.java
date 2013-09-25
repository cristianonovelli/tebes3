package it.enea.xlab.tebes.controllers.session;

import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.session.SessionManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.Messages;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class SessionManagerController extends WebController<Session> {

	private static final long serialVersionUID = 1L;

	public static final String CONTROLLER_NAME = "SessionManagerController";
	
	private SessionManagerRemote sessionManagerService;
	private UserManagerRemote userManagerService;
	private TestPlanManagerRemote testPlanManagerService;
	
	private List<Session> sessionsList;
	
	private Long selectedTestPlan;
	private Long selectedSUT;
	private List<SelectItem> testPlanSelection; 
	private List<SelectItem> SUTSelection;
	private String sessionFormMessage;
	private boolean showSessionFormMessage;
	private Long selectedSession;
	private String logMessage;
	private boolean isRunning = false;
	private Session viewCurrentSession;
	
	private UploadedFile uploadedFile;
	private boolean uploadSuccess = false;
	private String uploadMessage;
	private boolean isSessionWaiting = false;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public SessionManagerController() throws NamingException {
		sessionManagerService = JNDIServices.getSessionManagerService();
		userManagerService = JNDIServices.getUserManagerService();
		testPlanManagerService = JNDIServices.getTestPlanManagerService();
	}

	public void initContext() throws NotBoundException, NamingException {
		sessionManagerService = JNDIServices.getSessionManagerService();
	}
	
	public Session getSession(Long sessionId) {
		return sessionManagerService.readSession(sessionId);
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
		
		Long result = sessionManagerService.check(userId, sutId, testPlanId);
		System.out.println("check: " + result);
		
		
		if (result.intValue()>0)
			return sessionManagerService.createSession(userId, sutId, testPlanId);
		else
			return new Long(-1);
	} 

	public Report getReport(Long sessionId) {
		
		return this.getSession(sessionId).getReport();
	}

	public List<Long> getSessionIdList(User superUser) {
		
		return sessionManagerService.getSessionIdList(superUser);
	}

	public Boolean deleteSession(Long id) {
		
		return sessionManagerService.deleteSession(id);
	}

	public Session readSession(Long id) {
		
		return sessionManagerService.readSession(id);
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
		return userManagerService.readUsersByEmail(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()).get(0);
	}
	
	private void updateTestPlans() {
		List<TestPlan> userTestPlans = this.testPlanManagerService.readUserTestPlanList(this.getCurrentUser());
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
		this.isRunning = true;
		
		new Thread() {
			
			@Override
			public void run() {
				super.run();
				for (int i=0 ; i <10 ; i++) {
					logMessage += "output &lt;br /&gt;";
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}	
			
		}.start();
		return "";
	}
	
	public String viewSession() {
		if(selectedSession != null)
			this.viewCurrentSession = this.sessionManagerService.readSession(selectedSession);
		
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

	public String upload() throws IOException{
		try {
			InputStream stream = uploadedFile.getInputStream();
			long size = uploadedFile.getSize();
			byte [] buffer = new byte[(int)size];
			stream.read(buffer, 0, (int)size);
			stream.close();
			uploadSuccess = true;
			
		} catch (Exception ioe) {

			uploadMessage = "Errore nel caricamento del file. Ripetere l'operazione.";
			uploadSuccess = false;
		}
		return "";
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
			logMessage = "console: ";
		return this.logMessage;
	}

	public boolean getIsRunning() {
		if(this.viewCurrentSession != null) {
			if(this.viewCurrentSession.getState().equals(Session.getWorkingState()))
				this.isRunning = true;
			else
				this.isRunning = false;
		}
		
		return isRunning;
	}

	public Session getViewCurrentSession() {
		return viewCurrentSession;
	}

	public void setViewCurrentSession(Session viewCurrentSession) {
		this.viewCurrentSession = viewCurrentSession;
	}
	
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public boolean getUploadSuccess() {
		return uploadSuccess;
	}

	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public String getUploadMessage() {
		return uploadMessage;
	}

	public void setUploadMessage(String uploadMessage) {
		this.uploadMessage = uploadMessage;
	}

	public boolean getIsSessionWaiting() {
		
		if(this.viewCurrentSession != null) {
			if(this.viewCurrentSession.getState().equals("waiting"))
				this.isSessionWaiting = true;
			else
				this.isSessionWaiting = false;
		}
		
		return isSessionWaiting;
	}

	public void setIsSessionWaiting(boolean isSessionWaiting) {
		this.isSessionWaiting = isSessionWaiting;
	}

	public Boolean updateSession(Session session) {
		
		return sessionManagerService.updateSession(session);
	}
	
	public Session runWorkflow(ActionWorkflow workflow, Session session) {
		
		return sessionManagerService.runWorkflow(workflow, session);
	}

	public Session suspendSession(Session session) {
		
		return sessionManagerService.suspendSession(session);
	}

	public Session annulSession(Session session) {
		
		return sessionManagerService.annulSession(session);
	}

	public Session resumeSession(Session session) {
		
		return sessionManagerService.resumeSession(session);
	}
}

