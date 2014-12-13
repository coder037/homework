package nuhker;

import java.util.logging.*;
// import java.util.logging.Level;
// import java.util.logging.Logger;

import java.io.IOException;

/**
 * 
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *
 */
public class Log {

	private final static String TAB = "\t";

	/**
	 * This class defines a pre-configured and usable logger.
	 * It's grammar is described in dependant class SyslogLikeFormatter
	 * 
	 * @return configured and ready Logger adhering MY preferences 
	 */
	public static Logger standard() {

		Logger log = Logger.getLogger("nuhker"); // it's flat, without any
													// ee.xyz prefix

		// ConsoleHandler (System.err)

		Handler consoleHandler = null;
		Handler fileHandler = null;

		try {
			SyslogLikeFormatter humanWay = new SyslogLikeFormatter();

			// Creating consoleHandler and fileHandler
			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(humanWay);
			// binding handler to LOGGER object
			log.addHandler(consoleHandler);
			// Setting loglevel for this particular handler
			consoleHandler.setLevel(Level.ALL);

			// Defining output file
			fileHandler = new FileHandler("./temporary.log");
			// Assigning handler to it
			log.addHandler(fileHandler);
			// Setting loglevels to this particular handler
			fileHandler.setLevel(Level.ALL);

			// Make CLEAR and EVIDENT what our game rules are
			log.setLevel(Level.ALL);
			log.setUseParentHandlers(false);
			log.config(" Parent logger HAS BEEN SUSPENDED for esthetical reasons");
			log.config(TAB
					+ "do manually switch over to the DEBUG mode to see more");
			// final Statements
			log.config(" Configuration done.");
			String loggerName = log.getName();
			log.info("   Logger Name is : " + loggerName);

			// Should never happen but who knows
		} catch (IOException ex) {
			log.log(Level.SEVERE,
					"Some ERROR occured in FileHandler or (less likely, in Consolehandler).",
					ex);
		}

		boolean test = false;
		if (test) {
			log.finest(" Testing with FINEST  that LOGGING has been properly set up INDEED.");
			log.finer("  Testing with FINER   that LOGGING has been properly set up INDEED.");
			log.fine("   Testing with FINE    that LOGGING has been properly set up INDEED.");
			log.config(" Testing with CONFIG  that LOGGING has been properly set up INDEED.");
			log.info("   Testing with INFO    that LOGGING has been properly set up INDEED.");
			log.warning("Testing with WARNING that LOGGING has been properly set up INDEED.");
			log.severe(" Testing with SEVERE  that LOGGING has been properly set up INDEED.");

			log.info("   ========================= CHANGING THE LOGGER LEVEL NOW to: INFO");
			log.setLevel(Level.INFO);
			log.finest(" Testing with FINEST  that LOGGING has been properly set up INDEED.");
			log.finer("  Testing with FINER   that LOGGING has been properly set up INDEED.");
			log.fine("   Testing with FINE    that LOGGING has been properly set up INDEED.");
			log.config(" Testing with CONFIG  that LOGGING has been properly set up INDEED.");
			log.info("   Testing with INFO    that LOGGING has been properly set up INDEED.");
			log.warning("Testing with WARNING that LOGGING has been properly set up INDEED.");
			log.severe(" Testing with SEVERE  that LOGGING has been properly set up INDEED.");

			log.info("   ========================= CHANGING THE LOGGER LEVEL NOW to: ALL again");
			log.setLevel(Level.ALL);
			log.finest(" Testing with FINEST  that LOGGING has been properly set up INDEED.");
			log.finer("  Testing with FINER   that LOGGING has been properly set up INDEED.");
			log.fine("   Testing with FINE    that LOGGING has been properly set up INDEED.");
			log.config(" Testing with CONFIG  that LOGGING has been properly set up INDEED.");
			log.info("   Testing with INFO    that LOGGING has been properly set up INDEED.");
			log.warning("Testing with WARNING that LOGGING has been properly set up INDEED.");
			log.severe(" Testing with SEVERE  that LOGGING has been properly set up INDEED.");
		}

		log.info(TAB + "1. Logging an INFO-level message");

		// =====================================

		// LIST OF LEVELS:

		// SEVERE (highest level)
		// WARNING
		// INFO
		// CONFIG
		// FINE
		// FINER
		// FINEST (lowest level)
		// -----------------------
		// OFF
		// ALL

		// ==========================CODE NOT USABLE ANYMORE
		// Console handler removed
		// LOGGER.removeHandler(consoleHandler);

		return log;
	}

}
