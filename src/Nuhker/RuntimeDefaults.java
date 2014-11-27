/**
 * created: Nov 23, 2014 12:21:43 AM
 */
package Nuhker;

/**
 * @author coder037@xyz.ee
 * @identity 3fde112fe1ca443210b843745f21b58aaeb7713576bbdd296848ca7b05b018283dc82c50ff51fc8d7c5243d883bfca92a3be4e068eb1ce541e91b54b1697340f
 * 
 */
public class RuntimeDefaults {

	// These are pre-defined default variables used for a standard launch
	// Later I will add methods to set these from the CLI

	int countOfCountries = 1;
	String defaultCountry = "EE"; // Which is Estonia, of course
	int timeoutBetweenDownloadsMin = 3; // seconds
	int timeoutWhileWorkingMax = 1800; // seconds, unclean bailout later
	boolean verifiedLaunch = false; // not asking for author's name ;)
	boolean interactive = false; // asking all these questions on CLI -> -y/N
	boolean extraSources = false; // probably no time to implement extra sources like CleanMX

	int recursionDepth = 5; // Oh mama, it takes TIME!

	// My bad habit from another language
	long startTime = System.nanoTime(); // http://stackoverflow.com/questions/351565/system-currenttimemillis-vs-system-nanotime

	boolean outputDefinedManually = false; // so far not said anything on the command line
	String outputFilenamePrefix = "output";
	String OutputFilenameDesiredPrefix = "txt";
	String outputFilename = outputFilenamePrefix + "-" + startTime + "-"
			+ OutputFilenameDesiredPrefix;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Status: yeah!");

	}
}
