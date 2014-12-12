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

/**
 * created: Dec 11, 2014 9:48:34 AM
 */
package nuhker;

import java.util.ArrayList;

/**
 * @author coder037@xyz.ee
 * @identity 0fa1557ce3cbb37c25a6dd68a1f65c59d354b24788c39abf15fc2d1440d4f45c2f77425c1fe3d4b255fcd936042ef7ea0c202edbdd1505937da13455085c47ff
 *           knownSites is the Global Database of targets found throughout the
 *           run. Once here, we will not spend the time to ask site reputation
 *           twice
 * 
 */
public interface DBze {

	// We keep these as EXTERNAL and GLOBAL references
	// to use from multiple places and
	// through the multiple recursions.

	// Master list
	ArrayList<String> knownSites = new ArrayList<String>();
	// Only URLs/FQDNs but not ASNs
	ArrayList<String> knownDomains = new ArrayList<String>();
	// The same but only for constituency (option -c EE)
	ArrayList<String> knownDomainsInCC = new ArrayList<String>();
	// A subset of Sites without URLs/FQDNs
	ArrayList<String> knownASNs = new ArrayList<String>();
	// A sub-subset of these in target constituency (option -c EE)
	ArrayList<String> knownASNsInCC = new ArrayList<String>();

	// Ideas: ArrayList of Structures to count occurrencies of each

}
