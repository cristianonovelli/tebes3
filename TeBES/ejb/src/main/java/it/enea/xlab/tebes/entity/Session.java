package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	// WORKING (in elaborazione, interrogando altre strtture dati possiamo sapere a che punto siamo)
	// WAITING (in attesa di input dall’utente, interrogando … possiamo sapere cosa dobbiamo fare)
	// SUSPENDED (sospesa dall’utente, può essere ripresa)
	// ABORTED (annullata, può essere riavviata ma da capo generando una nuova sessione)
	// DONE (finito, è possibile esaminare il report finale)
	private static final String WORKING_STATE = "working";
	private static final String WAITING_STATE = "waiting";
	private static final String SUSPENDED_STATE = "suspended";
	private static final String ABORTED_STATE = "aborted";
	private static final String DONE_STATE = "done";


	
	// Sessione corrente o passata
	
	/*
	 *  Una sessione è relativa a:
	 *  1 user (1 user ha 0 o più sessioni)
	 *  1 sut (o più sut)
	 *  1 piano di test
	 *  devo capire se unirli con delle merge o join 
	 *  quando una sessione viene cancellata gli oggetti a cui fa riferimento rimangono
	 *  quando viene cancellato uno user, vengono cancellati i suoi sut, i suoi test plan e le sue sessioni
	 *  QUINDI sessione è "figlio" di user
	 *  MENTRE test plan può essere figlio del sistema oppure figlio di un utente
	 *  quando un utente se lo crea a partire da quello di sistema del test manager ne crea una copia in locale!
	 */
	
	private Long userId;
	private Long testPlanId;
	private Long sutId;
	
	// relazione con l'entity report?
	@OneToOne(cascade=CascadeType.ALL)
	Report report;
	
	
	private String state;

	private String starteDateTime;
	private String lastDateTime;
	private String endDateTime;
	
	@Column(length=9999) 
	private String messageStore;
	

	public Session() {

	}


	// Costruttore
	public Session(Long userId, Long sutId, Long testPlanId) {

		this.userId = userId;
		this.sutId = sutId;
		this.testPlanId = testPlanId;
				
		// Set state to "working"
		this.setState(getWorkingState());
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSutId() {
		return sutId;
	}

	public void setSutId(Long sutId) {
		this.sutId = sutId;
	}

	public Long getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(Long testPlanId) {
		this.testPlanId = testPlanId;
	}

	public String getState() {
		return state;
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


	public String getStarteDateTime() {
		return starteDateTime;
	}


	public void setStarteDateTime(String starteDateTime) {
		this.starteDateTime = starteDateTime;
	}


	public String getLastDateTime() {
		return lastDateTime;
	}


	public void setLastDateTime(String lastDateTime) {
		this.lastDateTime = lastDateTime;
	}


	public String getEndDateTime() {
		return endDateTime;
	}


	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}


	public String getMessageStore() {
		return messageStore;
	}


	public void setMessageStore(String messageStore) {
		this.messageStore = messageStore;
	}


}
