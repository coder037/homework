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
		OptionSet args = null; // declaration separated due to subsequent TRY
								// clause
		OptionParser preParser = CLIGrammar.main();
		LOG.info("   ===~ START: Option Conformity");

		// ==== WARNING, NEXT 26 lines are not considered fully mine,
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
				LOG.info("PARSE ERROR discovered, REASON: ");
				LOG.severe(TAB + e.getMessage() + ": "
						+ e.getCause().getMessage());
			} else {
				LOG.severe("PARSE ERROR discovered with UNEXPLAINED reason: ");
				LOG.severe(TAB + e.getMessage());
			}
			// preParser.printHelpOn(System.err);
			// ==== END of foreign code
			LOG.severe("ERROR: Non-conformant options. Bailout");
			System.exit(78);
			// according to http://www.opensource.apple.com/source/Libc/Libc-320/include/sysexits.h
		}
		// END of the code which authorship is partially of somebody's else.
		LOG.info("   ===~ DONE: Option Conformity");
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
		LOG.fine("      ===-( CLI Option Parser");

		// Somewhat special options FIRST

		if (cliOptions.has("d")) {
			LOG.finer(TAB + "Option d was found");
			String subOption = String.valueOf(cliOptions.valueOf("d"));
			LOG.finer(TAB + TAB + "and it had a suboption: "
					+ subOption);
			
			if (Candidate.level.isKosher(subOption)) {
			// Set the global DebugLevel from here
			LOG.finer(TAB + "Setting current loglevel value as: "
					+ subOption );
			RunTimes.setLogLevel(subOption);
			// =========== BUT HOW TO REALLY SET IT ?
			} else {
				LOG.severe(TAB + "As a punishment for |"
						+ subOption + "|, debug level will be set to ||ALL||.");
				RunTimes.setLogLevel("ALL");
			}
		} 

		if (cliOptions.has("help")) {
			postParser.printHelpOn(System.out);
			System.exit(0);
		}

		if (cliOptions.has("v")) {
			LOG.severe(TAB + "Nuhker version: " + VERSION);
			System.exit(0);
		}

		if (cliOptions.has("c")) {
			LOG.finest(TAB + "Option c was found");
			String subOption = (String) cliOptions.valueOf("c");
			if (CC.cc.isKosher(subOption)) {
				LOG.info(TAB + TAB + "CountryCode is kosher: "
						+ subOption);
			} else {
				LOG.warning(TAB + TAB + "Man, I deeply doubt *"
						+ subOption + "* is a CountryCode RIPE is aware of.");
				LOG.severe("BailOut");
				System.exit(1);
			}

		}

		if (cliOptions.has("x")) {
			LOG.warning(TAB
					+ "Option x was found which isn't yet implemented.");
			LOG.info(TAB + TAB + "Anyway, thnx for supporting it!");
		}

		// Special copyright copulation

		if (cliOptions.has("C")) {
			LOG.fine(TAB
					+ "Option C was called to expose the copyright message");
			String Suboption = (String) cliOptions.valueOf("C");
			LOG.finer(TAB + TAB + "and it had a sub-option: "
					+ Suboption);

			LOG.warning(Func.checkTheAuthorship(Suboption));
			LOG.warning(TAB
							+ "###############################################################################");
			LOG.warning(TAB
							+ "# Small portions of foreign copyleft noted as such in code, expressis verbis. #");
			LOG.warning(TAB
							+ "###############################################################################");
			LOG.info(TAB + "===-) END of Option Parser (phase 1)");
		}

		// More generic options (coefficients / parameters) to the runtime

		LOG.fine("      ===-( Option Parser phase 2");
		if (cliOptions.has("M")) {
			LOG.finer(TAB + "Option M was found");
			String subOption = String.valueOf(cliOptions.valueOf("M"));
			LOG.finest(TAB + TAB + "and it had a suboption: "
					+ subOption);
			LOG.finest(TAB + "Setting MaxRunTime value as: "
					+ subOption + " secs.");
			RunTimes.setMaxTimeToRunBeforeKilled(Integer.parseInt(subOption) * 1000); // DONE!!!
			LOG.fine(TAB + "Have set MaxRunTime value as: "
					+ RunTimes.getMaxTimeToRunBeforeKilled() + " msecs.");
			// Inspiration to convert by means of Integer.parseInt from reznic
			// http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java
		}

		if (cliOptions.has("R")) {
			LOG.fine(TAB + "Option R was found");
			String subOption = String.valueOf(cliOptions.valueOf("R"));
			LOG.finer(TAB + TAB + "and it had a suboption: "
					+ subOption);
			// Set recursion max level
			LOG.finer(TAB + "Setting Max Recursion depth as : "
					+ subOption);
			RunTimes.setDepthOfRecursion(Integer.parseInt(subOption));
			// Set relative level
			LOG.finer(TAB
					+ "Setting Current Recursion level the same: "
					+ subOption);
			RunTimes.setCurrentLevelOfRecursion(Integer.parseInt(subOption));

		}

		if (cliOptions.has("t")) {
			LOG.fine(TAB + "Option t was found");
			String subOption = String.valueOf(cliOptions.valueOf("t"));
			LOG.finer(TAB + TAB + "and it had a suboption: "
					+ subOption);
			// set timeout
			LOG.finer(TAB + "Setting GSB mandatory timeout >=: "
					+ subOption + " msec.");
			RunTimes.setMinTimeBetweenGSBRequests(Integer.parseInt(subOption));

		}

		if (cliOptions.has("o")) {
			LOG.fine(TAB + "Option o was found");
			String subOption = (String) cliOptions.valueOf("o");
			LOG.finer(TAB + TAB + "and it had a suboption: "
					+ subOption);
			// set filename
			LOG.finer(TAB + "Setting Base Filename as requested: "
					+ subOption);
			RunTimes.setFilenameForOutput(subOption);
		}

		LOG.info("      ===-) END of Option Parser, phase 2");
		LOG.info(TAB
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
		
		LOG.config("   0--------={Start}=--------0");
		
		// Alternatives for simulation (until we build the static CLI program)
		String[] simulation1 = { "--country", "EE", "--copyright", "Some Name",
				"--xtra", "-o", "output", "-R", "10", "-t", "2500", "-d", "INFO",
				"-M", "43200" };
		String[] simulation2 = { "-c", "EE", "-d", "5", "-M", "80000", "-n",
				"-o", "somefilename-001", "-R", "6", "-t", "2800" };
		String[] simulation3 = { "--help" };
		String[] effectiveOptions = simulation1; // vs argv

	
		LOG.severe("============= Starting M A I N ==============");
		
		// Formal check of command line options syntax
		checkConformity(effectiveOptions); // Else bailout
		LOG.info(TAB + "DONE:  Options assessed");
		// Parse RIPE for that country
		DefaultParms FinalOptions = parseContent(effectiveOptions);
		FinalOptions.setStartTime(firstVariable); // Start our timer
		
		String loggingLevel = FinalOptions.getLogLevel();
		LOG.severe(TAB + "Trying to set logging level to: " + loggingLevel);
		
		
		if (Candidate.level.isKosher(loggingLevel)) {
			LogHandler.setUpOnce(loggingLevel);
			LOG.severe(TAB + "The logging level now: ||" + FinalOptions.getLogLevel() + "||.");
		}
		
		
		// From this point on, the LOG output is correclty formatted
		LOG.info(TAB + "=================================");
		// printout of ACTUAL options		
		LOG.info(TAB + "~~~~~~~~ " + "Our Epoch started at: "
				+ FinalOptions.getStartTime());
		LOG.warning(TAB + "Loglevel has been set on ||"
				+ FinalOptions.getLogLevel() + "|| level.");
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
		
		LOG.info(TAB +"All options are considered now. Forcing them downstairs");
		FinalOptions.setCurrentTarget(FinalOptions.getCountryCodeToWorkWith());	
		LOG.info(TAB + "Launching DataDiver for the constituency: "
				+ FinalOptions.getCurrentTarget());

		LOG.info("==-> START of the actual launch of our business logic... ");
		DataDiver.entryPoint(FinalOptions);
		// it took long ;)
		
		long duration = (System.nanoTime() - FinalOptions.getStartTime());
		LOG.info("<<<<<<< " + "The Epoch lasted: "
				+ (duration / 1000000000) + " secs.");
		LOG.info("================  END of MAIN  ==========");
		System.exit(0);
	}

}
