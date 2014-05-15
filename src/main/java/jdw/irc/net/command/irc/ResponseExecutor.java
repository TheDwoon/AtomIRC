package jdw.irc.net.command.irc;

import jdw.irc.net.Response;

public interface ResponseExecutor {
	public void executeResponse(Response r);
}