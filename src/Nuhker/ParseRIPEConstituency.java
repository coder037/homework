/**
 * created: Nov 26, 2014 11:18:09 PM
 */
package Nuhker;

/**
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 * Tag Comments: @version @param @return @deprecated @since @throws @exception @see
 */

// Inspiration sources:
//    API PRINCIPLES: https://stat.ripe.net/docs/data_api
//    Inspiration: http://runnable.com/Uu83dm5vSScIAACw/download-a-file-from-the-web-for-java-files-and-save
//    Inspiration: http://www.mkyong.com/java/json-simple-example-read-and-write-json/
// SOLVED MYSELF: how to parse hierarchical JSON (have seen no public example to follow)

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
// import java.util.Iterator; // Will need it later?

//import java.io.FileWriter;
//import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class ParseRIPEConstituency {

	private final static String TAB = "\t";
	
	public static byte[] downLoader(String urlToDL) throws IOException {
		byte[] byteStream = null;
		URL urlToVisit = new URL(urlToDL);
		System.out.println("===* RIPE Downloader");
		System.out.println(TAB + "URL: " + urlToVisit);

		// Open a network stream to the resource
		// and record the answer.
		// input comes from here:
		InputStream networkSource = new BufferedInputStream(
				urlToVisit.openStream());
		// output goes there:
		ByteArrayOutputStream httpResult = new ByteArrayOutputStream();

		// FOREIGN COPYRIGHT WARNING
		// Hello man, this is some best industry-standard method
		// available in many textbooks. G: bufferlength = 1024 etc.
		// Me don't know who has written this code block ;)
		// GOOGLE for "java streams byte[1024]"

		byte[] queueBuffer = new byte[1024];
		int n = 0;
		while (-1 != (n = networkSource.read(queueBuffer))) {
			httpResult.write(queueBuffer, 0, n);
		}
		httpResult.close();
		networkSource.close();
		byteStream = httpResult.toByteArray();
		// end of the weird industry-standard HTTP seduction method
		// END of the FOREIGN COPYRIGHT WARNING
		return byteStream;
	}

	public static String grabCountryDescription(String countryCode)
			throws IOException {
		String jsonDataObtained = "{\"status\": \"not OK\"}";

		// 1. Form the URL we plan to grab and parse
		String parameterToURL = countryCode.toLowerCase();
		String QueryBaseLink = "https://stat.ripe.net/data/country-resource-list/data.json?resource=";
		String fullUrl = QueryBaseLink + parameterToURL;

		// 2. Open a network stream from the resource and read it in
		byte[] response = downLoader(fullUrl);
		System.out.println(TAB + "Chars in the array: " + response.length);
		jsonDataObtained = new String(response);
		// DEBUG System.out.println("BlobString: " + passToParse);

		// WARNING! Is it safe to convert use hyperLONG Strings?
		// (danger of eventual linesize limits).
		// Results approx 10kchars in case of EE
		return jsonDataObtained;
	}

	public static String[] asnJsonParser(String jsonedASNList) {
		String[] arrayedASNList = null;
		// We parse a 3-level json hierarchy here
		// and obtain asn names from the 4-th level

		JSONParser simpleParser = new JSONParser();
		try {

			// Hierarchy Level 1 - Outer braces of the json structure (main)
			Object outerObject = simpleParser.parse(jsonedASNList);
			JSONObject jsonObject1 = (JSONObject) outerObject;

			String status = (String) jsonObject1.get("status");
			System.out.println(TAB + "Query Status: " + status);
			String time = (String) jsonObject1.get("time");
			System.out.println(TAB + "Response Time: " + time);

			// Hierarhy Level 2 - "data" keyword
			Object midObject = (jsonObject1.get("data")); // everything inside
															// of the middle
															// braces
			JSONObject jsonObject2 = (JSONObject) midObject;

			String timeValue = (String) jsonObject2.get("query_time");
			System.out.println(TAB + "Data claimed valid at: "
					+ timeValue);

			// Hierarchy Level 3 - ASN record list from "resources"
			Object innerObject = (jsonObject2.get("resources"));
			JSONObject jsonObject3 = (JSONObject) innerObject;

			// Hierarhy level 4 - each particular ASN
			JSONArray asn = (JSONArray) jsonObject3.get("asn");

			// ToDo! This conversion can be nicer ;)
			@SuppressWarnings("unchecked")
			// http://stackoverflow.com/questions/367626/how-do-i-fix-the-expression-of-type-list-needs-unchecked-conversion
			ArrayList<String> outputList = new ArrayList<String>(asn);
			int arraySize = outputList.size();

			System.out.println(TAB + "AS count obtained: " + arraySize);
			arrayedASNList = (String[]) outputList
					.toArray(new String[arraySize]);

			// Actually there are yet IPv4 and IPv6 lists available
			// but we are not interested in these just now
			System.out.println("===~ END of RIPE parsing method.");

		} catch (ParseException e) {
			System.err
					.println("Parsing exception made a Boo-boo during asnJsonParser method,");
			System.err.println("        that's all I know currently...");
			e.printStackTrace();
		}

		return arrayedASNList;
	} // METHOD

	public static void main(String[] args) throws IOException {

		String cc = "EE";
		String toBeParsed = grabCountryDescription(cc);
		String[] hasBeenParsed = asnJsonParser(toBeParsed);
		int countOfASNsObtained = hasBeenParsed.length;

		// Print it out to be very sure
		for (int k = 0; k < countOfASNsObtained; k++) {
			System.out.println(TAB + TAB + hasBeenParsed[k] + " ");
		}

		// ToDo: json status field: if not OK or no data, then errmsg
		// this will happen if wrong country code asked from RIPE
		// ToDo: web timeout

	} // MAIN

} // CLASS
