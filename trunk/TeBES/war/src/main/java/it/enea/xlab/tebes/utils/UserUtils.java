package it.enea.xlab.tebes.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtils {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static String checkUserFields(String name, String surname, String email, String password, String confirmPassword) {
		
		if(name == null || name.equals("") || surname == null || surname.equals("") || email == null || email.equals("") || 
				password == null || password.equals("") || confirmPassword == null || confirmPassword.equals("")) {
			
			return Messages.FORM_ERROR_USER_NOT_COMPILED;
			
		} else if (!password.equals(confirmPassword)) {
			
			return Messages.FORM_ERROR_USER_PASSWORD_NOT_EQUAL;
		} 
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		
		if(!matcher.matches()) {
			return Messages.FORM_ERROR_USER_EMAIL_ERROR;
		}
		
		return null;
	}
	
}
