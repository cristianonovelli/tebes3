package it.enea.xlab.tebes.controllers.localization;

import java.util.Locale;

import javax.faces.context.FacesContext;

public class LocalizationController {

	private static String locale = "en";

	public void setLocale(String newLocale) {
		LocalizationController.locale = newLocale;
	}

	public synchronized String getLocale() {
		return locale;
	}

	public synchronized String italianLocalization() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(Locale.ITALIAN);
		this.setLocale("it");
		return "changed";
	}

	public synchronized String englishLocalization() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(Locale.ENGLISH);
		this.setLocale("en");
		return "changed";
	}

}
