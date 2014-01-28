package jdw.irc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IRCUserCollection {
	protected final List<IRCUser> users;
	
	public IRCUserCollection() {
		this.users = new ArrayList<IRCUser>();
	}
	
	public List<IRCUser> searchUsers(String regex) {
		List<IRCUser> result = new LinkedList<IRCUser>();
		
		for (IRCUser user : users) {
			if (user.match(regex))
				result.add(user);
		}
		
		return result;
	}
	
	public List<IRCUser> searchUsersByNick(String regex) {
		List<IRCUser> result = new LinkedList<IRCUser>();
		
		for (IRCUser user : users) {
			if (user.matchNick(regex))
				result.add(user);
		}
		
		return result;
	}
	
	public List<IRCUser> searchUsersByUser(String regex) {
		List<IRCUser> result = new LinkedList<IRCUser>();
		
		for (IRCUser user : users) {
			if (user.matchUser(regex))
				result.add(user);
		}
		
		return result;
	}
	
	public List<IRCUser> searchUsersByHost(String regex) {
		List<IRCUser> result = new LinkedList<IRCUser>();
		
		for (IRCUser user : users) {
			if (user.matchHost(regex))
				result.add(user);
		}
		
		return result;
	}
	
	public void addUser(IRCUser user) {
		if (user == null || users.contains(user)) 
			return;
		
		users.add(user);
	}
	
	public boolean removeUser(IRCUser user) {
		return users.remove(user);
	}
	
	public List<IRCUser> getUsers() {
		return new ArrayList<IRCUser>(users);
	}
	
	public boolean contains(IRCUser user) {
		return users.contains(user);
	}
	
	public IRCUser getUser(int index) {
		return users.get(index);
	}
	
	public IRCUser getUserByNick(String userName) {
		for (IRCUser user : users) {
			if (userName.equals(user.getNick())) {
				return user;
			}
		}
		
		return null;
	}
	
	public int size() {
		return users.size();
	}
}