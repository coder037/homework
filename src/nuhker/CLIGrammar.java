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


import java.util.Arrays;
import joptsimple.OptionParser;
//import joptsimple.OptionException;
//import joptsimple.OptionSet;

/**
 * This class is depending on the joptsimple library and is used
 * to describe the grammar of CLI options that Run.java takes.
 * 
 * created: Dec 02, 2014 08:00:00 AM
 * @author coder037@xyz.ee
 */
public class CLIGrammar extends OptionParser {

	private final static String OPT_COPYRIGHT = "copyright";
	private final static String OPT_COUNTRY = "country";
	private final static String OPT_MAXRUN = "max-running-time";
	private final static String OPT_OUTPUT = "output";
	private final static String OPT_RECURSIONLEVEL = "recursion-level";
	private final static String OPT_TIMEOUT = "timeout";
	private final static String OPT_VERBOSE = "verbose";
	private final static String OPT_VER = "version";
	private final static String OPT_XTRA = "xtra";

	public static OptionParser main() {

		OptionParser preParser = new OptionParser();

		preParser.acceptsAll(Arrays.asList("h", "?", "help"),
				"Show the help msg").forHelp();
		preParser
				.acceptsAll(Arrays.asList("C", OPT_COPYRIGHT),
						"Formalism: requering for author's name")
				.withRequiredArg().ofType(String.class);
		preParser
				.acceptsAll(Arrays.asList("c", OPT_COUNTRY),
						"Enter country code to work with; default=EE")
				.withRequiredArg().ofType(String.class);
		
		// Warning: the next option is a dummy option so far.
		// ToDo: use Java Logger verboseness semantics, not numbers

		preParser
				.acceptsAll(Arrays.asList("d", "V", OPT_VERBOSE),
						"Debuglevel 0-7; default=4").withRequiredArg()
				.ofType(Integer.class);
		preParser
				.acceptsAll(Arrays.asList("M", OPT_MAXRUN),
						"Max time in seconds we should run, kill then; default=80000 secs")
				.withRequiredArg().ofType(Integer.class);
		
		// So far we do it with String.class identifier not File.Class
		// (guess why ;) ) :
		preParser
				.acceptsAll(Arrays.asList("o", "filename", OPT_OUTPUT),
						"Name of the output file; default=output")
				.withRequiredArg().ofType(String.class);
		preParser
				.acceptsAll(Arrays.asList("R", OPT_RECURSIONLEVEL),
						"recursion depth; default=4").withRequiredArg()
				.ofType(Integer.class);
		preParser
				.acceptsAll(Arrays.asList("t", OPT_TIMEOUT),
						"Timeout between requests; default=2800 msecs or Google will block you")
				.withRequiredArg().ofType(Integer.class);
		preParser
				.acceptsAll(Arrays.asList("v", OPT_VER), "Show version number");
		preParser.acceptsAll(Arrays.asList("x", OPT_XTRA),
				"Some extra functionality we possible implement later");
		
		// These are some examples of the joptsimple power: 
		// .withOptionalArg().ofType( Level.class ); vs
		// .withRequiredArg().ofType(Integer.class):
		
		return preParser;
	}
}
