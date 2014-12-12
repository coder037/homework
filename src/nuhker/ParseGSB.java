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
 * @created: Nov 28, 2014 8:56:32 AM
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 */
public class ParseGSB {

	private final static String AS = "AS";
	private final static String COLON = ":";
	private final static String TAB = "\t";


	/**
	 * The method is axial to the whole package. The name
	 * of a network resource is being checked against the
	 * Google SafeBrowsing Interface (a reputation service).
	 * 
	 * @param a target which reputation is to be checked
	 * could be 1 of 3: a FQDN (xyz.ee/) or URL (xyz.ee/X/)
	 *  or ASN (AS:12345) 
	 * @return a String[] array containing references to
	 * the badness. Should be clear that this is not we
	 * inventing badness. This is what Google thinks.
	 * 
	 * We are not too precise within this experiment, we
	 * are satisfied with the 90 last days precision offered.
	 * @see doc/Parsing-GSB-sitepages-010 for a much more
	 * thorough assessment of the topic.
	 * @exception There are chances that Google will modify
	 * the page format. If so, hopefully this exception
	 * remembers us about the possibility.
	 */
	static String[] badReputation(String source) {
		String candidate = "";
		String baseURL = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=";
		ArrayList<String> vettedBadness = new ArrayList<String>();

		
		try {
			String url = (baseURL + source);
			System.out.println("===+ START badReputation");	
			System.out.println(TAB + "URL visited: " + url);
			
			Document doc = Jsoup.connect(url).get();
			Elements meaningfulSections = doc.select("a");
			
			// Some chemical laundry for the elements we don't need
			for (Element found : meaningfulSections) {
				candidate = found.text();
				// System.out.println(workspace);

				// Exclude promo targets
				if (Func.isSensible(candidate)) {
					vettedBadness.add(candidate);
					Func.doSomeBookkeepingOnThe(candidate);
				} 
			} // FOR
		} // TRY
		catch (IOException ex) {
			System.out
					.println("!!!WARNING!!! NETWORK glitch against GSB discovered!");
			System.out.println(TAB + "We probably should act somehow but we are too lazy for that");
		}
		// Who likes Chopin, but I like String[] array returns
		// much more than ArrayLists<>. Dancing between the datatypes
		// could be  considered as fuzzing and reveals bugs. ;)

		int count = vettedBadness.size();
		String[] someTargets = new String[count];
		someTargets = vettedBadness.toArray(someTargets);
		
		if (count == 0) {
			System.out.println("---= Uncopulatingbelieveable ... no badness discovered under " + source);
		} else {
			System.out.println("---= Normal business: badness count under " + source + " is " + count);
		}
			
		System.out.println("===- END badReputation ");
		return someTargets;

	}

	
	// ================ Here ends the program
	// and starts some experimental test ===========
	
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

