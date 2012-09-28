package it.enea.xlab.tebes.model;

public class Variable {

	private String name;
	private String value;
	private String type;
	
	// Constructor
	public Variable(String name, String value, String type) {

		this.setName(name);
		this.setValue(value);
		this.setType(type);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
