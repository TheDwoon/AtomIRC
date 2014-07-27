package jdw.irc.net.command.irc;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.ModeChangeEvent;
import jdw.irc.net.Response;

public class ModeExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public ModeExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser sender = IRCUser.parseFromString(r.getPrefix());		
		
		IRCChannel channel = null;
		IRCUser user = null;
		String mode = null;
		
		switch (r.getArgs().length) {
		case 2:
			user = IRCUser.parseFromString(r.getArgs()[0]);
			mode = r.getArgs()[1];
			break;
		case 3:
			channel = client.getChannelManager().getChannelByName(r.getArgs()[0]);
			mode = r.getArgs()[1];
			user = IRCUser.parseFromString(r.getArgs()[2]);
			break;
		default:			
			break;
		}

		ModeChangeEvent event = new ModeChangeEvent(client, sender, channel, user, mode);
		client.getEventSystem().dispatchEvent(event);
	}
}