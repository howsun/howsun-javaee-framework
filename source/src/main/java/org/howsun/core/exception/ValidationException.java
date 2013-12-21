/**
 *
 */
package org.howsun.core.exception;

/**
 * @author howsun
 *
 */
public class ValidationException extends BaseRuntimeException {

	private static final long serialVersionUID = -8387094853056141650L;

	public ValidationException() {
		super("数据验证错误");
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
