/**
 * created: Nov 23, 2014 12:44:46 AM
 */
package Nuhker;

/**
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *
 */



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.EnumSet;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.security.MessageDigest;

public class Run {

    private final static String AUTHOR = "0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff";
	private final static String VERSION = "0.3";
	private final static String TAB = "\t";
	
	
	public enum CountryCodesServedByRIPE {
		 AX, AL, AD, AM, AT, AZ, BH, BY, BE, BA,
		 BG, HR, CY, CZ, DK, EE, FO, FI, FR, GE,
		 DE, GI, GR, GL, GG, VA, HU, IS, IR, IQ,
		 IE, IM, IL, IT, JE, JO, KZ, KW, KG, LV,
		 LB, LI, LT, LU, MK, MT, MD, MC, ME, NL,
		 NO, OM, PS, PL, PT, QA, RO, RU, SM, SA,
		 RS, SK, SI, ES, SJ, SE, CH, SY, TJ, TR,
		 TM, UA, AE, GB, UZ, YE;

		public static boolean isKosher(String candidate) {
			// Inspiration: http://stackoverflow.com/questions/4936819/java-check-if-enum-contains-a-given-string
			System.out.println();
		    System.out.println("========== CountryCode Kosherness Check ==========");
			try {
	            CountryCodesServedByRIPE.valueOf(candidate);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
		}
	} //ENUM

	
	
