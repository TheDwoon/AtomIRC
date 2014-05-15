package jdw.irc;


public class IRCUserManager extends IRCUserCollection {	
	public IRCUserManager() {		
	}
	
	/**
	 * Updates the saved equivalent of the user and returns the saved one.
	 * 
	 * @param user User to be updated.
	 * @return the saved user.
	 */
	private IRCUser updateUser(IRCUser user) {		
		IRCUser savedUser = getUserByNick(user.getNick());
		if (savedUser == null) {
			addUser(user);
			savedUser = user;
		} else {
			if (!savedUser.hasUser() && user.hasUser()) {
				savedUser.setUser(user.getUser());
			}
			if (!savedUser.hasHost() && user.hasHost()) {
				savedUser.setHost(user.getHost());
			}
		}
		
		return savedUser;
	}
	
	public IRCUser getUserFromString(String prefix) {
		IRCUser user = IRCUser.parseFromString(prefix);
		return updateUser(user);		
	}
}