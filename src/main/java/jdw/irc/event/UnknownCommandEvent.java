package jdw.irc.event;

import jdw.irc.IRCClient;
import jdw.irc.net.Response;

/**
 * Fired when the command cannot be identified by the RFC, CTCP Protocol.
 * 
 * @author TheDwoon
 *
 */
public class UnknownCommandEvent extends IRCEvent {
	private final Response r;
	
	public UnknownCommandEvent(IRCClient source, Response r) {
		super(source);
		
		this.r = r;
	}

	public Response getResponse() {
		return r;
	}
}