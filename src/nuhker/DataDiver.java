/**
 * created: Dec 8, 2014 11:42:54 PM
 */
package nuhker;

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

	public static void waitFor(DefaultParametersForRun thisCopy) {
		long waitTime = thisCopy.getMinTimeBetweenGSBRequests();
		System.out.println("Here we should wait for: " + waitTime + " msec");
		try {
		    Thread.sleep(waitTime); //msecs
		} catch (InterruptedException enough) {
		    Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(DefaultParametersForRun Current) {
		long nowTime = System.nanoTime();
		boolean upperLevel = false;
		int currentLevel = Current.getCurrentLevelOfRecursion();
		System.out.println("===-> DataDiver Level " + currentLevel + " entered.");
		String outputFileName =  Current.getFilenameForOutput();
		System.out.println("===----> Results to be appended to: " + outputFileName);
		
		long billion = 1000000000;
		long thousand = 1000;
		long runTimeSoFar = (nowTime - Current.getStartTime());
		long remainedSecs= (Current.getMaxTimeToRunBeforeKilled() / thousand - (runTimeSoFar / billion));
		System.out.println(TAB + "Runtime so far: " + (runTimeSoFar / billion)+ " sec(s), remained: "  + (remainedSecs) + " sec(s) until killed.");
		// System.out.println(TAB + "Time remained until the KILL: " + (remainedSecs)  + " sec(s)");
		
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

				nuhker.TypeWriter.main(outputFileName, "*** THIS IS THE HEADER for country " + Current.getCountryCodeToWorkWith() + " ***");
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
				System.out.println(TAB + "DataDiver: Need to check: " + countOfASNsObtained + " ASNs");

				for(String target : hasBeenParsed) {
				    System.out.println(TAB + TAB + target);
				    Current.setCurrentTarget(ParseGSBReputation.asn2Colon(target));
				    waitFor(Current);
				    System.out.println("===-< calling the next level.");
				    main(Current); // RECURSIVELY foreach argument
				}
				
		} else {
			// Any other level except the upper
			// #### Alternative 2 - normal AS/FQDN work
			
			System.out.println("Target is: " + Current.getCurrentTarget() );
			System.out.println(TAB + "AS or FQDN/URL?");

			// Doing difference.

			System.out.println("===-< calling the next level.");
		
			// THIS IS WHERE WE ARE : ToDo! 
			
			// FOREACH argument
			// nuhker.TypeWriter.main(outputFileName, argument);

				// PARSE Reputation
			waitFor(Current);
			main(Current); // RECURSIVELY CALLING OUT ITSELF
			
		} 
		return;

	}

}
