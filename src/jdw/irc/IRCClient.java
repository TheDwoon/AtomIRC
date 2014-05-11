package jdw.irc;

import static jdw.irc.event.NumericResponseEvent.*;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import jdw.event.EventDispatcher;
import jdw.event.SyncQueueEventDispatcher;
import jdw.irc.event.ConnectionLostEvent;
import jdw.irc.net.Connection;
import jdw.irc.net.Response;
import jdw.irc.net.command.ctcp.CActionExecutor;
import jdw.irc.net.command.ctcp.CDefaultExecutor;
import jdw.irc.net.command.ctcp.CPingExecutor;
import jdw.irc.net.command.ctcp.CTimeExecutor;
import jdw.irc.net.command.ctcp.CVersionExecutor;
import jdw.irc.net.command.irc.DefaultResponseExecutor;
import jdw.irc.net.command.irc.JoinExecutor;
import jdw.irc.net.command.irc.ModeExecutor;
import jdw.irc.net.command.irc.NamReplyExecutor;
import jdw.irc.net.command.irc.NickExecutor;
import jdw.irc.net.command.irc.NoticeExecutor;
import jdw.irc.net.command.irc.NumericExecutor;
import jdw.irc.net.command.irc.PartExecutor;
import jdw.irc.net.command.irc.PingExecutor;
import jdw.irc.net.command.irc.PrivateMessageExecutor;
import jdw.irc.net.command.irc.QuitExecutor;
import jdw.irc.net.command.irc.ResponseManager;

/**
 * 
 * 
 * @author Daniel
 *
 */
public class IRCClient implements Observer {
	public static final String VERSION_NAME = "AtomIRC-Core";
	public static final String VERSION = "0.3.2";
	public static final String CREATOR = "TheDwoon";
			
	public static final File logFile = new File("irc-core.log");
	public static final Logger logger = Logger.getLogger("irc-core");
	
	public static boolean DEBUG = false;
	
