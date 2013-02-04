package it.enea.xlab.tebes.model;

public class Target {

	
	private String value;
	private String type;
	private String lg;
	private String idscheme;
	
	// Constructors
	public Target() {

	}
	public Target(String value, String type, String lg, String idscheme) {

		this.value = value;
		this.type = type;
		this.lg = lg;
		this.idscheme = idscheme;
	}
	

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLg() {
		return lg;
	}
	public void setLg(String lg) {
		this.lg = lg;
	}
	public String getIdscheme() {
		return idscheme;
	}
	public void setIdscheme(String idscheme) {
		this.idscheme = idscheme;
	}


		
}
