package Nuhker;

import java.util.Arrays;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;





public class ParserGrammar extends OptionParser {

	private static String AUTHOR = "0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff";
	private final static String OPT_COPYRIGHT = "copyright";
	private final static String OPT_COUNTRY = "country";
	private final static String OPT_MAXRUN = "max-running-time";
	private final static String OPT_OUTPUT = "output";
	private final static String OPT_RECURSIONLEVEL = "recursion-level";
	private final static String OPT_TIMEOUT = "timeout";
	private final static String OPT_VERBOSE = "verbose";
	private final static String OPT_VER = "version";
	private final static String OPT_XTRA = "xtra";
	private static String VERSION = "0.3";
	private static String TAB = "\t";
	
	
	public static OptionParser main() {

	
	OptionParser preParser = new OptionParser();
	
	preParser.acceptsAll(Arrays.asList("h", "?", "help"), "Show the help msg").forHelp();
	preParser.acceptsAll(Arrays.asList("C", OPT_COPYRIGHT), "Formalism: requering for author's name").withRequiredArg().ofType(String.class);
	preParser.acceptsAll(Arrays.asList("c", OPT_COUNTRY), "Enter country code to work with; default=EE").withRequiredArg().ofType(String.class);
	// Alternative: .withOptionalArg().ofType( Level.class ); vs .withRequiredArg().ofType(Integer.class);
	preParser.acceptsAll(Arrays.asList("d", "V", OPT_VERBOSE), "Debuglevel 0-7; default=4").withRequiredArg().ofType(Integer.class);
	preParser.acceptsAll(Arrays.asList("M", OPT_MAXRUN), "Max time in seconds we should run, kill then; default=80000 secs").withRequiredArg().ofType(Integer.class);
	// So far we do it with String.class identifier not File.Class one (guess why ;) )
	preParser.acceptsAll(Arrays.asList("o", "filename", OPT_OUTPUT), "Name of the output file; default=output").withRequiredArg().ofType(String.class);
	preParser.acceptsAll(Arrays.asList("R", OPT_RECURSIONLEVEL), "recursion depth; default=4").withRequiredArg().ofType(Integer.class);
	preParser.acceptsAll(Arrays.asList("t", OPT_TIMEOUT), "Timeout between requests; default=2800 msecs or Google will block you").withRequiredArg().ofType(Integer.class);
	preParser.acceptsAll(Arrays.asList("v", OPT_VER), "Show version number");
	preParser.acceptsAll(Arrays.asList("x", OPT_XTRA), "Some extra functionality we possible implement later");
	
	return preParser;
	}
}


