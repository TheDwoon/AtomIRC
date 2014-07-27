package jdw.irc.event;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

/**
 * Fired if someone uses /me.
 * 
 * @author TheDwoon
 *
 */
public class MeEvent extends IRCEvent {
	private final IRCUser sender;
	private final String message;
	
	public MeEvent(IRCClient source, IRCUser sender, String message) {
		super(source);

		this.sender = sender;
		this.message = message;
	}

	public IRCUser getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}
}