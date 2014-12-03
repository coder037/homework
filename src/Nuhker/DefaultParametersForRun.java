/**
 * created: Dec 1, 2014 2:33:48 AM
 */
package Nuhker;

/**
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *
 */
public class DefaultParametersForRun {

	// http://stackoverflow.com/questions/351565/system-currenttimemillis-vs-system-nanotime
	private long startTime = System.nanoTime(); 

	public long getStartTime() {
		return startTime;
	}
		
	// Default values when commandline options given:
	
	private int maxTimeToRunBeforeKilled = 80000000; // in milliseconds
	private int minTimeBetweenGSBRequests = 2800; // in milliseconds
	private int depthOfRecursion = 7;
	// actually this should use LEVELS not int numbers ... but :)
	private int debugLevel = 4;
	private String countryCodeToWorkWith = "EE";
	private String FilenameForOutput = "output";

	
	// Auto-generated getters-setters by Eclipse.
	
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
	// 
	public String getFilenameForOutput() {		
		return (FilenameForOutput + startTime + ".txt");
	}
	public void setFilenameForOutput(String filenameForOutput) {
		FilenameForOutput = filenameForOutput;
	}

}
