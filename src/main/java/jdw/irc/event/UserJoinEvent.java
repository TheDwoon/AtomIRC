package jdw.irc.event;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

/**
 * Fired when a user enters a channel.
 * 
 * @author TheDwoon
 *
 */
public class UserJoinEvent extends IRCEvent {
	private final IRCUser user;
	private final IRCChannel channel;
	
	public UserJoinEvent(IRCClient source, IRCUser user, IRCChannel channel) {
		super(source);
		
		this.user = user;
		this.channel = channel;
	}
	
	public IRCUser getUser() {
		return user;
	}
	
	public IRCChannel getChannel() {
		return channel;
	}
}