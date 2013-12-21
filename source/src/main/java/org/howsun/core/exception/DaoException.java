/**DecoderException.java*/
package org.howsun.core.exception;

/**
 * CodingException：
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-28
 * @version v0.1
 */
public class DaoException extends BaseRuntimeException {

	private static final long serialVersionUID = -2388096076971167755L;

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException() {
		super("数据库访问异常->");
	}
}
