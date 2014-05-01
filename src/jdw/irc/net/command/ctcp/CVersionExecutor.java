package jdw.irc.net.command.ctcp;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.CTCPEvent;
import jdw.irc.event.CTCPEventType;
import jdw.irc.net.Response;
import jdw.irc.net.command.irc.ResponseExecutor;

public class CVersionExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public CVersionExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());
		
		assert user != null;
		
		CTCPEvent event = new CTCPEvent(client, CTCPEventType.VERSION, user, "");
		client.getEventSystem().dispatchEvent(event);		
		
		client.notice(user, "\001VERSION " + IRCClient.getAboutString() + " " + event.getResponse() + "\001");
	}
}