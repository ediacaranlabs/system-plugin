package br.com.uoutec.community.ediacaran.system.tema;

public class TemaException extends RuntimeException{

	private static final long serialVersionUID = 4632242435979612295L;

	public TemaException() {
		super();
	}

	public TemaException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemaException(String message) {
		super(message);
	}

	public TemaException(Throwable cause) {
		super(cause);
	}

}
