package jdw.irc.net.command.irc;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.MessageEvent;
import jdw.irc.event.PrivateMessageEvent;
import jdw.irc.net.Response;

public class PrivateMessageExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public PrivateMessageExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {		
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());
		IRCChannel channel = client.getChannelManager().getChannelByName(r.getArgs()[0]);
		String message = r.getTrailing();

		if (message.startsWith("\001")) {
			message = message.replace("\001", "");
			
			int spaceIndex = message.indexOf(" ");
			if (spaceIndex != -1) {
				r.setCommand(message.substring(0, spaceIndex));
				r.setArgs(message.substring(spaceIndex + 1).split(" "));
			} else {
				r.setCommand(message);
			}
						
			client.getCommandManager().executeResponse(r);
		} else {		
			if (channel != null) {
				MessageEvent event = new MessageEvent(client, channel, user, message);		
				client.getEventSystem().dispatchEvent(event);
			} else {
				PrivateMessageEvent event = new PrivateMessageEvent(client, user, message);
				client.getEventSystem().dispatchEvent(event);
			}
		}
	}
}