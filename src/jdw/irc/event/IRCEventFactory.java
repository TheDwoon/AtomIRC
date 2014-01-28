package jdw.irc.event;

import java.util.ArrayList;
import java.util.List;

public class IRCEventFactory {
	private final List<IRCEventListener> listeners;
	
	public IRCEventFactory() {
		this.listeners = new ArrayList<IRCEventListener>();
	}
	
	public void registerListener(IRCEventListener listener) {
		if (listener == null)
			return;
		
		listeners.add(listener);
	}
	
	public boolean removeListener(IRCEventListener listener) {
		return listeners.remove(listener);
	}
	
	public void raiseUserJoinEvent(UserJoinEvent event) {
		for (IRCEventListener l : listeners)
			l.onUserJoinEvent(event);
	}
	
	public void raiseUserQuitEvent(UserQuitEvent event) {
		for (IRCEventListener l : listeners) 
			l.onUserQuitEvent(event);
	}
	
	public void raiseMessageEvent(MessageEvent event) {
		for (IRCEventListener l : listeners)
			l.onMessageEvent(event);
	}
	
	public void raiseNoticeEvent(NoticeEvent event) {
		for (IRCEventListener l : listeners) 
			l.onNoticeEvent(event);
	}
	
	public void raisePingEvent(PingEvent event) {
		for (IRCEventListener l : listeners)
			l.onPingEvent(event);
	}
	
	public void raiseModeChangeEvent(ModeChangeEvent event) {
		for (IRCEventListener l : listeners) 
			l.onModeChangeEvent(event);
	}

	public void raiseNumericResponseEvent(NumericResponseEvent event) {
		for (IRCEventListener l : listeners) 
			l.onNumericResponseEvent(event);
	}

	public void raiseNickChangeEvent(NickChangeEvent event) {
		for (IRCEventListener l : listeners)
			l.onNickChangeEvent(event);
	}

	public void raiseUnknownCommandEvent(UnknownCommandEvent event) {
		for (IRCEventListener l : listeners) {
			l.onUnknownCommandEvent(event);
		}
	}

	public void raiseMeEvent(MeEvent event) {
		for (IRCEventListener l : listeners) {
			l.onMeEvent(event);
		}
	}

	public void raiseCTCPEvent(CTCPEvent event) {
		for (IRCEventListener l : listeners) {
			l.onCTCPEvent(event);
		}
	}

	public void raiseUserPartEvent(UserPartEvent event) {
		for (IRCEventListener l : listeners) {
			l.onUserPartEvent(event);
		}
	}

	public void raisePrivateMessageEvent(PrivateMessageEvent event) {
		for (IRCEventListener l : listeners) {
			l.onPrivateMessageEvent(event);
		}
	}

	public void raiseConnectionLostEvent(ConnectionLostEvent event) {
		for (IRCEventListener l : listeners) {
			l.onConnectionLostEvent(event);
		}
	}
}