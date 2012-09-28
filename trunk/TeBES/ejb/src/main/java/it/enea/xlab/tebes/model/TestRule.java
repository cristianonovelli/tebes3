package it.enea.xlab.tebes.model;

public class TestRule {

	public static final String AND_DELIMITER = " AND ";
	public static final String OR_DELIMITER = " OR ";
	
	private String language;
	private String value;
	private boolean logicConnectorIsPresent;

	
	public TestRule(String language, String value) {
		
		this.setLanguage(language);
		this.setValue(value);
		
		if (value.contains(AND_DELIMITER) || value.contains(OR_DELIMITER)) {
			
			logicConnectorIsPresent = true;
		}
		else
			logicConnectorIsPresent = false;
	}
	
	public boolean isLogicConnectorPresent() {
		
		return this.logicConnectorIsPresent;
	}
	
	public void setLogicConnectorIsPresent(boolean logicConnectorIsPresent) {
		this.logicConnectorIsPresent = logicConnectorIsPresent;
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}

