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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.EnumSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.security.MessageDigest;


/**
 * 
 * No multi-threading at all. This is the runnable class intended to be launched
 * from CLI
 * 
 * It takes CLI Grammar, stores it in a data class and then calls
 * DataDiver.main(DefaultParms modified) to do the actual work
 * 
 * @created Nov 23, 2014 12:44:46 AM
 * @author coder037@xyz.ee
 * @version 0.8 so far
 */
public class Run {

	private final static String VERSION = "0.8";
	private final static String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	
	
	
	
	public static void setLogger() {
		
		// ConsoleHandler (System.err)

		Handler consoleHandler = null;
		Handler fileHandler = null;

		try {
			SyslogLikeFormatter humanWay = new SyslogLikeFormatter();

			// Creating consoleHandler and fileHandler
			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(humanWay);
			// binding handler to LOGGER object	
			LOG.addHandler(consoleHandler);
			// Setting loglevel for this particular handler
			consoleHandler.setLevel(Level.ALL);

			// Defining output file
			fileHandler = new FileHandler("./temporary.log");
			// Assigning handler to it
			LOG.addHandler(fileHandler);
			// Setting loglevels to this particular handler
			fileHandler.setLevel(Level.ALL);

			// Make CLEAR and EVIDENT what our game rules are
			LOG.setLevel(Level.ALL);
			LOG.setUseParentHandlers(false);
			LOG.config(" Parent logger HAS BEEN SUSPENDED for esthetical reasons");
			LOG.config(TAB
					+ "do manually switch over to the DEBUG mode to see more");
			// final Statements
			LOG.config(" Logger configuration done.");
			String loggerName = LOG.getName();
			LOG.info("   Logger Name is : " + loggerName);
		}
		// Should never happen but who know
		catch (IOException ex) {
		LOG.log(Level.SEVERE,
				"Some ERROR occured in FileHandler or (less likely, in Consolehandler).",
				ex);
	}

	}
	
	
	
	
	
	
	
	
	
	
	
	// Here we first parse the argv line to be sure it is parsable nuff

	/**
	 * Depends on the jopt-simple library Makes a copy of the CLIGrammar and
	 * then validates CLI options. No attempt is made to understand the options
	 * - this is the preliminar pass. It it seems to the library that options
	 * are correct and adhere to the Grammar, then returns Boolean true,
	 * otherwise false.
	 * 
	 * @param a copy of CLI option to be validated
	 * @return boolean decision whether the conformancy was true or false
	 */
	public static boolean checkConformity(String[] arguments) {
		// Logger log = Log.standard();
		OptionSet args = null; // declaration separated due to subsequent TRY
								// clause
		OptionParser preParser = CLIGrammar.main();
		LOG.info("===~ START: Option Conformity");
		// ("   ===* Option Conformity Check");

		System.out.println("===* Option Conformity Check");
		// ==== WARNING, NEXT 25 lines are not considered fully mine,
		// but a neat trick from
		// https://github.com/martinpaljak/GlobalPlatform/blob/master/src/openkms/gp/GPTool.java
		// lines 133-147
		try {
			args = preParser.parse(arguments);
			// Try to fetch all values so that their format is checked before
			// actual usage
			for (String s : preParser.recognizedOptions().keySet()) {
				args.valuesOf(s);
			}
		} catch (OptionException e) {
			if (e.getCause() != null) {
				System.err.println("PARSE ERROR discovered, REASON: ");
				System.err.println(TAB + e.getMessage() + ": "
						+ e.getCause().getMessage());
			} else {
				System.err
						.println("PARSE ERROR discovered with UNEXPLAINED reason: ");
				System.err.println(TAB + e.getMessage());
			}
			System.err.println();
			// preParser.printHelpOn(System.err);
			// ==== END of foreign code
			System.out.println("ERROR: Non-conformant options. Bailout");
			System.exit(1);
		}
		// END of somebody's else code.
		System.out.println("===~ DONE: Option Conformity");
		return true; // We reached this point, thus no bailout
	}




