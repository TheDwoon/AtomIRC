package jdw.irc.net.command.irc;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.Log;
import jdw.irc.event.NoticeEvent;
import jdw.irc.net.Response;

public class NoticeExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public NoticeExecutor(IRCClient client) {
		this.client = client;
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getArgs()[0]);
		String message = r.getTrailing();
		
		Log.notNull(user, "User was null! " + Log.dumpInformation(this, r));
		Log.notNull(message, "Message was null! " + Log.dumpInformation(this, r));
		
		NoticeEvent event = new NoticeEvent(client, user, message);
		client.getEventFactory().raiseNoticeEvent(event);
	}
}