package it.enea.xlab.tebes.external.jolie.gjs;

public class GJSResult {

	private final String RESULT_SUCCESS = "success";
	private final String RESULT_FAULT = "fault";
	private final String RESULT_ERROR = "error";
	
	private String call;
	private String type;
	private String description;
	private String url;

	
	
	public GJSResult() {

		this.call = null;
		this.type = RESULT_ERROR;
		this.description = "GJS Result EMPTY";
		this.url = null;
	}
	
	public boolean isSuccess() {
		if (this.type.equals(RESULT_SUCCESS))
			return true;
		else
			return false;
	}	
	
	public boolean isError() {
		if (this.type.equals(RESULT_ERROR))
			return true;
		else
			return false;
	}	

	public boolean isFault() {
		if (this.type.equals(RESULT_FAULT))
			return true;
		else
			return false;
	}	

	public void setSuccess() {
		this.setDescription("GJS WS-Generator Success for call " + this.getCall());
		this.type = this.RESULT_SUCCESS;
	}	
	
	public void setSuccess(String url) {

		this.type = this.RESULT_SUCCESS;
		this.url = url;
		
		if (this.call.equals(GJS.CLIENT))
			this.setDescription("Success: Client generated and call to WS performed!");
		
		if (this.call.equals(GJS.SERVER))
			this.setDescription("Success: Web Service generated at URL: " + this.getUrl() + " Test your Client!");
	}	

	public void setFault(String description) {
		this.type = this.RESULT_FAULT;
		this.description = description;
	}	

	public void setError(String description) {
		this.type = this.RESULT_ERROR;
		this.description = description;
	}	
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
