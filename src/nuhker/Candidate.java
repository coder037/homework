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

import java.util.logging.Logger;

/**
 * This class contains the manually kept enum database of country codes that
 * RIPE is serving. It also contains a boolean method isKosher(String candidate)
 * to calculate whether a particular country code is mentioned in this database.
 * 
 * created: Dec 14, 2014 11:10:05 PM
 * 
 * @author coder037@xyz.ee
 */
public class Candidate {
	private final static String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );
	
	public enum level {
		ALL, SEVERE, FINEST, FINER, FINE, CONFIG, INFO, WARNING, OFF ;
		
		// Much simpler to deal than with the LEVEL class

		/** 
		 * A substitution to Level values. Is easy to check.
		 * 
		 * @param candidate - some of Logger LEVELS
		 * @return boolean value whether the value is usable
		 */
		public static boolean isKosher(String candidate) {
			
			// Inspiration:
			// http://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
			LOG.info(TAB + TAB + "==- Level Kosherness Check");
			try {
				level.valueOf(candidate);
				LOG.fine(TAB + TAB + "Level " + candidate + " found to be kosher enough.");
				LOG.info(TAB + TAB + "==-> CountryCode NORMAL EXIT");
				return true;

			} catch (Exception e) {
				LOG.info(TAB + TAB
						+ "Sry but ||" + candidate + "|| cannot be used as a Log LEVEL.");
				LOG.info(TAB + TAB
						+ "==-> Level ||" + candidate + "|| is UNSUITABLE.");
				return false;
			}
		}
	} // ENUM

}