	/**
	 * This is the most important method of the class. IT parses CLI options
	 * depending on the jopt-simple library and a predefined in class
	 * DefaultParms options.
	 * 
	 * @param arguments
	 *            to be parsed as options, taken straight from CLI
	 * @return an object Runtimes of type DefaultParameter that all DataDiver
	 *         recursions will be called with
	 * @throws Exception
	 *             which has left mostly unhandled. The reason - a PoC code.
	 */
	public static DefaultParms parseContent(String[] arguments)
			throws Exception {
		OptionParser postParser = CLIGrammar.main();
		OptionSet cliOptions = postParser.parse(arguments);

		DefaultParms RunTimes = new DefaultParms();
		System.out.println();
		System.out.println("===-( CLI Option Parser");

		// Somewhat special options FIRST

		if (cliOptions.has("d")) {
			System.out.println(TAB + "Option d was found");
			String optionValue = String.valueOf(cliOptions.valueOf("d"));
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			// Set the global DebugLevel from here
			System.out.println(TAB + "Setting current loglevel value as: "
					+ optionValue);
			RunTimes.setDebugLevel(Integer.parseInt(optionValue));
		} else {
			// or the DebugLevel remains whatever the default is
		}

		if (cliOptions.has("help")) {
			postParser.printHelpOn(System.out);
			System.exit(0);
		}

		if (cliOptions.has("v")) {
			System.out.println(TAB + "Nuhker version: " + VERSION);
			System.exit(0);
		}

		if (cliOptions.has("c")) {
			System.out.println(TAB + "Option c was found");
			String optionValue = (String) cliOptions.valueOf("c");
			if (CC.cc.isKosher(optionValue)) {
				System.out.println(TAB + TAB + "CountryCode is kosher: "
						+ optionValue);
			} else {
				System.err.println(TAB + TAB + "Man, I deeply doubt *"
						+ optionValue + "* is a CountryCode RIPE is aware of.");
				System.err.println("BailOut");
				System.exit(1);
			}

		}

		if (cliOptions.has("x")) {
			System.out.println(TAB
					+ "Option x was found which isn't yet implemented.");
			System.out.println(TAB + TAB + "Anyway, thnx for supporting it!");
		}

		// Special copyright copulation

		if (cliOptions.has("C")) {
			System.out.println(TAB
					+ "Option C was called to expose the copyright message");
			String optionValue = (String) cliOptions.valueOf("C");
			System.out.println(TAB + TAB + "and it had a sub-option: "
					+ optionValue);

			System.out.println(Func.checkTheAuthorship(optionValue));
			System.out
					.println(TAB
							+ "###############################################################################");
			System.out
					.println(TAB
							+ "# Small portions of foreign copyleft noted as such in code, expressis verbis. #");
			System.out
					.println(TAB
							+ "###############################################################################");
			System.out.println("===-) END of Option Parser");
		}

		// More generic options (coefficients / parameters) to the runtime

		System.out.println("===-( Option Parser phase 2");
		if (cliOptions.has("M")) {
			System.out.println(TAB + "Option M was found");
			String optionValue = String.valueOf(cliOptions.valueOf("M"));
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			System.out.println(TAB + "Setting MaxRunTime value as: "
					+ optionValue + " secs.");
			RunTimes.setMaxTimeToRunBeforeKilled(Integer.parseInt(optionValue) * 1000); // DONE!!!
			System.out.println(TAB + "Have set MaxRunTime value as: "
					+ RunTimes.getMaxTimeToRunBeforeKilled() + " msecs.");
			// Inspiration to convert by means of Integer.parseInt from reznic
			// System.out.println(TAB + "Our Epoch started at: " +
			// FinalOptions.getStartTime());
			// http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java
		}

		if (cliOptions.has("R")) {
			System.out.println(TAB + "Option R was found");
			String optionValue = String.valueOf(cliOptions.valueOf("R"));
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			// Set recursion max level
			System.out.println(TAB + "Setting Max Recursion depth as : "
					+ optionValue);
			RunTimes.setDepthOfRecursion(Integer.parseInt(optionValue));
			// Set relative level
			System.out.println(TAB
					+ "Setting Current Recursion level the same: "
					+ optionValue);
			RunTimes.setCurrentLevelOfRecursion(Integer.parseInt(optionValue));

		}

		if (cliOptions.has("t")) {
			System.out.println(TAB + "Option t was found");
			String optionValue = String.valueOf(cliOptions.valueOf("t"));
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			// set timeout
			System.out.println(TAB + "Setting GSB mandatory timeout >=: "
					+ optionValue + " msec.");
			RunTimes.setMinTimeBetweenGSBRequests(Integer.parseInt(optionValue));

		}

		if (cliOptions.has("o")) {
			System.out.println(TAB + "Option o was found");
			String optionValue = (String) cliOptions.valueOf("o");
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			// set filename
			System.out.println(TAB + "Setting Base Filename as requested: "
					+ optionValue);
			RunTimes.setFilenameForOutput(optionValue);
		}

		System.out.println("===-) END of Option Parser, phase 2");
		System.out.println(TAB
				+ "options init DONE according to the CLI values.");
		return RunTimes;
	}

