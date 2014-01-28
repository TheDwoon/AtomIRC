package jdw.irc.event;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

public class UserPartEvent extends IRCEvent {
	private final IRCUser user;
	private final IRCChannel channel;

	public UserPartEvent(IRCClient client, IRCUser user, IRCChannel channel) {
		super (client);
		
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