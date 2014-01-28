package jdw.irc.net.command.irc;

import jdw.irc.IRCClient;
import jdw.irc.Log;
import jdw.irc.event.PingEvent;
import jdw.irc.net.Response;

public class PingExecutor implements ResponseExecutor {
	private final IRCClient client;
	
	public PingExecutor(IRCClient client) {		
		this.client = client;
	}

	@Override
	public void executeResponse(Response r) {
		String message = r.getTrailing();
						
		Log.notNull(message, "Message was null! " + Log.dumpInformation(this, r));
		
		PingEvent event = new PingEvent(client, message);
		client.getEventFactory().raisePingEvent(event);
		
		client.sendPong(r.getTrailing());
	}
}