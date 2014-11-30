/**
 * created: Nov 23, 2014 12:44:46 AM
 */
package Nuhker;

/**
 * @author coder037@xyz.ee
 * @identity 3fde112fe1ca443210b843745f21b58aaeb7713576bbdd296848ca7b05b018283dc82c50ff51fc8d7c5243d883bfca92a3be4e068eb1ce541e91b54b1697340f
 *
 */


// HINT: args4j : Java command line arguments parser - http://args4j.kohsuke.org/

// http://martin-thoma.com/how-to-parse-command-line-arguments-in-java/

// HINT: The Apache Commons CLI library provides an API for parsing command line
// options passed to programs. It's also able to print help messages detailing the
// options available for a command line tool.
//    http://commons.apache.org/proper/commons-cli/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;


public class Run {

	private final static String OPT_COUNTRY = "country";
	private final static String OPT_MAXRUN = "maxrunningtime";
	private final static String OPT_NOCOPYRIGHT = "nocopyright";
	private final static String OPT_OUTPUT = "output";
	private final static String OPT_RECURSIONLEVEL = "recuresionlevel";
	private final static String OPT_TIMEOUT = "timeout";
	private final static String OPT_VERBOSE = "verbose";
	private final static String OPT_VER = "version";
	private final static String OPT_XTRA = "xtra";
		
		/**
		 * @param args
		 */
			public static void main(String[] argv) throws Exception {

				// Way 1
				System.out.println(" ======= classic way ===== ");
				if (true) {
					System.out.println("No options were passed.");
				}
				for (int s = 0; s < argv.length ; s++) { 
					System.out.println("POS: " + s + " Value: " + argv[s]);
				}
				System.out.println(" ======= jobt-simple way ===== ");
				System.out.println("So far just EMULATING the commandline due the no CLASSPATH set.");
				

				
				// Parse arguments
				OptionSet args = null;
				OptionParser parser = new OptionParser();
			
				
//				try {
//					args = parser.parse(argv);
//					// Try to fetch all values so that format is checked before usage
//					for (String s: parser.recognizedOptions().keySet()) {args.valuesOf(s);}
//				} catch (OptionException e) {
//					if (e.getCause() != null) {
//						System.out.println("ASUKOHT 1");
//						System.err.println(e.getMessage() + ": " + e.getCause().getMessage());
//					} else {
//						System.out.println("ASUKOHT 2");
//						System.err.println(e.getMessage());
//					}
//					System.err.println();
//					parser.printHelpOn(System.err);
//					System.exit(1);
//				}
				

		        
				parser.acceptsAll(Arrays.asList("h", "help"), "Shows some help").forHelp();
				parser.acceptsAll(Arrays.asList("c", OPT_COUNTRY), "Enter country code to work with; default=EE");
				parser.acceptsAll(Arrays.asList("d", OPT_VERBOSE), "Debuglevel; default=3").withRequiredArg().ofType(Integer.class);
				parser.acceptsAll(Arrays.asList("M", OPT_MAXRUN), "Max time in seconds we should run, kill then; default=80000 secs").withRequiredArg().ofType(Integer.class);
				parser.acceptsAll(Arrays.asList("n", OPT_NOCOPYRIGHT), "Supress asking author's name on CLI");
				parser.acceptsAll(Arrays.asList("o", OPT_OUTPUT), "Name of the output file; default=output");
				parser.acceptsAll(Arrays.asList("R", OPT_RECURSIONLEVEL), "recursion depth; default=4").withRequiredArg().ofType(Integer.class);
				parser.acceptsAll(Arrays.asList("t", OPT_TIMEOUT), "Timeout between requests; default=2800 ms or Google will block you").withRequiredArg().ofType(Integer.class);
				parser.acceptsAll(Arrays.asList("v", OPT_VER), "Shows version number");
				parser.acceptsAll(Arrays.asList("x", OPT_XTRA), "Possibly we implement an extra functionality later");
			
				
		        
		        // OptionSet cliOptions = parser.parse("--country", "EE", "-d", "7", "-M", "80000", "-n", "-o", "somefilename-001", "-R", "4", "-t", "2800");
		        // OptionSet cliOptions = parser.parse("-c", "EE", "-d", "7", "-M", "80000", "-n", "-o", "somefilename-001", "-R", "4", "-t", "2800");
				OptionSet cliOptions = parser.parse("--help");
				
		        if (cliOptions.has( "c" )) {
		        	System.out.println("Option c was given");
		        }
		        
		        if (cliOptions.has( "d" )) {
		        	System.out.println("Option d was given");
		        }
		        
		        if (cliOptions.has( "M" )) {
		        	System.out.println("Option M was given");
		        	String optionValue = (String)cliOptions.valueOf("M");
		        	System.out.println("     and it had a suboption: " + optionValue); 
		        }
		        




//				// Do the work, based on arguments
//				if (args.has("help")) {
//					parser.printHelpOn(System.out);
//					System.exit(0);
//				}
		        
//				if (args.has(OPT_VERSION)) {
//					System.out.println("Nuhker version: " + "Kust me saame versiooninumbri?");
//				}
		
//				System.exit(0);
			}



	//========================================================
		

}
