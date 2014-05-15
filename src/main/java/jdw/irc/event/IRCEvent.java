package jdw.irc.event;

import jdw.event.Event;
import jdw.irc.IRCClient;

/**
 * The Default {@link IRCEvent}.
 * 
 * @author Daniel
 *
 */
public abstract class IRCEvent implements Event {
	private final IRCClient source;
	
	public IRCEvent(IRCClient source) {
		this.source = source;
	}
	
	public IRCClient getSource() {
		return source;
	}
}