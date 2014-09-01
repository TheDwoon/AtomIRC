package jdw.irc;

/**
 * Represents a Channel.
 * 
 * @author TheDwoon
 *
 */
public final class IRCChannel extends IRCUserCollection {
	private final String name;	
	
	/**
	 * Creates a Channel.
	 * 
	 * @param name The channels name.
	 */
	public IRCChannel(String name) {
		this.name = name;		
	}

	/**
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Matches the channels name against the given pattern.
	 *  
	 * @param regex A pattern to match with.
	 * @return true if it matches, false otherwise.
	 */
	public boolean match(String regex) {
		return name.matches(regex);
	}
	
	@Override
	public String toString() {
		return "IRCChannel [name=" + name + ", users=" + super.toString() + "]";
	}
}