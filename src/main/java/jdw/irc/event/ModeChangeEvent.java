package jdw.irc.event;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

/**
 * Fired when a users mode is changed.
 * 
 * @author TheDwoon
 *
 */
public class ModeChangeEvent extends IRCEvent {
	private final IRCUser sender;
	private final IRCChannel channel;
	private final IRCUser user;
	private final String mode;
	
	public ModeChangeEvent(IRCClient source, IRCUser sender, IRCChannel channel, IRCUser user, String mode) {
		super(source);
		
		this.sender = sender;
		this.channel = channel;
		this.user = user;
		this.mode = mode;
	}

	public IRCUser getSender() {
		return sender;
	}

	public IRCChannel getChannel() {
		return channel;
	}

	public IRCUser getUser() {
		return user;
	}

	public String getMode() {
		return mode;
	}
}