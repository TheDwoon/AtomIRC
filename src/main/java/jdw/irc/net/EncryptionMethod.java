package jdw.irc.net;

/**
 * Supported encryptions for the {@link Connection}.
 * 
 * @author TheDwoon
 *
 */
public enum EncryptionMethod {
	NONE("Plaintext"),
	SSL("Secure Socket Layer");
	
	private String name;
	
	private EncryptionMethod(String name) {
		this.name = name;
	}
	
	/**
	 * @return The name of the encryption used.
	 */
	public String getMethodName() {
		return name;
	}
}
