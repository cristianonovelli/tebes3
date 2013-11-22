package it.enea.xlab.tebes.controllers.users;

import java.io.File;
import java.io.IOException;

import org.xlab.file.XLabFileManager;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		//XLabFileManager.delete("C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/4/"); 
		try {
			delete(new File("C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/4/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void delete(File file) throws IOException {
	 
	    	if (file.isDirectory()) {
	 
	    		// directory is empty, then delete it
	    		if(file.list().length==0) 
	    		   file.delete();
	 
	    		else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if (file.list().length==0)
	           	     file.delete();
	        	   
	    		}
	 
	    	} else
	    		// if file, then delete it
	    		file.delete();
	    	
	    }

}
