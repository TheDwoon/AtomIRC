package jdw.irc.event;

import jdw.irc.IRCClient;

/**
 * Fired, when a ping is received from the IRCServer.
 * 
 * @author TheDwoon
 *
 */
public class PingEvent extends IRCEvent {
	private final String message;
	
	public PingEvent(IRCClient source, String message) {
		super(source);
		
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}