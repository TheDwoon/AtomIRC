package jdw.irc.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;

//FIXME: Remove observer pattern.
public class Connection extends Observable {	
	private final String host;
	private final int port;
	
	
	private final Socket socket;	
	private final EncryptionMethod encryption;
	private final BufferedWriter output;
	private final BufferedReader input;
	
	private Listener listener;
	
	public Connection(String host, int port, EncryptionMethod encryption) throws IOException {				
		this.host = host;
		this.port = port;
		
		this.encryption = encryption;
		
		switch (encryption) {
			case NONE:
				socket = new Socket(getHost(), getPort());
				break;
			case SSL:
				socket = SSLSocketFactory.getDefault().createSocket(getHost(), getPort());
				break;
			default:
				throw new IllegalArgumentException("Unsupported Encryption!");
		}
		
		output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		
		listener = new Listener();
	}	
		
	public static Connection connect(String host, int port) throws UnknownHostException, IOException {
		return new Connection(host, port, EncryptionMethod.NONE);
	}
		
	public void sendObject(Object o) throws IOException {
		if (o == null) 
			return;
		
		output.write(o.toString());
		output.newLine();
		output.flush();			
	}

	public String recieveObject() throws IOException {				
		return input.readLine();						
	}
	
	public boolean isConnected() {
		return socket.isConnected() && !socket.isClosed();
	}
	
	public void close() throws IOException {
		if (listener != null)
			listener.interrupt();
		if (input != null) 
			input.close();
		if (output != null)
			output.close();
		if (socket != null)
			socket.close();		
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public EncryptionMethod getEncryptionMethod() {
		return encryption;
	}
	
	private class Listener extends Thread {		
		private Listener() {
			setName("Listener for " + host + ":" + port);
			start();
		}
		
		@Override
		public void run() {
			while (!isInterrupted()) {
				try {
					String text = recieveObject();
					
					setChanged();
					notifyObservers(text);
				} catch (IOException e) {
					Logger.getLogger(Connection.class.getName()).severe("Could not recieve Text.");
					break;
				}
			}
			
			System.out.println(this + " stopped!");
		}
	}
}