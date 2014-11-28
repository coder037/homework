/**
 * created: Nov 28, 2014 8:56:32 AM
 */
package Nuhker;

import java.io.IOException;
import java.util.logging.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

//SRC http://stackoverflow.com/questions/12361925/html-parsing-with-jsoup

/**
 * @author coder037@xyz.ee
 * @identity 3fde112fe1ca443210b843745f21b58aaeb7713576bbdd296848ca7b05b018283dc82c50ff51fc8d7c5243d883bfca92a3be4e068eb1ce541e91b54b1697340f
 * Tag Comments: @author @version @param @return @deprecated @since @throws @exception @see
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
		
		
	public static void main(String[] args) {
		String url1 = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=www.ee/";
		String url2 = "https://safebrowsing.google.com/safebrowsing/diagnostic?site=AS:3249";
		
		  String workspace = "";
		  String as = "AS"; 


	try {
	  String url = url1;
	  Document doc = Jsoup.connect(url).get();
	  Elements meaningfulSections = doc.select("a");
	 
	  for(Element fqdnCandidate : meaningfulSections) {
		 
		 workspace = fqdnCandidate.text();
	     // System.out.println(workspace);
	     
	  	    if (workspace.contains(as)) {
		 		System.out.println("Found AS info: " + workspace);
	  		}
		
	  	    if (isNonsense(workspace)) {
	  	    	// System.out.println("Sub says: nonsense!");
	  	    } else {
	  	    	System.out.println("Sub says: " + workspace);
	  	    }
	  	    
	  }  


	} //TRY
	catch (IOException ex) {
	  Logger.getLogger(parseGSBReputation.class.getName())
	        .log(Level.SEVERE, null, ex);
	   }
	  }
}
	

