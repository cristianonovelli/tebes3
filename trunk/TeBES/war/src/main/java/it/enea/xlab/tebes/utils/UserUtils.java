package it.enea.xlab.tebes.utils;

import it.enea.xlab.tebes.controllers.localization.LocalizationController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtils {

	static LocalizationController lc = new LocalizationController();
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static String checkUserFields(String name, String surname, String email, String password, String confirmPassword) {
		
		if(name == null || name.equals("") || surname == null || surname.equals("") || email == null || email.equals("") || 
				password == null || password.equals("") || confirmPassword == null || confirmPassword.equals("")) 
	
			return FormMessages.getErrorUserNotCompiled();	
			
		else if (!password.equals(confirmPassword)) 
			
			return FormMessages.getErrorUserPasswordNotEqual();
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		
		if(!matcher.matches()) 
			return FormMessages.getErrorUserEmailError();
		
		return null;
	}
	
	public static String checkSUTFields() {
		
		return null;
	}

	public static String checkGroupFields(String name, String description) {
		
		if (name == null || name.equals("") || description == null || description.equals("") )
			return FormMessages.getErrorGroupNotCompiled();	
		
		return null;
	}
	
}
