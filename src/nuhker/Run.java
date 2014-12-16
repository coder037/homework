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
	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());

	// Here we first parse the argv line to be sure it is parsable nuff
	/**
	 * Depends on the jopt-simple library. Take a copy of the CLIGrammar and
	 * then validate the actual CLI options according to the grammar. No attempt
	 * is made to understand the options - this is the preliminar pass.
	 * 
	 * If it seems to the library that options are correct and adhere to the
	 * Grammar, then we return Boolean true, otherwise false.
	 * 
	 * @param a
	 *            copy of CLI option to be validated
	 * @return boolean decision whether the conformancy was true or false
	 */
	public static boolean checkConformity(String[] arguments) {
		OptionSet args = null;
		OptionParser preParser = CLIGrammar.description();
		LOG.info("   ===~ START: Option Conformity");

		// ==== WARNING, NEXT 26 lines are not considered fully mine,
		// This kind of parsing is learnt from
		// https://github.com/martinpaljak/GlobalPlatform/blob/master/src/openkms/gp/GPTool.java
		// lines 133-147
		try {
			args = preParser.parse(arguments);
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
			try {
				LOG.info("Please check the command line options twice!");
				preParser.printHelpOn(System.err);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// ==== END of foreign code
			LOG.severe("ERROR: Non-conformant options. Bailout");
			System.exit(78);
			// http://www.opensource.apple.com/source/Libc/Libc-320/include/sysexits.h
		}
		// END of the code which authorship is partially of somebody's else.
		LOG.info("   ===~ DONE: Option Conformity");
		return true; // We reached this point, thus no bailout
	}

	/**
	 * This is the utmost important method of the class. CLI options are parsed
	 * by means of the jopt-simple library and recorded into DefaultParms
	 * runTimeParms options.
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
		OptionParser postParser = CLIGrammar.description();
		OptionSet cliOptions = postParser.parse(arguments);

		DefaultParms runTimeParms = new DefaultParms();
		LOG.fine("      ===-( CLI Option Parser");

		// Somewhat special options FIRST

		if (cliOptions.has("help")) {
			postParser.printHelpOn(System.out);
			System.exit(0);
		}

		if (cliOptions.has("v")) {
			LOG.severe(TAB + "Nuhker version: " + VERSION);
			System.exit(0);
		}

		if (cliOptions.has("x")) {
			LOG.warning(TAB + "Option x was found which isn't yet implemented.");
			LOG.info(TAB + TAB + "Anyway, thnx for supporting it!");
		}

		if (cliOptions.has("c")) {
			LOG.finest(TAB + "Option c was found");
			String subOption = (String) cliOptions.valueOf("c");
			if (EnumOf.cc.isKosher(subOption)) {
				LOG.info(TAB + TAB + "CountryCode is kosher: " + subOption);
				runTimeParms.setCountryCodeToWorkWith(subOption);
			} else {
				LOG.warning(TAB + TAB + "Man, I deeply doubt *" + subOption
						+ "* is a CountryCode RIPE is aware of.");
				LOG.severe("BailOut");
				System.exit(1);
			}
		} // option c

		if (cliOptions.has("d")) {
			LOG.finer(TAB + "Option d was found");
			String subOption = String.valueOf(cliOptions.valueOf("d"));
			LOG.finer(TAB + TAB + "and it had a suboption: " + subOption);

			if (EnumOf.level.isKosher(subOption.toUpperCase())) {
				// Set the global DebugLevel from here
				LOG.severe(TAB + "Setting current loglevel value as: "
						+ subOption);
				runTimeParms.setLogLevel(subOption);
			} else {
				LOG.severe(TAB + "As a punishment for yelling |" + subOption
						+ "|, debug level will be set to ||ALL||.");
				runTimeParms.setLogLevel("ALL");
			}
		} // option d

		// Special copyright routines

		if (cliOptions.has("C")) {
			LOG.fine(TAB
					+ "Option C was called to expose the copyright message");
			String Suboption = (String) cliOptions.valueOf("C");
			LOG.finer(TAB + TAB + "and it had a sub-option: " + Suboption);

			LOG.warning(Func.checkTheAuthorship(Suboption));
			LOG.warning(TAB
					+ "###############################################################################");
			LOG.warning(TAB
					+ "# Small portions of foreign copyleft noted as such in code, expressis verbis. #");
			LOG.warning(TAB
					+ "###############################################################################");
			LOG.info(TAB + "===-) END of Option Parser (phase 1)");
		} // option C

		// More generic options (coefficients / parameters) to the runtime

		LOG.fine("      ===-( Option Parser phase 2");
		if (cliOptions.has("M")) {
			LOG.finer(TAB + "Option M was found");
			String subOption = String.valueOf(cliOptions.valueOf("M"));
			LOG.finest(TAB + TAB + "and it had a suboption: " + subOption);
			LOG.finest(TAB + "Setting MaxRunTime value as: " + subOption
					+ " secs.");
			runTimeParms.setMaxTimeToRunBeforeKilled(Integer
					.parseInt(subOption) * 1000); // DONE!!!
			LOG.fine(TAB + "Have set MaxRunTime value as: "
					+ runTimeParms.getMaxTimeToRunBeforeKilled() + " msecs.");
			// Inspiration to convert by means of Integer.parseInt from reznic
			// http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java
		} // option M

		if (cliOptions.has("R")) {
			LOG.fine(TAB + "Option R was found");
			String subOption = String.valueOf(cliOptions.valueOf("R"));
			LOG.finer(TAB + TAB + "and it had a suboption: " + subOption);
			// Set recursion max level
			LOG.finer(TAB + "Setting Max Recursion depth as : " + subOption);
			runTimeParms.setDepthOfRecursion(Integer.parseInt(subOption));
			// Set relative level
			LOG.finer(TAB + "Setting Current Recursion level the same: "
					+ subOption);
			runTimeParms
					.setCurrentLevelOfRecursion(Integer.parseInt(subOption));
		} // option R

		if (cliOptions.has("t")) {
			LOG.fine(TAB + "Option t was found");
			String subOption = String.valueOf(cliOptions.valueOf("t"));
			LOG.finer(TAB + TAB + "and it had a suboption: " + subOption);
			// set timeout
			LOG.finer(TAB + "Setting GSB mandatory timeout >=: " + subOption
					+ " msec.");
			runTimeParms.setMinTimeBetweenGSBRequests(Integer
					.parseInt(subOption));
		} // option t

		if (cliOptions.has("o")) {
			LOG.fine(TAB + "Option o was found");
			String subOption = (String) cliOptions.valueOf("o");
			LOG.finer(TAB + TAB + "and it had a suboption: " + subOption);
			// set filename
			LOG.finer(TAB + "Setting Base Filename as requested: " + subOption);
			runTimeParms.setFilenameForOutput(subOption);
		} // option o

		LOG.info("      ===-) END of Option Parser, phase 2");
		LOG.info(TAB + "options init DONE according to the CLI values.");
		return runTimeParms;
	}

	public static void loggingBlock(DefaultParms values) {
		// From this point on, the LOG output is correctly formatted
		LOG.info(TAB + "=================================");
		// printout of ACTUAL options
		LOG.info(TAB + "~~~~~~~~ " + "Our Epoch started at: "
				+ values.getStartTime());
		LOG.warning(TAB + "Loglevel has been set on ||" + values.getLogLevel()
				+ "|| level.");
		LOG.fine(TAB + "We seek for Malwarized Sites in country: "
				+ values.getCountryCodeToWorkWith());
		LOG.fine(TAB + "Recursion depth has been limited to: "
				+ values.getDepthOfRecursion());
		LOG.fine(TAB + "Time allocated for the run is: "
				+ values.getMaxTimeToRunBeforeKilled() + " millisecs");
		LOG.fine(TAB + "START:     NanoSec Epoch Time was: "
				+ values.getStartTime());
		LOG.fine(TAB
				+ " prediction: we will kill ourselves at: "
				+ (values.getStartTime() + values.getMaxTimeToRunBeforeKilled()));
		LOG.fine(TAB + "Guard time between any other GSB request is: "
				+ values.getMinTimeBetweenGSBRequests() + " millisecs");
		LOG.fine(TAB + "FILENAME for the output: "
				+ values.getFilenameForOutput());

		LOG.info(TAB
				+ "All options are considered now. Forcing them downstairs");
		values.setCurrentTarget(values.getCountryCodeToWorkWith());
		LOG.info(TAB + "Launching DataDiver for the constituency: "
				+ values.getCurrentTarget());

	}

	/**
	 * The main routine parses CLI options from the command line argv and sets
	 * the running options according these.
	 * 
	 * Some simulated CLI option examples are left commented out.
	 * 
	 * @param argv
	 *            CLI options passed to other methods
	 * @throws Exception
	 *             which left properly unhandled - b/c this is a PoC code.
	 */
	public static void main(String[] argv) throws Exception {
		long firstVariable = System.nanoTime();

		LOG.config("   0--------={Start}=--------0");

		// CLI alternatives for Eclipse simulation
		// String[] simulation1 = { "--country", "EE", "--copyright",
		// "Some Name",
		// "--xtra", "-o", "output", "-R", "15", "-t", "2500", "-d", "FINEST",
		// "-M", "43200" };
		// String[] simulation2 = { "-c", "EE", "-d", "5", "-M", "80000", "-n",
		// "-o", "somefilename-001", "-R", "6", "-t", "2800" };
		// String[] simulation3 = { "--help" };
		String[] effectiveOptions = argv; // or some simulation

		LOG.severe("============= Starting M A I N ==============");

		// Formal check of command line options syntax
		checkConformity(effectiveOptions); // Else bailout
		LOG.info(TAB + "DONE:  Options conformity");

		// Parse RIPE for that country
		DefaultParms FinalOptions = parseContent(effectiveOptions);
		FinalOptions.setStartTime(firstVariable); // Start our timer

		String loggingLevel = FinalOptions.getLogLevel();
		LOG.severe(TAB + "Trying to set logging level to: " + loggingLevel);
		if (EnumOf.level.isKosher(loggingLevel)) {
			LogHandler.setUpOnce(loggingLevel);
			LOG.severe(TAB + "The logging level now: ||"
					+ FinalOptions.getLogLevel() + "||.");
		}
		// From this point on, the LOG output should be correctly formatted
		loggingBlock(FinalOptions);

		LOG.info("==-> START of the actual launch of our business logic... ");
		DataDiver.entryPoint(FinalOptions);
		// The recursive grabber will work long ;)

		// End Statements
		long duration = (System.nanoTime() - FinalOptions.getStartTime());
		LOG.info("<<<<<<< " + "The Epoch lasted: " + (duration / 1000000000)
				+ " secs.");
		LOG.info("================  END of MAIN  ==========");
		System.exit(0);
	}

}
