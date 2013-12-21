package org.howsun.core.exception;

/**
 * 异常基类，所有都为非强制检查异常
 * 
 * @author 张纪豪
 * @Date 2010-10-13
 * @version v1.0
 */
public abstract class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -7307961667791435686L;
	
	/**
	 * 避免OSGi环境中调用getMessage()方法出现死锁
	 */
	static{
		ExceptionUtils.class.getName();
	}

	public BaseRuntimeException(String message) {
		super(message);
	}
	
	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getMessage() {
		return ExceptionUtils.buildMessage(super.getMessage(), getCause());
	}

	/**
	 * 获取异常根原因
	 * @return 如果没有向上追溯到原因，则返回自己。
	 */
	public Throwable getRootCause(){
		Throwable rootCause = null;
		Throwable cause = getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause == null ? this : rootCause;
	}
	
	/**
	 * 判断是否异常包含一个给定类型的异常
	 * @param exceptionClass
	 * @return
	 */
	public boolean contains(Class<?> exceptionClass) {
		if (exceptionClass == null) {
			return false;
		}
		if (exceptionClass.isInstance(this)) {
			return true;
		}
		Throwable cause = getCause();
		if (cause == this) {
			return false;
		}
		if (cause instanceof BaseRuntimeException) {
			return ((BaseRuntimeException) cause).contains(exceptionClass);
		}
		else {
			while (cause != null) {
				if (exceptionClass.isInstance(cause)) {
					return true;
				}
				if (cause.getCause() == cause) {
					break;
				}
				cause = cause.getCause();
			}
			return false;
		}
	}
}
