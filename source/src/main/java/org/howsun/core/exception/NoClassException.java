/**NoClassException.java*/
package org.howsun.core.exception;


/**
 * 未找到类异常
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-22
 * @version v0.1
 */
public class NoClassException extends BaseRuntimeException {

	private static final long serialVersionUID = 5511167223080721946L;

	public NoClassException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NoClassException(Throwable cause) {
		super("Class not found ->", cause);
	}

	public NoClassException(String message) {
		super(message);
	}

}
