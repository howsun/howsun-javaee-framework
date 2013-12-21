package org.howsun.core.exception;


/**
 * 资源未找到异常
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-22
 * @version v0.1
 */
public class ResourceNotFoundException extends BaseRuntimeException {

	private static final long serialVersionUID = 4675314005552418662L;

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(Throwable cause) {
		super("Resource not found ->", cause);
	}
}
