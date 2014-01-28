package jdw.irc.event;

import jdw.irc.IRCClient;

/**
 * Fired, when a ping is recieved from the IRCServer.
 * 
 * @author Daniel
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