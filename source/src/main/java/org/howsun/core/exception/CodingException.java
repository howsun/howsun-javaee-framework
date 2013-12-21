/**DecoderException.java*/
package org.howsun.core.exception;

/**
 * CodingException：
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-28
 * @version v0.1
 */
public class CodingException extends BaseRuntimeException {

	private static final long serialVersionUID = -2388096076971167755L;

	public CodingException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodingException(String message) {
		super(message);
	}

	public CodingException() {
		super("编码异常->");
	}
}
