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

import java.util.logging.Logger;
import java.io.IOException;

/**
 * The class supports main routines datawise, makes decisions on whether or not
 * to deal with next target, depending on the recorded "database".
 * 
 * The class serves DataDiver class, uses interface defined in DbFace and logs
 * according to LogHandler.
 * 
 * @author coder037@xyz.ee
 * 
 */
public class Accountant {

	private final static String AS = "AS";
	private final static String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());
	// Src:
	// http://www.mkyong.com/regular-expressions/domain-name-regular-expression-example/
	private static final String PARTIAL_DOMAIN_NAME_PATTERN = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)";

	/**
	 * The method initializes log files via TypeWriter. Called only once from
	 * Accountant class.
	 * 
	 * @param countryCode
	 *            - a 2-letter countrycode according to EnumOf.cc
	 * @param fileWritingBasename
	 *            - a Basename used for most files.
	 */
	public static void init(String countryCode, String fileWritingBasename) {
		// the countryCode has been verified previously
		String fileSuffix = ".txt";
		String dummyHeader = "*** Target list while hunting for country code "
				+ countryCode + " ***";
		for (EnumOf.preyCodeWord next : EnumOf.preyCodeWord.values()) {
			LOG.info(TAB + "Initializing output file for the category " + next);
			String fullFileName = (fileWritingBasename + "-" + next + fileSuffix);
			try {
				TypeWriter.main(fullFileName, dummyHeader + " Category: "
						+ next + " ***");
			} catch (IOException e) {
				LOG.severe(TAB + "FAILURE to open a new file:" + fullFileName
						+ e);
				// e.printStackTrace();
			}
		}
	}

	/**
	 * The method writes down the initial set of ASN values got from RIPE. This
	 * is done for 2 reasons, to have a clear input trail and, to obtain AS
	 * descriptions later.
	 * 
	 * @param asn
	 *            - one AS name in format AS12345
	 * @param baseFileName
	 *            - a timevalue used in filename
	 */
	public static void initialASRegistration(String asn, String baseFileName) {
		String fileSuffix = ".txt";
		String fullFileName = "";

		if (!DbFace.initialASNs.contains(asn)) {
			DbFace.initialASNs.add(asn);
			fullFileName = (baseFileName + "-" + "InitialASN" + fileSuffix);
			try {
				TypeWriter.main(fullFileName, asn);
			} catch (IOException e) {
				LOG.severe(TAB + "FAILURE to open a new file:" + fullFileName
						+ e);
				// e.printStackTrace();
			}
		}
	}

	/**
	 * The method sits between upper level saysWorthToDive() method and lower
	 * level TypeWriter class and DbFace interface, trying to screen a big
	 * quantity of details. It actually should be rewritten clean to avoid even
	 * the idea of parametrization of variable names ;)
	 * 
	 * @param switchWord
	 *            - token passed from the caller dictates the type of reaction
	 *            needed.
	 * @param target
	 *            - the site name needed to be written into one or more data
	 *            tables
	 * @param baseFileName
	 *            is the generic part of the filename, derived from a timevalue,
	 *            thus logfiles can easily grouped
	 */
	public static void recordItAs(String switchWord, String target,
			String baseFileName) {
		String fullFileName = "";
		String fileSuffix = ".txt";
		String arrow = "===---===---===---===---===---> ";

		// a SWITCH substitute
		if (switchWord.equals("AllSites")) {
			DbFace.knownSites.add(target);
			LOG.fine(arrow + "AllSites          COUNT: "
					+ (DbFace.knownSites.size() - 1) + " records");
		}
		// DbFace.knownASNs.add(prey);
		if (switchWord.equals("ASN")) {
			DbFace.knownASNs.add(target);
			LOG.fine(arrow + "knownASNs         COUNT: "
					+ (DbFace.knownASNs.size() - 1) + " records");
		}
		// DbFace.knownASNsInCC.add(prey);
		if (switchWord.equals("ASNCC")) {
			DbFace.knownASNsInCC.add(target);
			LOG.fine(arrow + "knownASNsinCC     COUNT: "
					+ (DbFace.knownASNsInCC.size() - 1) + " records");
		}
		// DbFace.knownDomains.add(prey);
		if (switchWord.equals("Domain")) {
			DbFace.knownDomains.add(target);
			LOG.fine(arrow + "knownDomains      COUNT: "
					+ (DbFace.knownDomains.size() - 1) + " records");
		}
		// DbFace.knownDomainsInCC.add(prey);
		if (switchWord.equals("DomainCC")) {
			DbFace.knownDomainsInCC.add(target);
			LOG.fine(arrow + "knownDomainsInCC  COUNT: "
					+ (DbFace.knownDomainsInCC.size() - 1) + " records");
		}
		// DbFace.knownNumericSites.add(prey);
		if (switchWord.equals("Numeric")) {
			DbFace.knownNumericSites.add(target);
			LOG.fine(arrow + "knownNumericSites COUNT: "
					+ (DbFace.knownNumericSites.size() - 1) + " records");
		}

		// Also adding the pray to the file
		fullFileName = (baseFileName + "-" + switchWord + fileSuffix);

		try {
			TypeWriter.main(fullFileName, target);
		} catch (IOException e) {
			LOG.severe(TAB + "FAILURE to open file " + fullFileName
					+ " for appending " + e);
		}
	} // END of method

	/**
	 * The method makes decision about the target being ASN, URL or numeric ASN.
	 * Some extra decisions are made - to catch ASNs related to the country
	 * being harvested and
	 * 
	 * @param countrycode
	 *            - a parameter defining "own" country so that own resources can
	 *            be separated into different result files
	 * @param prey
	 *            - next target to be analyzed - an AS or an URL
	 * @param baseFileName
	 *            - a timevalue string used as a part for logfile names
	 * @return wasFreshMeat - a boolean value indicating whether the target was
	 *         new or was already seen earlier
	 */
	public static boolean saysWorthToDive(String countrycode, String prey,
			String baseFileName) {
		boolean wasFreshMeat = false;

		// For ALL unknown prey names
		if (!DbFace.knownSites.contains(prey)) {
			wasFreshMeat = true;
			// adding to Knownsites Database
			recordItAs("AllSites", prey, baseFileName);

			// Now asking: what kind of meat it is?
			String resultWord = Func.whatIsIt(prey);
			// For all prey that occurs to be AS
			if (AS.equals(resultWord)) {
				recordItAs("ASN", prey, baseFileName);

				// Recording Full descriptions for initial/CC ASNs
				if (DbFace.initialASNs.contains(Func.removeASDescr(prey))) {
					recordItAs("ASNCC", prey, baseFileName);
				}
			} // Well, it wasn't an AS
			if (resultWord.equals("URL")) {
				recordItAs("Domain", prey, baseFileName);
				// A strange method to determine local CC URLS
				String freakyPattern = (PARTIAL_DOMAIN_NAME_PATTERN
						+ countrycode.toLowerCase() + "\\/.*$");
				if (prey.matches(freakyPattern)) {
					recordItAs("DomainCC", prey, baseFileName);
				}
			} // URL
			if (resultWord.equals("IPV4")) {
				recordItAs("Numeric", prey, baseFileName);
			} // IPV4

		} else {
			// Previously known. No reaction.
		}
		return wasFreshMeat;
	} // method

	/**
	 * The method will publicize some status data about our so called database
	 * hidden behind DbFace interface.
	 * 
	 */
	public static void publicizeStatistics() {
		String arrow = "STAT--===---===---===---===---> ";
		LOG.info(arrow + "AllSites          COUNT: "
				+ (DbFace.knownSites.size() - 1) + " records");

		LOG.info(arrow + "knownDomains      COUNT: "
				+ (DbFace.knownDomains.size() - 1) + " records");

		LOG.info(arrow + "DomainsInCC       COUNT: "
				+ (DbFace.knownDomainsInCC.size() - 1) + " records");

		LOG.info(arrow + "knownASNs         COUNT: "
				+ (DbFace.knownASNs.size() - 1) + " records");

		LOG.info(arrow + "knownASNsInCC     COUNT: "
				+ (DbFace.knownASNsInCC.size() - 1) + " records");

		LOG.info(arrow + "knownNumericSites COUNT: "
				+ (DbFace.knownNumericSites.size() - 1) + " records");

		return;
	}

	/**
	 * A formal method to present some run statistics.
	 */
	public static void finalStatements() {
		LOG.warning("WARNING: End of the Epoch - we are forced to close the business.");
		LOG.warning("The statistics about the run:");
		publicizeStatistics();
		LOG.info("Logs are available in Run directory.");
	}
}
