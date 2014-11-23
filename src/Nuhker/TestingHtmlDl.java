/**
 * created: Nov 23, 2014 12:52:52 AM
 */
package Nuhker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

// SRC http://runnable.com/Uu83dm5vSScIAACw/download-a-file-from-the-web-for-java-files-and-save
	public class TestingHtmlDl {

	  public static void main(String[] args) throws IOException {
			 
			 String fileName = "resource-list.txt"; //The file that will be saved on your computer
			//The file that you want to download
			 URL link = new URL("https://stat.ripe.net/data/country-resource-list/data.json?resource=ee");
		

			 
			
	     //Code to download
			 InputStream in = new BufferedInputStream(link.openStream());
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 byte[] buf = new byte[1024];
			 int n = 0;
			 while (-1!=(n=in.read(buf)))
			 {
			    out.write(buf, 0, n);
			 }
			 out.close();
			 in.close();
			 byte[] response = out.toByteArray();
	 
			 FileOutputStream fos = new FileOutputStream(fileName);
			 fos.write(response);
			 fos.close();
	     //End download code
			 // FWK005 parse may not be called while parsing.
			 System.out.println("Finished");

		}

	}

	

