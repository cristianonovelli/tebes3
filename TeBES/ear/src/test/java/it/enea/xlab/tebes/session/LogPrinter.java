package it.enea.xlab.tebes.session;

import org.apache.log4j.Logger;

public class LogPrinter {

	// Logger
	private static Logger logger = Logger.getLogger(SessionManagerImplITCase.class);
	
	
	public static void before_print1() {
		logger.info("*******************************************");
		logger.info("*** TEST SessionManagerImplITCase START ***");
		logger.info("*******************************************");
		logger.info("");
	}
	
}
