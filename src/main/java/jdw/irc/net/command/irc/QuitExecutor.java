package jdw.irc.net.command.irc;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.UserQuitEvent;
import jdw.irc.net.Response;

public class QuitExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public QuitExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());
		String message = r.getTrailing();
		
		client.getChannelManager().removeUserFromAllChannels(user);
		
		UserQuitEvent event = new UserQuitEvent(client, user, message);
		client.getEventSystem().dispatchEvent(event);
	}
}