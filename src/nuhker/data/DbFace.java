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

package nuhker.data;

import java.util.ArrayList;

/**
 * The interface is used as a pseudo Database interface. We keep our data both
 * EXTERNAL (to classes) and GLOBAL (to the package) to be used from multiple
 * classes and *through the multi level recursions*.
 * 
 * ArrayList of knownSites is the Global Database for targets found throughout
 * the recursive run. This way, we will not spend time asking for the reputation
 * of a site twice.
 * 
 * However, a separate task is responsible for writing down (at FS level) the
 * data gathered, doing so right before the end.
 * 
 * created Dec 11, 2014 9:48:34 AM
 * 
 * @author coder037@xyz.ee
 * 
 */
public interface DbFace {

	// Actually these names are in sync with EnumOf.preyCodeWord
	// and should be defined otherwise
	
	// Master list
	ArrayList<String> initialASN = new ArrayList<String>();
	// A sub-subset of these in target constituency (option -c EE)
	ArrayList<String> AllSites = new ArrayList<String>();
	// Only URLs/FQDNs but not ASNs
	ArrayList<String> Domain = new ArrayList<String>();
	// The same but only for constituency (option -c EE)
	ArrayList<String> DomainCC = new ArrayList<String>();
	// A subset of Sites without URLs/FQDNs
	ArrayList<String> ASN = new ArrayList<String>();
	// A sub-subset of these in target constituency (option -c EE)
	ArrayList<String> ASNCC = new ArrayList<String>();
	// IPv4 numeric sites
	ArrayList<String> Numeric = new ArrayList<String>();

	// Future ideas: ArrayList of Structures
	// to count occurrencies of each
	// and to get the statistics about the run.
	//
	// http://stackoverflow.com/questions/19337788/java-arraylist-of-multidimensional-arrays-or-arraylist-of-multidimensional-arr

}
