package jdw.irc.net.command.ctcp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.event.CTCPEvent;
import jdw.irc.event.CTCPEventType;
import jdw.irc.net.Response;
import jdw.irc.net.command.irc.ResponseExecutor;

public class CTimeExecutor implements ResponseExecutor {
	private final SimpleDateFormat formatter;
	private final IRCClient client;
	
	public CTimeExecutor(IRCClient client) {
		this.formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
		this.client = client;		
	}
	
	@Override
	public void executeResponse(Response r) {
		IRCUser user = client.getUserManager().getUserFromString(r.getPrefix());
		
		assert user != null;		
				
		CTCPEvent event = new CTCPEvent(client, CTCPEventType.TIME, user, formatter.format(Calendar.getInstance().getTime()));
		client.getEventFactory().raiseCTCPEvent(event);
		
		if (!event.isCanceled())
			client.notice(user, "\001TIME " + event.getResponse() + "\001");
	}
}