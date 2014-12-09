/**
 * 
 */
package nuhker;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author coder037@xyz.ee
 * @identity 0f
 *           a1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 */
public class TypeWriter {

	/**
	 * @param fileName to write, lineToWrite to that file
	 */
	
	// This is a non-threaded and very SIMPLE logger.
	
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
