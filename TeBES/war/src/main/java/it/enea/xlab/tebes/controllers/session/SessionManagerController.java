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
import it.enea.xlab.tebes.session.SessionManagerRemote;
import it.enea.xlab.tebes.testplan.TestPlanManagerRemote;
import it.enea.xlab.tebes.users.UserManagerRemote;
import it.enea.xlab.tebes.utils.File;
import it.enea.xlab.tebes.utils.Messages;

import java.io.ByteArrayOutputStream;
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
	private boolean isRunning;
	private Session viewCurrentSession;
	
	private UploadedFile uploadedFile;
	private boolean uploadSuccess = false;
	private String uploadMessage;
	private boolean isSessionWaiting = false;
	private Action currentAction;
	private Input currentInput;
	private String guiMessage;
	private String inputTextMessage;
	private boolean showUploadFileBox = false;
	private boolean showInputTextBox = false;
	private boolean showMessageOkBox = false;

	// FILE UPLOAD
	private ArrayList<File> files = new ArrayList<File>();
    private int uploadsAvailable = 1;
    private boolean autoUpload = true;
    private boolean useFlash = false;
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public SessionManagerController() throws NamingException {
		sessionManagerService = JNDIServices.getSessionManagerService();
		userManagerService = JNDIServices.getUserManagerService();
		testPlanManagerService = JNDIServices.getTestPlanManagerService();
		fileManagerService = JNDIServices.getFileManagerService();
		actionManagerService = JNDIServices.getActionManagerService();
		this.isRunning = false;
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
		return this.logMessage + this.viewCurrentSession.getReport().getXml();
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
	
	public boolean getIsRunning() {
		
		return this.isRunning;
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
		return showUploadFileBox;
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

	public String upload() throws IOException{
		try {
			InputStream stream = uploadedFile.getInputStream();
			byte [] buffer = this.convertInputStreamToByteArray(stream);
			this.uploadFile(this.currentInput, uploadedFile.getName(), this.viewCurrentSession.getSut().getType(), buffer, this.viewCurrentSession);
			uploadSuccess = true;
			
		} catch (Exception ioe) {

			uploadMessage = "Errore nel caricamento del file. Ripetere l'operazione.";
			uploadSuccess = false;
		}
		return "";
	}

	
	public String execute() {
		
		if(this.viewCurrentSession != null) {
			this.isRunning = true;
			this.resetBoxFlags();
			this.viewCurrentSession = this.runWorkflow(this.viewCurrentSession.getTestPlan().getWorkflow(), this.viewCurrentSession);
		}

		return "";
	}

	public boolean getIsSessionWaiting() {
		
		if(this.viewCurrentSession != null) {
			if(this.viewCurrentSession.getState().equals(Session.getWaitingState())) {
				this.isRunning = false;
				this.isSessionWaiting = true;
				this.currentAction = this.viewCurrentSession.getTestPlan().getWorkflow().getCurrentAction();
				
				for (Input input : this.currentAction.getInputs()) {
					if(!input.isInputSolved()) {
						this.currentInput = input;
						this.guiMessage = input.getGuiMessage();
						if(input.getGuiReaction().equals("upload")) {
							this.showUploadFileBox = true;
							this.showInputTextBox = false;
							this.showMessageOkBox = false;
							break;
						} else if(input.getGuiReaction().equals("text")) {
							this.showUploadFileBox = false;
							this.showInputTextBox = true;
							this.showMessageOkBox = false;
							break;
						} else if(input.getGuiReaction().equals("message")) {
							this.showUploadFileBox = false;
							this.showInputTextBox = false;
							this.showMessageOkBox = true;
							break;
						}
					}
				}
				
			} else
				this.isSessionWaiting = false;
		}
		
		return isSessionWaiting;
	}
	
	private void resetBoxFlags() {
		this.showUploadFileBox = false;
		this.showInputTextBox = false;
		this.showMessageOkBox = false;
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
	
	private Session uploadFile(Input input, String fileName, String type, byte[] fileContent, Session session) throws Exception {

		// Aggiorno l'input
		input.setInputSolved(true);
		actionManagerService.updateInput(input);
		
		Action action = input.getAction();
		boolean checking = actionManagerService.checkActionReady(action);
		
		if (checking)
			action.setStateToReady();
		
		Session result = fileManagerService.upload(input.getFileIdRef(), fileName, type, fileContent, session);

		return result;
	}
	
	public void listener(UploadEvent event) throws Exception{
        UploadItem item = event.getUploadItem();
        File file = new File();
        file.setLength(item.getData().length);
        file.setName(item.getFileName());
        file.setData(item.getData());
        files.add(file);
        uploadsAvailable--;
    } 

	public int getSize() {
		if (getFiles().size()>0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public String clearUploadData() {
		files.clear();
		setUploadsAvailable(5);
		return null;
	}

	public long getTimeStamp(){
		return System.currentTimeMillis();
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) { 
		this.files = files;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}
}

