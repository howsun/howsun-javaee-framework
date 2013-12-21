/**
 *
 */
package org.howsun.core.exception;

/**
 * @author howsun
 *
 */
public class AuthException extends BaseRuntimeException {

	private static final long serialVersionUID = -8387094853056141651L;

	public AuthException() {
		super("认证错误");
	}

	public AuthException(String message) {
		super(message);
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}

}