	public static boolean checkConformity (String [] arguments) {
		OptionSet args = null; // declaration separated due to subsequent TRY clause
		OptionParser preParser = ParserGrammar.main();
		
		System.out.println();
	    System.out.println("========== Option Conformity Check ==========");
		// WARNING, NEXT 15 lines are not considered fully mine, but a neat trick from
		// https://github.com/martinpaljak/GlobalPlatform/blob/master/src/openkms/gp/GPTool.java
		// lines 133-147
		try {
			args = preParser.parse(arguments);
			// Try to fetch all values so that their format is checked before actual usage
			for (String s: preParser.recognizedOptions().keySet()) {args.valuesOf(s);}
		} catch (OptionException e) {
			if (e.getCause() != null) {
				System.err.println("PARSE ERROR discovered, REASON:");
				System.err.println(TAB + e.getMessage() + ": " + e.getCause().getMessage());
			} else {
				System.err.println("PARSE ERROR discovered with UNEXPLAINED reason:");
				System.err.println(TAB + e.getMessage());
			}
			System.err.println();
				// preParser.printHelpOn(System.err);
			// END of foreign code
			System.out.println("ERROR: Non-conformant options. Bailout");
			System.exit(1);
		}
		// END of somebody's else code.
		return true; // We reached this point, thus no bailout
	}
	
	
	public static String checkTheAuthorship (String argument) throws Exception {
		String message = "";
		
	       System.out.println();
	       System.out.println("========== Authorship test ==========");
		MessageDigest hashhash = MessageDigest.getInstance("SHA-512");
		// WARNING: NEXT 7 lines of code are of SOMEBODY'S ELSE authorship:
		//Inspiration: http://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
	    byte[] sha512bytes = hashhash.digest(argument.getBytes());
	    //Inspiration: convert the byte to hex format http://www.mkyong.com/java/java-sha-hashing-example/
	    StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sha512bytes.length; i++) {
          sb.append(Integer.toString((sha512bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        // END of the WARNING scope
        
        if (AUTHOR.equals(sb.toString())) {
        	message = TAB + TAB + "Copyright: " + argument + " (validated cryptographically).";
        } else {
        	message = TAB + TAB + "You seem not to know who the actual author is...";
        }
		return message;
	}
	
	
	
	public static DefaultParametersForRun parseContent (String [] arguments) throws Exception {
		OptionParser postParser = ParserGrammar.main();
		OptionSet cliOptions = postParser.parse(arguments);
		
        DefaultParametersForRun RunTimes = new DefaultParametersForRun();
    	
        System.out.println();
        System.out.println("========== Option Parser ==========");
        
		// Somewhat special options FIRST
		
		if (cliOptions.has( "d" )) {
        	System.out.println(TAB + "Option d was found");
        	// Set the global DebugLevel from here 
        } else {
        	// or the DebugLEvel = Default;
        }
		

		if (cliOptions.has("help")) {
			postParser.printHelpOn(System.out);
			System.exit(0);
		}
		
		if (cliOptions.has( "v" )) {
			 
	       	System.out.println(TAB + "Nuhker version: " + VERSION);

	        	System.exit(0);
	    }
		 
	    if (cliOptions.has( "c" )) {
	        	System.out.println(TAB + "Option c was found");
	        	String optionValue = (String)cliOptions.valueOf("c");
	        	if (CountryCodesServedByRIPE.isKosher(optionValue)) {
		        // if (2 == optionValue.length()) {
	        		System.out.println(TAB + TAB + "CountryCode is kosher: " + optionValue); 
	        	} else {
	        		System.out.println(TAB + TAB + "Man, I deeply doubt *"
	        				+ optionValue + "* is a CountryCode RIPE is aware of.");
	        		System.out.println("BailOut");
		        	System.exit(1);
	        	}

	     }
		 
	     if (cliOptions.has( "x" )) {
	        	System.out.println(TAB + "Option x was found which isn't yet implemented. Anyway, thnx for supporting it!");
	        }
	       
		 
		 // Special copyright phuck
	     
	        if (cliOptions.has( "C" )) {
	        	System.out.println(TAB + "Option C was called to expose the copyright message");
        	    String optionC = (String)cliOptions.valueOf("C");
        	    System.out.println(TAB + TAB + "and it had a sub-option: " + optionC); 
        	    
        	    System.out.println(checkTheAuthorship(optionC));
        	    	   
	        }

        
        // More generic options (coefficients / parameters) to runtime
        
        if (cliOptions.has( "M" )) {
        	System.out.println(TAB + "Option M was found");
        	String optionValue = String.valueOf(cliOptions.valueOf("M"));
        	System.out.println(TAB + TAB + "and it had a suboption: " + optionValue);
        	RunTimes.setMaxTimeToRunBeforeKilled( Integer.parseInt(optionValue) ); //DONE!!!
        	// However, see reznic http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java
        }
        

        if (cliOptions.has( "R" )) {
        	System.out.println(TAB + "Option R was found");
        	String optionValue = String.valueOf(cliOptions.valueOf("R"));
        	System.out.println(TAB + TAB + "and it had a suboption: " + optionValue); 
        }
        
        if (cliOptions.has( "t" )) {
        	System.out.println(TAB + "Option t was found");
        	String optionValue = String.valueOf(cliOptions.valueOf("t"));
        	System.out.println(TAB + TAB + "and it had a suboption: " + optionValue); 
        }
        

        
        if (cliOptions.has( "o" )) {
        	System.out.println(TAB + "Option o was found");
        	String optionValue = (String)cliOptions.valueOf("o");
        	System.out.println(TAB + TAB + "and it had a suboption: " + optionValue); 
        }

		
		return RunTimes;
	}
	
	
		// ToDo:
		//    Candidates for separate methods:
		//		1. syntax check
		// 		2. an overloaded method to set undefault arguments
	
	
		/**
		 * @param args
		 */
			public static void main(String[] argv) throws Exception {

				System.out.println(" ======= Start ===== ");
				
	

				// OptionParser postParser = ParserGrammar.main();
				// OptionSet cliOptions = parser.parse(argv);
				// OptionSet cliOptions = postParser.parse(simulation1);

		        // Currently we simulate (until we build the static CLI program)
					String[] simulation1 = { "--country", "EE", "--copyright", "Some Name", "--xtra", "-o", "somefilename-001", "-R", "4", "-t", "2800", "-d", "7", "-M", "86400" };
					String[] simulation2 = {"-c", "EE", "-d", "5", "-M", "80000", "-n", "-o", "somefilename-001", "-R", "4", "-t", "2800"};
					String[] simulation3 = {"--help"};
				
					String[] effectiveOptions = simulation1;
				checkConformity(effectiveOptions); // Else bailout
				DefaultParametersForRun FinalOptions = parseContent(effectiveOptions);
				

		        // printout of ACTUAL options
				System.out.println();
				System.out.println("===================  M A I N ==============");
				System.out.println("MAIN: READY to attempt the actual launch...");
				System.out.println(TAB + "Debuglevel has been set as: " + FinalOptions.getDebugLevel() + " of max 7");
				System.out.println(TAB + "We seek Malwared Sites for country: " + FinalOptions.getCountryCodeToWorkWith());
				System.out.println(TAB + "Recursion level has been limited to: " + FinalOptions.getDepthOfRecursion());
				System.out.println(TAB + "Time allocated for the run is:      " + FinalOptions.getMaxTimeToRunBeforeKilled() + " millisecs");
				System.out.println(TAB + "Start UNIX msec Epoch Time  was: " + FinalOptions.getStartTime());
				System.out.println(TAB + "Time prognosis until the END is: " + ( FinalOptions.getStartTime() + FinalOptions.getMaxTimeToRunBeforeKilled()) );
				System.out.println(TAB + "Guard time between any other GSB request is: " + FinalOptions.getMinTimeBetweenGSBRequests() + " millisecs");
				System.out.println(TAB + "FILENAME for the output: " + FinalOptions.getFilenameForOutput());
		        
		        // HERE starts the ACTUAL LAUNCH CODE
		        
		        // Parse RIPE for that country
		        // Call Tic-Tac(Options, Target)
		        //
		        System.out.println(" =======  END  ===== ");
//				System.exit(0);
			}



	//========================================================
		

}
