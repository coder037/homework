/**
 * created: Nov 28, 2014 8:56:32 AM
 */
package Nuhker;

import java.io.IOException;
import java.util.logging.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

//Tutorial: http://stackoverflow.com/questions/12361925/html-parsing-with-jsoup

/**
 * @author coder037@xyz.ee
 * @identity 3fde112fe1ca443210b843745f21b58aaeb7713576bbdd296848ca7b05b018283dc82c50ff51fc8d7c5243d883bfca92a3be4e068eb1ce541e91b54b1697340f
 * Tag Comments: @version @param @return @deprecated @since @throws @exception @see
 */
	public class parseGSBReputation {

		
		
		public static boolean isNonsense (String suspect) {
			// This method calculates whether the sentence testified 
			// is a valid data or some pre-defined crap that we don't need
			
			String [] urlNotUseful = {
					"Webmaster Help Center",
					"Webmaster Tools",
					"Return to the previous page.",
					"Google Home"
					};
			boolean whetherToDiscard = false;
			
			virginity: for (int i = 0; i < urlNotUseful.length ; i++) { 
		  		  if (urlNotUseful[i].equals(suspect)) {
		  			whetherToDiscard = true;
		  			// System.out.println("Discard this and exit");
		  			break virginity;
		  		  }
		  	} // FOR  
			return whetherToDiscard;
		} 
		
		
		// ToDo: method: public static String[] grabClientReputation (String fqdnOrAsn)

		public static String[] grabClientReputation (String fqdnOrAsn) {
/**
 * @param either a shortened URL like www.ee or an ASN (number only)
 * @return an array of string extracted from GSB page. 
 */
			String [] suspiciousSites = null;
			// First determine whether the parameter is FQDN or ASN
			// Inspiration: http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java
			int asNumber = 0;
			boolean isURI = false;
			String baseURL = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=";
			String url = "";
			String as = "AS";
			String workspace = "";
			  int a = 0;
			  int u = 1;
			
			// Decide whether the input string is ASN or URL
			try {
		      asNumber =  Integer.parseInt(fqdnOrAsn);
		    } catch (NumberFormatException e) {
		        isURI = true;
		    }
			  

			 // Compose the final URL used for the request
			if (isURI) {
				System.out.println("The reputation check for an URI: "+ fqdnOrAsn);
				url = baseURL + fqdnOrAsn;	
			} else {
				// isASN
				System.out.println("The reputation check for an ASN: " + fqdnOrAsn);
				url = baseURL + as + ":" +fqdnOrAsn;
			}

			// Actually connect to GSB
			System.out.println("START grabbing: " + url);
			try {  
				  Document doc = Jsoup.connect(url).get();
				  Elements meaningfulSections = doc.select("a"); // A HREF sections
				 // find memes that contain meaningful FQDNs.
				  for(Element fqdnCandidate : meaningfulSections) {

					 workspace = fqdnCandidate.text();
				     // System.out.println(workspace);

				  	 // Checking each meme for nonsenselessness   
				  	    if ( ! (isNonsense(workspace))) { // throw away these Google promo links
					  	    if (workspace.contains(as)) {
						 		System.out.println("Also found AS info: " + workspace);
						 		System.out.println("        : is it our initial ASN or extra?");
						 		a = a + 1;
							// ToDo: the routine to exfiltrate all other ASNs except our initial one
						 		// Is this ASN our initial ASN?
					  	    } else {
				  	    	System.out.println("Sub extracted a shit-related URL: " + workspace);
				  	    		u = u + 1;
				  	    	// ToDo: We should extrafiltrate these to some global list
				  	    } 
				  	 }
				}
				  System.out.println("AS count:" + a);
				  System.out.println("FQDN count:" + u);
				  
			}	
			// Funny thing obtained from a source. Should we use it elsewhere?
			// SRC: http://tutorials.jenkov.com/java-logging/logger.html
			catch (IOException ex) {
			  Logger.getLogger(parseGSBReputation.class.getName())
			        .log(Level.SEVERE, null, ex);
			   }
			// SecondRoute
			
			return suspiciousSites;
		}
		
		
	public static void main(String[] args) {
		
		  System.out.println("============= 1 - FQDN =============");
		  grabClientReputation("www.ee");
		  System.out.println("============= 2 - ASN =============");
		  grabClientReputation("3249");
		  
		  // ToDo:
		  //     DONE 1. to empty main method, create a separate method
		  //     DONE 2. parametrize that method so that we can enter a string param
		  //     DONE 3. do checks on param entry: whether FQDN or ASN (AS, no 
		  //     4. some routine checks whether FQDN looks normalized (/$ off)
		  //     5. separate AS info from AS number for future bookkeeping
		  //	 6. compare uniqueness against the DB. Currently there _are_ repeats. 

		
    } // MAIN

	} //CLASS
	