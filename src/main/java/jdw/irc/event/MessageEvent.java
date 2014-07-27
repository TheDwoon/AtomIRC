package jdw.irc.event;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

/**
 * Fired when you receive a message.
 * 
 * @author TheDwoon
 *
 */
public class MessageEvent extends IRCEvent {
	private final IRCChannel channel;
	private final IRCUser sender;	
	private final String message;
	
	
	public MessageEvent(IRCClient source, IRCChannel channel, IRCUser sender, String message) {
		super(source);
		
		this.channel = channel;
		this.sender = sender;
		this.message = message;
	}

	public IRCChannel getChannel() {
		return channel;
	}

	public IRCUser getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}
}