/**
 * created: Dec 8, 2014 11:42:54 PM
 */
package Nuhker;

import java.io.IOException;

import joptsimple.OptionParser;

/**
 * @author coder037@xyz.ee
 * @identity 0f
 *           a1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 * 
 */

public class DataDiver {

	private final static String TAB = "\t";

	/**
	 * @param args
	 */
	public static void main(DefaultParametersForRun Current) {
		boolean upperLevel = false;
		int currentLevel = Current.getCurrentLevelOfRecursion();
		System.out.println("===-> Level " + currentLevel + " entered.");
		
		if (Current.getDepthOfRecursion() == currentLevel) {
			upperLevel = true;
			System.out.println(TAB + "this is the UPPER level: " + currentLevel);
		}

		if (0 == currentLevel) { // Remaining depth = 0
			System.out.println("==== --{The seabed has been reached}-- ====");
			System.out.println(TAB + "RETURN from this tree.");
			return;
		}
		
		 { // Work to do, extra level(s) to dive
			// Announcement and Decrement
			System.out.println(TAB + "Recursion level so far was: "
					+ currentLevel);
			Current.setCurrentLevelOfRecursion(currentLevel - 1);
			System.out.println(TAB + "Recursion level for siblings is: "
					+ Current.getCurrentLevelOfRecursion());
		}
		
		// Two alternatives - what kind of work to do.
		
			// #### Alternative 1 - upper level
			if (upperLevel) { // RIPE thing
				String[] targetList = null;
				System.out.println(TAB + "This is the first level of the recursion");
				System.out.println(TAB + "Country we work with: "
						+ Current.getCountryCodeToWorkWith());


				String toBeParsed = "";
				try {
					toBeParsed = ParseRIPEConstituency.grabCountryDescription(Current.getCountryCodeToWorkWith());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("Unable to parse constituency. RIPE connection error. BAILOUT");
					e.printStackTrace();
					System.err.println("ABNORMAL Bailout.");
					System.exit(66);
					// http://www.opensource.apple.com/source/Libc/Libc-320/include/sysexits.h
				}

				String[] hasBeenParsed = ParseRIPEConstituency.asnJsonParser(toBeParsed);
				int countOfASNsObtained = hasBeenParsed.length;
				System.out.println("Count of ASNs in within the constituency: " + countOfASNsObtained);
				//
//				// Print it out to be very sure
//				for (int k = 0; k < countOfASNsObtained; k++) {
//					System.out.println(hasBeenParsed[k] + " ");
//				}
				
				// 
				System.out.println("===-< calling the next level.");	
				// call MYSELF for each argument found.	
				main(Current); // RECURSIVELY CALLING OUT ITSELF
		} else {
			// Any other level except the upper
			// #### Alternative 2 - normal AS/FQDN work
			
			System.out.println(TAB + "AS or FQDN/URL?");

			// Doing difference.
			System.out.println("Here we should wait for: " + Current.getMinTimeBetweenGSBRequests() + " msec");
			try {
			    Thread.sleep(Current.getMinTimeBetweenGSBRequests());                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			System.out.println("===-< calling the next level.");
		
			// PARSE Reputation
		main(Current); // RECURSIVELY CALLING OUT ITSELF
			
		} 
		return;

	}

}
