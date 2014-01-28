package jdw.irc.event;

public interface IRCEventListener {
	public void onUserJoinEvent(UserJoinEvent event);
	public void onMessageEvent(MessageEvent event);
	public void onUserQuitEvent(UserQuitEvent event);
	public void onNoticeEvent(NoticeEvent event);
	public void onPingEvent(PingEvent event);
	public void onModeChangeEvent(ModeChangeEvent event);
	public void onNumericResponseEvent(NumericResponseEvent event);
	public void onNickChangeEvent(NickChangeEvent event);
	public void onUnknownCommandEvent(UnknownCommandEvent event);
	public void onMeEvent(MeEvent event);
	public void onCTCPEvent(CTCPEvent event);
	public void onUserPartEvent(UserPartEvent event);
	public void onPrivateMessageEvent(PrivateMessageEvent event);
	public void onConnectionLostEvent(ConnectionLostEvent event);
}