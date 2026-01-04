package br.com.uoutec.community.ediacaran.system.lock;

public class LockManagerProviderException extends RuntimeException{

	private static final long serialVersionUID = -5202390528122394876L;
	
	public LockManagerProviderException() {
		super();
	}

	public LockManagerProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockManagerProviderException(String message) {
		super(message);
	}

	public LockManagerProviderException(Throwable cause) {
		super(cause);
	}

}
