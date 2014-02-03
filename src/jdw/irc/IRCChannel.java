package jdw.irc;

/**
 * Represents a Channel.
 * 
 * @author daniel
 *
 */
public class IRCChannel extends IRCUserCollection {
	private String name;	
	
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
	 * Changes the channels name.
	 * 
	 * @param name The new name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Mathes the channels name against the given pattern.
	 *  
	 * @param regex A pattern to match with.
	 * @return true if it matches, false otherwise.
	 */
	public boolean match(String regex) {
		return name.matches(regex);
	}
	
	@Override
	public String toString() {
		return "IRCChannel [name=" + name + ", users=" + users + "]";
	}
}