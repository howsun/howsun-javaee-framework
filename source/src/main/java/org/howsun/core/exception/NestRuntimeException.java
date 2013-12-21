package org.howsun.core.exception;


/**
 * NestRuntimeException：
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-26
 * @version v0.1
 */
public class NestRuntimeException extends BaseRuntimeException {

	private static final long serialVersionUID = 7164918453204535340L;

	public NestRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NestRuntimeException(String message) {
		super(message);
	}

}
