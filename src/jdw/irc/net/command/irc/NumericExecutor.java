package jdw.irc.net.command.irc;

import jdw.irc.IRCClient;
import jdw.irc.event.NumericResponseEvent;
import jdw.irc.net.Response;

/**
 * A basic executor for the numeric responses. Mainly raising the events.
 * 
 * @author Daniel
 *
 */
public class NumericExecutor implements ResponseExecutor {
	private final IRCClient client;
	private final int id;
	
	public NumericExecutor(IRCClient client, int id) {
		this.client = client;
		this.id = id;
	}
	
	@Override
	public void executeResponse(Response r) {
		String args[] = r.getArgs();
		String message = r.getTrailing();				
		
		NumericResponseEvent event = new NumericResponseEvent(client, id, args, message);
		client.getEventFactory().raiseNumericResponseEvent(event);
	}
	
	protected IRCClient getClient() {
		return client;
	}
	
	protected int getId() {
		return id;
	}
}