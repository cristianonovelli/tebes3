package it.enea.xlab.tebes.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	private final String DONE_STATE = "done";
	private final String SUSPENDED_STATE = "suspended";
	private final String ABORTED_STATE = "aborted";
	private final String WORKING_STATE = "working";
	
	// Sessione corrente o passata
	
	/*
	 *  Una sessione è relativa a:
	 *  1 user
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
	
	private Long reportId;
	private String state;
	
	// Costruttore
	public Session(Long userId, Long sutId, Long testPlanId, Long reportId, String state) {

		this.userId = userId;
		this.sutId = sutId;
		this.testPlanId = testPlanId;
		this.reportId = reportId;
		this.state = state;
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

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getState() {
		return state;
	}

	public void setDoneState() {
		this.state = this.DONE_STATE;
	}
	
	public void setSuspendedState() {
		this.state = this.SUSPENDED_STATE;
	}
	
	public void setAbortedState() {
		this.state = this.ABORTED_STATE;
	}
	
	public void setWorkingState() {
		this.state = this.WORKING_STATE;
	}
}
