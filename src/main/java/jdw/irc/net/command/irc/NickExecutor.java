package jdw.irc.net.command.irc;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.NickChangeEvent;
import jdw.irc.net.Response;

public class NickExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public NickExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());
		
		String oldNick = user.getNick();
		String newNick = r.getTrailing();
		
		user.setNick(newNick);

		NickChangeEvent event = new NickChangeEvent(client, oldNick, user);
		client.getEventSystem().dispatchEvent(event);
	}
}