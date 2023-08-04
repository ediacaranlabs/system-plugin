package br.com.uoutec.community.ediacaran.system.event;

import br.com.uoutec.entity.registry.RegistryException;

public class SystemEventRegistryException extends RegistryException{

	private static final long serialVersionUID = -6373674653483866872L;

	public SystemEventRegistryException() {
		super();
	}

	public SystemEventRegistryException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemEventRegistryException(String message) {
		super(message);
	}

	public SystemEventRegistryException(Throwable cause) {
		super(cause);
	}

}
