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

/**
 * Here are concentrated some simple functions (oops, methods) we need
 * here and there. Basically said, these are simple bit-banging things,
 * thus nothing complex.
 * 
 * created: Dec 12, 2014 8:36:39 PM
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *
 */
public class Func {

	// I like constants, somebody might like Chopin.
	private final static String AUTHOR = "0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff";
	private final static String AS = "AS";
	private final static String COLON = ":";
	private final static String TAB = "\t";
	
	/**
	 * Method delay(long milliSeconds) introduces
	 * a couple of seconds delay between the internet
	 * requests, not to overload the services used.
	 * 
	 * For conspirative reasons, the delay is non-static
	 * and varies a little bit.
	 * 
	 * @param long milliSeconds to variate a little bit 
	 */
	public static int normalizedValue (int base) {
	Random generator = new Random();
	int empiricTambovConstant = 1800 ;
	int value = generator.nextInt(empiricTambovConstant);
	int variatedTime = (base+value);
	System.out.println("----------- Base: " + base + " ----------- Value: " + value + " ----------- Slightly Randomized WaitTime: " + variatedTime);
		return variatedTime;
	}
	
	
	/**
	 * Method delay(long milliSeconds) introduces
	 * a couple of seconds delay between the internet
	 * requests, not to overload the services used.
	 * 
	 * @param long milliSeconds to wait
	 */
	public static void delay(int someNumber) {
		int kiikingNumber = normalizedValue(someNumber);
		System.out.println("Here we should wait for: " + kiikingNumber + " msec");
		try {
		    Thread.sleep(someNumber);
		} catch (InterruptedException enough) {
		    Thread.currentThread().interrupt();
		}
		return; // does nothing beside the time waste
	}
	
	/**
	 * 
	 * The method eliminates Google promo links from actual data. There are 4
	 * previously known links. We compare the argument with these. On the first
	 * found, break the cycle and return a signal to the calling program
	 * 
	 * @param suspect a "link"/sitename to discard as invalid data
	 * @return a decision whether to discard the initial parameter
	 */
	public static boolean isSensible(String suspect) {

		// DEBUG System.out.println(TAB + "---> isSensible Candidate");
		String[] googleCrap = { "Webmaster Help Center", "Webmaster Tools",
				"Return to the previous page.", "Google Home" };
		boolean thisIsCrap = false;
		boolean whetherToKeep;

		virginity: for (int i = 0; i < googleCrap.length; i++) {
			if (googleCrap[i].equals(suspect)) {
				thisIsCrap = true;
				// DEBUG System.out.println(TAB + TAB + "Discard: " + suspect);
				break virginity; // at first match
			}
		} // FOR
			// DEBUG System.out.println(TAB + "---< isSensible Candidate");
		whetherToKeep = !thisIsCrap; // ;) 3x
		return whetherToKeep;
	}

	public static void doSomeBookkeepingOnThe(String suspect) {
		// ArrayList<String> validSites = new ArrayList<String>();
		
		if (suspect.contains(AS)) {
			// AS or AS: ?
			// It is an AS and should go to that Arrayist
			System.out.println(TAB + "AS info found: " + suspect);

		} else {
			// it is an URL and should go to THIS ArrayList
			System.out.println(TAB + "Badsite  found: "+ suspect);

		} // ELSE
		
		return;
	}
	
	
	/**
	 * A formal conversion method AS:12345 -> 12345. Several checks and
	 * consequential format transformations are included to be VERY SURE
	 * about our assumptions how Google uses its data at GSB.
	 * 
	 * @param The number or an ASN in numeric form (12345)
	 * @return Google proprietary AS:12345 format converted to int 12345
	 */
	static String as2ASN(String source) {

		System.out.println(TAB + "---> as2ASN");
		String destination = source.replaceAll("^AS", "");
		destination = destination.replaceAll(":", "");
		System.out.println(TAB + TAB + destination);
		// to be extremely sure canalize it through the snakes dick:
		int sourceNo = Integer.parseInt(destination);
		destination = Integer.toString(sourceNo);
		System.out.println(TAB + "---< as2ASN");
		return destination;
	}
	
	 /**
	  * The method does the conversion from int ASN to a Google preferred
	  * AS:12345 format
	  *
	  * @param The number or an ASN in integer format (12345)
	  * @return the same ASN with "AS:" prepended -> AS:12345 to be directly usable against GSB
	  */
	static String asn2Colon(String source) {

		String destination = "";
		// DEBUG System.out.println(TAB + "---> asn2Colon");
		// DEBUG System.out.println(TAB + TAB + "SRC: " + source);
		destination = source.replaceAll(" .*$", "");
		// DEBUG System.out.println(TAB + TAB + "MID: " + destination);
		destination = destination.replaceAll("^AS", "");
		// DEBUG System.out.println(TAB + TAB + "DST: " + destination);
		// To be extremely sure put it through the cobra's penis
		int sourceNo = Integer.parseInt(destination);
		destination = Integer.toString(sourceNo);
		destination = (AS + COLON + destination);
		// DEBUG System.out.println(TAB + TAB + "FIN: " + destination);
		// DEBUG System.out.println(TAB + "---< asn2Colon");
		return destination;
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
		if (AUTHOR.equals(ForeignCode.calculateHash(argument))) {
			message = TAB + TAB + "Copyright: " + argument
					+ " (validated cryptographically).";
		} else {
			message = TAB + "You seem not to know who the actual author is...";
		}
		System.out.println(TAB + "==-< END of the Authorship test ===");
		return message;
	}
	
	// I doubt we need main here ;)
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
