package jdw.irc.net.command.ctcp;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.CTCPEvent;
import jdw.irc.event.CTCPEventType;
import jdw.irc.net.Response;
import jdw.irc.net.command.irc.ResponseExecutor;

public class CPingExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public CPingExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());
		
		assert user != null;
		
		CTCPEvent event = new CTCPEvent(client, CTCPEventType.PING, user, r.getArgs()[0]);
		client.getEventSystem().dispatchEvent(event);
		
		if (!event.isCanceled())
			client.notice(user, "\001PING " + event.getResponse() + "\001");
	}
}