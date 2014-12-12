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
import java.util.logging.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.util.regex.*;
import java.util.ArrayList;

// INSPIRATION to use jsoup  got from M.M. 
// technical hints obtained: http://stackoverflow.com/questions/12361925/html-parsing-with-jsoup

/**
 * GSB - Google Safe Browsing - a reputation service to indicate whether
 * aninternet site is clean or contaminated by the malware.
 * 
 * This particular exercise ignores dates; "listed on GSB" means - has been
 * contaminated by malware within last 90 days".
 * 
 * @created: Nov 28, 2014 8:56:32 AM
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 */
public class ParseGSB {

	private final static String AS = "AS";
	private final static String COLON = ":";
	private final static String TAB = "\t";

	/**
	 * 
	 * The method eliminates Google promo links from actual data. There are 4
	 * previously known links. We compare the argument with these. On the first
	 * found, break the cycle and return a signal to the calling program
	 * 
	 * @param suspect
	 *            - a link to discard as not a valid data
	 * @return boolean decision whether to discard the argument
	 */
	public static boolean isNonsense(String suspect) {

		// DEBUG System.out.println(TAB + "---> isNonsense");
		String[] googleCrap = { "Webmaster Help Center", "Webmaster Tools",
				"Return to the previous page.", "Google Home" };
		boolean whetherToDiscard = false;

		virginity: for (int i = 0; i < googleCrap.length; i++) {
			if (googleCrap[i].equals(suspect)) {
				whetherToDiscard = true;
				// DEBUG System.out.println(TAB + TAB + "Discard: " + suspect);
				break virginity; // at first match
			}
		} // FOR
			// DEBUG System.out.println(TAB + "---< isNonsense");
		return whetherToDiscard;
	}

	/**
	 * A formal conversion method 12345 -> AS:12345. Several checks and
	 * consequential format conversions are included to be VERY SURE about our
	 * assumptions how Google uses its data at GSB
	 * 
	 * @param source
	 *            The number or an ASN in numeric form (12345)
	 * @param source
	 * @return this ASN with "AS:" prepended -> AS:12345 to be directly usable
	 *         against GSB
	 */
	static String asn2Colon(String source) {
		/**
		 * @param String
		 *            12345 -> AS:12345
		 */
		String destination = "";
		// DEBUG System.out.println(TAB + "---> asn2Colon");
		// DEBUG System.out.println(TAB + TAB + "SRC: " + source);
		destination = source.replaceAll(" .*$", "");
		// DEBUG System.out.println(TAB + TAB + "MID: " + destination);
		destination = destination.replaceAll("^AS", "");
		// DEBUG System.out.println(TAB + TAB + "DST: " + destination);
		// To be extremely sure:
		int sourceNo = Integer.parseInt(destination);
		destination = Integer.toString(sourceNo);
		destination = (AS + COLON + destination);
		// DEBUG System.out.println(TAB + TAB + "FIN: " + destination);
		// DEBUG System.out.println(TAB + "---< asn2Colon");
		return destination;
	}

	/**
	 * A formal conversion method AS:12345 -> 12345. Several checks and
	 * consequential format transformations included to be VERY SURE about our
	 * assumptions how Google uses its data at GSB.
	 * 
	 * @param source
	 *            The number or an ASN in numeric form (12345)
	 * @param source
	 * @return Google preferred AS:12345 format converted to an int number 12345
	 */
	static String as2ASN(String source) {

		System.out.println(TAB + "---> as2ASN");
		String destination = source.replaceAll("^AS", "");
		destination = destination.replaceAll(":", "");
		System.out.println(TAB + TAB + destination);
		// to be extremely sure:
		int sourceNo = Integer.parseInt(destination);
		destination = Integer.toString(sourceNo);
		System.out.println(TAB + "---< as2ASN");
		return destination;
	}

	/**
	 * This is the most important method in this class which is axial to the
	 * whole package. A network resource is checked against Google SafeBrowsing
	 * Interface.
	 * 
	 * 
	 * @param source
	 *            an FQDN or URL or ASN (AS:12345) to be checked
	 * @return a String[] array containing references to the badness
	 */
	static String[] badReputation(String source) {
		ArrayList<String> validSites = new ArrayList<String>();
		String workspace = "";
		String baseURL = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=";

			// ToDo: how to handle network layer ERRORS?
			// either a cycle with (false) until no Exception
			// or dismiss errors (faster recovery)
		
		try {
			String url = (baseURL + source);
			System.out.println("===+ START badReputation");
			System.out.println(TAB + "URL: " + url);

			Document doc = Jsoup.connect(url).get();
			Elements meaningfulSections = doc.select("a");
			// keep only memes with meaningful FQDNs / AS's.
			for (Element fqdnCandidate : meaningfulSections) {
				workspace = fqdnCandidate.text();
				// System.out.println(workspace);

				// Exclude promo targets
				if (!(isNonsense(workspace))) {

					if (workspace.contains(AS)) {
						System.out.println(TAB + "AS info: " + workspace);

					} else {
						// DEBUG System.out.println(TAB + "New Malwaresite: "+
						// workspace);
						validSites.add(workspace);
						// DEBUG System.out.println();
					} // ELSE
				} // FI
			} // FOR
		} // TRY
		catch (IOException ex) {
			System.out
					.println("!!!WARNING!!! Some connectivity glitch against GSB");
			System.out.println(TAB + "should we act somehow?");
		}
		String checkout = validSites.toString();
		System.out.println(TAB + "badReputation found: " + checkout);

		String[] validTargets = new String[validSites.size()];
		validTargets = validSites.toArray(validTargets);
		System.out.println("===- END badReputation ");
		return validTargets;
	}

	/**
	 * main method is mostly kept here for debugging purposes.
	 * 
	 * @param args
	 *            none expected
	 * @return nothing. Only print some debug info.
	 */
	public static void main(String[] args) {
		String target1 = "delfi.ee/";
		String target2 = "AS:8728";
		String target3 = "AS:12757";

		System.out.println("=== ParseGSB MAIN ===");
		String needToDive[] = badReputation(target1); // choose 1 or 2 or 3
		System.out.println("=== MAIN Printout: ");
		for (String s : needToDive) {
			System.out.println(TAB + TAB + s);
		}
		System.out.println("=== End of ParseGSB MAIN");
	} // MAIN

} // CLASS

