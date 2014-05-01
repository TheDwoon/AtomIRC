package jdw.irc.net.command.ctcp;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.MeEvent;
import jdw.irc.net.Response;
import jdw.irc.net.command.irc.ResponseExecutor;

public class CActionExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public CActionExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < r.getArgs().length; i++) {
			if (i > 0)
				builder.append(' ');
			
			builder.append(r.getArgs()[i]);
		}
		
		String message = builder.toString();
		
		MeEvent event = new MeEvent(client, user, message);
		client.getEventSystem().dispatchEvent(event);
	}
}