package jdw.irc;

public class IRCUser {
	private String nick;	
	private String user;
	private String host;
	
	protected static IRCUser parseFromString(String token) {
		IRCUser user = new IRCUser();
		
		int indexNick = token.indexOf("!");				
		if (indexNick != -1) {
			user.setNick(token.substring(0, indexNick));
			token = token.substring(indexNick + 1); 
		}
		
		int indexUser = token.indexOf("@");
		if (indexUser != -1) {
			user.setUser(token.substring(0, indexUser));
			token = token.substring(indexUser + 1);
		}
		
		if (indexUser == -1 && indexNick == -1) {
			//No ! and @ in that string. Just take it as the nick.
			user.setNick(token);			
		} else {		
			user.setHost(token);
		}
		return user;		
	}
	
	protected IRCUser() {
		this(null, null, null);
	}
	
	protected IRCUser(String nick, String user, String host) {
		this.nick = nick;
		this.user = user;
		this.host = host;
	}
	
	public boolean match(String regex) {
		return toString().matches(regex);
	}
	
	public boolean matchNick(String regex) {
		return (nick == null) ? false : nick.matches(regex);
	}
	
	public boolean matchUser(String regex) {
		return (user == null) ? false : user.matches(regex);
	}
	
	public boolean matchHost(String regex) {
		return (host == null) ? false : host.matches(regex);
	}
	
	public boolean hasNick() {
		return nick != null && !nick.isEmpty();
	}
	
	public boolean hasUser() {
		return user != null && !user.isEmpty();
	}
	
	public boolean hasHost() {
		return host != null && !host.isEmpty();
	}
	
	public boolean isComplete() {
		return hasNick() && hasUser() && hasHost();
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}	
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
	
		builder.append(nick).append('!').append(user).append('@').append(host);
		
		return builder.toString();
	}
}