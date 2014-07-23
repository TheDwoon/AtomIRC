package jdw.irc.net.command.irc;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.UserPartEvent;
import jdw.irc.net.Response;

public class PartExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public PartExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());
		IRCChannel channel = client.getChannelManager().getChannelByName(r.getArgs()[0]);
		
		channel.removeUser(user);
		
		UserPartEvent event = new UserPartEvent(client, user, channel);
		client.getEventSystem().dispatchEvent(event);
	}
}