package jdw.irc;


public class IRCChannel extends IRCUserCollection {
	private String name;	
	
	public IRCChannel(String name) {
		this.name = name;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean match(String regex) {
		return name.matches(regex);
	}
	
	@Override
	public String toString() {
		return "IRCChannel [name=" + name + ", users=" + users + "]";
	}
}