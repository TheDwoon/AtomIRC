package jdw.irc.net.command.irc;

import jdw.irc.IRCClient;
import jdw.irc.Log;
import jdw.irc.event.UnknownCommandEvent;
import jdw.irc.net.Response;

public class DefaultResponseExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public DefaultResponseExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		Log.notNull(null, "No executor set for: " + r.getCommand() + " " + Log.dumpInformation(this, r));
		
		UnknownCommandEvent event = new UnknownCommandEvent(client, r);
		client.getEventFactory().raiseUnknownCommandEvent(event);
	}
}