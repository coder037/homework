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

	private final static String AS = "AS";
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
		int nextLevel = (currentLevel - 1);
		System.out.println("===-> DataDiver Level " + currentLevel + " entered.");
		String outputFileName =  Current.getFilenameForOutput();
		// DEBUG System.out.println("===----> Results to be appended to: " + outputFileName);
		
		long billion = 1000000000;
		long thousand = 1000;
		long runTimeSoFar = (nowTime - Current.getStartTime());
		long remainedSecs= (Current.getMaxTimeToRunBeforeKilled() / thousand - (runTimeSoFar / billion));
		System.out.println(TAB + "Runtime so far: " + (runTimeSoFar / billion)+ " sec(s), remained: "  + (remainedSecs) + " sec(s) until killed.");
		// System.out.println(TAB + "Time remained until the KILL: " + (remainedSecs)  + " sec(s)");
		
		if (Current.getDepthOfRecursion() == currentLevel) {
			upperLevel = true;
			System.out.println(TAB + "this is the FIRST call of the DataDiver, Level : " + currentLevel);
		}

		if (0 == currentLevel) { // Remaining depth = 0
			System.out.println("==== --{Recursion DEPTH has been reached}-- ====");
			// System.out.println(TAB + "RETURN from this tree.");
			return;
		}
		else {
		
		// Work to do, extra level(s) to dive
			// Announcement and Decrement
			System.out.println(TAB + "====¤ Recursion level "
					+ currentLevel + " changed to " + nextLevel);
			Current.setCurrentLevelOfRecursion(nextLevel);
		}


		 
		// ================================================
		// Two alternatives - what kind of work to do.
		
			// #### Alternative 1 - upper level
			if (upperLevel) { // RIPE thing
				String cc = Current.getCountryCodeToWorkWith();
				String toBeParsed = "";
				System.out.println(TAB + "We only call this ONCE (ALT1)");
				System.out.println(TAB + TAB + "for a country: " + cc);
				
				// Our primitive Logger called
				nuhker.TypeWriter.main(outputFileName, "*** THIS IS THE HEADER for country " + cc + " ***");
				
				try {
					toBeParsed = ParseRIPEConstituency.grabCountryDescription(cc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("Unable to parse constituency. RIPE connection error. BAILOUT");
					e.printStackTrace();
					System.err.println("ABNORMAL Bailout.");
					System.exit(66);
					// http://www.opensource.apple.com/source/Libc/Libc-320/include/sysexits.h
				}

				String[] resultOfFirstParsing = ParseRIPEConstituency.asnJsonParser(toBeParsed);
				int countOfASNsObtained = resultOfFirstParsing.length;
				System.out.println(TAB + "DataDiver: Need to check: " + countOfASNsObtained + " ASNs");

				for(String target : resultOfFirstParsing) {
				    System.out.println(TAB + TAB + "Yet another AS to check for: " + target);
				    Current.setCurrentTarget(ParseGSB.asn2Colon(target));
				    waitFor(Current);
				    System.out.println("===-< calling the next level.");

				    main(Current); // RECURSIVELY foreach argument
				    
				}
				System.out.println("===-! DONE withAll arguments for the country: " + cc);
				System.out.println(TAB + "===-< end of the UPPER level.");
		} // END of Upper Level
			
			
			
			else { // #### Alternative 2 
					// 		- any other level except the upper one

			System.out.println("===+===-> regular AS/FQDN parsing (ALT2).");
			String target2Dive = Current.getCurrentTarget();
			System.out.println(TAB + "Target is: " + target2Dive );
			System.out.println(TAB + "+===+ Calling ParseGSB for : " + target2Dive );
			String lowerTargets[] = ParseGSB.badReputation(target2Dive);
			int countOfTargetsOnThisLevel = lowerTargets.length;
			System.out.println(TAB + "+===+ Got " + countOfTargetsOnThisLevel + " subtargets to check under this target: " + target2Dive);
			
			String subTarget = "";
			
			for(String target : lowerTargets) {
			    System.out.println(TAB + TAB + target);
			    
			    // in case of AS: prepend "AS:" particle
			   
//			    if (target.contains(AS)) {
//					System.out.println(TAB + "ASN needs an AS: particle to be prepended : " + target);
//					subTarget = (ParseGSB.asn2Colon(target));
//				} else {
			    // however: in case of FQDN/URL: pass arg transparently.
//					subTarget = target;
//				}
			    System.out.println(TAB + TAB + "An actual string to pass down is: " + target);
			    Current.setCurrentTarget(target);
			    waitFor(Current);
			    System.out.println("===-< Going down, Mr Demon, from level " + currentLevel + " to level " + nextLevel );
				nuhker.TypeWriter.main(outputFileName, target);
				String toBeParsed = "";
				// BS but:
				// Current.setCurrentLevelOfRecursion(currentLevel - 1);
			    main(Current); // RECURSIVELY foreach argument
			    // nuhker.TypeWriter.main(outputFileName, argument);
			}
			System.out.println("===+===-! DONE withAll arguments");
		} 
		System.out.println("===+===-< out of this parsing dive");
		return;

	}

}
