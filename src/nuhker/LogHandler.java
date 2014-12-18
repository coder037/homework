/*
 *  My choice of the "copyright" is the full copyleft.
 *  What it means?
 *  I sincerely believe that the humanity is the whole and
 *  the information flow is to be free. If otherwise, not
 *  these people will take the power and enslave the knowledge.
 *  
 *  Disregarding what I personally believe, I hereby make three statements:
 *     1) everyone's code is worth citing (i.e. I cite foreign code)
 *     2) I will certainly not offer anybody's else code as of mine
 *     3) I do not intend make money with this code
 *         (because of if otherwise, other ethics might apply)
 *         
 * Disclaimer: I refuse to accept the copyright as the weapon to limit
 *  the knowledge. Provided we speak about the sole knowledge and not
 *  making money with it, I refuse to recognize anybody's right to
 *  publish the code, and then cover it by some "copyright" prohibiting
 *  the re-distribution. If so - DO NOT publish it. However, should
 *  readers earn with the help of that code, things might be different.
 */

package nuhker;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * From here we initialize our Java logger. created: Dec 14, 2014 11:10:05 PM
 * 
 * @author coder037@xyz.ee
 */
public class LogHandler {

	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());
	private final static String TAB = "\t";

	public static void setUpOnce(String logLevel) {
		logLevel = logLevel.toUpperCase();

		Handler consoleHandler = null;
		Handler fileHandler = null;

		try {
			SyslogLikeFormatter humanWay = new SyslogLikeFormatter();

			// Set LOGGER level first, handlers' levels later
			LOG.setLevel(Level.parse(logLevel));

			// ====== Creating consoleHandler
			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(humanWay);

			// binding handler to our LOGGER object
			LOG.addHandler(consoleHandler);
			// Setting loglevel for this particular handler
			LOG.severe(" The loglevel should be: " + logLevel);
			// consoleHandler.setLevel(Level.parse(logLevel));
			consoleHandler.setLevel(Level.parse(logLevel));
			Level such = consoleHandler.getLevel();
			String currentLevel = such.toString();
			LOG.severe(" The loglevel actually is: " + currentLevel);

			// ====== Creating fileHandler
			fileHandler = new FileHandler("./nuhker-DEBUG.xml");
			// Assigning handler to our particular LOGGER
			LOG.addHandler(fileHandler);
			// Setting loglevel to this particular handler
			fileHandler.setLevel(Level.parse(logLevel));

			LOG.setUseParentHandlers(false);
			LOG.config(" Parent logger HAS BEEN SUSPENDED for esthetical reasons");
			LOG.config(TAB + "do turn to the FINER resolutions to see more");
			// final Statements
			LOG.config(" Logger configuration done.");
			String loggerName = LOG.getName();
			LOG.info("   Logger Name is : " + loggerName);
		}
		// Should never happen but we never know
		catch (IOException ex) {
			LOG.log(Level.SEVERE,
					"Some ERROR occured in FileHandler or (less likely, in Consolehandler).",
					ex);
		}
	}
}