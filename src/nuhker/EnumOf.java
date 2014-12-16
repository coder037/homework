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
 * This class contains two shadow type enum "databases", 1. of Level string
 * values for logging and 2. of country codes that RIPE is serving.
 * 
 * Both enums contain a boolean method isKosher(String candidate) to calculate
 * whether the data in question is contained in enum.
 * 
 * created: Dec 14, 2014 11:10:05 PM
 * 
 * @author coder037@xyz.ee
 */
public class EnumOf {
	private final static String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());

	/**
	 * A shadow enum mimicking the Level class. Much simpler to deal than with
	 * the LEVEL class
	 * 
	 * @author coder037@xyz.ee
	 * 
	 */
	public enum level {
		ALL, SEVERE, FINEST, FINER, FINE, CONFIG, INFO, WARNING, OFF;

		/**
		 * A substitution to Level values. Is easy to check.
		 * 
		 * @param candidate
		 *            - some of Logger LEVELS
		 * @return boolean value whether the argument was acceptable
		 */
		public static boolean isKosher(String candidate) {

			// Inspiration:
			// http://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
			LOG.info(TAB + TAB + "==- Level Kosherness Check");
			try {
				level.valueOf(candidate);
				LOG.fine(TAB + TAB + "Level " + candidate
						+ " found to be kosher enough.");
				LOG.info(TAB + TAB + "==-> CountryCode NORMAL EXIT");
				return true;

			} catch (Exception e) {
				LOG.info(TAB + TAB + "Sry but ||" + candidate
						+ "|| cannot be used as a Log LEVEL.");
				LOG.info(TAB + TAB + "==-> Level ||" + candidate
						+ "|| is UNSUITABLE.");
				return false;
			}
		}
	} // ENUM

	/**
	 * An enum collecting possible country codes RIPE is serving. taken manually
	 * (as of 2014-12-02) from:
	 * http://www.ripe.net/lir-services/member-support/info
	 * /list-of-members/list-of-country-codes-and-rirs
	 * 
	 * @author coder037@xyz.ee
	 * 
	 */
	public enum cc {
		AX, AL, AD, AM, AT, AZ, BH, BY, BE, BA, BG,
		HR, CY, CZ, DK, EE, FO, FI, FR, GE, DE, GI,
		GR, GL, GG, VA, HU, IS, IR, IQ, IE, IM, IL,
		IT, JE, JO, KZ, KW, KG, LV, LB, LI, LT, LU,
		MK, MT, MD, MC, ME, NL, NO, OM, PS, PL, PT,
		QA, RO, RU, SM, SA, RS, SK, SI, ES, SJ, SE,
		CH, SY, TJ, TR, TM, UA, AE, GB, UZ, YE;
		
		/**
		 * 
		 * @param candidate
		 *            - a two-letter ISO 3166-1 code
		 * @return boolean value whether the code is served by RIPE
		 */
		public static boolean isKosher(String candidate) {

			// Inspiration:
			// http://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
			LOG.info(TAB + TAB + "==- CountryCode Kosherness Check");
			try {
				cc.valueOf(candidate);
				LOG.fine(TAB + TAB + "CC found to be kosher enough.");
				LOG.info(TAB + TAB + "==-> CountryCode NORMAL EXIT");
				return true;

			} catch (Exception e) {
				LOG.info(TAB + TAB + "CC extends the bearable kosherness.");
				LOG.info(TAB + TAB + "==-> CountryCode check UNSUXXESSFUL");
				return false;
			}
		}
	} // ENUM

}
