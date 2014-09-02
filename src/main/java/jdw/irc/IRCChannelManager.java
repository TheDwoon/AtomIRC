package jdw.irc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class IRCChannelManager {
	private final List<IRCChannel> channels;
	
	public IRCChannelManager() {
		this.channels = new LinkedList<IRCChannel>();
	}
	
	public IRCChannel getChannel(int index) {
		return channels.get(index);
	}
	
	public int size() {
		return channels.size();
	}
	
	public IRCChannel getChannelByName(String channelName) {
		for (IRCChannel channel : channels) {
			if (channel.getName().equalsIgnoreCase(channelName)) {
				return channel;
			}
		}
		
		return null;
	}
	
	public List<IRCChannel> getChannels() {
		return new ArrayList<IRCChannel>(channels);
	}
	
	public boolean containsChannel(String channelName) {
		return getChannelByName(channelName) != null;
	}
		
	public void registerChannel(IRCChannel channel) {
		channels.add(channel);
	}
	
	public boolean removeChannelByName(String channelName) {
		IRCChannel channel = getChannelByName(channelName);
		if (channel == null)
			return true;
		
		return removeChannel(channel);
	}
	
	public boolean removeChannel(IRCChannel channel) {
		return channels.remove(channel);
	}
	
	public void removeUserFromAllChannels(IRCUser user) {
		for (IRCChannel channel : channels) 
			channel.removeUser(user);
	}
}