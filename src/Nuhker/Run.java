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

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;


public class Run {


		
		/**
		 * @param args
		 */
			public static void main(String[] argv) throws Exception {

				// Way 1
				System.out.println(" ======= classical way ===== ");
				if (true) {
					System.out.println("No options were passed.");
				}
				for (int s = 0; s < argv.length ; s++) { 
					System.out.println("POS: " + s + " Value: " + argv[s]);
				}
				System.out.println(" ======= jobt-simple way ===== ");
				System.out.println("So far EMULATING the command line.");
				
				// Argument plan
				// -n or no-copyright OR name will be asked
				// --country=EE
				// -o OR --outputfile filename (.txt added?)
				// -t or --timeoutbetween ms 3000
				// -M or --maxrunningtime ms 80000000
				// -R or --recursionlevel 5
				// -v or --verbose 3 (verbosity level)
				
				
				
				
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
				

				
		        
		        parser.accepts( "x" );
		        parser.accepts( "Y" );
		        parser.accepts( "z" ).withRequiredArg();

		        

		        
		        
		        OptionSet cliOptions = parser.parse("-xYzsomeargument");

		        if (cliOptions.has( "x" )) {
		        	System.out.println("Option a was given");
		        }
		        
		        if (cliOptions.has( "Y" )) {
		        	System.out.println("Option Y was given");
		        }
		        
		        if (cliOptions.has( "z" )) {
		        	System.out.println("Option c was given");
		        	String optionValue = (String)cliOptions.valueOf("z");
		        	System.out.println("     and it had a suboption: " + optionValue); 
		        }
		        

				// Generic options
//				parser.acceptsAll(Arrays.asList("h", CMD_HELP), "Shows help text").forHelp();



				



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
