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
import java.util.logging.Logger;

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
 */
public class DataDiver {

	private final static String AS = "AS";
	private final static String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName() );

	
	/**
	 * The MAIN logic of the whole package - an actual recursion.
	 * 
	 * This method is invoked with a copy of previously initialized
	 * structure of DefaultParms type.
	 * 
	 * * There are two main flows:
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
	 */
	public static void entryPoint(DefaultParms LevelVariables) {
		// Introductory part - safeguards and calculations
		DefaultParms Current = LevelVariables;
		long nowTime = System.nanoTime();
		int waitTime = Current.getMinTimeBetweenGSBRequests();
		int currentLevel = Current.getCurrentLevelOfRecursion();
		boolean upperLevel = false;
		LOG.fine("===-> DataDiver HEADER part, level" + currentLevel + " entered.");
	
		String outputFileName =  Current.getFilenameForOutput();
		LOG.fine(TAB +"output filename defined as: " + outputFileName);
		
		long billion = 1000000000;
		long thousand = 1000;
		long runTimeSoFar = (nowTime - Current.getStartTime());
		long remainedSecs= (Current.getMaxTimeToRunBeforeKilled() / thousand - (runTimeSoFar / billion));
		LOG.finer(TAB + TAB + TAB + "Runtime so far: " + (runTimeSoFar / billion)+ " sec(s); remained: "  + (remainedSecs) + " sec(s) until killed.");
		
		if (0 > remainedSecs) {
			Func.publicizeStatistics();
			LOG.warning("==== TIMEOUT REACHED - End Forced ===");
			LOG.finer(TAB + "might be we attempt some houskeeping before that.");
			System.exit(0);
		}
		
		if (0 == currentLevel) { // Remaining depth = 0
			LOG.finer("==== --{Recursion FLOOR reached}-- ====");
			return;
		}

		if (Current.getDepthOfRecursion() == currentLevel) {
			upperLevel = true;
			LOG.info(TAB + "Still on the Uppermost level=" +  + currentLevel );
		}


		// ================================================
		// Two alternatives - what kind of work to do.
		
			// #### Alternative 1 - upper level
			if (upperLevel) { // RIPE thing
				String cc = Current.getCountryCodeToWorkWith();
				String toBeParsed = "";
				LOG.info(TAB + "We only call this ONCE (ALT1)");
				LOG.info(TAB + TAB + "for a country: " + cc);
				
				// Our primitive FileLogger called
				LOG.fine(TAB + "Some NOT YET DONE filewriting thingy");
				TypeWriter.main(outputFileName, "*** THIS IS THE HEADER for country " + cc + " ***");
				
				try {
					toBeParsed = ParseRIPE.grabCountryDescription(cc);
				} catch (IOException e) {
					LOG.severe("Unable to parse constituency. RIPE connection error. BAILOUT");
					e.printStackTrace();
					LOG.severe("ABNORMAL Bailout.");
					System.exit(66);
					// http://www.opensource.apple.com/source/Libc/Libc-320/include/sysexits.h
				}

				String[] resultOfFirstParsing = ParseRIPE.asnJsonParser(toBeParsed);
				// ^ this ^ attay is to be written into a DB and textfile
				
				int countOfASNsObtained = resultOfFirstParsing.length;
				LOG.info(TAB + "DataDiver got " + countOfASNsObtained + " ASNs from the ParseRIPE");

				for(String targetASN : resultOfFirstParsing) {
				    LOG.fine(TAB + TAB + "Yet another AS to check for: " + targetASN);
				    Current.setCurrentTarget(Func.asn2Colon(targetASN));
				    Func.delay(waitTime);
				    int nextLevel = (currentLevel - 1);
				    LOG.finer("DataDiver called recursively: ");
				    LOG.fine("===-< Descending from level: " + currentLevel + " to level " + nextLevel);
				    Current.setCurrentLevelOfRecursion(nextLevel);
				    entryPoint(Current); // RECURSIVELY foreach argument  
				}
				LOG.info("===-! DONE with the All arguments for  country=" + cc);
				LOG.info(TAB + "===-< ascending from level: " + currentLevel);
		} // END of Upper Level
			
	
			else { // #### Alternative 2 
					// 		- any other level except the upper one

			// First PRINTout how much entries are there on the list xN
				Func.publicizeStatistics();
			
			String target2Dive = Current.getCurrentTarget();
			String fullTarget = "";
			LOG.fine("===+===-> regular AS/FQDN parsing (ALT2), target=" + target2Dive);
			LOG.finest(TAB + "+===+ Calling ParseGSB for : " + target2Dive );
			String subTargets[] = ParseGSB.badReputation(target2Dive);
			int countOfTargetsOnThisLevel = subTargets.length;
			LOG.fine(TAB + "+===+ Got " + countOfTargetsOnThisLevel + " subtargets to check under this target: " + target2Dive);
						
			for(String target : subTargets) {
				fullTarget = target;
				// OTSUSTAJA võiks välja kutsuda juba siin -
				// - saaks AS kirjelduse ka kätte regexp ( ja ) vahelt.
			    LOG.info(TAB + TAB + "DownStairs Decision: --=---==---=-- next subtarget: " + target);
			    // Hahaa: Exception in thread "main" java.lang.NumberFormatException: For input string: "facebook.com/NASDAQOMXStockholm/"
			    if ( AS.equals (  Func.whatIsIt(target) ) ) {
			    	
			    	target = Func.asn2Colon(Func.removeASDescr(target));
			    	LOG.finer(TAB + TAB + TAB +  "DownStairs: an AS NAME (colonized) : " + target);
			    } else {
			    	LOG.finer(TAB + TAB + TAB +  "DownStairs: an URL left as it was  : " + target);
			    }
			    // SIIN tuleb OTSUSTAJA välja kutsuda fullTarget kallal
			    // Decider ning 5-6 olemasoleva kategooria kohta
			    // EE on vaja alla anda kuidagi, ülejäänud arvutatakse
			    // isknown, isASN, isIP, is-cc
			    // ja typewriter kutsutakse maha kirjutama.
			    // loginimi on vaja alla anda kuidagi
			    // ja oma varasema ASN listiga võrdlemiseks IP'sid kontrollida
			     if (! DBze.knownSites.contains(target)) {
			    	   DBze.knownSites.add(target);
			    	   // ja tulemused kirja panna
			    	   TypeWriter.main(outputFileName, target);
			    	   // publicizeStatistics() tuleb ka siia sisse panna
			    	   
			    	   // siin OTSUSTAJA enam sekkuda ei saa:
			    	   // OTSUSTAJA tagastuskood on: boolean kasTegeleda?!
			    	   Current.setCurrentTarget(target);
			    	   int nextLevel = (currentLevel - 1);
			    	   LOG.fine("===-===-===-===-===-< SUBMERGING from level " + currentLevel + " to level " + nextLevel );
			    	   Current.setCurrentLevelOfRecursion(nextLevel);
			    	   
			    	   // spare some extra seconds
			    	   if (nextLevel > 0) {
			    	   Func.delay(waitTime);
			    	   }
			    	   entryPoint(Current); // RECURSIVELY foreach argument
			        } else {
			        	// Target was already recorded to DB. Not wasting time
			        	LOG.fine("---+---+---+---+---> Target " + target + " was previously KNOWN, a noGo.");
			        }
			}
			LOG.fine("===+===-! DONE with All arguments");
		} 
		LOG.fine("===+===-< End of oxygen, out of this Parsing Dive");
		return;

	}



}
