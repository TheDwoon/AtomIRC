package jdw.irc.event;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;

/**
 * Fired when you get a ctpc based message from another client.
 * 
 * @author TheDwoon
 *
 */
public class CTCPEvent extends IRCEvent {
	private final CTCPEventType type;
	private final IRCUser sender;
		
	private String response;
	private boolean canceled;
	
	public CTCPEvent(IRCClient source, CTCPEventType type, IRCUser sender, String response) {
		super(source);

		this.sender = sender;
		this.type = type;
		this.response = response;
		this.canceled = false;
	}
	
	public boolean isCanceled() {
		return canceled;
	}
	
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public IRCUser getSender() {
		return sender;
	}

	public CTCPEventType getType() {
		return type;
	}
}