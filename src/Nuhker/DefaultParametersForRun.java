/**
 * created: Dec 1, 2014 2:33:48 AM
 */
package Nuhker;

/**
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *
 */
public class DefaultParametersForRun {

	
	// Choice of possible country codes RIPE serves according
	// http://www.ripe.net/lir-services/member-support/info/list-of-members/list-of-country-codes-and-rirs
    // as of 2014-12-02
	
	public enum countryCodes {
		 AX, AL, AD, AM, AT, AZ, BH, BY, BE, BA,
		 BG, HR, CY, CZ, DK, EE, FO, FI, FR, GE,
		 DE, GI, GR, GL, GG, VA, HU, IS, IR, IQ,
		 IE, IM, IL, IT, JE, JO, KZ, KW, KG, LV,
		 LB, LI, LT, LU, MK, MT, MD, MC, ME, NL,
		 NO, OM, PS, PL, PT, QA, RO, RU, SM, SA,
		 RS, SK, SI, ES, SJ, SE, CH, SY, TJ, TR,
		 TM, UA, AE, GB, UZ, YE;
		}
	
	
	// Demonstrating some not so OOP tricks
	
	private int maxTimeToRunBeforeKilled = 80000000; // in milliseconds
	private int minTimeBetweenGSBRequests = 2800; // in milliseconds
	private int depthOfRecursion = 7;
	// most likely this should use LEVELS not int numbers .... however:
	private int debugLevel = 4;
	// should be enum
	private String countryCodeToWorkWith = "EE";
	private String FilenameForOutput = "output-Systime.txt";
	
//	-M, --max-running-time <Integer>  Max time in seconds we should run,     
//	                                    kill then; default=80000 secs        
//	-R, --recursion-level <Integer>   recursion depth; default=4             
//	-V, -d, --verbose <Integer>       Debuglevel 0-7; default=4              
//	-c, --country                     Enter country code to work with;       
//	                                    default=EE                           
//	-o, --filename, --output          Name of the output file; default=output
//	-t, --timeout <Integer>           Timeout between requests; default=2800 
//	                                    msecs or Google will block you       

	
	
	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
