package jdw.irc.net.command.irc;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.UserJoinEvent;
import jdw.irc.net.Response;

public class JoinExecutor implements ResponseExecutor {
	private IRCClient client;

	public JoinExecutor(IRCClient client) {
		this.client = client;
	}

	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());

		IRCChannel channel = client.getChannelManager().getChannelByName(
				r.getArgs()[0]);
		if (channel == null) {
			channel = new IRCChannel(r.getArgs()[0]);
			client.getChannelManager().registerChannel(channel);
		}

		channel.addUser(user);

		UserJoinEvent event = new UserJoinEvent(client, user, channel);
		client.getEventSystem().dispatchEvent(event);
	}
}