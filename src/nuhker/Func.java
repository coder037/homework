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

import java.util.*;
import java.util.logging.Logger;

/**
 * Here are concentrated some simple functions (oops, methods) we constantly
 * need. Basically said, these are simple bit-banging things, thus nothing
 * complex.
 * 
 * There is a separate question of unit testing (or not). These little dirty
 * routines have been checked with tens of thousands of passes on the real data
 * on the Internet. The question is, could a 10-argument unit test do it better?
 * 
 * created Dec 12, 2014 8:36:39 PM
 * 
 * @author coder037@xyz.ee
 */
public class Func {

	// I like constants, somebody might like Chopin.
	private final static String AUTHOR = "0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff";
	private final static String AS = "AS";
	private final static String COLON = ":";
	private final static String TAB = "\t";
	private final static String BEGINNING = "^"; // for regexp use
	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());

	/**
	 * Method delay(long milliSeconds) introduces a couple of seconds delay
	 * between the internet requests, not to overload the services used.
	 * 
	 * For conspirative reasons, the delay is non-static and varies a little bit
	 * each time.
	 * 
	 * @param base
	 *            time in millisecs to variate a little bit
	 */
	public static int variateTheTime(int base) {
		LOG.finer(TAB + "variateTheTime");
		LOG.finest("Basetime: " + base);
		Random generator = new Random();
		// ToDo: Actually we should go to float and multiply the base to margin
		// where margin is 1.5.
		int empiricTambovConstant = 1800;
		int value = generator.nextInt(empiricTambovConstant);
		LOG.finest("Random Value: " + value);
		int variatedTime = (base + value);
		LOG.finer("Slightly Randomized WaitTime: " + variatedTime);
		return variatedTime;
	}

	/**
	 * Method delay (int milliseconds) keeps internet requests a couple of
	 * seconds apart. This is in order not to abuse Net services.
	 * 
	 * @param someNumber
	 *            milliSeconds to wait
	 */
	public static void delay(int someNumber) {
		int delayTime = variateTheTime(someNumber);
		LOG.finest("Here we should wait for: " + delayTime + " msec");
		try {
			Thread.sleep(someNumber);
		} catch (InterruptedException enough) {
			Thread.currentThread().interrupt();
		}
		return; // does nothing beside wasting time
	}

	/**
	 * 
	 * The method recognizes Google promotional links and removes these from
	 * actual data. There are 4 previously known links. We compare the argument
	 * with these four. On the first match, we break the cycle and return a
	 * boolean flag to the calling program.
	 * 
	 * @param suspect
	 *            URL/sitename to discard as invalid data
	 * @return decision whether to discard the initial parameter
	 */
	public static boolean isSensible(String suspect) {

		LOG.finest(TAB + "---> isSensible Candidate?");
		String[] googleCrap = { "Webmaster Help Center", "Webmaster Tools",
				"Return to the previous page.", "Google Home" };
		boolean thisIsCrap = false;
		boolean whetherToKeep;

		virginity: for (int i = 0; i < googleCrap.length; i++) {
			if (googleCrap[i].equals(suspect)) {
				thisIsCrap = true;
				LOG.finest(TAB + TAB + "Discard it: |" + suspect + "|.");
				break virginity; // at first match
			}
		} // FOR
		if (!thisIsCrap) {
			LOG.finer(TAB + "---<" + suspect + " is a Sensible Candidate");
		}
		whetherToKeep = !thisIsCrap; // ;) 3x
		return whetherToKeep;
	}

	/**
	 * An especially freaky regexp method to distinguish between AS name, an
	 * URL/FQDN and (last but not least) an IPv4 address. No hopes that no
	 * errors. This is RegExp. However, tested on tens thousands of cases.
	 * 
	 * DataDiver is using our decision to work properly with each of these
	 * categories. We expect following data: - AS29024 (BALLOU-AS) -> AS -
	 * bjornsweden.com/ -> URL - facebook.com/NASDAQOMXStockholm/ - URL -
	 * 192.168.2.0/whatever/ -> IPV4
	 * 
	 * @param candidate
	 *            to check
	 * @return the decision - choice of 3 or ERROR
	 */
	static String whatIsIt(String candidate) {
		String decision = "ERROR";
		String workspace = "";
		String pattern = "";
		LOG.finer(TAB + "whatIsIt START");

		LOG.fine(TAB + TAB + "SRC: |" + candidate + "|.");
		// Pattern: Beginning , AS, digit
		// Bastard AS55592 is guilty - it has no description ;)
		if (candidate.matches("^AS\\d+.*")) {
			decision = (AS);
			LOG.finest(TAB + TAB + " chosen: " + decision);

		}

		// URL or IPV4?

		// Pattern Beginning, Decimal.dotted.IP , Slash
		LOG.finest(TAB + TAB + "SRC: |" + candidate + "|.");
		workspace = candidate.replaceAll(
				"(^\\d+)(\\.)(\\d+)(\\.)(\\d+)(\\.)(\\d+)(\\/)(\\/*$)",
				"$2$4$6$8");
		pattern = ".../"; // Disregard numbers - 3 dots and slash
		LOG.finest(TAB + TAB + "MID: |" + workspace + "| and |" + pattern
				+ "|.");
		if (pattern.equals(workspace)) {
			decision = "IPV4";
			LOG.finest(TAB + TAB + " chosen: " + decision);
		} else {

			// Pattern Caret, Whatever, Slash+END
			LOG.finest(TAB + TAB + "SRC: |" + candidate + "|.");
			workspace = candidate.replaceAll("(^)(.*)(\\/$)", "$3");
			pattern = "/";
			LOG.finest(TAB + TAB + "MID: |" + workspace + "| and |" + pattern
					+ "|.");
			if (pattern.equals(workspace)) {
				decision = "URL";
				LOG.finest(TAB + TAB + " chosen: " + decision);
			}

		}
		// TEMPORARY THINGY to check the validity of the IPv4 pattern:
		if (decision.equals("ERROR")) {
			LOG.severe(TAB + "---+---+---+---> whatIsIt made decision="
					+ decision + " for target " + candidate);
			LOG.warning(TAB + "Man, a program could be written better!");
			System.exit(70);
			// http://www.opensource.apple.com/source/Libc/Libc-320/include/sysexits.h
		}
		LOG.finer(TAB + "---+---+---+---> whatIsIt decision=" + decision);
		return decision;
	}

	/**
	 * The method does the conversion from int ASN to a Google preferred
	 * AS:12345 format
	 * 
	 * To be very sure, it can convert even from AS12345 to 12345 Altogether
	 * used 2 times.
	 * 
	 * @param source
	 *            - number or an ASN in pseudo-int format ("12345")
	 * @return the same ASN with "AS:" prepended -> AS:12345 to be directly
	 *         usable against GSB
	 */
	static String asn2Colon(String source) {
		String workspace = source;
		LOG.fine(TAB + "---> asn2Colon (" + source + ")");
		LOG.finest(TAB + TAB + "SRC: " + workspace);
		workspace = workspace.replaceAll((BEGINNING + AS), "");
		LOG.finest(TAB + TAB + "MID: " + workspace);
		// To be extremely sure press it through the cobra's penis
		int sourceNo = Integer.parseInt(workspace);
		workspace = Integer.toString(sourceNo);
		LOG.finest(TAB + TAB + "CLN: " + workspace);

		workspace = (AS + COLON + workspace);
		LOG.finest(TAB + TAB + "FIN: " + workspace);
		LOG.finer(TAB + "---< asn2Colon converted |" + source + "| to |"
				+ workspace + "|.");
		return workspace;
	}

	/**
	 * The method will strip the AS description part off the data. We will put
	 * in something like that: AS42337 (RESPINA-AS) and get pure number back:
	 * 42337 .
	 * 
	 * Usage: 1 occasion
	 * 
	 * Future plans: do not rip it off but save separately
	 * 
	 * @param source
	 * @return AS String with the description removed
	 */
	static String removeASDescr(String source) {
		String cleanResult = source;
		LOG.fine(TAB + "---> removeASDescr |" + source + "|.");
		LOG.finest(TAB + "---> SRC: " + source);
		cleanResult = cleanResult.replaceAll("(\\d+)(\\s.*)", "$1");
		LOG.finest(TAB + "---> MID: " + cleanResult);
		// Fuzz it through the anaconda's member - my saver
		cleanResult = cleanResult.replaceAll("^AS", "");
		int asNumber = Integer.parseInt(cleanResult);
		LOG.finest(TAB + "---> INT: " + asNumber);
		cleanResult = Integer.toString(asNumber);
		cleanResult = (AS + Integer.toString(asNumber));
		LOG.finest(TAB + "---> FIN: " + cleanResult);
		LOG.finer(TAB + "---< removeASDescr");
		return cleanResult;
	}

	/**
	 * This is the wrapper method to check the Autorship. It does nothing except
	 * taking the proposed author's name and calling a cryptographic validation
	 * calculateHash(String argument) against it.
	 * 
	 * The idea is that if you PREVIOUSLY know the author's name, you can prove
	 * it; but if you did'nt know, the program will not share its authors name.
	 * 
	 * @param argument
	 *            Author's name or what you think it is
	 * @return String message whether you guessed it or not
	 * @throws Exception
	 */
	public static String checkTheAuthorship(String argument) throws Exception {
		String message = null;
		LOG.info(TAB + "==-< Authorship test ===");
		if (AUTHOR.equals(ForeignCode.calculateHash(argument))) {
			message = TAB + TAB + "Copyright: " + argument
					+ " (validated cryptographically).";
		} else {
			message = TAB + "Only use option -c with the actual author's name";
		}
		return message;
	}
}