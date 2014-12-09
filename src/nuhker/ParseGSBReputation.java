/**
 * created: Nov 28, 2014 8:56:32 AM
 */
package nuhker;

import java.io.IOException;
import java.util.logging.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

//INSPIRATION http://stackoverflow.com/questions/12361925/html-parsing-with-jsoup

/**
 * @author coder037@xyz.ee
 * @identity 0f
 *           a1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *           Tag Comments: @version @param @return @deprecated @since @throws @exception @see
 */
public class ParseGSBReputation {

	public static boolean isNonsense(String suspect) {
		// This method calculates whether the sentence testified
		// is a valid data or some pre-defined crap that we don't need

		String[] urlNotUseful = { "Webmaster Help Center", "Webmaster Tools",
				"Return to the previous page.", "Google Home" };
		boolean whetherToDiscard = false;

		virginity: for (int i = 0; i < urlNotUseful.length; i++) {
			if (urlNotUseful[i].equals(suspect)) {
				whetherToDiscard = true;
				// System.out.println("Discard this and exit");
				break virginity;
			}
		} // FOR
		return whetherToDiscard;
	}

	// ToDo: method: public static String[] grabGSBReputation (String fqdnOrAsn)

	public static void main(String[] args) {
		String url1 = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=www.ee/";
		String url2 = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=AS:3249";

		String workspace = "";
		String as = "AS";

		// ToDo:
		// 1. to empty main method, create a separate method
		// 2. parametrize that method so that we can enter a string param
		// 3. do checks on param entry: whether FQDN or ASN (AS, no /$)
		// 4. some routine checks whether FQDN looks normalized (/ off)
		// 5. separate AS info from AS number for future bookkeeping

		try {
			String url = url1;
			System.out.println("START: " + url);
			Document doc = Jsoup.connect(url).get();
			Elements meaningfulSections = doc.select("a");
			// need to find memes that contain meaningful FQDNs.
			for (Element fqdnCandidate : meaningfulSections) {

				workspace = fqdnCandidate.text();
				// System.out.println(workspace);

				// Checking each meme for nonsenselessness
				if (!(isNonsense(workspace))) { // throw away Google's own promo
												// links
					if (workspace.contains(as)) {
						System.out.println("Also found AS info: " + workspace);
						// ToDo: the routine to exfiltrate all other ASNs except
						// our initial one
						// Is this ASN our initial ASN?
					} else {
						System.out.println("Sub says SHIT FOUND AT: "
								+ workspace);
						// ToDo: We should extrafiltrate these to some global
						// list

					}
				}
			}

		} // TRY

		// Funny thing seen at a source. Should we use it elsewhere?
		// SRC1: http://tutorials.jenkov.com/java-logging/logger.html
		// SRC2: http://www.vogella.com/tutorials/Logging/article.html
		catch (IOException ex) {
			Logger.getLogger(ParseGSBReputation.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	} // MAIN
} // CLASS

