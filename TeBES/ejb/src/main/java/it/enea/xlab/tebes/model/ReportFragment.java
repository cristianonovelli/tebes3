package it.enea.xlab.tebes.model;

public class ReportFragment {

	private String label;
	private String message;
	private String description;
	
	
	public ReportFragment(String label, String message, String description) {

		this.label = label;
		this.message = message;
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
