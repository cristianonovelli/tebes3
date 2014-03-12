package it.enea.xlab.tebes.utils;

import it.enea.xlab.tebes.controllers.localization.LocalizationController;

public class FormMessages {

	static LocalizationController lc = new LocalizationController();
	
	// Form Messages in ENGLISH 
	private static final String ERROR_NOMATCH_EN = "There isn't match between selected TestPlan and SUT because the types of interaction are different. Specify a different TestPlan-SUT combination OR define a new TestPlan or new SUT before to create the Test Session.";
	private static final String ERROR_DELETE_USER_EN = "User Deleting ERROR!";
	private static final String ERROR_USER_NOT_COMPILED_EN = "Attention: fill every field!";
	private static final String ERROR_USER_PASSWORD_NOT_EQUAL_EN = "Attention: the password isn't the same!";
	private static final String ERROR_USER_ALREADY_EXISTING_EN= "Attention: the user already exists!";
	private static final String ERROR_USER_CREATION_EN = "Attention: user creation error!";
	private static final String USER_CREATION_SUCCESS_EN = "User creation successful!";
	private static final String USER_NOT_EXISTING_EN = "Attention: the user doesn't exist!";	
	private static final String ERROR_USER_UPDATE_EN = "Attention: User account saving successful!";
	private static final String ERROR_USER_EMAIL_ERROR_EN = "Attention: the e-mail address isn't correct!";
	private static final String ERROR_SUT_CREATION_EN = "Attention: SUT creating error!";
	private static final String TEST_PLAN_IMPORT_SUCCESS_EN = "Test Plan importing successful!";
	private static final String TEST_PLAN_IMPORT_FAIL_EN = "Attention: Test Plan importing error!";
	private static final String SESSION_CREATION_FAIL_EN = "Attention: Session creation error!";	
	private static final String ERROR_DELETE_SUT_EN = "Attention: SUT deleting error!";
	
	
	// Messaggi per Form in ITALIANO
	private static final String ERROR_NOMATCH_IT = "Non c'è match tra i TestPlan e SUT selezionati perché i rispettivi tipi di interazioni sono diversi. Specificare quindi una differente combinazione TestPlan-SUT OPPURE definire un nuovo TestPlan o un nuovo SUT prima di creare la Sessione di Test.";	
	private static final String ERROR_DELETE_USER_IT = "Errore nella rimozione dell'utente!";
	private static final String ERROR_USER_NOT_COMPILED_IT = "Attenzione: riempire tutti i campi!";	
	private static final String ERROR_USER_PASSWORD_NOT_EQUAL_IT = "Attenzione: le due password non coincidono!";
	private static final String ERROR_USER_ALREADY_EXISTING_IT = "Attenzione: utente già esistente!";
	private static final String ERROR_USER_CREATION_IT = "Attenzione: errore nella creazione dell'utente.";
	private static final String USER_CREATION_SUCCESS_IT = "Utente creato correttamente!";
	private static final String USER_NOT_EXISTING_IT = "Attenzione: l'utente non esiste!";
	private static final String ERROR_USER_UPDATE_IT = "Attenzione: errore nel salvataggio dell'utente!";
	private static final String ERROR_USER_EMAIL_ERROR_IT = "Attenzione: indirizzo e-mail non corretto!";
	private static final String ERROR_SUT_CREATION_IT = "Attenzione: errore nella creazione del SUT!";
	private static final String TEST_PLAN_IMPORT_SUCCESS_IT = "Test Plan importato correttamente!";
	private static final String TEST_PLAN_IMPORT_FAIL_IT = "Attenzione: errore nell'importazione del Test Plan!";
	private static final String SESSION_CREATION_FAIL_IT = "Attenzione: errore nella creazione della sessione!";	
	private static final String ERROR_DELETE_SUT_IT = "Errore nella rimozione del SUT!";



	public static String getErrorNomatch() {

		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_NOMATCH_EN;	
		else
			return ERROR_NOMATCH_IT;	
	}

	public static String getErrorDeleteUser() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_DELETE_USER_EN;	
		else
			return ERROR_DELETE_USER_IT;
	}

	public static String getErrorDeleteSUT() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_DELETE_SUT_EN;	
		else
			return ERROR_DELETE_SUT_IT;
	}
	
	public static String getErrorUserNotCompiled() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_USER_NOT_COMPILED_EN;	
		else
			return ERROR_USER_NOT_COMPILED_IT;
	}

	public static String getErrorUserPasswordNotEqual() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_USER_PASSWORD_NOT_EQUAL_EN;	
		else
			return ERROR_USER_PASSWORD_NOT_EQUAL_IT;
	}

	public static String getErrorUserAlreadyExisting() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_USER_ALREADY_EXISTING_EN;	
		else
			return ERROR_USER_ALREADY_EXISTING_IT;
	}

	public static String getErrorUserCreation() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_USER_CREATION_EN;	
		else
			return ERROR_USER_CREATION_IT;
	}

	public static String getUserCreationSuccess() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return USER_CREATION_SUCCESS_EN;	
		else
			return USER_CREATION_SUCCESS_IT;
	}

	public static String getUserNotExisting() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return USER_NOT_EXISTING_EN;	
		else
			return USER_NOT_EXISTING_IT;
	}

	public static String getErrorUserUpdate() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_USER_UPDATE_EN;	
		else
		return ERROR_USER_UPDATE_IT;
	}

	public static String getErrorUserEmailError() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_USER_EMAIL_ERROR_EN;	
		else
		return ERROR_USER_EMAIL_ERROR_IT;
	}

	public static String getErrorSutCreation() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return ERROR_SUT_CREATION_EN;	
		else
			return ERROR_SUT_CREATION_IT;
	}

	public static String getTestPlanImportSuccess() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return TEST_PLAN_IMPORT_SUCCESS_EN;	
		else
			return TEST_PLAN_IMPORT_SUCCESS_IT;
	}

	public static String getTestPlanImportFail() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return TEST_PLAN_IMPORT_FAIL_EN;	
		else
			return TEST_PLAN_IMPORT_FAIL_IT;
	}

	public static String getSessionCreationFail() {
		
		if (lc.getLocale().equals(LocalizationController.ENG_LOCALIZATION))
			return SESSION_CREATION_FAIL_EN;	
		else
			return SESSION_CREATION_FAIL_IT;
	}
	
}
