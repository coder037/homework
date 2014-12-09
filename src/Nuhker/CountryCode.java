/**
 * created: Dec 8, 2014 11:12:05 PM
 */
package Nuhker;


/**
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *
 */
public class CountryCode {
	private final static String TAB = "\t";
	
	public enum cc {
		AX, AL, AD, AM, AT, AZ, BH, BY, BE, BA, BG,
		HR, CY, CZ, DK, EE, FO, FI, FR, GE, DE, GI,
		GR, GL, GG, VA, HU, IS, IR, IQ, IE, IM, IL,
		IT, JE, JO, KZ, KW, KG, LV, LB, LI, LT, LU,
		MK, MT, MD, MC, ME, NL, NO, OM, PS, PL, PT,
		QA, RO, RU, SM, SA, RS, SK, SI, ES, SJ, SE,
		CH, SY, TJ, TR, TM, UA, AE, GB, UZ, YE;

		// Choice of possible country codes RIPE serves according
		// http://www.ripe.net/lir-services/member-support/info/list-of-members/list-of-country-codes-and-rirs
		// as of 2014-12-02
		
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
				System.err.println(TAB + TAB + "CC extends the bearable kosherness.");
				System.out.println(TAB + TAB + "==-> CountryCode check UNSUXXESSFUL");
				return false;
			}
		}
	} // ENUM
	
}
