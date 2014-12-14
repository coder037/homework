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

// INSPIRATION to use jsoup got from M.M. 
// technical hints obtained: http://stackoverflow.com/questions/12361925/html-parsing-with-jsoup

/**
 * GSB - Google Safe Browsing - a reputation service to indicate whether
 * aninternet site is clean or contaminated by the malware.
 * 
 * This particular exercise ignores dates; "listed on GSB" means - has been
 * contaminated by malware within last 90 days".
 * 
 * @created Nov 28, 2014 8:56:32 AM
 * @author coder037@xyz.ee
 */
public class ParseGSB {

	private final static String TAB = "\t";
	private final static String AS = "AS";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );

	/**
	 * The method is axial to the whole package. The name of a network resource
	 * is being checked against the Google SafeBrowsing Interface (a reputation
	 * service).
	 * 
	 * @param target which reputation is to be checked could be 1 of 3: a
	 *            FQDN (xyz.ee/) or URL (xyz.ee/X/) or ASN (AS:12345)
	 * @return array containing references to the badness. Should be
	 *         clear that this is not we inventing badness. This is what Google
	 *         thinks.
	 * 
	 *         We are not too precise within this experiment, we are satisfied
	 *         with the 90 last days precision offered.
	 * @link doc/Parsing-GSB-sitepages-010.pdf for a much more thorough assessment of
	 *      the topic.
	 * @exception There
	 *                are chances that Google will modify the page format. If
	 *                so, hopefully this exception remembers us about the
	 *                possibility.
	 */
	static String[] badReputation(String source) {
		String nameOfTheCandidate = "";
		String baseURL = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=";
		ArrayList<String> temporaryList = new ArrayList<String>();
		boolean glitch = true;
		
		while (glitch) {
		try {
			String url = (baseURL + source);
			LOG.fine("===+ START badReputation");
			LOG.finer(TAB + "URL visited: " + url);

			Document doc = Jsoup.connect(url).get();
			Elements meaningfulSections = doc.select("a");

			// Some chemical laundry for the elements we don't need
			for (Element found : meaningfulSections) {
				nameOfTheCandidate = found.text();
				// System.out.println(workspace);

				// Exclude promo targets
				if (Func.isSensible(nameOfTheCandidate)) {
					
//					// Badsite  found: 15830
//					String numbersOnly = "(\\d+)";
//					String workspace = nameOfTheCandidate.replaceAll(numbersOnly, "");
//					if ("".equals(workspace)) {
//						System.out.println(TAB + "==========This was an AS");
//					} else {
//						System.out.println(TAB + "==========This was something else");
//					}
//					
//					if (nameOfTheCandidate.contains(AS)) {
//						nameOfTheCandidate = Func.cleanASDescr(nameOfTheCandidate);
//						System.out.println(TAB + "AS string CLEANED UP: " + nameOfTheCandidate);
//					}
					temporaryList.add(nameOfTheCandidate);
					Func.doSomeBookkeepingOnThe(nameOfTheCandidate);
				}
			} // FOR
			glitch = false; // i.e. it succeeded
		} // TRY
		catch (IOException ex) {
			LOG.severe(TAB + "!WARNING!   A NETWORK glitch against GSB discovered!" + ex);
			glitch = true;
			LOG.warning(TAB + TAB + "timeout: Let the Net rest for next 15 secs.");
			Func.delay(15000);
		}
		// Who likes Chopin, but I like String[] array returns
		// much more than ArrayLists<>. Dancing between the datatypes
		// should be considered as fuzzing because it eveal bugs. ;)
	} //WHILE
		
		int count = temporaryList.size();
		String[] someTargets = new String[count];
		someTargets = temporaryList.toArray(someTargets);

		if (count == 0) {
			LOG.fine("---= Uncopulatingbelieveable ... no badness discovered under "
							+ source);
		} else {
			LOG.fine("---= Normal business: badness count under "
					+ source + " is " + count);
		}

		LOG.fine("===- END badReputation ");
		return someTargets;

	}

	// ================ Main class is left for debugging only

	/**
	 * main method is mostly kept here for debugging purposes.
	 * 
	 * @param args
	 *            none expected
	 */
	public static void main(String[] args) {
		String target1 = "delfi.ee/";
		String target2 = "AS:8728";
		String target3 = "AS:12757";

		LOG.info("=== ParseGSB MAIN ===");
		String needToDive[] = badReputation(target1); // choose 1 or 2 or 3
		LOG.info("=== MAIN Printout: ");
		for (String s : needToDive) {
			LOG.info(TAB + TAB + s);
		}
		LOG.info("=== End of ParseGSB MAIN");
	} // MAIN

} // CLASS

