package it.enea.xlab.tebes.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Test Session (Sessione di Test, corrente o passata)
 * è relativa all'esecuzione di un piano di Test e coinvolge:
 *  1 user (1 user ha 0 o più sessioni)
 *  1 sut (o più sut)
 *  1 piano di test
 */
@Entity
@Table(name = "testsession")
public class Session implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	// States:
	// NEW (sessione appena creata, il workflow non è ancora stato eseguito)
	// WORKING (in elaborazione, interrogando altre strtture dati possiamo sapere a che punto siamo)
	// WAITING (in attesa di input dall’utente, interrogando … possiamo sapere cosa dobbiamo fare)
	// SUSPENDED (sospesa dall’utente, può essere ripresa)
	// ABORTED (annullata, può essere riavviata ma da capo generando una nuova sessione)
	// DONE (finito, è possibile esaminare il report finale)
	private static final String NEW_STATE = "new";
	private static final String WORKING_STATE = "working";
	private static final String WAITING_STATE = "waiting";
	private static final String SUSPENDED_STATE = "suspended";
	private static final String CANCELLED_STATE = "cancelled";
	private static final String DONE_STATE = "done";

	private String state;
	

	@ManyToOne
	private User user;
	
	@ManyToOne
	private TestPlan testPlan;
	
	@ManyToOne
	private SUT sut;
	
	// relazione con l'entity report?
	@OneToOne(cascade=CascadeType.ALL)
	Report report;


	private String creationDateTime;
	private String lastUpdateDateTime;
	
	@Column(length=9999) 
	private String messageStore;
	
	
	@OneToMany(mappedBy="session",cascade = {CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<FileStore> files;
	

	// Empty Constructor
	public Session() {

	}

	// Constructor
	public Session(User user, TestPlan testPlan, SUT sut) {

		this.user = user;
		this.testPlan = testPlan;
		this.sut = sut;
		
		files = new Vector<FileStore>();
		
		// Set state to "new"
		this.setStateToNew();
	}
	
	
	
	/////////////////////////////////
	/// STATE Getters and Setters ///
	/////////////////////////////////
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public static String getNewState() {
		return NEW_STATE;
	}
	
	public void setStateToNew() {
		this.setState(NEW_STATE);
	}	

	public boolean isStateNew() {
		
		if ( getState().equals(NEW_STATE) )
			return true;
		else
			return false;
	}
	
	public static String getWorkingState() {
		return WORKING_STATE;
	}
	
	public void setStateToWorking() {
		this.setState(WORKING_STATE);
	}	
	
	public boolean isStateWorking() {
		
		if ( getState().equals(WORKING_STATE) )
			return true;
		else
			return false;
	}
	
	public static String getWaitingState() {
		return WAITING_STATE;
	}
	
	public void setStateToWaiting() {
		this.setState(WAITING_STATE);
	}	

	public boolean isStateWaiting() {
		
		if ( getState().equals(WAITING_STATE) )
			return true;
		else
			return false;
	}
	
	public static String getSuspendedState() {
		return SUSPENDED_STATE;
	}	
	
	public void setStateToSuspended() {
		this.setState(SUSPENDED_STATE);
	}	
	
	public boolean isStateSuspended() {
		
		if ( getState().equals(SUSPENDED_STATE) )
			return true;
		else
			return false;
	}
	
	public static String getCancelledState() {
		return CANCELLED_STATE;
	}
	
	public void setStateToCancelled() {
		this.setState(CANCELLED_STATE);
	}
	
	public boolean isStateCancelled() {
		
		if ( getState().equals(CANCELLED_STATE) )
			return true;
		else
			return false;
	}
	
	public static String getDoneState() {
		return DONE_STATE;
	}	
	
	public void setStateToDone() {
		this.setState(DONE_STATE);
	}	
	
	public boolean isStateDone() {
		
		if ( getState().equals(DONE_STATE) )
			return true;
		else
			return false;
	}
	
	///////////////////////////
	/// Getters and Setters ///
	///////////////////////////
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public String getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(String creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public String getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(String lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getMessageStore() {
		return messageStore;
	}

	public void setMessageStore(String messageStore) {
		this.messageStore = messageStore;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public SUT getSut() {
		return sut;
	}

	public void setSut(SUT sut) {
		this.sut = sut;
	}

	public List<FileStore> getFiles() {
		return files;
	}

	public void setFiles(List<FileStore> files) {
		this.files = files;
	}

}