	/**
	 * The main routine Takes CLI options from the command line argv and passes
	 * these. Has some commented out Strings to simulate and debug various CLI
	 * options.
	 * 
	 * @param argv
	 *            CLI options passed to other methods
	 * @throws Exception
	 *             which left properly unhandled - b/c this is a PoC code.
	 */
	public static void main(String[] argv) throws Exception {
		long firstVariable = System.nanoTime();

		// Logger log = Logger.getLogger("nuhker");
		// Logger log = Log.standard();
		
		LOG.config("   0--------={Start}=--------0");
		
		// Alternatives for simulation (until we build the static CLI program)
		String[] simulation1 = { "--country", "EE", "--copyright", "Some Name",
				"--xtra", "-o", "output", "-R", "8", "-t", "2400", "-d", "7",
				"-M", "43200" };
		String[] simulation2 = { "-c", "EE", "-d", "5", "-M", "80000", "-n",
				"-o", "somefilename-001", "-R", "6", "-t", "2800" };
		String[] simulation3 = { "--help" };
		String[] effectiveOptions = simulation1; // vs argv

		// Formal check of command line options syntax
		checkConformity(effectiveOptions); // Else bailout
		// Parse RIPE for that country
		DefaultParms FinalOptions = parseContent(effectiveOptions);
		FinalOptions.setStartTime(firstVariable); // Start our timer

		setLogger();
		// printout of ACTUAL options
		LOG.info("===================  M A I N ==============");
		LOG.info("===================  Options assessed");
		LOG.config("~~~~~~~~ " + "Our Epoch started at: "
				+ FinalOptions.getStartTime());
		LOG.fine(TAB + "Debuglevel has been set as: "
				+ FinalOptions.getDebugLevel() + " of max 7");
		LOG.fine(TAB + "We seek for Malwarized Sites in country: "
				+ FinalOptions.getCountryCodeToWorkWith());
		LOG.fine(TAB + "Recursion depth has been limited to: "
				+ FinalOptions.getDepthOfRecursion());
		LOG.fine(TAB + "Time allocated for the run is: "
				+ FinalOptions.getMaxTimeToRunBeforeKilled() + " millisecs");
		LOG.fine(TAB + "Start UNIX nsec Epoch Time  was: "
				+ FinalOptions.getStartTime());
		LOG.fine(TAB
				+ "Time prognosis until the END is: "
				+ (FinalOptions.getStartTime() + FinalOptions
						.getMaxTimeToRunBeforeKilled()));
		LOG.fine(TAB
				+ "Guard time between any other GSB request is: "
				+ FinalOptions.getMinTimeBetweenGSBRequests() + " millisecs");
		LOG.fine(TAB + "FILENAME for the output: "
				+ FinalOptions.getFilenameForOutput());
		LOG.info("       ============END of the Options.");
			//
		FinalOptions.setCurrentTarget(FinalOptions.getCountryCodeToWorkWith());
		
		LOG.info(TAB + "will ask thea DataDiver for this constituency: "
				+ FinalOptions.getCurrentTarget());

		LOG.info("==-> START of the actual launch... ");
		// HERE starts the ACTUAL LAUNCH CODEwe launch the business logic
		nuhker.DataDiver.entryPoint(FinalOptions);
		//
		long duration = (System.nanoTime() - FinalOptions.getStartTime());
		System.out.println("<<<<<<< " + "The Epoch lasted: "
				+ (duration / 1000000000) + " secs.");
		System.out.println("================  END of MAIN  ==========");
		System.exit(0);
	}

}
