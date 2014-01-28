package jdw.irc.net.command.irc;

import java.util.HashMap;

import jdw.irc.net.Response;

public class ResponseManager implements ResponseExecutor {
	private final HashMap<String, ResponseExecutor> executors;
	
	private ResponseExecutor defaultExecutor;
	
	public ResponseManager() {
		this.executors = new HashMap<String, ResponseExecutor>();
	}
	
	public void setExecutor(String label, ResponseExecutor executor) {
		executors.put(label.toLowerCase(), executor);
	}
	
	public void setExecutor(int label, ResponseExecutor executor) {
		executors.put(Integer.toString(label), executor);
	}
	
	public ResponseExecutor remove(String label) {
		return executors.remove(label);
	}

	@Override
	public void executeResponse(Response r) {
		if (r == null)
			return;
		
		ResponseExecutor executor = executors.get(r.getCommand().toLowerCase());
		if (executor == null) {			
			executor = defaultExecutor;
		} 
		
		if (executor != null)
			executor.executeResponse(r);
	}

	public void setDefaultExecutor(ResponseExecutor defaultExecutor) {
		this.defaultExecutor = defaultExecutor;
	}
}