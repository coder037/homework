/**
 * created: Nov 26, 2014 11:18:09 PM
 */
package Nuhker;

/**
 * @author coder037@xyz.ee
 * @identity 3fde112fe1ca443210b843745f21b58aaeb7713576bbdd296848ca7b05b018283dc82c50ff51fc8d7c5243d883bfca92a3be4e068eb1ce541e91b54b1697340f
 *
 */

// Inspirations sources:
//    API PRINCIPLES: https://stat.ripe.net/docs/data_api
//    SRC http://runnable.com/Uu83dm5vSScIAACw/download-a-file-from-the-web-for-java-files-and-save
//    SRC http://www.mkyong.com/java/json-simple-example-read-and-write-json/
// SOLVED MYSELF: how to parse hierarchical JSON (no simple example to follow)


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

	
		public static String grabCountryDescription (String countryCode) throws IOException {
			String jsonDataObtained =  "{\"status\": \"not OK\"}";		

			// 1. Form the URL we plan to grab and parse
			 String parameterToURL = countryCode.toLowerCase();
			 String QueryBaseLink = "https://stat.ripe.net/data/country-resource-list/data.json?resource=";
			 String fullUrl = QueryBaseLink + parameterToURL; 
			 URL urlToVisit = new URL(fullUrl);
			 System.out.println("URL: " + urlToVisit);
		

					
		     // 2. Open a network stream from the resource and read it in
			 	// input as:
				 InputStream networkSource = new BufferedInputStream(urlToVisit.openStream());
				// output as:
				 ByteArrayOutputStream httpResult = new ByteArrayOutputStream();
				// some weird industry-standard procedure with bufferlength = 1024
				 byte[] fifoBuffer = new byte[1024];
				 int n = 0;
				 while (-1!=(n=networkSource.read(fifoBuffer)))
				 {
				    httpResult.write(fifoBuffer, 0, n);
				 }
				 httpResult.close();
				 networkSource.close();
				 byte[] response = httpResult.toByteArray();
				 // end of the weird industry standard HTTP secuction method
				 
				 System.out.println("Chars in the array: " + response.length);
				 jsonDataObtained = new String(response);
				// DEBUG System.out.println("BlobString: " + passToParse);
				 
		 // WARNING! Is it safe to convert use hyperLONG Strings?
				 // (danger of eventual linesize limits).
		 // Results approx 10kchars in case of EE					
			return jsonDataObtained;
		}


		
		public static String[] asnJsonParser (String jsonedASNList) throws IOException {
			String[] arrayedASNList = null;
			// We parse a 3-level json hierarchy here and obtain 4-th level names
			// See self-documenting URL:
			// https://stat.ripe.net/data/country-resource-list/data.json?resource=ee
			// will need it later
			JSONParser simpleParser = new JSONParser(); 
			try {
			
			// Hierarchy Level 1 - // Outer braces of json structure
			Object outerObject = simpleParser.parse(jsonedASNList);
			JSONObject jsonObject1 = (JSONObject) outerObject;

			String status = (String) jsonObject1.get("status");
			System.out.println("Status: " + status);
			
			String time = (String) jsonObject1.get("time");
			System.out.println("Time: " + time);
						
			// Hierarhy Level 2 - "data" keyword
				Object midObject = (jsonObject1.get("data")); // everything inside of the middle braces
				JSONObject jsonObject2 = (JSONObject) midObject;
			
				String timeValue = (String) jsonObject2.get("query_time");
				System.out.println("Data was valid at that particular time: " + timeValue);
				
				// Hierarchy Level 3 - ASN record list from "resources"
					Object innerObject = (jsonObject2.get("resources"));
					JSONObject jsonObject3 = (JSONObject) innerObject;
					
					// Hierarhy level 4 - each particular AS
					JSONArray asn = (JSONArray) jsonObject3.get("asn");
					
				// ToDo! This conversion can be nicer ;)
				ArrayList outputList = new ArrayList(asn);
				int arraySize = outputList.size();

				System.out.println("Size is: " + arraySize);
				arrayedASNList = (String[]) outputList.toArray(new String[arraySize]);
		
		// Actually there are yet IPv4 and IPv6 lists available
					/// but we are not currently interested in these
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			return arrayedASNList;
		} // METHOD
		
		
	
	  public static void main(String[] args) throws IOException {
			 
		  String cc = "EE";
		  String toBeParsed =  grabCountryDescription (cc);
		  String[] hasBeenParsed = asnJsonParser(toBeParsed);
		  int countOfASNsObtained = hasBeenParsed.length;
		
		  // Print it out to be very sure
		  for (int k = 0; k < countOfASNsObtained ; k++) {
			  System.out.println(hasBeenParsed[k] + " ");
		  }
			 
     } //MAIN
			 
} //CLASS
