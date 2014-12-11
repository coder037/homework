/**
 * created: Dec 11, 2014 9:48:34 AM
 */
package nuhker;

import java.util.ArrayList;

/**
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *  This is the Global Database of targets.
 * Once here, we will not spend the time to ask site reputation twice
 * 
 */
public class DataBase {

	// Ideas - http://stackoverflow.com/questions/16462163/java-how-to-access-an-arraylist-of-another-class

	private ArrayList<String> knownSites;
	
	// interface of DataBase :
	// boolean isPresent(String argument)
	// int count()
	// void add(String argument)
	
	public void add(String argument) {
		this.knownSites.add(argument);
		return;
	}
	
	public boolean isPresent(String argument) {
		boolean indeed = false;
		if (knownSites.contains(argument)) {
		    indeed = true;
		} 
		return indeed;
	}

	
	public int count() {
		int thisMuch = knownSites.size();
		return thisMuch;
	}

}