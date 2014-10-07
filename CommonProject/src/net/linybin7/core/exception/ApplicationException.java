package net.linybin7.core.exception;

/**
 * ϵͳ�쳣
 * 
 * 
 */
public class ApplicationException extends Exception {
	public ApplicationException() {

	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

}
