/**
 * 
 */
package nuhker;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author n0ne
 *
 */
public class TypeWriter {

	/**
	 * @param args
	 */
	public static void main(String fileName, String lineToWrite) {
	
		System.out.println("===-> Writing another line to " + fileName);
		System.out.println(lineToWrite);
		// try-catch
		System.out.println("        succesfully.");
		return;
	}

	// Funny thing seen at a source. Should we use it elsewhere?
	// SRC1: http://tutorials.jenkov.com/java-logging/logger.html
	// SRC2: http://www.vogella.com/tutorials/Logging/article.html
//	catch (IOException ex) {
//		Logger.getLogger(ParseGSBReputation.class.getName()).log(
//				Level.SEVERE, null, ex);
	
}
