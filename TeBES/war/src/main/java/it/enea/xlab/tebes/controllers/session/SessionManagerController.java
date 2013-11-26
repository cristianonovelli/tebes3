package it.enea.xlab.tebes.controllers.session;

import it.enea.xlab.tebes.action.ActionManagerRemote;
import it.enea.xlab.tebes.common.JNDIServices;
import it.enea.xlab.tebes.controllers.common.WebController;
import it.enea.xlab.tebes.controllers.file.FileManagerController;
import it.enea.xlab.tebes.dao.NestedCriterion;
import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.ActionWorkflow;
import it.enea.xlab.tebes.entity.Input;
import it.enea.xlab.tebes.entity.Report;
import it.enea.xlab.tebes.entity.SUT;
import it.enea.xlab.tebes.entity.Session;
import it.enea.xlab.tebes.entity.TestPlan;
import it.enea.xlab.tebes.entity.User;
import it.enea.xlab.tebes.file.FileManagerRemote;
import it.enea.xlab.tebes.report.ReportManagerRemote;
import it.enea.xlab.tebes.session.SessionManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.Messages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

public class SessionManagerController extends WebController<Session> {

	private static final long serialVersionUID = 1L;

	public static final String CONTROLLER_NAME = "SessionManagerController";
	
	private SessionManagerRemote sessionManagerService;
	private UserManagerRemote userManagerService;
	private TestPlanManagerRemote testPlanManagerService;
	private FileManagerRemote fileManagerService;
	private ActionManagerRemote actionManagerService;
	
	private List<Session> sessionsList;
	
	private Long selectedTestPlan;
	private Long selectedSUT;
	private List<SelectItem> testPlanSelection; 
	private List<SelectItem> SUTSelection;
	private String sessionFormMessage;
	private boolean showSessionFormMessage;
	private Long selectedSession;
	private String logMessage;
	private boolean isPollerRunning;
	private Session viewCurrentSession;
	
	private boolean uploadSuccess = false;
	private String uploadMessage;
	private boolean isSessionWaiting = false;
	private boolean isSessionWorking = false;
	private Action currentAction;
	private Input currentInput;
	private String guiMessage;
	private String inputTextMessage;
	private boolean showUploadFileBox = false;
	private boolean showInputTextBox = false;
	private boolean showMessageOkBox = false;
	private boolean showMessageBox = false;
	private String sessionState;
	private boolean canBeExecuted = false;
	private boolean canBeStopped = false;
	private boolean canBeRestarted = false;
	private boolean isBackEnabled = false;

