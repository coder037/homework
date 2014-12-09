/**
 * created: Dec 8, 2014 11:42:54 PM
 */
package Nuhker;

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
		System.out.println("===-> Level " + Current.getDepthOfRecursion() + " entered.");

		if (0 != Current.getDepthOfRecursion()) {
			System.out.println(TAB + "Recursion level so far: "
					+ Current.getDepthOfRecursion());
			Current.setDepthOfRecursion((Current.getDepthOfRecursion() - 1));
			System.out.println(TAB + "Recursion level for siblings: "
					+ Current.getDepthOfRecursion());

			if (Current.getDepthOfRecursion() == Current
					.getCurrentLevelOfRecursion()) {
				System.out.println(TAB + "This is the first level of the recursion");
				System.out.println(TAB + "Country we work with: "
						+ Current.getCountryCodeToWorkWith());
				
				// RIPE thing.
				// call it and count the results
				System.out.println("===-< calling the next level.");	
				// call MYSELF for each argument found.
				
			} else {
				// This is not the upper level
				System.out.println(TAB + "AS or FQDN/URL?");

				// Doing difference.
				
				System.out.println("===-< calling the next level.");
			
				// PARSE Reputation
			main(Current); // RECURSIVELY CALLING OUT ITSELF
			}
		} else { // Remaining Depth = 0
			System.out.println("==== --{The seabed has been reached}-- ====");
		}
		return;

	}

}
