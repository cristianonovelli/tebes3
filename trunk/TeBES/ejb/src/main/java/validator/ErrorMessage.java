package validator;

public class ErrorMessage {
	public static String ERROR = "ERROR";
    public static String WARNING = "WARNING";
    public static String FATAL_ERROR = "FATAL ERROR";
    public static String FAILED_ASSERT = "FAILED ASSERT";
	
    private String errorType;
	private int lineNumber;
	private int colNumber;
	private String description;
	
	
	public ErrorMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getColNumber() {
		return colNumber;
	}
	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public static ErrorMessage buildError(String et, int l, int col, String desc){
		ErrorMessage em = new ErrorMessage();
		em.setColNumber(col);
		em.setDescription(desc);
		em.setErrorType(et);
		em.setLineNumber(l);
		
		return em;
	}
	
	
}
