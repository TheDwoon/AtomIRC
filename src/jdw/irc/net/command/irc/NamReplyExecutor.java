package jdw.irc.net.command.irc;

import jdw.irc.IRCChannel;
import jdw.irc.IRCClient;
import jdw.irc.IRCUser;
import jdw.irc.net.Response;

public class NamReplyExecutor extends NumericExecutor {
	public NamReplyExecutor(IRCClient client, int id) {
		super(client, id);
	}

	@Override
	public void executeResponse(Response r) {
		assert r.getArgs() != null;
		assert r.getArgs().length == 3;
		assert r.getTrailing() != null;
		
		IRCChannel channel = getClient().getChannelManager().getChannelByName(r.getArgs()[2]);
		
		assert channel != null;
		
		String[] userInfo = r.getTrailing().split(" ");
		for (String userString : userInfo) {
			IRCUser user = processUser(userString);
			channel.addUser(user);
		}		
		
		super.executeResponse(r);				
	}
	
	private IRCUser processUser(String userString) {
		if (userString.startsWith("+") || userString.startsWith("@")) {
			userString = userString.substring(1);
		}
		
		return getClient().getUserManager().getUserFromString(userString);		
	}
}