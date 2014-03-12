package it.enea.xlab.tebes.controllers.localization;

import java.util.Locale;

import javax.faces.context.FacesContext;

public class LocalizationController {

	public static final String ENG_LOCALIZATION = "en";
	public static final String ITA_LOCALIZATION = "it";
	
	private static String locale = ENG_LOCALIZATION;

	public void setLocale(String newLocale) {
		LocalizationController.locale = newLocale;
	}

	public synchronized String getLocale() {
		return locale;
	}

	public synchronized String italianLocalization() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(Locale.ITALIAN);
		this.setLocale(ITA_LOCALIZATION);
		return "changed";
	}

	public synchronized String englishLocalization() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(Locale.ENGLISH);
		this.setLocale(ENG_LOCALIZATION);
		return "changed";
	}

}
