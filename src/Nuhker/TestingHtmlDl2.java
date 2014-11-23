/**
 * created: Nov 23, 2014 1:19:40 AM
 */


package Nuhker;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset; // et ikka UTF-8 ära tunneks Charseti nimes

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author coder037@xyz.ee
 * @identity 3fde112fe1ca443210b843745f21b58aaeb7713576bbdd296848ca7b05b018283dc82c50ff51fc8d7c5243d883bfca92a3be4e068eb1ce541e91b54b1697340f
 *
 */
public class TestingHtmlDl2 {

	/**
	 * @param args
	 */
    public static void main(String[] args) {

        URL RequestFromRipe = null; // haha: null to satisfy MalformedURLException
        try {
                RequestFromRipe = new URL("https://stat.ripe.net/data/country-resource-list/data.json?resource=ee");
        } catch (MalformedURLException e) {
                System.out.println("ERROR, D/L link seem to suck in some way...");
                System.out.println("            " + RequestFromRipe);
                e.printStackTrace();
        }

        // http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
        // ORIG: JSONObject json = new JSONObject(IOUtils.toString(new URL("https://graph.facebook.com/me"), Charset.forName("UTF-8")));


                try {
                        // JSONObject ripeResourcesList = new JSONObject(IOUtils.toString(RequestFromRipe));
                        JSONObject ripeResourcesList = new JSONObject(IOUtils.toString(RequestFromRipe));
                        // JSONObject json = new JSONObject(IOUtils.toString(new URL("https://graph.facebook.com/me")));
                } catch (JSONException e) {
                        System.out.println("ERROR, while parsing, json created a present for you - an EXCEPTION.");
                        e.printStackTrace();
                } catch (IOException e) {
                        System.out.println("ERROR, while downloading Apache IoUtils created a present for you - an EXCEPTION.");
                        e.printStackTrace();
                }



                System.out.println("DONE");

}


}
