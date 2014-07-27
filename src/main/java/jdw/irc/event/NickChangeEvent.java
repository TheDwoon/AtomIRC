package jdw.irc.event;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

/**
 * Fired when a user changed his nick.
 * 
 * @author TheDwoon
 *
 */
public class NickChangeEvent extends IRCEvent {
	private final String oldNick;
	private final IRCUser user;
	
	public NickChangeEvent(IRCClient source, String oldNick, IRCUser user) {
		super(source);
		
		this.oldNick = oldNick;
		this.user = user;
	}

	public String getOldNick() {
		return oldNick;
	}

	public String getNewNick() {
		return user.getNick();
	}
	
	public IRCUser getUser() {
		return user;
	}
}