package net.linybin7.core.exception;

/**
 * �ظ������쳣
 * 
 * 
 */
public class DuplicateKeyException extends ApplicationException {

	public DuplicateKeyException(String message) {
		super(message);
	}

	public DuplicateKeyException(Throwable cause) {
		super(cause);
	}

	public DuplicateKeyException(String message, Throwable cause) {
		super(message, cause);
	}
}
