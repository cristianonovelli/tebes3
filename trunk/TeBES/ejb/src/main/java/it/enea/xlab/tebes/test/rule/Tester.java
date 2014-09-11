package it.enea.xlab.tebes.test.rule;

import org.xlab.file.XLabFileManager;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( !XLabFileManager.isFileOrDirectoryPresent("C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/4/ws/report.xml") )
			System.out.println("non esiste");
		else
			System.out.println("ok");

	}

}
