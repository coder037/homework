/**
 * created: Dec 12, 2014 11:27:57 PM
 */
package nuhker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Foreign code is kept in this Class, that means the code whose authors are not
 * precisely known. This is to mitigate the risk of eventual plagiarism.
 * 
 * @author various sources
 * 
 */
public class ForeignCode {

	private final static String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());

	/**
	 * This is the web downloader of a specific purpose. NB! The code has been
	 * put into a separate subroutine because of it contains lines with
	 * SOMEBODY's copyright.
	 * 
	 * However, because of no responsibility, it is nice to play with a
	 * truckload of try/catch blocks.
	 * 
	 * Authors are unknown. The code represents an industry standard example
	 * represented in many textbooks. And ... some lines of the code are mine,
	 * is this too risky to claim?
	 * 
	 * @param urlToDL
	 *            download link at RIPE
	 * @return byteArray[] to further convert into a String
	 * @throws IOException
	 *             if socket transfer to/from the RIPE failed
	 */
	public static byte[] downLoader(String urlToDL) {
		byte[] byteArray = null;
		URL urlToVisit = null;
		try {
			urlToVisit = new URL(urlToDL);
		} catch (MalformedURLException e) {
			LOG.severe("You see this message because or a malformed URL" + e);
			e.printStackTrace();
		}
		LOG.info("===* RIPE Downloader");
		LOG.fine(TAB + "URL: " + urlToVisit);

		// Open a network stream to the resource
		// and record the answer.
		// input comes from here:
		InputStream networkSource = null;
		try {
			networkSource = new BufferedInputStream(urlToVisit.openStream());
		} catch (IOException e) {
			LOG.severe("Some error happened trying to contact RIPE server" + e);
			e.printStackTrace();
		}
		// output goes there:
		ByteArrayOutputStream httpResult = new ByteArrayOutputStream();

		// FOREIGN COPYRIGHT WARNING - next 18 lines
		// #### START OF SOME industry-standard method
		// available in many textbooks. G: bufferlength = 1024 etc.
		// Me don't know who has written this code block ;)
		// GOOGLE for "java streams byte[1024]"

		byte[] queueBuffer = new byte[1024];
		int n = 0;
		try {
			while (-1 != (n = networkSource.read(queueBuffer))) {
				httpResult.write(queueBuffer, 0, n);
			}
		} catch (IOException e) {
			LOG.severe("You only see this message when the network to RIPE is down"
					+ e);
			e.printStackTrace();
		}
		try {
			httpResult.close();
		} catch (IOException e) {
			LOG.warning("Wow! I was able to open the Byte Stream but not to close it"
					+ e);
			e.printStackTrace();
		}
		try {
			networkSource.close();
		} catch (IOException e) {
			LOG.severe("Copying a ByteStream to the ResultArray[] failed miserably"
					+ e);
			e.printStackTrace();
		}
		byteArray = httpResult.toByteArray();
		// end of the weird industry-standard HTTP seduction method
		// END of the FOREIGN COPYRIGHT WARNING
		return byteArray;
	}

	/**
	 * Because the general warning by NSA is - never make crypto at home - I
	 * used this method to mark SOMEBODY'S ELSE COPYRIGHT on hash calculation
	 * routines.
	 * 
	 * This method does some hashing with the string.
	 * 
	 * @param argument
	 *            Name of the Author to be checked cryptographically
	 * @return String hash digest
	 * @throws Exception
	 *             which is marked but not actually used
	 */
	public static String calculateHash(String argument) {
		String digest = null;
		LOG.fine(TAB + "==- Calculating the hash.");

		MessageDigest hash = null;
		try {
			hash = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			LOG.severe(TAB + "Calulation of the hash did not succeed");
			e.printStackTrace();
		}

		// ### WARNING: NEXT 10 lines of code mostly are of SOMEBODY'S ELSE
		// authorship:
		// Inspiration:
		// http://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
		byte[] sha512bytes = hash.digest(argument.getBytes());
		// Inspiration: convert the byte to hex format
		// http://www.mkyong.com/java/java-sha-hashing-example/
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sha512bytes.length; i++) {
			sb.append(Integer.toString((sha512bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		// === END of the WARNING scope
		digest = sb.toString();
		LOG.finest(TAB + "==- Done with the hash calculation");
		return digest;
	}

}
