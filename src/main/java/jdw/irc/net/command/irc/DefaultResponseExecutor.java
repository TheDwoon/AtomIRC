package jdw.irc.net.command.irc;

import jdw.irc.IRCClient;
import jdw.irc.event.UnknownCommandEvent;
import jdw.irc.net.Response;

public class DefaultResponseExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public DefaultResponseExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		UnknownCommandEvent event = new UnknownCommandEvent(client, r);
		client.getEventSystem().dispatchEvent(event);
	}
}