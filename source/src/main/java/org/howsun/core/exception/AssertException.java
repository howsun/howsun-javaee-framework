package org.howsun.core.exception;

/**
 * AssertException：
 *
 * @author howsun(zjh@58.com)
 * @version v0.1
 */
public class AssertException extends BaseRuntimeException {

	private static final long serialVersionUID = 7191652930906552027L;

	public AssertException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssertException(String message) {
		super(message);
	}

	public AssertException() {
		super("Assert Exception ->");
	}

	
}