	static {
		try {
			logger.addHandler(new FileHandler(logFile.getAbsolutePath(), true));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final Connection connection;
	
	private EventDispatcher evtDispatcher;
	
	private ResponseManager cmdMgr;	
	private ResponseManager ctcpMgr;
	private IRCChannelManager channelMgr;
	private IRCUserManager userMgr;
	
	private String password;
	private IRCUser me;
	
	private IRCClient(Connection connection) {
		this.connection = connection;		
		
		this.evtDispatcher = new SyncQueueEventDispatcher();
		((SyncQueueEventDispatcher) this.evtDispatcher).start();
		
		this.ctcpMgr = new ResponseManager();
		this.cmdMgr = new ResponseManager();
		this.channelMgr = new IRCChannelManager();
		this.userMgr = new IRCUserManager();
				
		initCmdMgr();
		initCtcpMgr();
		
		connection.deleteObservers();
		connection.addObserver(this);		
	}	
	
	private void initCtcpMgr() {
		ctcpMgr.setDefaultExecutor(new CDefaultExecutor(this));
		ctcpMgr.setExecutor("PING", new CPingExecutor(this));
		ctcpMgr.setExecutor("VERSION", new CVersionExecutor(this));
		ctcpMgr.setExecutor("TIME", new CTimeExecutor(this));		
		ctcpMgr.setExecutor("ACTION", new CActionExecutor(this));
	}
	
	private void initCmdMgr() {		
		//Commands
		cmdMgr.setDefaultExecutor(new DefaultResponseExecutor(this));
		cmdMgr.setExecutor("PING", new PingExecutor(this));
		cmdMgr.setExecutor("PRIVMSG", new PrivateMessageExecutor(this));
		cmdMgr.setExecutor("NOTICE", new NoticeExecutor(this));
		cmdMgr.setExecutor("MODE", new ModeExecutor(this));
		cmdMgr.setExecutor("JOIN", new JoinExecutor(this));
		cmdMgr.setExecutor("QUIT", new QuitExecutor(this));
		cmdMgr.setExecutor("NICK", new NickExecutor(this));
		cmdMgr.setExecutor("PART", new PartExecutor(this));
		
		//Numeric Responses
		cmdMgr.setExecutor(ERR_NOSUCHNICK, new NumericExecutor(this, ERR_NOSUCHNICK));
		cmdMgr.setExecutor(ERR_NOSUCHSERVER , new NumericExecutor(this, ERR_NOSUCHSERVER));
		cmdMgr.setExecutor(ERR_NOSUCHCHANNEL , new NumericExecutor(this, ERR_NOSUCHCHANNEL));
		cmdMgr.setExecutor(ERR_CANNOTSENDTOCHAN , new NumericExecutor(this, ERR_CANNOTSENDTOCHAN));
		cmdMgr.setExecutor(ERR_TOOMANYCHANNELS , new NumericExecutor(this, ERR_TOOMANYCHANNELS));
		cmdMgr.setExecutor(ERR_WASNOSUCHNICK , new NumericExecutor(this, ERR_WASNOSUCHNICK));
		cmdMgr.setExecutor(ERR_TOOMANYTARGETS , new NumericExecutor(this, ERR_TOOMANYTARGETS));
		cmdMgr.setExecutor(ERR_NOORIGIN , new NumericExecutor(this, ERR_NOORIGIN));
		cmdMgr.setExecutor(ERR_NORECIPIENT , new NumericExecutor(this, ERR_NORECIPIENT));
		cmdMgr.setExecutor(ERR_NOTEXTTOSEND , new NumericExecutor(this, ERR_NOTEXTTOSEND));
		cmdMgr.setExecutor(ERR_NOTOPLEVEL , new NumericExecutor(this, ERR_NOTOPLEVEL));
		cmdMgr.setExecutor(ERR_WILDTOPLEVEL , new NumericExecutor(this, ERR_WILDTOPLEVEL));
		cmdMgr.setExecutor(ERR_UNKNOWNCOMMAND , new NumericExecutor(this, ERR_UNKNOWNCOMMAND));
		cmdMgr.setExecutor(ERR_NOMOTD , new NumericExecutor(this, ERR_NOMOTD));
		cmdMgr.setExecutor(ERR_NOADMININFO , new NumericExecutor(this, ERR_NOADMININFO));
		cmdMgr.setExecutor(ERR_FILEERROR , new NumericExecutor(this, ERR_FILEERROR));
		cmdMgr.setExecutor(ERR_NONICKNAMEGIVEN , new NumericExecutor(this, ERR_NONICKNAMEGIVEN));
		cmdMgr.setExecutor(ERR_ERRONEUSNICKNAME , new NumericExecutor(this, ERR_ERRONEUSNICKNAME));
		cmdMgr.setExecutor(ERR_NICKNAMEINUSE , new NumericExecutor(this, ERR_NICKNAMEINUSE));
		cmdMgr.setExecutor(ERR_NICKCOLLISION , new NumericExecutor(this, ERR_NICKCOLLISION));
		cmdMgr.setExecutor(ERR_USERNOTINCHANNEL , new NumericExecutor(this, ERR_USERNOTINCHANNEL));
		cmdMgr.setExecutor(ERR_NOTONCHANNEL , new NumericExecutor(this, ERR_NOTONCHANNEL));
		cmdMgr.setExecutor(ERR_USERONCHANNEL , new NumericExecutor(this, ERR_USERONCHANNEL));
		cmdMgr.setExecutor(ERR_NOLOGIN , new NumericExecutor(this, ERR_NOLOGIN));
		cmdMgr.setExecutor(ERR_SUMMONDISABLED , new NumericExecutor(this, ERR_SUMMONDISABLED));
		cmdMgr.setExecutor(ERR_USERSDISABLED , new NumericExecutor(this, ERR_USERSDISABLED));
		cmdMgr.setExecutor(ERR_NOTREGISTERED , new NumericExecutor(this, ERR_NOTREGISTERED));
		cmdMgr.setExecutor(ERR_NEEDMOREPARAMS , new NumericExecutor(this, ERR_NEEDMOREPARAMS));
		cmdMgr.setExecutor(ERR_ALREADYREGISTERED , new NumericExecutor(this, ERR_ALREADYREGISTERED));
		cmdMgr.setExecutor(ERR_NOPERMFORHOST , new NumericExecutor(this, ERR_NOPERMFORHOST));
		cmdMgr.setExecutor(ERR_PASSWDMISMATCH , new NumericExecutor(this, ERR_PASSWDMISMATCH));
		cmdMgr.setExecutor(ERR_YOUREBANNEDCREEP , new NumericExecutor(this, ERR_YOUREBANNEDCREEP));
		cmdMgr.setExecutor(ERR_KEYSET , new NumericExecutor(this, ERR_KEYSET));
		cmdMgr.setExecutor(ERR_CHANNELISFULL , new NumericExecutor(this, ERR_CHANNELISFULL));
		cmdMgr.setExecutor(ERR_UNKNOWNMODE , new NumericExecutor(this, ERR_UNKNOWNMODE));
		cmdMgr.setExecutor(ERR_INVITEONLYCHAN , new NumericExecutor(this, ERR_INVITEONLYCHAN));
		cmdMgr.setExecutor(ERR_BANNEDFROMCHAN , new NumericExecutor(this, ERR_BANNEDFROMCHAN));
		cmdMgr.setExecutor(ERR_BADCHANNELKEY , new NumericExecutor(this, ERR_BADCHANNELKEY));
		cmdMgr.setExecutor(ERR_NOPRIVILEGES , new NumericExecutor(this, ERR_NOPRIVILEGES));
		cmdMgr.setExecutor(ERR_CHANOPRIVSNEEDED , new NumericExecutor(this, ERR_CHANOPRIVSNEEDED));
		cmdMgr.setExecutor(ERR_CANTKILLSERVER , new NumericExecutor(this, ERR_CANTKILLSERVER));
		cmdMgr.setExecutor(ERR_NOOPERHOST , new NumericExecutor(this, ERR_NOOPERHOST));
		cmdMgr.setExecutor(ERR_UMODEUNKNOWNFLAG , new NumericExecutor(this, ERR_UMODEUNKNOWNFLAG));
		cmdMgr.setExecutor(ERR_USERSDONTMATCH , new NumericExecutor(this, ERR_USERSDONTMATCH));
		
		cmdMgr.setExecutor("001", new NumericExecutor(this, RPL_WELCOME));
		cmdMgr.setExecutor("002", new NumericExecutor(this, RPL_YOURHOST));
		cmdMgr.setExecutor("003", new NumericExecutor(this, RPL_CREATED));
		cmdMgr.setExecutor("004", new NumericExecutor(this, RPL_MYINFO));
		cmdMgr.setExecutor("005", new NumericExecutor(this, RPL_BOUNCE));		
		cmdMgr.setExecutor(RPL_NONE , new NumericExecutor(this, RPL_NONE));
		cmdMgr.setExecutor(RPL_USERHOST , new NumericExecutor(this, RPL_USERHOST));
		cmdMgr.setExecutor(RPL_ISON , new NumericExecutor(this, RPL_ISON));
		cmdMgr.setExecutor(RPL_AWAY , new NumericExecutor(this, RPL_AWAY));
		cmdMgr.setExecutor(RPL_UNAWAY , new NumericExecutor(this, RPL_UNAWAY));
		cmdMgr.setExecutor(RPL_NOAWAY , new NumericExecutor(this, RPL_NOAWAY));
		cmdMgr.setExecutor(RPL_WHOISUSER , new NumericExecutor(this, RPL_WHOISUSER));
		cmdMgr.setExecutor(RPL_WHOISSERVER , new NumericExecutor(this, RPL_WHOISSERVER));
		cmdMgr.setExecutor(RPL_WHOISOPERATOR , new NumericExecutor(this, RPL_WHOISOPERATOR));
		cmdMgr.setExecutor(RPL_WHOISIDLE , new NumericExecutor(this, RPL_WHOISIDLE));
		cmdMgr.setExecutor(RPL_ENDOFWHOIS , new NumericExecutor(this, RPL_ENDOFWHOIS));
		cmdMgr.setExecutor(RPL_WHOISCHANNELS , new NumericExecutor(this, RPL_WHOISCHANNELS));
		cmdMgr.setExecutor(RPL_WHOWASUSER , new NumericExecutor(this, RPL_WHOWASUSER));
		cmdMgr.setExecutor(RPL_ENDOFWHOWAS , new NumericExecutor(this, RPL_ENDOFWHOWAS));
		cmdMgr.setExecutor(RPL_LISTSTART , new NumericExecutor(this, RPL_LISTSTART));
		cmdMgr.setExecutor(RPL_LIST , new NumericExecutor(this, RPL_LIST));
		cmdMgr.setExecutor(RPL_LISTEND , new NumericExecutor(this, RPL_LISTEND));
		cmdMgr.setExecutor(RPL_CHANNELMODEIS , new NumericExecutor(this, RPL_CHANNELMODEIS));
		cmdMgr.setExecutor(RPL_NOTOPIC , new NumericExecutor(this, RPL_NOTOPIC));
		cmdMgr.setExecutor(RPL_TOPIC , new NumericExecutor(this, RPL_TOPIC));
		cmdMgr.setExecutor(RPL_INVITING , new NumericExecutor(this, RPL_INVITING));
		cmdMgr.setExecutor(RPL_SUMMONING , new NumericExecutor(this, RPL_SUMMONING));
		cmdMgr.setExecutor(RPL_VERSION , new NumericExecutor(this, RPL_VERSION));
		cmdMgr.setExecutor(RPL_WHOREPLY , new NumericExecutor(this, RPL_WHOREPLY));
		cmdMgr.setExecutor(RPL_ENDOFWHO , new NumericExecutor(this, RPL_ENDOFWHO));
		cmdMgr.setExecutor(RPL_NAMREPLY , new NamReplyExecutor(this, RPL_NAMREPLY));
		cmdMgr.setExecutor(RPL_ENDOFNAMES , new NumericExecutor(this, RPL_ENDOFNAMES));
		cmdMgr.setExecutor(RPL_LINKS , new NumericExecutor(this, RPL_LINKS));
		cmdMgr.setExecutor(RPL_ENDOFLINKS , new NumericExecutor(this, RPL_ENDOFLINKS));
		cmdMgr.setExecutor(RPL_BANLIST , new NumericExecutor(this, RPL_BANLIST));
		cmdMgr.setExecutor(RPL_ENDOFBANLIST , new NumericExecutor(this, RPL_ENDOFBANLIST));
		cmdMgr.setExecutor(RPL_INFO , new NumericExecutor(this, RPL_INFO));
		cmdMgr.setExecutor(RPL_ENDOFINFO , new NumericExecutor(this, RPL_ENDOFINFO));
		cmdMgr.setExecutor(RPL_MOTDSTART , new NumericExecutor(this, RPL_MOTDSTART));
		cmdMgr.setExecutor(RPL_MOTD , new NumericExecutor(this, RPL_MOTD));
		cmdMgr.setExecutor(RPL_ENDOFMOTD , new NumericExecutor(this, RPL_ENDOFMOTD));
		cmdMgr.setExecutor(RPL_YOUREOPER , new NumericExecutor(this, RPL_YOUREOPER));
		cmdMgr.setExecutor(RPL_REHASHING , new NumericExecutor(this, RPL_REHASHING));
		cmdMgr.setExecutor(RPL_TIME , new NumericExecutor(this, RPL_TIME));
		cmdMgr.setExecutor(RPL_USERSSTART , new NumericExecutor(this, RPL_USERSSTART));
		cmdMgr.setExecutor(RPL_USERS , new NumericExecutor(this, RPL_USERS));
		cmdMgr.setExecutor(RPL_ENDOFUSERS , new NumericExecutor(this, RPL_ENDOFUSERS));
		cmdMgr.setExecutor(RPL_NOUSERS , new NumericExecutor(this, RPL_NOUSERS));
		cmdMgr.setExecutor(RPL_TRACELINK , new NumericExecutor(this, RPL_TRACELINK));
		cmdMgr.setExecutor(RPL_TRACECONNECTING , new NumericExecutor(this, RPL_TRACECONNECTING));
		cmdMgr.setExecutor(RPL_TRACEHANDSHAKE , new NumericExecutor(this, RPL_TRACEHANDSHAKE));
		cmdMgr.setExecutor(RPL_TRACEUNKNOWN , new NumericExecutor(this, RPL_TRACEUNKNOWN));
		cmdMgr.setExecutor(RPL_TRACEOPERATOR , new NumericExecutor(this, RPL_TRACEOPERATOR));
		cmdMgr.setExecutor(RPL_TRACEUSER , new NumericExecutor(this, RPL_TRACEUSER));
		cmdMgr.setExecutor(RPL_TRACESERVER , new NumericExecutor(this, RPL_TRACESERVER));
		cmdMgr.setExecutor(RPL_TRACENEWTYPE , new NumericExecutor(this, RPL_TRACENEWTYPE));
		cmdMgr.setExecutor(RPL_TRACELOG , new NumericExecutor(this, RPL_TRACELOG));
		cmdMgr.setExecutor(RPL_STATSLINKINFO , new NumericExecutor(this, RPL_STATSLINKINFO));
		cmdMgr.setExecutor(RPL_STATSCOMMANDS , new NumericExecutor(this, RPL_STATSCOMMANDS));
		cmdMgr.setExecutor(RPL_STATSCLINE , new NumericExecutor(this, RPL_STATSCLINE));
		cmdMgr.setExecutor(RPL_STATSNLINE , new NumericExecutor(this, RPL_STATSNLINE));
		cmdMgr.setExecutor(RPL_STATSILINE , new NumericExecutor(this, RPL_STATSILINE));
		cmdMgr.setExecutor(RPL_STATSKLINE , new NumericExecutor(this, RPL_STATSKLINE));
		cmdMgr.setExecutor(RPL_STATSYLINE , new NumericExecutor(this, RPL_STATSYLINE));
		cmdMgr.setExecutor(RPL_ENDOFSTATS , new NumericExecutor(this, RPL_ENDOFSTATS));
		cmdMgr.setExecutor(RPL_STATSLLINE , new NumericExecutor(this, RPL_STATSLLINE));
		cmdMgr.setExecutor(RPL_STATSUPTIME , new NumericExecutor(this, RPL_STATSUPTIME));
		cmdMgr.setExecutor(RPL_STATSOLINE , new NumericExecutor(this, RPL_STATSOLINE));
		cmdMgr.setExecutor(RPL_STATSHLINE , new NumericExecutor(this, RPL_STATSHLINE));
		cmdMgr.setExecutor(RPL_UMODEIS , new NumericExecutor(this, RPL_UMODEIS));
		cmdMgr.setExecutor(RPL_LUSERCLIENT , new NumericExecutor(this, RPL_LUSERCLIENT));
		cmdMgr.setExecutor(RPL_LUSEROP , new NumericExecutor(this, RPL_LUSEROP));
		cmdMgr.setExecutor(RPL_LUSERUNKNOWN , new NumericExecutor(this, RPL_LUSERUNKNOWN));
		cmdMgr.setExecutor(RPL_LUSERCHANNELS , new NumericExecutor(this, RPL_LUSERCHANNELS));
		cmdMgr.setExecutor(RPL_LUSERME , new NumericExecutor(this, RPL_LUSERME));
		cmdMgr.setExecutor(RPL_ADMINME , new NumericExecutor(this, RPL_ADMINME));
		cmdMgr.setExecutor(RPL_ADMINLOC1 , new NumericExecutor(this, RPL_ADMINLOC1));
		cmdMgr.setExecutor(RPL_ADMINLOC2 , new NumericExecutor(this, RPL_ADMINLOC2));
		cmdMgr.setExecutor(RPL_ADMINEMAIL , new NumericExecutor(this, RPL_ADMINEMAIL));
	}
	
	public static IRCClient connect(String host, int port, String nick, String password, String identity) throws UnknownHostException, IOException {
		Connection connection = Connection.connect(host, port);
				
		IRCClient client = new IRCClient(connection);
				
		client.connect(nick, identity, password);
		
		return client;
	}
	
	private void connect(String nick, String user, String password) throws IOException {
		/*
		 * Direktes Senden um keine reconnect Events auszulösen.
		 * Denn falls das passiert versucht er die Verbindung neu aufzubauen und dann
		 * verwendet er dummerweiße diese Methode...
		 */
		
		this.password = password;
		
		if (password != null && !password.isEmpty())
			connection.sendObject("PASS " + password);
		
		connection.sendObject("NICK " + nick);
		connection.sendObject("USER " + user + " SERVER SERVER :Max Mustermann");
		
		me = getUserManager().getUserFromString(nick);
	}
	
	/**
	 * @return Returns the Version-Name, the Version and the creator.
	 */
	public static String getAboutString() {
		return VERSION_NAME + " " + VERSION + " by " + CREATOR;
	}
	
	/**
	 * @return The host you are connected to.
	 */
	public String getHost() {
		return connection.getHost();
	}
	
	/**
	 * @return The Port you are connected to.
	 */
	public int getPort() {
		return connection.getPort();
	}
	
	/**
	 * Sends the "AUTH" Command.
	 * This should verfify a user for the server.
	 * It won't be send if one ore both arguments are empty or null.
	 * 
	 * @param user The User.
	 * @param password The Password.
	 * @return true if it was send, false otherwise.
	 */
	public synchronized boolean auth(String user, String password) {
		if (user == null || user.isEmpty()
				|| password == null || password.isEmpty())
			return false;
		
		return sendObject("AUTH " + user + " " + password);
	}
	
	/**
	 * Sends a message in a specified channel.
	 * 
	 * @param channel The channel.
	 * @param message The message.
	 * @return true if it was send, false otherwise.
	 */
	public synchronized boolean sendMessage(IRCChannel channel, String message) {
		if (message == null || channel == null 
				|| message.isEmpty() || channel.getName().isEmpty())
			return false;
				
		
		return sendObject("PRIVMSG " + channel.getName() + " :" + message);
	}
	
	public synchronized boolean whisper(IRCUser user, String message) {
		if (user == null || message == null 
				|| user.getNick().isEmpty() || message.isEmpty())
			return false;
		
		return sendObject("PRIVMSG " + user.getNick() + " :" + message);
	}
	
	public synchronized boolean whisper(String user, String message) {
		if (user == null || message == null 
				|| user.isEmpty() || message.isEmpty())
			return false;
		
		return sendObject("PRIVMSG " + user + " :" + message);
	}
	
	public synchronized boolean notice(IRCUser user, String message) {
		if (user == null || message == null 
				|| user.getNick().isEmpty() || message.isEmpty())
			return false;
		
		return sendObject("NOTICE " + user.getNick() + " :" + message);
	}
	
	public synchronized IRCChannel joinChannel(String channelName) {
		if (channelName == null || channelName.isEmpty())
			return null;
		
		if (channelMgr.containsChannel(channelName))
			return null;
		
		IRCChannel channel = new IRCChannel(channelName);
		channelMgr.registerChannel(channel);;
		
		boolean send = sendObject("JOIN " + channelName);
		if (!send) {
			channelMgr.removeChannel(channel);
			return null;
		}
		
		return channel;
	}
	
	public synchronized void leaveChannel(String channelName) {
		if (channelName == null || channelName.isEmpty())
			return;
	}
	
	public synchronized boolean me(IRCChannel channel, String message) {
		if (channel == null || message == null
				|| channel.getName().isEmpty() || message.isEmpty()) {
			return false;
		}
		
		return sendObject("\001ACTION " + message + "\001");
	}
	
	public synchronized boolean changeNick(String newNick) {
		return sendObject("NICK :" + newNick);
	}
	
	public synchronized boolean sendPong(String timestamp) {
		return sendObject("PONG " + timestamp);		
	}
	
	protected synchronized boolean sendObject(Object o) {		
		for (int i = 0; true; i++) {
			try {				
				connection.sendObject(o);
//				logger.info("Successfully transmitted: " + o);
				return true;
			} catch (IOException e) {	
				/*
				 * Der Fehlschlag wird in das Log geschrieben und anschließend per Event gefragt ob er sich neu verbinden soll.
				 * Bei den ersten 3 Versuchen ist der Standartwert reconnect. Der Nutzer kann es aber ändern.
				 */
				logger.info("Sending of \"" + o + "\" failed at attempt " + i + ". Caused by " + e);
				ConnectionLostEvent event = new ConnectionLostEvent(this, (i < 3) ? true : false, i);
				
				getEventSystem().dispatchEvent(event);
				
				if (event.shouldReconnect()) {
					if (connection.isConnected()) {
						logger.info("Connection seems to be ok!");
					} else {
						try {
							connection.reconnect();	
							connect(me.getNick(), me.getUser(), password);
						} catch (IOException e1) {							
							logger.warning("Reconnecting to " + connection.getHost() + ":" + connection.getPort() + " failed!");
						}
					}
				} else {
					return false;
				}
			}			
		}		
	}
	
	public synchronized boolean quit() {
		return quit(null);
	}
	
	public synchronized boolean quit(String message) {
		boolean send;
		if (message == null || message.isEmpty())
			send = sendObject("QUIT Disconneted");
		else
			send = sendObject("QUIT :" + message);
		
		if (!send)
			return false;
		
		close();
		return true;
	}
	
	protected synchronized boolean close() {
		try {
			connection.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	public IRCUser getMe() {
		return me;
	}
	
	public IRCUserManager getUserManager() {
		return userMgr;
	}
	
	public ResponseManager getResponseManager() {
		return cmdMgr;
	}
	
	public IRCChannelManager getChannelManager() {
		return channelMgr;
	}
	
	public EventDispatcher getEventSystem() {
		return evtDispatcher;
	}
	
	public ResponseManager getCommandManager() {
		return ctcpMgr;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//TODO: Hide this part from the user of this.
		/*
		 * Update from the connection to the server.
		 * This should try to execute them.
		 */
//		long start = System.currentTimeMillis();
		Response r = Response.parseResponse((String) arg);		
		cmdMgr.executeResponse(r);
//		long end = System.currentTimeMillis();
//		System.out.println("ExecuteTime:  " + (end - start) + "ms");
	}
}