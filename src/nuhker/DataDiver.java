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

import java.io.IOException;
import java.util.ArrayList;

import joptsimple.OptionParser;

/**
 * DataDiver is the class that does the actual recursion.
 * For each targeted FQDN, it will launch some OSI on the net
 * and record the parsed results.
 * Then it will recursively call itself for all results found.
 * The permitted depth of the recursion is defined by CLI option
 * -R (an integer).
 * 
 * created: Dec 8, 2014 11:42:54 PM
 * @author coder037@xyz.ee
 * @identity 0f
 *           a1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 * 
 */
public class DataDiver {

	private final static String AS = "AS";
	private final static String TAB = "\t";

	
	/**
	 * The MAIN logic of the whole package - an actual recursion.
	 * 
	 * This method is invoked with a copy of previously initialized
	 * structure of DefaultParms type.
	 * 
	 * @param DefaultParms
	 * 
	 * Several parameters are passed between down the recursion levels
	 * including these:
	 *    - target - whom to OSI
	 *    - depth level - (which level I am on)
	 *    - max depth of the recursion 
	 *    - max working time, after which the program will stop itself.
	 *  
	 * Parameters are driven via getters setters interface of the
	 * DefaultParms class
	 * 
	 * There are two main flows:
	 *    on the UPPER level, a country code is transformed into a
	 *    corresponding array of ASNs belonging to that country.
	 *    This is done calling ParseRIPE.grabCountryDescription(cc)
	 *    
	 *    in the remaining levels, ParseGSB.badReputation(target2Dive)
	 *    is called which returns an array of String[] subtargets
	 * 
	 * A scrupulous care has been excercised to pass both ASNs and
	 * FFQDNs via the same methods (and this will need some back-and-
	 * -forth translation between 12345 <--> AS:12345 forms.  
	 */
	public static void main(DefaultParms LevelVariables) {

		DefaultParms Current = LevelVariables;
		long nowTime = System.nanoTime();
		int waitTime = Current.getMinTimeBetweenGSBRequests();
		int currentLevel = Current.getCurrentLevelOfRecursion();
		boolean upperLevel = false;

		// Introductory part - safeguards and calculations
		
		System.out.println("===-> DataDiver Level " + currentLevel + " entered.");
		String outputFileName =  Current.getFilenameForOutput();
		
		long billion = 1000000000;
		long thousand = 1000;
		long runTimeSoFar = (nowTime - Current.getStartTime());
		long remainedSecs= (Current.getMaxTimeToRunBeforeKilled() / thousand - (runTimeSoFar / billion));
		System.out.println(TAB + "Runtime so far: " + (runTimeSoFar / billion)+ " sec(s), remained: "  + (remainedSecs) + " sec(s) until killed.");
		
		if (0 > remainedSecs) {
			System.out.println("==== DB SIZE before killed: " + DBze.knownSites.size() + " records");
			System.out.println("==== TIMEOUT REACHED - End Forced ===");
			System.exit(0);
		}
		
		if (0 == currentLevel) { // Remaining depth = 0
			System.out.println("==== --{Recursion FLOOR reached}-- ====");
			// System.out.println(TAB + "RETURN from this tree.");
			return;
		}

		if (Current.getDepthOfRecursion() == currentLevel) {
			upperLevel = true;
			System.out.println(TAB + "this is the FIRST call of the DataDiver, Level : " + currentLevel);
		}

		// DEBUG System.out.println("===----> Results to be appended to: " + outputFileName);
		 
		// ================================================
		// Two alternatives - what kind of work to do.
		
			// #### Alternative 1 - upper level
			if (upperLevel) { // RIPE thing
				String cc = Current.getCountryCodeToWorkWith();
				String toBeParsed = "";
				System.out.println(TAB + "We only call this ONCE (ALT1)");
				System.out.println(TAB + TAB + "for a country: " + cc);
				
				// Our primitive Logger called
				TypeWriter.main(outputFileName, "*** THIS IS THE HEADER for country " + cc + " ***");
				
				try {
					toBeParsed = ParseRIPE.grabCountryDescription(cc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("Unable to parse constituency. RIPE connection error. BAILOUT");
					e.printStackTrace();
					System.err.println("ABNORMAL Bailout.");
					System.exit(66);
					// http://www.opensource.apple.com/source/Libc/Libc-320/include/sysexits.h
				}

				String[] resultOfFirstParsing = ParseRIPE.asnJsonParser(toBeParsed);
				int countOfASNsObtained = resultOfFirstParsing.length;
				System.out.println(TAB + "DataDiver: Need to check: " + countOfASNsObtained + " ASNs");

				for(String targetASN : resultOfFirstParsing) {
				    System.out.println(TAB + TAB + "Yet another AS to check for: " + targetASN);
				    Current.setCurrentTarget(Func.asn2Colon(targetASN));
				    Func.delay(waitTime);
				    int nextLevel = (currentLevel - 1);
				    System.out.println("===-< Descending from level: " + currentLevel + " to level "+ nextLevel);
				    Current.setCurrentLevelOfRecursion(nextLevel);
				    main(Current); // RECURSIVELY foreach argument  
				}
				System.out.println("===-! DONE withAll arguments for the country: " + cc);
				System.out.println(TAB + "===-< end of the UPPER level.");
		} // END of Upper Level
			
	
			else { // #### Alternative 2 
					// 		- any other level except the upper one

			// First PRINTout how much entries are there on the list
				System.out.println("===---===---===--- SIZE: " + DBze.knownSites.size() + " records");
				
			System.out.println("===+===-> regular AS/FQDN parsing (ALT2).");
			String target2Dive = Current.getCurrentTarget();
			System.out.println(TAB + "Target is: " + target2Dive );
			System.out.println(TAB + "+===+ Calling ParseGSB for : " + target2Dive );
			String subTargets[] = ParseGSB.badReputation(target2Dive);
			int countOfTargetsOnThisLevel = subTargets.length;
			System.out.println(TAB + "+===+ Got " + countOfTargetsOnThisLevel + " subtargets to check under this target: " + target2Dive);
			
						
			for(String target : subTargets) {
			    System.out.println(TAB + TAB + "An actual string to pass down is: " + target);

			     if (! DBze.knownSites.contains(target)) {
			    	   DBze.knownSites.add(target);

			    	   Current.setCurrentTarget(target);
			    	   TypeWriter.main(outputFileName, target);

			    	   int nextLevel = (currentLevel - 1);
			    	   System.out.println("===-< Going down, Mr Demon, from " + currentLevel + " to level " + nextLevel );
			    	   Current.setCurrentLevelOfRecursion(nextLevel);
			    	   Func.delay(waitTime);
			    	   main(Current); // RECURSIVELY foreach argument
			        } else {
			        	System.out.println("---+---+---+---+---> Target " + target + " already KNOWN!");
			        }
			}
			System.out.println("===+===-! DONE with All arguments");
		} 
		System.out.println("===+===-< out of this parsing dive");
		return;

	}



}