	// FILE UPLOAD
    private Map<String, File> uploadedFiles = new LinkedHashMap<String, File>();
    private int uploadsAvailable = 1;
    private String currentFileName;
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public SessionManagerController() throws NamingException {
		sessionManagerService = JNDIServices.getSessionManagerService();
		userManagerService = JNDIServices.getUserManagerService();
		testPlanManagerService = JNDIServices.getTestPlanManagerService();
		fileManagerService = JNDIServices.getFileManagerService();
		actionManagerService = JNDIServices.getActionManagerService();
		this.isPollerRunning = false;
		this.canBeExecuted = true;
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
			return result;
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
	
	public String viewSession() {
		if(selectedSession != null)
			this.viewCurrentSession = this.sessionManagerService.readSession(selectedSession);
		
		return "view_session";
	}
	
	public String createNewSession() {
		
		Long sessionId = createSession(getCurrentUser().getId(), selectedSUT, selectedTestPlan);
		
		if (sessionId.intValue() > 0) {
			this.updateDataModel();
			return "session_creation_success";
		}
		else {
			
			
			/** 
			 * CODIFICA DEL VALORE DI RITORNO
			 * CHECK
			 * @return	 1 if OK
			 * 			-1 one or more ID isn't a valid identifier
			 * 			-2 there isn't match testplan-sut of user
			 * 			-3 there is match testplan-sut of user but it isn't supported in tebes
			 * 
			 * CREATE SESSION	 
			 * -4 an unexpected exception happened
			 * -5 report null
			 * sessionId if ok
			 */
			this.showSessionFormMessage = true;
			
			if (sessionId.intValue() == -2)
				this.sessionFormMessage = "There isn't match between TestPlan and SUT. Specify a different TestPlan-SUT combination OR define a new TestPlan or SUT before to create a new Sesion Test.";
			else
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
			logMessage = "console: ";
		return this.logMessage + this.viewCurrentSession.getReport().getFullDescription();
	}

//	public boolean getIsRunning() {
//		if(this.viewCurrentSession != null) {
//			if(this.viewCurrentSession.getState().equals(Session.getWorkingState()))
//				this.isRunning = true;
//			else
//				this.isRunning = false;
//		}
//		
//		return isRunning;
//	}
	
	public boolean getIsPollerRunning() {
		if(this.viewCurrentSession.getState().equals(Session.getDoneState()) || this.viewCurrentSession.getState().equals(Session.getWaitingState())) {
			this.isPollerRunning = false;
			return false;
		}
		else {
			this.isPollerRunning = true;
			return true;
		}
			
	}

	public Session getViewCurrentSession() {
		return viewCurrentSession;
	}

	public void setViewCurrentSession(Session viewCurrentSession) {
		this.viewCurrentSession = viewCurrentSession;
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

	public String getGuiMessage() {
		return guiMessage;
	}

	public void setGuiMessage(String guiMessage) {
		this.guiMessage = guiMessage;
	}

	public String getInputTextMessage() {
		return inputTextMessage;
	}

	public void setInputTextMessage(String inputTextMessage) {
		this.inputTextMessage = inputTextMessage;
	}

	public void setIsSessionWaiting(boolean isSessionWaiting) {
		this.isSessionWaiting = isSessionWaiting;
	}

	public boolean getShowUploadFileBox() {
		return this.showUploadFileBox;
	}

	public void setShowUploadFileBox(boolean showUploadFileBox) {
		this.showUploadFileBox = showUploadFileBox;
	}

	public boolean getShowInputTextBox() {
		return showInputTextBox;
	}

	public void setShowInputTextBox(boolean showInputTextBox) {
		this.showInputTextBox = showInputTextBox;
	}

	public boolean getShowMessageOkBox() {
		return showMessageOkBox;
	}

	public void setShowMessageOkBox(boolean showMessageOkBox) {
		this.showMessageOkBox = showMessageOkBox;
	}

	public Input getCurrentInput() {
		return currentInput;
	}

	public void setCurrentInput(Input currentInput) {
		this.currentInput = currentInput;
	}

	public String getSessionState() {
		this.viewCurrentSession = this.sessionManagerService.readSession(selectedSession);
		this.sessionState = this.viewCurrentSession.getState();
		return sessionState;
	}

	public void setSessionState(String sessionState) {
		this.sessionState = sessionState;
	}

	public boolean getCanBeExecuted() {
		if(this.viewCurrentSession.getState().equals(Session.getDoneState()))
			this.canBeExecuted = false;
		return canBeExecuted;
	}

	public void setCanBeExecuted(boolean canBeExecuted) {
		this.canBeExecuted = canBeExecuted;
	}

	public boolean getCanBeStopped() {
		if(this.viewCurrentSession.getState().equals(Session.getDoneState()))
			this.canBeStopped = false;
		return canBeStopped;
	}

	public void setCanBeStopped(boolean canBeStopped) {
		this.canBeStopped = canBeStopped;
	}

	public boolean getCanBeRestarted() {
		if(this.viewCurrentSession.getState().equals(Session.getDoneState()))
			this.canBeRestarted = false;
		return canBeRestarted;
	}

	public void setCanBeRestarted(boolean canBeRestarted) {
		this.canBeRestarted = canBeRestarted;
	}

	public boolean getIsSessionWorking() {
		
		if(this.getSessionState().equals(Session.getWorkingState()))
			this.isSessionWorking = true;
		else
			this.isSessionWorking = false;
		return isSessionWorking;
	}

	public boolean getIsBackEnabled() {
		if(this.getSessionState().equals(Session.getDoneState()))
			this.isBackEnabled = true;
		else
			this.isBackEnabled = false;
		return isBackEnabled;
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
	
	public String back() {
		return "back";
	}
	
	public String suspend() {
		this.viewCurrentSession = this.suspendSession(this.viewCurrentSession);
		this.canBeExecuted = false;
		this.canBeStopped = false;
		this.canBeRestarted = true;
		return "";
	}
	
	public String execute() {
		
		if(this.viewCurrentSession != null) {
			this.isPollerRunning = true;
			
			//this.resetBoxFlags();
			this.viewCurrentSession = this.runWorkflow(this.viewCurrentSession.getTestPlan().getWorkflow(), this.viewCurrentSession);
			this.currentAction = this.viewCurrentSession.getTestPlan().getWorkflow().getCurrentAction();
			
		}

		return "";
	}

	public boolean getIsSessionWaiting() {
		
		if(this.viewCurrentSession != null) {
			if(this.viewCurrentSession.getState().equals(Session.getWaitingState())) {
				//this.isPollerRunning = false;
				this.isSessionWaiting = true;
				
				this.currentAction = this.viewCurrentSession.getTestPlan().getWorkflow().getCurrentAction();
				System.out.println("TEBES DEBUG - CURRENT ACTION: "+currentAction.getActionName()+", STATE: "+currentAction.getState()+ ", TOTAL INPUTS: "+currentAction.getInputs().size());
				boolean found = false;
				
				for (Input input : this.currentAction.getInputs()) {
					if(!input.isInputSolved()) {
						System.out.println("TEBES DEBUG - INPUT: "+input.getName()+" not solved. TYPE: "+input.getGuiReaction());
						found = true;
						this.canBeExecuted = false;
						this.currentInput = input;
						this.guiMessage = input.getGuiMessage();
						if(input.getGuiReaction().equals("upload")) {
							this.showUploadFileBox = true;
							this.showInputTextBox = false;
							this.showMessageOkBox = false;
							this.showMessageBox = false;
							break;
						} else if(input.getGuiReaction().equals("text")) {
							this.showUploadFileBox = false;
							this.showInputTextBox = true;
							this.showMessageOkBox = false;
							this.showMessageBox = false;
							break;
						} else if(input.getGuiReaction().equals("message")) {
							this.showUploadFileBox = false;
							this.showInputTextBox = false;
							this.showMessageOkBox = true;
							this.showMessageBox = false;
							break;
						}
					}
				}
				
				if(!found) {
					System.out.println("TEBES DEBUG - ALL INPUTS SOLVED for action "+currentAction.getActionName()+", resetting box flags.");
					this.resetBoxFlags();
					this.isPollerRunning = true;
					this.isSessionWaiting = false;
					this.canBeExecuted = true;
					this.canBeStopped = false;
					this.canBeRestarted = false;
					
					this.guiMessage = "Tutti gli input richiesti dalla Action "+this.currentAction.getActionName()+" sono stati forniti. Premi ESEGUI per eseguire la Action.";
					this.showMessageBox = true;
					
				}
				
			} else if(this.viewCurrentSession.getState().equals(Session.getWorkingState())) {
				System.out.println("TEBES DEBUG - SESSION IN WORKING STATE");
				this.guiMessage = "Action "+this.currentAction.getActionName()+" conclusa correttamente. Premi ESEGUI per riprendere il workflow con la successiva Action.";
				this.showMessageBox = true;
				//this.isPollerRunning = false;
				//this.execute();
			}
		}
		
		return isSessionWaiting;
	}
	
	private void resetBoxFlags() {
		this.showUploadFileBox = false;
		this.showInputTextBox = false;
		this.showMessageOkBox = false;
		this.showMessageBox = false;
	} 
	
	private byte[] convertInputStreamToByteArray(InputStream is) throws IOException { 
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		
		int reads = is.read(); 
		while(reads != -1){ 
			baos.write(reads); 
			reads = is.read(); 
		} 
		
		return baos.toByteArray(); 
		
	}

	public String upload() throws IOException{
		try {
			InputStream stream = new FileInputStream(this.uploadedFiles.get(this.currentFileName));
			byte [] buffer = this.convertInputStreamToByteArray(stream);
			this.viewCurrentSession = this.uploadFile(this.currentInput, this.currentFileName, this.viewCurrentSession.getSut().getType(), buffer, this.viewCurrentSession);
			uploadSuccess = true;
			this.isPollerRunning = true;
			this.clearUploadedFile(null);
			
		} catch (Exception ioe) {

			uploadMessage = "Errore nel caricamento del file. Ripetere l'operazione.";
			uploadSuccess = false;
		}
		return "";
	}
	
	private Session uploadFile(Input input, String fileName, String type, byte[] fileContent, Session session) throws Exception {

		// Aggiorno l'input
		input.setInputSolved(true);
		actionManagerService.updateInput(input);
		
		Action action = input.getAction();
		boolean checking = actionManagerService.checkActionReady(action);
		
		if (checking)
			action.setStateToReady();
		
		Session result = fileManagerService.upload(input.getFileIdRef(), fileName, type, fileContent, session);
		
		/*if (action.isStateReady()) {
			this.guiMessage = "Action "+action.getActionName()+" conclusa. Premi ESEGUI per riprendere il workflow.";
			this.showMessageOkBox = true;
		}*/

		return result;
	}
	
	public void fileUploadListener(UploadEvent event) throws Exception{
        UploadItem item = event.getUploadItem();
        this.uploadedFiles.put(item.getFileName(), item.getFile());
        this.currentFileName = item.getFileName();
        uploadsAvailable--;
    } 

	public void clearUploadedFile(ActionEvent event) {
		this.uploadedFiles.clear();
		this.currentFileName = "";
		setUploadsAvailable(1);
	}

	public int getSize() {
		if (this.uploadedFiles.size()>0) {
			return this.uploadedFiles.size();
		} else {
			return 0;
		}
	}

	public long getTimeStamp(){
		return System.currentTimeMillis();
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public String getReportURL(Session session) {
		
		return sessionManagerService.getReportURL(session);
	}

	public Action getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(Action currentAction) {
		this.currentAction = currentAction;
	}

	public boolean isShowMessageBox() {
		return showMessageBox;
	}

	public void setShowMessageBox(boolean showMessageBox) {
		this.showMessageBox = showMessageBox;
	}
}

