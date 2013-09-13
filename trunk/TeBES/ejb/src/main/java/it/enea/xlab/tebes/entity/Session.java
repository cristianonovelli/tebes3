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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Test Session (Sessione di Test)
 * 
 */
@Entity
public class Session implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	// State
	// NEW (sessione appena creata, il workflow non � ancora stato eseguito)
	// WORKING (in elaborazione, interrogando altre strtture dati possiamo sapere a che punto siamo)
	// WAITING (in attesa di input dall�utente, interrogando � possiamo sapere cosa dobbiamo fare)
	// SUSPENDED (sospesa dall�utente, pu� essere ripresa)
	// ABORTED (annullata, pu� essere riavviata ma da capo generando una nuova sessione)
	// DONE (finito, � possibile esaminare il report finale)
	private static final String NEW_STATE = "new";
	private static final String WORKING_STATE = "working";
	private static final String WAITING_STATE = "waiting";
	private static final String SUSPENDED_STATE = "suspended";
	private static final String ABORTED_STATE = "aborted";
	private static final String DONE_STATE = "done";


	
	// Sessione corrente o passata
	
	/*
	 *  Una sessione � relativa a:
	 *  1 user (1 user ha 0 o pi� sessioni)
	 *  1 sut (o pi� sut)
	 *  1 piano di test
	 *  devo capire se unirli con delle merge o join 
	 *  quando una sessione viene cancellata gli oggetti a cui fa riferimento rimangono
	 *  quando viene cancellato uno user, vengono cancellati i suoi sut, i suoi test plan e le sue sessioni
	 *  QUINDI sessione � "figlio" di user
	 *  MENTRE test plan pu� essere figlio del sistema oppure figlio di un utente
	 *  quando un utente se lo crea a partire da quello di sistema del test manager ne crea una copia in locale!
	 */
	

	@ManyToOne
	private User user;
	
	@ManyToOne
	private TestPlan testPlan;
	
	@ManyToOne
	private SUT sut;
	
	// relazione con l'entity report?
	@OneToOne(cascade=CascadeType.ALL)
	Report report;
	
	
	private String state;
	
	// TODO QUALI SONO I TIPI DI INTERAZIONE CHE RICHIEDE UNA ACTION?
	
	//private String requestInteraction;
	

	private String creationDateTime;
	private String lastUpdateDateTime;
	
	@Column(length=9999) 
	private String messageStore;
	
	
	@OneToMany(mappedBy="session",cascade = {CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UserInteraction> userInteractions;
	

	public Session() {

	}

	// Costruttore
	public Session(User user, TestPlan testPlan, SUT sut) {

		this.user = user;
		this.testPlan = testPlan;
		this.sut = sut;
				
		setUserInteractions(new Vector<UserInteraction>());
		
		// Set state to "new"
		this.setState(getNewState());
	}
	
	
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public static String getNewState() {
		return NEW_STATE;
	}
	
	
	
	public static String getWorkingState() {
		return WORKING_STATE;
	}


	public static String getWaitingState() {
		return WAITING_STATE;
	}


	public static String getSuspendedState() {
		return SUSPENDED_STATE;
	}


	public static String getAbortedState() {
		return ABORTED_STATE;
	}


	public static String getDoneState() {
		return DONE_STATE;
	}


	public void setState(String state) {
		this.state = state;
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

	public List<UserInteraction> getUserInteractions() {
		return userInteractions;
	}

	public void setUserInteractions(List<UserInteraction> userInteractions) {
		this.userInteractions = userInteractions;
	}

	
	public void addUserInteraction(UserInteraction userInteraction) {
		this.userInteractions.add(userInteraction);
	}	

}
