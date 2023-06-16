package br.com.uoutec.community.ediacaran.system.error;

public class ErrorMessageMappingException extends Exception{

	private static final long serialVersionUID = -3468654795628033264L;

	public ErrorMessageMappingException() {
		super();
	}

	public ErrorMessageMappingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorMessageMappingException(String message) {
		super(message);
	}

	public ErrorMessageMappingException(Throwable cause) {
		super(cause);
	}

}
