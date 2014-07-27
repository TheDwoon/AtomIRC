package jdw.irc.net.command.ctcp;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.CTCPEvent;
import jdw.irc.event.CTCPEventType;
import jdw.irc.net.Response;
import jdw.irc.net.command.irc.ResponseExecutor;

public class CFingerExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public CFingerExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = IRCUser.parseFromString(r.getPrefix());
		
		assert user != null;
		
		CTCPEvent event = new CTCPEvent(client, CTCPEventType.FINGER, user, "Go search for it!");
		client.getEventSystem().dispatchEvent(event);
		
		if (!event.isCanceled())
			client.notice(user, "\001FINGER " + event.getResponse() + "\001");
	}
}
