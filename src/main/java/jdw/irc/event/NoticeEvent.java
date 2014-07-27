package jdw.irc.event;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

/**
 * Fired when the client get noticed by someone.
 * 
 * @author TheDwoon
 *
 */
public class NoticeEvent extends IRCEvent {
	private final IRCUser user;
	private final String message;
	
	public NoticeEvent(IRCClient source, IRCUser user, String message) {
		super(source);
		
		this.user = user;
		this.message = message;
	}

	public IRCUser getSender() {
		return user;
	}

	public String getMessage() {
		return message;
	}
}