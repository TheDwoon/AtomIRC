package jdw.irc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A collection of IRCUsers.
 * 
 * @author TheDwoon
 */
public class IRCUserCollection {
	protected final List<IRCUser> users;
	
	/**
	 * Creates a new {@link IRCUserCollection}.
	 */
	public IRCUserCollection() {
		this.users = new ArrayList<IRCUser>();
	}
	
	/**
	 * Searches for all users matching the given pattern.
	 * It will match against <br/> <code>"nick!user@host"</code>
	 * 
	 * @param regex A search pattern.
	 * @return A list of users matching the given pattern.
	 */
	public List<IRCUser> searchUsers(String regex) {
		List<IRCUser> result = new LinkedList<IRCUser>();
		
		for (IRCUser user : users) {
			if (user.match(regex))
				result.add(user);
		}
		
		return result;
	}
	
	/**
	 * Searches for all users and matches their nick with the given pattern.
	 * 
	 * @param regex A search pattern.
	 * @return A list of users matching.
	 */
	public List<IRCUser> searchUsersByNick(String regex) {
		List<IRCUser> result = new LinkedList<IRCUser>();
		
		for (IRCUser user : users) {
			if (user.matchNick(regex))
				result.add(user);
		}
		
		return result;
	}
	
	/**
	 * Searches for all users and matches their user with the given pattern.
	 * 
	 * @param regex A search pattern.
	 * @return A list of users matching.
	 */
	public List<IRCUser> searchUsersByUser(String regex) {
		List<IRCUser> result = new LinkedList<IRCUser>();
		
		for (IRCUser user : users) {
			if (user.matchUser(regex))
				result.add(user);
		}
		
		return result;
	}
	
	/**
	 * Searches for all users and matches their host with the given pattern.
	 * 
	 * @param regex A search pattern.
	 * @return A list of users matching.
	 */
	public List<IRCUser> searchUsersByHost(String regex) {
		List<IRCUser> result = new LinkedList<IRCUser>();
		
		for (IRCUser user : users) {
			if (user.matchHost(regex))
				result.add(user);
		}
		
		return result;
	}
	
	/**
	 * Adds a user.
	 * If the user is null or is already in the list it won't be added again.
	 * 
	 * @param user The {@link IRCUser} to add.
	 */
	public void addUser(IRCUser user) {
		if (user == null || users.contains(user)) 
			return;
		
		//Remove users with the same nick.
		List<IRCUser> foundUsers = searchUsersByNick(user.getNick());
		for (IRCUser u : foundUsers) {
			removeUser(u);
		}
		
		users.add(user);
	}
	
	/**
	 * Removes a user from the collection.
	 * 
	 * @param user The {@link IRCUser} to remove.
	 * @return true on success, false otherwise.
	 */
	public boolean removeUser(IRCUser user) {
		return users.remove(user);
	}
	
	/**
	 * Returns all users in the list.
	 * 
	 * @return A copy of the userlist.
	 */
	public List<IRCUser> getUsers() {
		return new ArrayList<IRCUser>(users);
	}
	
	/**
	 * Returns true if the user is contained in the collection.
	 * 
	 * @param user The {@link IRCUser}.
	 * @return true if it is contained, false otherwise.
	 */
	public boolean contains(IRCUser user) {
		return users.contains(user);
	}
	
	/**
	 * Returns the {@link IRCUser} at the given index.
	 * 
	 * @param index The index.
	 * @return The {@link IRCUser} at the given index.
	 */
	public IRCUser getUser(int index) {
		return users.get(index);
	}
	
	/**
	 * Returns the first {@link IRCUser} with the given nick.
	 * 
	 * @param userName The nick to search for.
	 * @return The {@link IRCUser} with the given nick or null.
	 */
	public IRCUser getUserByNick(String userName) {
		for (IRCUser user : users) {
			if (userName.equals(user.getNick())) {
				return user;
			}
		}
		
		return null;
	}
	
	/**
	 * @return How many {@link IRCUser}s are in this collection
	 */
	public int size() {
		return users.size();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append('[');
		
		for (int i = 0; i < users.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			
			sb.append(users.get(i).toString());
		}
		
		sb.append(']');
		
		return sb.toString();
	}
}