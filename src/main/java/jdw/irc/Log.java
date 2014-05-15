package jdw.irc;

import jdw.irc.net.Response;

public final class Log {
	private Log() {
		
	}		
	
	public static String dumpInformation(Object o, Response r) {		
		return "Error in " + ((o == null) ? "null" : o.getClass().getName()) 
				+ " in Response " + ((r == null) ? "null" : r.getRaw());
	}
	
	public static void notNull(Object o, String note) {
		if (!IRCClient.DEBUG)
			return;
		
		if (o == null) {
			IRCClient.logger.warning(note);;
		}
	}
	
	public static void equals(Object o, Object p, String note) {
		if (!IRCClient.DEBUG)
			return;
		
		if (o != p) {
			IRCClient.logger.warning(o + " != " + p + ": " + note);
		}
	}
}