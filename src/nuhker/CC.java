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

/**
 * This class contains the manually kept enum database of country codes that
 * RIPE is serving. It also contains a boolean method isKosher(String candidate)
 * to calculate whether a particular country code is mentioned in this database.
 * 
 * created: Dec 8, 2014 11:12:05 PM
 * 
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 * 
 */
public class CC {
	private final static String TAB = "\t";

	public enum cc {
		AX, AL, AD, AM, AT, AZ, BH, BY, BE, BA, BG,
		HR, CY, CZ, DK, EE, FO, FI, FR, GE, DE, GI,
		GR, GL, GG, VA, HU, IS, IR, IQ, IE, IM, IL,
		IT, JE, JO, KZ, KW, KG, LV, LB, LI, LT, LU,
		MK, MT, MD, MC, ME, NL, NO, OM, PS, PL, PT,
		QA, RO, RU, SM, SA, RS, SK, SI, ES, SJ, SE,
		CH, SY, TJ, TR, TM, UA, AE, GB, UZ, YE;

		// Choice of possible country codes RIPE is serving
		// taken manually from:
		// http://www.ripe.net/lir-services/member-support/info/list-of-members/list-of-country-codes-and-rirs
		// as of 2014-12-02

		/** 
		 * 
		 * @param candidate - a two-letter ISO 3166-1 code
		 * @return boolean value whether the code is served by RIPE
		 */
		public static boolean isKosher(String candidate) {
			// Inspiration:
			// http://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
			System.out.println(TAB + TAB + "==- CountryCode Kosherness Check");
			try {
				cc.valueOf(candidate);
				System.out.println(TAB + TAB + "CC found to be kosher enough.");
				System.out.println(TAB + TAB + "==-> CountryCode NORMAL EXIT");
				return true;

			} catch (Exception e) {
				System.err.println(TAB + TAB
						+ "CC extends the bearable kosherness.");
				System.out.println(TAB + TAB
						+ "==-> CountryCode check UNSUXXESSFUL");
				return false;
			}
		}
	} // ENUM

}
