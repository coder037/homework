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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.security.MessageDigest;

public class Run {

	private final static String IDENTITY = "0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff";
	private final static String OPT_COPYRIGHT = "copyright";
	private final static String OPT_COUNTRY = "country";
	private final static String OPT_MAXRUN = "max-running-time";
	private final static String OPT_OUTPUT = "output";
	private final static String OPT_RECURSIONLEVEL = "recursion-level";
	private final static String OPT_TIMEOUT = "timeout";
	private final static String OPT_VERBOSE = "verbose";
	private final static String OPT_VER = "version";
	private final static String OPT_XTRA = "xtra";
	private final static String VERSION = "0.3";
	private final static String TAB = "\t";
	
		/**
		 * @param args
		 */
			public static void main(String[] argv) throws Exception {

				// Way 1
				System.out.println(" ======= Start ===== ");

				// Parse arguments
				OptionSet args = null;
				OptionParser parser = new OptionParser();
			
				// WARNING, NEXT 15 lines are not of mine but from ELSEWHERE 
				// Neat trick from https://github.com/martinpaljak/GlobalPlatform/blob/master/src/openkms/gp/GPTool.java
				// lines 133-147
				try {
					args = parser.parse(argv);
					// Try to fetch all values so that format is checked before usage
					for (String s: parser.recognizedOptions().keySet()) {args.valuesOf(s);}
				} catch (OptionException e) {
					if (e.getCause() != null) {
						System.err.println(e.getMessage() + ": " + e.getCause().getMessage());
					} else {
						System.err.println(e.getMessage());
					}
					System.err.println();
					parser.printHelpOn(System.err);
					System.exit(1);
				}
				// END of somebody's else code.
				// START of my code again

		        
				parser.acceptsAll(Arrays.asList("h", "help"), "Shows the help msg here").forHelp();
				parser.acceptsAll(Arrays.asList("C", OPT_COPYRIGHT), "Formalism in square: requering for author's name").withRequiredArg().ofType(String.class);
				parser.acceptsAll(Arrays.asList("c", OPT_COUNTRY), "Enter country code to work with; default=EE").withRequiredArg().ofType(String.class);
				parser.acceptsAll(Arrays.asList("d", "V", OPT_VERBOSE), "Debuglevel; default=3").withRequiredArg().ofType(Integer.class);
				parser.acceptsAll(Arrays.asList("M", OPT_MAXRUN), "Max time in seconds we should run, kill then; default=80000 secs").withRequiredArg().ofType(Integer.class);
						// So far done with String.class not File.Class (guess why ;) )
				parser.acceptsAll(Arrays.asList("o", "filename", OPT_OUTPUT), "Name of the output file; default=output").withRequiredArg().ofType(String.class);
				parser.acceptsAll(Arrays.asList("R", OPT_RECURSIONLEVEL), "recursion depth; default=4").withRequiredArg().ofType(Integer.class);
				parser.acceptsAll(Arrays.asList("t", OPT_TIMEOUT), "Timeout between requests; default=2800 ms or Google will block you").withRequiredArg().ofType(Integer.class);
				parser.acceptsAll(Arrays.asList("v", OPT_VER), "Shows version number");
				parser.acceptsAll(Arrays.asList("x", OPT_XTRA), "Possibly we implement an extra functionality later");
			
		        // Currently we simulate (until we build the static program) 
		        OptionSet cliOptions = parser.parse("--country", "EET", "--copyright", "Some Name", "--xtra", "-o", "somefilename-001", "-R", "4", "-t", "2800", "-d", "7", "-M", "80000");
		        // OptionSet cliOptions = parser.parse("-c", "EE", "-d", "7", "-M", "80000", "-n", "-o", "somefilename-001", "-R", "4", "-t", "2800");
				// OptionSet cliOptions = parser.parse("--help");
				
				// Somewhat special options FIRST
				
				if (cliOptions.has( "d" )) {
		        	System.out.println(TAB + "Option d was found");
		        	// Set the global DebugLevel from here 
		        } else {
		        	// or the DebugLEvel = Default;
		        }
				

				if (cliOptions.has("help")) {
					parser.printHelpOn(System.out);
					System.exit(0);
				}
				
				 if (cliOptions.has( "v" )) {
					 
			        	System.out.println(TAB + "Nuhker version: " + VERSION);
			        	System.exit(0);
			        }
				 
			     if (cliOptions.has( "x" )) {
			        	System.out.println(TAB + "Option x was found which isn't yet implemented. Thnx for supporting it anyway!");
			        }
			       
				 
				 // Special copyright phuck

			        if (cliOptions.has( "C" )) {
			        	System.out.println(TAB + "Option C was called to expose the copyright message");
		        	    String optionC = (String)cliOptions.valueOf("C");
		        	    System.out.println(TAB + TAB + "and it had a sub-option: " + optionC); 
		        	    
			        	    MessageDigest hashhash = MessageDigest.getInstance("SHA-512");
			        	    byte[] sha512bytes = hashhash.digest(optionC.getBytes());
			        	    // String output = String.format("%032X", new BigInteger(1, sha512sum));
	
			        	    //convert the byte to hex format http://www.mkyong.com/java/java-sha-hashing-example/
			                StringBuffer sb = new StringBuffer();
			                for (int i = 0; i < sha512bytes.length; i++) {
			                  sb.append(Integer.toString((sha512bytes[i] & 0xff) + 0x100, 16).substring(1));
			                }
			                
			                
			                if (IDENTITY.equals(sb.toString())) {
			                	System.out.println(TAB + TAB + "Copyright: " + optionC + " (validated cryptographically).");
			                } else {
			                	System.out.println(TAB + TAB + "Seems you dunno know who the actual author is...");
			                }
			               
			        }

		        
		        // More generic options (coefficients / parameters) to runtime
		        
		        if (cliOptions.has( "M" )) {
		        	System.out.println(TAB + "Option M was found");
		        	String optionValue = String.valueOf(cliOptions.valueOf("M"));
		        	System.out.println(TAB + TAB + "and it had a suboption: " + optionValue); 
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
		        
		        if (cliOptions.has( "c" )) {
		        	System.out.println(TAB + "Option c was found");
		        	String optionValue = (String)cliOptions.valueOf("c");
		        	if (2 == optionValue.length()) {
		        		System.out.println(TAB + TAB + "CountryCode is: " + optionValue); 
		        	} else {
		        		System.out.println(TAB + TAB + "Man, I deeply doubt *"
		        				+ optionValue + "* is a CountryCode RIPE is aware of.");
		        	}
		    
		        }
		        
		        if (cliOptions.has( "o" )) {
		        	System.out.println(TAB + "Option o was found");
		        	String optionValue = (String)cliOptions.valueOf("o");
		        	System.out.println(TAB + TAB + "and it had a suboption: " + optionValue); 
		        }


		
//				System.exit(0);
			}



	//========================================================
		

}
