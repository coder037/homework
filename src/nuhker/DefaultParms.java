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

/**
 * This class keeps important runtime parameters that the business
 * logic has to consider at any time.
 * 
 * The parameters are mostly set while parsing the CLI options, this
 * is described in CLIGrammar class.
 * 
 * Some of the parameters are later manipulated between recursion
 * levels by DataDiver.main method.
 *           
 * created: Dec 1, 2014 2:33:48 AM
 * 
 * @author coder037@xyz.ee
 */
public class DefaultParms {

	// An idea about nanotime:
	// http://stackoverflow.com/questions/351565/system-currenttimemillis-vs-system-nanotime
	private long startTime = 0;

	// Default values when commandline options given:

	private int maxTimeToRunBeforeKilled = 80000000; // in milliseconds
	private int minTimeBetweenGSBRequests = 2500; // in milliseconds
	private int depthOfRecursion = 7; // How deep we intend to go?
	private int currentLevelOfRecursion = 7; // How deep we are?

	// actually this should use LEVELS not int numbers ... but later:)
	private int debugLevel = 4;
	private String countryCodeToWorkWith = "EE";
	private String filenameForOutput = "output";
	private String currentTarget = "";

	// Auto-generated getters-setters by Eclipse.

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getMaxTimeToRunBeforeKilled() {
		return maxTimeToRunBeforeKilled;
	}

	public void setMaxTimeToRunBeforeKilled(int maxTimeToRunBeforeKilled) {
		this.maxTimeToRunBeforeKilled = maxTimeToRunBeforeKilled;
	}

	//
	public int getMinTimeBetweenGSBRequests() {
		return minTimeBetweenGSBRequests;
	}

	public void setMinTimeBetweenGSBRequests(int minTimeBetweenGSBRequests) {
		this.minTimeBetweenGSBRequests = minTimeBetweenGSBRequests;
	}

	//
	public int getDepthOfRecursion() {
		return depthOfRecursion;
	}

	//
	public void setDepthOfRecursion(int depthOfRecursion) {
		this.depthOfRecursion = depthOfRecursion;
	}

	//
	public int getCurrentLevelOfRecursion() {
		return currentLevelOfRecursion;
	}

	public void setCurrentLevelOfRecursion(int currentLevelOfRecursion) {
		this.currentLevelOfRecursion = currentLevelOfRecursion;
	}

	//
	public int getDebugLevel() {
		return debugLevel;
	}

	public void setDebugLevel(int debugLevel) {
		this.debugLevel = debugLevel;
	}

	//
	public String getCountryCodeToWorkWith() {
		return countryCodeToWorkWith;
	}

	public void setCountryCodeToWorkWith(String countryCodeToWorkWith) {
		this.countryCodeToWorkWith = countryCodeToWorkWith;
	}

	// Generally not a way to generate the filenames :)
	public String getFilenameForOutput() {
		return (filenameForOutput + "-" + startTime + ".txt");
	}

	public void setFilenameForOutput(String filenameForOutput) {
		this.filenameForOutput = filenameForOutput;
	}

	public String getCurrentTarget() {
		return currentTarget;
	}

	public void setCurrentTarget(String currentTarget) {
		this.currentTarget = currentTarget;
	}

}
