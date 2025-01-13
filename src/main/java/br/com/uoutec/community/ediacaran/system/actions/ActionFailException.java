package br.com.uoutec.community.ediacaran.system.actions;

public class ActionFailException extends Exception{

	private static final long serialVersionUID = 4467704322001043721L;

	public ActionFailException() {
		super();
	}

	public ActionFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionFailException(String message) {
		super(message);
	}

	public ActionFailException(Throwable cause) {
		super(cause);
	}

}
