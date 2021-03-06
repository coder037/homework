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

// Inspiration sources:
//    API PRINCIPLES: https://stat.ripe.net/docs/data_api
//    Inspiration: http://runnable.com/Uu83dm5vSScIAACw/download-a-file-from-the-web-for-java-files-and-save
//    Inspiration: http://www.mkyong.com/java/json-simple-example-read-and-write-json/
// SOLVED MYSELF: how to parse hierarchical JSON (still have seen no public example to follow)

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The ParseRIPE class is to raise an argument against RIPE public interface to
 * return the constituency (extent of IP networks) of a country.
 * 
 * created Nov 26, 2014 11:18:09 PM
 * 
 * @author coder037@xyz.ee
 */
public class ParseRIPE {
	// Some like Chopin, me like constants
	private final static String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());

	/**
	 * This is the wrapper method around downLoader method. It basically does
	 * nothing except converting byte[] array into a String
	 * 
	 * @param countryCode
	 *            - two letter ISO 3166-1 code
	 * @return String with the constituency extent of that particular country
	 *         described
	 * @throws IOException
	 */
	public static String grabCountryDescription(String countryCode)
			throws IOException {
		String jsonDataObtained = "{\"status\": \"not OK\"}";
		LOG.fine(TAB + "START of grabCountryDescription(" + countryCode + ").");

		// 1. Form the URL we plan to grab and parse
		String parameterToURL = countryCode.toLowerCase();
		String QueryBaseLink = "https://stat.ripe.net/data/country-resource-list/data.json?resource=";
		String fullUrl = QueryBaseLink + parameterToURL;

		// 2. Open a network stream from the resource and read it in
		byte[] response = ForeignCode.downLoader(fullUrl);
		LOG.finer(TAB + "Chars in the array: " + response.length);
		jsonDataObtained = new String(response);

		// ToDo WARNING! Is it so safe to convert use hyperLONG Strings?
		// (danger of eventual linesize limits).
		// Results are approx 10kchars in case of EE
		LOG.fine(TAB + "RETURN from grabCountryDescription(" + countryCode
				+ ").");
		LOG.finest(TAB + TAB + "with BlobString: " + jsonDataObtained);
		return jsonDataObtained;
	}

	/**
	 * This is the axial method of the class. It takes the description of a
	 * country's Internet obtained from RIPE and parses it into a usableform.
	 * 
	 * Depends on external json-simple library.
	 * 
	 * Currently we only use AS numbers out of the RIPE description. There are
	 * yet IPv4 and IPv6 lists available which we are currently uninterested.
	 * 
	 * @param jsonedASNList
	 *            - A JSONPArser object with data structures
	 * @return arrayedASNList with all ASNs (int) nicely listed
	 */
	public static String[] asnJsonParser(String jsonedASNList) {
		String[] arrayedASNList = null;
		LOG.fine("===~ STARTing RIPE parsing method.");

		// We parse a 3-level json hierarchy here
		// and obtain asn names from the 4-th level

		JSONParser simpleParser = new JSONParser();
		try {

			// Hierarchy Level 1 - Outer braces of the json structure (main)
			Object outerObject = simpleParser.parse(jsonedASNList);
			JSONObject jsonObject1 = (JSONObject) outerObject;

			String status = (String) jsonObject1.get("status");
			LOG.finer(TAB + "Query Status: " + status);
			String time = (String) jsonObject1.get("time");
			LOG.finer(TAB + "Response Time: " + time);

			// Hierarhy Level 2 - "data" keyword
			// everything inside of the middle braces
			Object midObject = (jsonObject1.get("data"));
			JSONObject jsonObject2 = (JSONObject) midObject;

			String timeValue = (String) jsonObject2.get("query_time");
			LOG.finer(TAB + "Data claimed valid at: " + timeValue);

			// Hierarchy Level 3 - ASN record list from "resources"
			Object innerObject = (jsonObject2.get("resources"));
			JSONObject jsonObject3 = (JSONObject) innerObject;

			// Hierarhy level 4 - each particular ASN
			JSONArray asn = (JSONArray) jsonObject3.get("asn");

			// ToDo! This conversion could certainly be nicer ;)
			// http://stackoverflow.com/questions/367626/how-do-i-fix-the-expression-of-type-list-needs-unchecked-conversion
			@SuppressWarnings("unchecked")
			ArrayList<String> outputList = new ArrayList<String>(asn);
			int arraySize = outputList.size();

			LOG.finer(TAB + "AS count obtained: " + arraySize);
			arrayedASNList = (String[]) outputList
					.toArray(new String[arraySize]);

			LOG.fine("===~ END of RIPE parsing method.");

		} catch (ParseException e) {
			LOG.severe("An exception occurred during asnJsonParser method,");
			LOG.severe("        that's all I know currently..." + e);
			e.printStackTrace();
		}

		return arrayedASNList;
	} // METHOD//

	// ============== Remnant MAIN method

	/**
	 * Main method kept here for debugging. No actual use, other classes do not
	 * call it.
	 * 
	 * @param args
	 *            not used
	 * @throws IOException
	 *             actually ignored :)
	 */
	public static void main(String[] args) throws IOException {

		String cc = "LV";
		String toBeParsed = grabCountryDescription(cc);
		String[] hasBeenParsed = asnJsonParser(toBeParsed);
		int countOfASNsObtained = hasBeenParsed.length;

		LOG.info("===~===~=== ParseRIPE.main, debug for " + cc);
		// Print it out to be very sure
		for (int k = 0; k < countOfASNsObtained; k++) {
			LOG.info(TAB + hasBeenParsed[k] + " ");
		}
		LOG.info("===~===~=== end of the ParseRIPE.main");

	} // MAIN

}
