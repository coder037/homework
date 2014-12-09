/**
 * created: Nov 28, 2014 8:56:32 AM
 */
package nuhker;

import java.io.IOException;
import java.util.logging.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.util.regex.*; 
import java.util.ArrayList;

//INSPIRATION got from M.M. 
//    & http://stackoverflow.com/questions/12361925/html-parsing-with-jsoup

/**
 * @author coder037@xyz.ee
 * @identity 0f
 *           a1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *           Tag Comments: @version @param @return @deprecated @since @throws @exception @see
 */
public class ParseGSBReputation {
	
	private final static String AS = "AS";
	private final static String COLON = ":";
	private final static String TAB = "\t";
	
	public static boolean isNonsense(String suspect) {
		// This method marks Google promo links
		System.out.println(TAB + "---> isNonsense");
		String[] googleCrap = { "Webmaster Help Center",
							"Webmaster Tools",
							"Return to the previous page.",
							"Google Home" };
		boolean whetherToDiscard = false;

		virginity: for (int i = 0; i < googleCrap.length; i++) {
			if (googleCrap[i].equals(suspect)) {
				whetherToDiscard = true;
				System.out.println(TAB + TAB + "Discard: " + suspect);
				break virginity; // at first match
			}
		} // FOR
		System.out.println(TAB + "---< isNonsense");
		return whetherToDiscard;
	}

	
	static String asn2Colon (String source) {
		/**
		 * @param String 12345 -> AS:12345
		 */
		String destination = "";
		System.out.println(TAB + "---> asn2Colon");
		System.out.println(TAB + TAB + "SRC: " + source);
		destination = source.replaceAll(" .*$", "");
		System.out.println(TAB + TAB + "MID: " + destination);
		destination = destination.replaceAll("^AS", "");
		System.out.println(TAB + TAB + "DST: " + destination);
		// To be extremely sure:
		int sourceNo = Integer.parseInt(destination);
		destination = Integer.toString(sourceNo);
		destination = (AS + COLON + destination);
		System.out.println(TAB + TAB + "FIN: " + destination);
		System.out.println(TAB + "---< asn2Colon");
		return destination;
	}
	
	static String as2ASN (String source) {
		/**
		 * @param String AS12345 or AS:12345 -> 12345
		 */
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
	
	
	static String[] badReputation (String source) {
		ArrayList validSites = new ArrayList();
		String [] validTargets;
		String workspace = "";

		// Put this try/catch BS into a static String[] badReputation (String source)
		// CALL it badReputation(String)
		// and it returns a String array of the further shit

		try {
			String url = source;
			System.out.println("===+ START ParseGSBReputation.main ");
			System.out.println( TAB + TAB + url);
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
						// PoC
						as2ASN(asn2Colon(workspace));
						// ADD to list

					} else {
						System.out.println(TAB + "New Malwaresite: "
								+ workspace);
						System.out.println();
						// ADD to list

					} //ELSE
				} //FI
			}

		}
		catch (IOException ex) {
			System.out.println("!!!WARNING!!! Some connectivity glitch against GSB");
			System.out.println(TAB + "should we act somehow?");
		}
		validTargets = new String[5];
		
		return validTargets;
	}

	
	
	public static void main(String[] args) {
		String url1 = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=delfi.ee/";
		String url2 = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=AS:8728";
		String url3 = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=AS:12757";
		
		String needToDive[] = badReputation(url1);
		
		// PRINTOUT

	} // MAIN
		
} // CLASS
