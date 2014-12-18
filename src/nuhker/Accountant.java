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
import java.util.ArrayList;
import java.util.logging.Logger;

public class Accountant {

	private final static String AS = "AS";
	private final static String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(Thread.currentThread()
			.getStackTrace()[0].getClassName());
	// Src: http://www.mkyong.com/regular-expressions/domain-name-regular-expression-example/
	private static final String PARTIAL_DOMAIN_NAME_PATTERN = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)";

	public static boolean init(String countryCode, String fileWritingBasename) {
		boolean success = false;
		// the countryCode has already been checked being a kosher one

		String fileSuffix = ".txt";

		String dummyHeader = "*** Prey list while hunting within country code "
				+ countryCode + " ***";
		for (EnumOf.preyCodeWord next : EnumOf.preyCodeWord.values()) {
			LOG.info(TAB + "Initializing output file for the category " + next);
			String fullFileName = (fileWritingBasename + "-" + next + fileSuffix);
			try {
				TypeWriter.main(fullFileName, dummyHeader);
			} catch (IOException e) {
				LOG.severe(TAB + "FAILURE to open a new file:" + fullFileName
						+ e);
				// e.printStackTrace();
			}
		}
		return success;
	}

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
				e.printStackTrace();
			}
		}
	}

	public static boolean saysWorthToDive(String countrycode, String prey,
			String baseFileName) {
		boolean wasFreshMeat = false;
		String fileSuffix = ".txt";
		String fullFileName = "";

		// For ALL unknown prey names
		if (!DbFace.knownSites.contains(prey)) {
			wasFreshMeat = true;
			// adding to Knownsites Database
			DbFace.knownSites.add(prey);
			// Also adding to knownsites FILE
			fullFileName = (baseFileName + "-" + "AllSites" + fileSuffix);
			LOG.fine("===---===---===---===---===---> knownSites        COUNT: "
					+ DbFace.knownSites.size() + " records");
			try {
				TypeWriter.main(fullFileName, prey);
			} catch (IOException e) {
				LOG.severe(TAB + "FAILURE to open file " + fullFileName
						+ " for appending " + e);
			}

			String resultWord = Func.whatIsIt(prey);
			// For all prey that occurs to be AS
			if (AS.equals(resultWord)) {

				DbFace.knownASNs.add(prey);
				fullFileName = (baseFileName + "-" + "ASN" + fileSuffix);
				LOG.fine("===---===---===---===---===---> knownASNs         COUNT: "
						+ DbFace.knownASNs.size() + " records");
				try {
					TypeWriter.main(fullFileName, prey);
				} catch (IOException e) {
					LOG.severe(TAB + "FAILURE to open file:" + fullFileName
							+ " for appending " + e);
				}

				// Recording Full descriptions for initial/CC ASNs
				if (DbFace.initialASNs.contains(Func.removeASDescr(prey))) {
					DbFace.knownASNsInCC.add(prey);
					fullFileName = (baseFileName + "-" + "ASNCC" + fileSuffix);
					LOG.fine("===---===---===---===---===---> knownASNsInCC     COUNT: "
							+ DbFace.knownASNsInCC.size() + " records");
					try {
						TypeWriter.main(fullFileName, prey);
					} catch (IOException e) {
						LOG.severe(TAB + "FAILURE to open file:" + fullFileName
								+ " for appending " + e);
					}
				}
			} // Well, it wasn't an AS

			if (resultWord.equals("URL")) {

				DbFace.knownDomains.add(prey);
				fullFileName = (baseFileName + "-" + "Domain" + fileSuffix);
				LOG.fine("===---===---===---===---===---> knownDomains      COUNT: "
						+ DbFace.knownDomains.size() + " records");
				try {
					TypeWriter.main(fullFileName, prey);
				} catch (IOException e) {
					LOG.severe(TAB + "FAILURE to open file:" + fullFileName
							+ " for appending " + e);
				}

				// A strange method to determine local CC URLS
				
				String freakyPattern = ( PARTIAL_DOMAIN_NAME_PATTERN + countrycode.toLowerCase() + "\\/.*$");
				if (prey.matches(freakyPattern)) {

					DbFace.knownDomainsInCC.add(prey);
					fullFileName = (baseFileName + "-" + "DomainCC" + fileSuffix);
					LOG.fine("===---===---===---===---===---> knownDomainsInCC  COUNT: "
							+ DbFace.knownDomainsInCC.size() + " records");
					try {
						TypeWriter.main(fullFileName, prey);
					} catch (IOException e) {
						LOG.severe(TAB + "FAILURE to open file:" + fullFileName
								+ " for appending " + e);
					}
				}

			} // URL

			if (resultWord.equals("IPV4")) {

				DbFace.knownNumericSites.add(prey);
				fullFileName = (baseFileName + "-" + "Numeric" + fileSuffix);
				LOG.fine("===---===---===---===---===---> knownNumericSites COUNT: "
						+ DbFace.knownNumericSites.size() + " records");
				try {
					TypeWriter.main(fullFileName, prey);
				} catch (IOException e) {
					LOG.severe(TAB + "FAILURE to open file:" + fullFileName
							+ " for appending " + e);
				}

			} // IPV4

		} else {
			// Previously known. No reaction.
		}
		return wasFreshMeat;
	} // method

	/**
	 * The method will publicize some status data otherwise kept in the DBze.
	 * 
	 */
	public static void publicizeStatistics() {

		LOG.info("STAT--===---===---===---===---> knownSites        COUNT: "
				+ DbFace.knownSites.size() + " records");

		LOG.info("STAT--===---===---===---===---> knownDomains      COUNT: "
				+ DbFace.knownDomains.size() + " records");

		LOG.info("STAT--===---===---===---===---> DomainsInCC       COUNT: "
				+ DbFace.knownDomainsInCC.size() + " records");

		LOG.info("STAT--===---===---===---===---> knownASNs         COUNT: "
				+ DbFace.knownASNs.size() + " records");

		LOG.info("STAT--===---===---===---===---> knownASNsInCC     COUNT: "
				+ DbFace.knownASNsInCC.size() + " records");

		LOG.info("STAT--===---===---===---===---> knownNumericSites COUNT: "
				+ DbFace.knownNumericSites.size() + " records");

		return;
	}

	public static void finalStatements() {

		// ToDo
		// long startTime = 000;
		// long endTime = 000;
		// String targetCC = "NIPITIRI";
		// int recursionLevel = 777;
		// int total = 0;
		// int noOfUniqueTargets = 0;
		// int noOfASNs = 0;
		// int noOfASNsInCC = 0;
		// int noOfURLs = 0;
		// int noOfURLsInCC = 0;
		// int noOfNumericIPv4 = 0;
		// String reasonOfStop = "unknown";

	}

}
