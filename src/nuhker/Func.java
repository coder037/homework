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
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Here are concentrated some simple functions (oops, methods) we need here and
 * there. Basically said, these are simple bit-banging things, thus nothing
 * complex.
 * 
 * @created Dec 12, 2014 8:36:39 PM
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
		int empiricTambovConstant = 1800;
		int value = generator.nextInt(empiricTambovConstant);
		LOG.finest("Random Value: " + value);
		int variatedTime = (base + value);
		LOG.finer("Slightly Randomized WaitTime: " + variatedTime);
		return variatedTime;
	}

	/**
	 * Method delay (int milliseconds) keeps internet requests a couple of
	 * seconds apart. This is in order not to abuse services.
	 * 
	 * @param long milliSeconds to wait
	 */
	public static void delay(int someNumber) {
		int kiikingNumber = variateTheTime(someNumber);
		LOG.finest("Here we should wait for: " + kiikingNumber + " msec");
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
	 * with these found. On the firstfound, we break the cycle and return a flag
	 * to the calling program.
	 * 
	 * @param - a "link"/sitename to discard as invalid data
	 * @return - the decision whether to discard the initial parameter
	 */
	public static boolean isSensible(String suspect) {

		LOG.finer(TAB + "---> isSensible Candidate");
		String[] googleCrap = { "Webmaster Help Center", "Webmaster Tools",
				"Return to the previous page.", "Google Home" };
		boolean thisIsCrap = false;
		boolean whetherToKeep;

		virginity: for (int i = 0; i < googleCrap.length; i++) {
			if (googleCrap[i].equals(suspect)) {
				thisIsCrap = true;
				LOG.finest(TAB + TAB + "Discard: " + suspect);
				break virginity; // at first match
			}
		} // FOR
		LOG.fine(TAB + "---<" + suspect + " isSensible Candidate");
		whetherToKeep = !thisIsCrap; // ;) 3x
		return whetherToKeep;
	}

	public static void doSomeBookkeepingOnThe(String suspect) {
		// ArrayList<String> validSites = new ArrayList<String>();

		if (suspect.contains(AS)) {
			// It is an AS and should go to that Arraylist
			LOG.finer(TAB + "AS info found: " + suspect);

		} else {
			// it is an URL and should go to THIS ArrayList
			LOG.finer(TAB + "Malsite  found: " + suspect);

		} // ELSE

		// ToDo: there exist a rare case when the suspect is numeric,
		// probably should it be reverse-resolved or compared
		// to the ASN list of the constituency?

		return;
	}

	// /**
	// * A formal conversion method AS:12345 -> 12345. Several checks and
	// * consequential format transformations are included to be VERY SURE
	// * about our assumptions how Google uses its data at GSB.
	// *
	// * @param The number or an ASN in numeric form (12345)
	// * @return Google proprietary AS:12345 format converted to int 12345
	// */
	// static String as2ASN(String source) {
	//
	// LOG.fine(TAB + "---> as2ASN (" + source + ")");
	// String destination = source.replaceAll("^AS", "");
	// destination = destination.replaceAll(":", "");
	// LOG.finer(TAB + TAB + "FIN: " + destination);
	// // to be extremely sure canalize it through the snakes dick:
	// int sourceNo = Integer.parseInt(destination);
	// destination = Integer.toString(sourceNo);
	// LOG.finer(TAB + "---< as2ASN converted |" + source + "| to |" +
	// destination + "|.");
	// return destination;
	// }

	/**
	 * The method does the conversion from int ASN to a Google preferred
	 * AS:12345 format
	 * 
	 * To be very sure, it can convert even from AS12345 to 12345 Altogether
	 * used 2 times.
	 * 
	 * @param The
	 *            number or an ASN in pseudo-int format ("12345")
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
	 * @return
	 */
	static String removeASDescr(String source) {
		String cleanResult = source;
		LOG.fine(TAB + "---> removeASDescr (" + source + ").");
		LOG.finest(TAB + "---> SRC: " + source);
		cleanResult = cleanResult.replaceAll("(\\d+)(\\s.*)", "$1");
		LOG.finest(TAB + "---> MID: " + cleanResult);
		// Fuzz it through the anaconda's member
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
	 * The method will publicize some status data otherwise kept in the DBze.
	 * E.g. we call it before killing the program and elsewhere
	 * 
	 * @param none
	 * @return nothing but prints interesting thingz to StdOut.
	 */
	public static void publicizeStatistics() {
		LOG.info("===---===---===---===---===---> knownSites        COUNT: "
				+ DBze.knownSites.size() + " records");
		
		LOG.info("===---===---===---===---===---> knownDomains      COUNT: "
				+ DBze.knownDomains.size() + " records");
		
		LOG.info("===---===---===---===---===---> DomainsInCC       COUNT: "
				+ DBze.knownDomainsInCC.size() + " records");
		
		LOG.info("===---===---===---===---===---> knownASNs         COUNT: "
				+ DBze.knownASNs.size() + " records");
		// Can only be done if a separate array of CC ASNs exist
//		LOG.info("===---===---===---===---===---> knownASNsInCC     COUNT: "
//				+ DBze.knownASNsInCC.size() + " records");

		LOG.info("===---===---===---===---===---> knownNumericSites COUNT: "
				+ DBze.knownNumericSites.size() + " records");
		
		return;
	}

	/**
	 * This is the wrapper class to check the Autorship. It does nothing except
	 * taking the proposed author's name and calling a cryptographic validation
	 * calculateHash(String argument) of that.
	 * 
	 * The idea is that if you PREVIOUSLY know the author's name, you can prove
	 * it but if you don't know it, the program will not share its authors name,
	 * too.
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
