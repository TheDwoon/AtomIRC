package jdw.irc.net;

import java.util.Arrays;


public class Response {
	private String raw;
	private String error;
	private String prefix;
	private String command;
	private String trailing;
	private String[] args;
	
	public Response() {
		setRaw(null);
		setPrefix(null);
		setCommand(null);
		setArgs(null);
		setTrailing(null);
	}
	
	public static Response parseResponse(String line) {
		Response r = new Response();
		
		if (line == null)
			return null;
		
		r.setRaw(line);
		
		if (line.startsWith(":")) {
			//PREFIX
			int spaceIndex = line.indexOf(" ");
			r.setPrefix(line.substring(1, spaceIndex));
			line = line.substring(spaceIndex + 1);
		}
		
		//Command
		int spaceIndex = line.indexOf(" ");
		r.setCommand(line.substring(0, spaceIndex));
		line = line.substring(spaceIndex + 1);			
		
		
		int colonIndex = line.indexOf(":");
		if (colonIndex >= 0) {
			r.setTrailing(line.substring(colonIndex + 1));
			line = line.substring(0, colonIndex);
		} 
		
		r.setArgs(line.split(" "));
		
		return r;
	}

	@Override
	public String toString() {
		return "Response [raw=" + raw + ", error=" + error + ", prefix="
				+ prefix + ", command=" + command + ", trailing=" + trailing
				+ ", args=" + Arrays.toString(args) + "]";
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getTrailing() {
		return trailing;
	}

	public void setTrailing(String trailing) {
		this.trailing = trailing;
	}
}