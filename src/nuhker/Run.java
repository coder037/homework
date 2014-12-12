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

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.security.MessageDigest;

/**
 * created: Nov 23, 2014 12:44:46 AM
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *
 */
public class Run {

	private final static String AUTHOR = "0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff";
	private final static String VERSION = "0.5";
	private final static String TAB = "\t";

	// Here we first parse the argv line to be sure it is parsable nuff
	
	public static boolean checkConformity(String[] arguments) {
		OptionSet args = null; // declaration separated due to subsequent TRY
							  // clause
		OptionParser preParser = CLIGrammar.main();

		System.out.println();
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

		// This method does some hashing with the string
	    // The warning - never try to make crypto at home ;)
	
	public static String calculateHash(String argument) throws Exception {
		String digest = null;
		System.out.println();
		System.out.println(TAB + "==- Authorship test ==========");
		MessageDigest hashhash = MessageDigest.getInstance("SHA-512");
		// === WARNING: NEXT 10 lines of code are of SOMEBODY'S ELSE authorship:
		// Inspiration:
		// http://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
		byte[] sha512bytes = hashhash.digest(argument.getBytes());
		// Inspiration: convert the byte to hex format
		// http://www.mkyong.com/java/java-sha-hashing-example/
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sha512bytes.length; i++) {
			sb.append(Integer.toString((sha512bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		// === END of the WARNING scope
		digest = sb.toString();
		return digest;
	}
	
		// This method is to Fourier the hash to the package author name
	public static String checkTheAuthorship(String argument) throws Exception {
		String message = null;
		if (AUTHOR.equals(calculateHash(argument))) {
			message = TAB + TAB + "Copyright: " + argument
					+ " (validated cryptographically).";
		} else {
			message = TAB
					+ "You seem not to know who the actual author is...";
		}
		System.out.println(TAB + "==-< END of the Authorship test ===");
		return message;
	}

	
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
			System.out.println(TAB + "Setting current loglevel value as: " + optionValue);
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
			System.out.println(TAB + "Option x was found which isn't yet implemented.");
			System.out.println(TAB + TAB + "Anyway, thnx for supporting it!");
		}

		// Special copyright phuck

		if (cliOptions.has("C")) {
			System.out.println(TAB
					+ "Option C was called to expose the copyright message");
			String optionValue = (String) cliOptions.valueOf("C");
			System.out.println(TAB + TAB + "and it had a sub-option: "
					+ optionValue);

			System.out.println(checkTheAuthorship(optionValue));
			System.out.println(TAB + "###############################################################################");
			System.out.println(TAB + "# Small portions of foreign copyleft noted as such in code, expressis verbis. #");
			System.out.println(TAB + "###############################################################################");
			System.out.println("===-) END of Option Parser");
		}

		// More generic options (coefficients / parameters) to the runtime

		System.out.println("===-( Option Parser phase 2");
		if (cliOptions.has("M")) {
			System.out.println(TAB + "Option M was found");
			String optionValue = String.valueOf(cliOptions.valueOf("M"));
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			System.out.println(TAB + "Setting MaxRunTime value as: " + optionValue + " secs.");
			RunTimes.setMaxTimeToRunBeforeKilled(Integer.parseInt(optionValue) * 1000); // DONE!!!
			System.out.println(TAB + "Have set MaxRunTime value as: " + RunTimes.getMaxTimeToRunBeforeKilled() + " msecs.");
			// Inspiration to convert by means of Integer.parseInt from reznic		System.out.println(TAB + "Our Epoch started at: " + FinalOptions.getStartTime());
			// http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java
		}

		if (cliOptions.has("R")) {
			System.out.println(TAB + "Option R was found");
			String optionValue = String.valueOf(cliOptions.valueOf("R"));
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			// Set recursion max level
			System.out.println(TAB + "Setting Max Recursion depth as : " + optionValue);
			RunTimes.setDepthOfRecursion(Integer.parseInt(optionValue));
			// Set relative level
			System.out.println(TAB + "Setting Current Recursion level the same: " + optionValue);
			RunTimes.setCurrentLevelOfRecursion(Integer.parseInt(optionValue));
			
		}

		if (cliOptions.has("t")) {
			System.out.println(TAB + "Option t was found");
			String optionValue = String.valueOf(cliOptions.valueOf("t"));
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			// set timeout
			System.out.println(TAB + "Setting GSB mandatory timeout >=: " + optionValue + " msec.");
			RunTimes.setMinTimeBetweenGSBRequests(Integer.parseInt(optionValue));
			
		}

		if (cliOptions.has("o")) {
			System.out.println(TAB + "Option o was found");
			String optionValue = (String) cliOptions.valueOf("o");
			System.out.println(TAB + TAB + "and it had a suboption: "
					+ optionValue);
			// set filename
			System.out.println(TAB + "Setting Base Filename as requested: " + optionValue);
			RunTimes.setFilenameForOutput(optionValue);
		}

		System.out.println("===-) END of Option Parser, phase 2");
		System.out.println(TAB + "options init DONE according to the CLI values.");
		return RunTimes;
	}


	/**
	 * @param args
	 */
	public static void main(String[] argv) throws Exception {
		long firstVariable = System.nanoTime();
		
		System.out.println("0--------={Start}=--------0");

		// Alternatives for simulation (until we build the static CLI program)
		String[] simulation1 = { "--country", "EE", "--copyright", "Some Name",
				"--xtra", "-o", "output", "-R", "6", "-t", "2780",
				"-d", "7", "-M", "20000" };
		String[] simulation2 = { "-c", "EE", "-d", "5", "-M", "80000", "-n",
				"-o", "somefilename-001", "-R", "6", "-t", "2800" };
		String[] simulation3 = { "--help" };
		String[] effectiveOptions = simulation1; // vs argv

		// Formal check of command line options syntax
		checkConformity(effectiveOptions); // Else bailout
		// Parse RIPE for that country
		DefaultParms FinalOptions = parseContent(effectiveOptions);
		FinalOptions.setStartTime(firstVariable); // Start our timer

		// printout of ACTUAL options
		System.out.println();
		System.out.println("===================  M A I N ==============");
		System.out.println("~~~~~~~~ " + "Our Epoch started at: " + FinalOptions.getStartTime());
		System.out.println("MAIN: READY to attempt the actual launch...");
		System.out.println(TAB + "Debuglevel has been set as: "
				+ FinalOptions.getDebugLevel() + " of max 7");
		System.out.println(TAB + "We seek for Malwarized Sites in country: "
				+ FinalOptions.getCountryCodeToWorkWith());
		System.out.println(TAB + "Recursion depth has been limited to: "
				+ FinalOptions.getDepthOfRecursion());
		System.out.println(TAB + "Time allocated for the run is: "
				+ FinalOptions.getMaxTimeToRunBeforeKilled() + " millisecs");
		System.out.println(TAB + "Start UNIX nsec Epoch Time  was: "
				+ FinalOptions.getStartTime());
		System.out.println(TAB
				+ "Time prognosis until the END is: "
				+ (FinalOptions.getStartTime() + FinalOptions
						.getMaxTimeToRunBeforeKilled()));
		System.out.println(TAB
				+ "Guard time between any other GSB request is: "
				+ FinalOptions.getMinTimeBetweenGSBRequests() + " millisecs");
		System.out.println(TAB + "FILENAME for the output: "
				+ FinalOptions.getFilenameForOutput());
		System.out.println("==-> START of the actual launch... ");
		FinalOptions.setCurrentTarget(FinalOptions.getCountryCodeToWorkWith());
		System.out.println(TAB + "asking DataDiver for this constituency: "
				+ FinalOptions.getCurrentTarget());

		// HERE starts the ACTUAL LAUNCH CODE
		nuhker.DataDiver.main(FinalOptions);
		//
		long duration = (System.nanoTime() - FinalOptions.getStartTime() );
		System.out.println("<<<<<<< " + "The Epoch lasted: " + (duration / 1000000000) + " secs.");
		System.out.println("================  END of MAIN  ==========");
		System.exit(0);
	}

	// ========================================================
	
	// ToDo: rename Nuhker nuhker

}
