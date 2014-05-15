package jdw.irc.event;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

public class PrivateMessageEvent extends IRCEvent {
	private final IRCUser sender;
	private final String message;
	
	public PrivateMessageEvent(IRCClient source, IRCUser sender, String message) {
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