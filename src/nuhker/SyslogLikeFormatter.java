package nuhker;

import java.util.logging.*;

// http://stackoverflow.com/questions/194765/how-do-i-get-java-logging-output-to-appear-on-a-single-line

/** 
 * The class describes a Unix Style Syslog formatter to use with the java
 * logging
 * 
 */
public class SyslogLikeFormatter extends Formatter {

	/**
	 * This is a java log formatter meant to override some idiot conventions For
	 * SEVERE reasons, any VERTICAL TABULATION in any log suxx at a great rate
	 * (admins think so, programmers not, feel the power)
	 * 
	 * @param logRecord
	 *            to transform from java style to Unix style
	 * @return on-liner which is formatted UNIX style
	 */
	public String format(LogRecord logRecord) {
		return new java.util.Date() + " " + logRecord.getLevel() + " "
				+ logRecord.getMessage() + "\r\n";
	}

}
