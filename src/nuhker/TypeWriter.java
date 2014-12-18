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

import java.io.IOException;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * The class handles some simpliest kind of the file logging.
 * 
 * created Dec 10, 2014 11:18:09 AM
 * 
 * @author coder037@xyz.ee
 * @param fileName
 *            which file to append to, filenames are calculated by Accountant
 *            class
 * @param message
 *            line which is to be appended to the logfile
 */
public class TypeWriter {
	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());

	/**
	 * 
	 * @param fileName
	 *            - which file to append to; filenames are provided by
	 *            Accountant class
	 * @param message
	 *            - the line to append to the file
	 * @throws IOException
	 *             - if appending to the file or creating the file was
	 *             unsuccessful
	 */
	public static void main(String fileName, String message) throws IOException {
		// Ideas: http://www.homeandlearn.co.uk/java/write_to_textfile.html
		LOG.finest("===+ START FileWriter" + fileName + " to append the prey "
				+ message);
		boolean weAreAppending = true;
		FileWriter write = new FileWriter(fileName, weAreAppending);
		PrintWriter oneliner = new PrintWriter(write);
		oneliner.printf("%s" + "%n", message);
		oneliner.close();
		return;
	}
}
