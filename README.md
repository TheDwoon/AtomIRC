AtomIRC
=======

A simple IRC-Client without any GUI.

Usage:
First off you have to clone this project and the EventSystem [https://github.com/TheDwoon/EventSystem].
After you have setup your workspace you might first implement a class implementing EventListener.
In this class you can write some methods using this syntax:

@EventHandler
public void onMessageEvent(MessageEvent event) {
  // Do something.
}

These Methods will be called by the EventSystem if someone fires an Event matching the methods argument.

Now you may call IRCClient.connect(...) and get an instance of it. Now you must register youre implemented listener by using the client.getEventSystem().registerListener(...). From now on you could call methods in the client and events will be send to every listener you registered.
