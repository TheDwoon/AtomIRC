package jdw.irc;

/**
 * Represents a user.
 * Each user could have a nick, user and a host.
 * 
 * @author TheDwoon
 *
 */
public class IRCUser {
	//FIXME: Make these parts final.
	private String nick;	
	private String user;
	private String host;
	
	public static IRCUser parseFromString(String token) {
		String nick = null;
		String user = null;
		String host = null;
		
		int indexNick = token.indexOf("!");				
		if (indexNick != -1) {
			nick = token.substring(0, indexNick);
			token = token.substring(indexNick + 1); 
		}
		
		int indexUser = token.indexOf("@");
		if (indexUser != -1) {
			user = token.substring(0, indexUser);
			token = token.substring(indexUser + 1);
		}
		
		if (indexUser == -1 && indexNick == -1) {
			//No ! and @ in that string. Just take it as the nick.
			nick = token;			
		} else {		
			host = token;
		}
		
		return new IRCUser(nick, user, host);		
	}
	
	/**
	 * Creates a new user with no user (null) and no host (null).
	 */
	public IRCUser(String nick) {
		this(nick, null, null);
	}
	
	/**
	 * Creates a new user.
	 * 
	 * @param nick Its nick.
	 * @param user Its user.
	 * @param host Its host.
	 */
	public IRCUser(String nick, String user, String host) {	
		this.nick = nick;		
		this.user = user;
		this.host = host;
	}
	
	/**
	 * Matches the composed userstring "<nick>!<user>@<host>" with the given pattern.
	 * 
	 * @param regex The pattern.
	 * @return True if it machtes, false otherwise.
	 */
	public boolean match(String regex) {
		return toString().matches(regex);
	}
	
	/**
	 * Matches the users nick with the given pattern.
	 * 
	 * @param regex The pattern.
	 * @return True if it matches, false otherwise.
	 */
	public boolean matchNick(String regex) {
		return (nick == null) ? false : nick.matches(regex);
	}
	
	/**
	 * Matches the users user with the given pattern.
	 * 
	 * @param regex The pattern.
	 * @return True if it matches, false otherwise.
	 */
	public boolean matchUser(String regex) {
		return (user == null) ? false : user.matches(regex);
	}
	
	/**
	 * Matches the users host with the given pattern.
	 * 
	 * @param regex The pattern.
	 * @return True if it matches, false otherwise.
	 */
	public boolean matchHost(String regex) {
		return (host == null) ? false : host.matches(regex);
	}
	
	/**
	 * @return True if the nick is not null and not empty, false otherwise.
	 */
	public boolean hasNick() {
		return nick != null && !nick.isEmpty();
	}
	
	/**
	 * @return True if the user is not null and not empty, false otherwise.
	 */
	public boolean hasUser() {
		return user != null && !user.isEmpty();
	}
	
	/**
	 * @return True if the host is not null and not empty, false otherwise.
	 */
	public boolean hasHost() {
		return host != null && !host.isEmpty();
	}
	
	/**
	 * @return True if nick, user and host are not set to not null and are not empty, false otherwise.
	 */
	public boolean isComplete() {
		return hasNick() && hasUser() && hasHost();
	}
	
	/**
	 * @return The nick.
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * Changes the nick. You are not allowed to set 
	 * nick to null or an empty value. Every user 
	 * MUST HAVE a valid nick.
	 * 
	 * @param nick New nick.
	 */
	public void setNick(String nick) {
		if (nick == null || nick.isEmpty()) {
			throw new IllegalArgumentException("nick was null empty.");
		}
		
		this.nick = nick;
	}
	
	/**
	 * @return The user.
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Changes the user.
	 * 
	 * @param user New user.
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * @return The host.
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * Changes the host.
	 * 
	 * @param host New host.
	 */
	public void setHost(String host) {
		this.host = host;
	}	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
	
		builder.append(nick).append('!').append(user).append('@').append(host);
		
		return builder.toString();
	}
}