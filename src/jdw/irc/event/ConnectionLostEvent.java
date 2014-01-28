package jdw.irc.event;

import jdw.irc.IRCClient;

public class ConnectionLostEvent extends IRCEvent {
	private final int attempt;	
	private boolean reconnect;
	
	
	public ConnectionLostEvent(IRCClient source, boolean reconnect, int attempt) {
		super(source);
		
		this.reconnect = reconnect;
		this.attempt = attempt;
	}

	public boolean shouldReconnect() {
		return reconnect;
	}
	
	public void setReconnect(boolean reconnect) {
		this.reconnect = reconnect;
	}

	public int getAttempt() {
		return attempt;
	}
}