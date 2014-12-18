package nuhker;

import java.util.logging.*;

// http://stackoverflow.com/questions/194765/how-do-i-get-java-logging-output-to-appear-on-a-single-line

/**
 * The class describes a Unix Style Syslog formatter which makes java native
 * logging more or less usable.
 */
public class SyslogLikeFormatter extends Formatter {

	/**
	 * A java log formatter meant to override some idiot conventions - for
	 * SEVERE reasons, any VERTICAL TABULATION in any logging suxx at a great
	 * rate (admins think so, programmers not, thus feel the power!).
	 * 
	 * @param logRecord
	 *            - the mesg to be transformed from java style to Unix style
	 * @return on-liner which is formatted UNIX style
	 */
	public String format(LogRecord logRecord) {
		return new java.util.Date() + " " + logRecord.getLevel() + " "
				+ logRecord.getMessage() + "\r\n";
	}

}
