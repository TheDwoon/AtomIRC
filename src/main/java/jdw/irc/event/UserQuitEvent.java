package jdw.irc.event;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

/**
 * Called when a user quits the IRC Server.
 * 
 * @author TheDwoon
 *
 */
public class UserQuitEvent extends IRCEvent {
	private final IRCUser user;
	private final String message;
	
	public UserQuitEvent(IRCClient source, IRCUser user, String message) {
		super(source);
		
		this.user = user;
		this.message = message;
	}

	public IRCUser getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}
}