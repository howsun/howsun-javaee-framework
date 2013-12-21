package org.howsun.log;

import org.slf4j.Marker;

/**
 * Log日志接口
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-29
 * @version v0.1
 */
public interface Log {

	public abstract String getLoggerName();

	/**
	 * Is the logger instance enabled for the TRACE level?
	 * @return True if this Logger is enabled for the TRACE level,
	 * false otherwise.
	 * 
	 * @since 1.4
	 */
	public abstract boolean isTraceEnabled();

	/**
	 * Log a message at the TRACE level.
	 *
	 * @param msg the message string to be logged
	 * @since 1.4
	 */
	public abstract void trace(String msg);

	/**
	 * Log a message at the TRACE level according to the specified format
	 * and argument.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the TRACE level. </p>
	 *
	 * @param format the format string 
	 * @param arg  the argument
	 * 
	 * @since 1.4
	 */
	public abstract void trace(String format, Object arg);

	/**
	 * Log a message at the TRACE level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the TRACE level. </p>
	 *
	 * @param format the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 * 
	 * @since 1.4
	 */
	public abstract void trace(String format, Object arg1, Object arg2);

	/**
	 * Log a message at the TRACE level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the TRACE level. </p>
	 *
	 * @param format the format string
	 * @param argArray an array of arguments
	 * 
	 * @since 1.4
	 */
	public abstract void trace(String format, Object[] argArray);

	/**
	 * Log an exception (throwable) at the TRACE level with an
	 * accompanying message. 
	 * 
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log
	 * 
	 * @since 1.4
	 */
	public abstract void trace(String msg, Throwable t);

	/**
	 * Similar to {@link #isTraceEnabled()} method except that the
	 * marker data is also taken into account.
	 * 
	 * @param marker The marker data to take into consideration
	 * 
	 * @since 1.4
	 */
	public abstract boolean isTraceEnabled(Marker marker);

	/**
	 * Log a message with the specific Marker at the TRACE level.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param msg the message string to be logged
	 * 
	 * @since 1.4
	 */
	public abstract void trace(Marker marker, String msg);

	/**
	 * This method is similar to {@link #trace(String, Object)} method except that the 
	 * marker data is also taken into consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg the argument
	 * 
	 * @since 1.4
	 */
	public abstract void trace(Marker marker, String format, Object arg);

	/**
	 * This method is similar to {@link #trace(String, Object, Object)}
	 * method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 * 
	 * @since 1.4
	 */
	public abstract void trace(Marker marker, String format, Object arg1,
			Object arg2);

	/**
	 * This method is similar to {@link #trace(String, Object[])}
	 * method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param argArray an array of arguments
	 * 
	 * @since 1.4
	 */
	public abstract void trace(Marker marker, String format, Object[] argArray);

	/**
	 * This method is similar to {@link #trace(String, Throwable)} method except that the
	 * marker data is also taken into consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log
	 * 
	 * @since 1.4
	 */
	public abstract void trace(Marker marker, String msg, Throwable t);

	/**
	 * Is the logger instance enabled for the DEBUG level?
	 * @return True if this Logger is enabled for the DEBUG level,
	 * false otherwise.
	 * 
	 */
	public abstract boolean isDebugEnabled();

	/**
	 * Log a message at the DEBUG level.
	 *
	 * @param msg the message string to be logged
	 */
	public abstract void debug(String msg);

	/**
	 * Log a message at the DEBUG level according to the specified format
	 * and argument.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the DEBUG level. </p>
	 *
	 * @param format the format string 
	 * @param arg  the argument
	 */
	public abstract void debug(String format, Object arg);

	/**
	 * Log a message at the DEBUG level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the DEBUG level. </p>
	 *
	 * @param format the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 */
	public abstract void debug(String format, Object arg1, Object arg2);

	/**
	 * Log a message at the DEBUG level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the DEBUG level. </p>
	 *
	 * @param format the format string
	 * @param argArray an array of arguments
	 */
	public abstract void debug(String format, Object[] argArray);

	/**
	 * Log an exception (throwable) at the DEBUG level with an
	 * accompanying message. 
	 * 
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log
	 */
	public abstract void debug(String msg, Throwable t);

	/**
	 * Similar to {@link #isDebugEnabled()} method except that the
	 * marker data is also taken into account.
	 * 
	 * @param marker The marker data to take into consideration
	 */
	public abstract boolean isDebugEnabled(Marker marker);

	/**
	 * Log a message with the specific Marker at the DEBUG level.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param msg the message string to be logged
	 */
	public abstract void debug(Marker marker, String msg);

	/**
	 * This method is similar to {@link #debug(String, Object)} method except that the 
	 * marker data is also taken into consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg the argument
	 */
	public abstract void debug(Marker marker, String format, Object arg);

	/**
	 * This method is similar to {@link #debug(String, Object, Object)}
	 * method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 */
	public abstract void debug(Marker marker, String format, Object arg1,
			Object arg2);

	/**
	 * This method is similar to {@link #debug(String, Object[])}
	 * method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param argArray an array of arguments
	 */
	public abstract void debug(Marker marker, String format, Object[] argArray);

	/**
	 * This method is similar to {@link #debug(String, Throwable)} method except that the
	 * marker data is also taken into consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log
	 */
	public abstract void debug(Marker marker, String msg, Throwable t);

	/**
	 * Is the logger instance enabled for the INFO level?
	 * @return True if this Logger is enabled for the INFO level,
	 * false otherwise.
	 */
	public abstract boolean isInfoEnabled();

	/**
	 * Log a message at the INFO level.
	 *
	 * @param msg the message string to be logged
	 */
	public abstract void info(String msg);

	/**
	 * Log a message at the INFO level according to the specified format
	 * and argument.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the INFO level. </p>
	 *
	 * @param format the format string 
	 * @param arg  the argument
	 */
	public abstract void info(String format, Object arg);

	/**
	 * Log a message at the INFO level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the INFO level. </p>
	 *
	 * @param format the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 */
	public abstract void info(String format, Object arg1, Object arg2);

	/**
	 * Log a message at the INFO level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the INFO level. </p>
	 *
	 * @param format the format string
	 * @param argArray an array of arguments
	 */
	public abstract void info(String format, Object[] argArray);

	/**
	 * Log an exception (throwable) at the INFO level with an
	 * accompanying message. 
	 * 
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log 
	 */
	public abstract void info(String msg, Throwable t);

	/**
	 * Similar to {@link #isInfoEnabled()} method except that the marker
	 * data is also taken into consideration.
	 *
	 * @param marker The marker data to take into consideration
	 */
	public abstract boolean isInfoEnabled(Marker marker);

	/**
	 * Log a message with the specific Marker at the INFO level.
	 * 
	 * @param marker The marker specific to this log statement
	 * @param msg the message string to be logged
	 */
	public abstract void info(Marker marker, String msg);

	/**
	 * This method is similar to {@link #info(String, Object)} method except that the 
	 * marker data is also taken into consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg the argument
	 */
	public abstract void info(Marker marker, String format, Object arg);

	/**
	 * This method is similar to {@link #info(String, Object, Object)}
	 * method except that the marker data is also taken into
	 * consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 */
	public abstract void info(Marker marker, String format, Object arg1,
			Object arg2);

	/**
	 * This method is similar to {@link #info(String, Object[])}
	 * method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param argArray an array of arguments
	 */
	public abstract void info(Marker marker, String format, Object[] argArray);

	/**
	 * This method is similar to {@link #info(String, Throwable)} method
	 * except that the marker data is also taken into consideration.
	 * 
	 * @param marker the marker data for this log statement
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log
	 */
	public abstract void info(Marker marker, String msg, Throwable t);

	/**
	 * Is the logger instance enabled for the WARN level?
	 * @return True if this Logger is enabled for the WARN level,
	 * false otherwise.
	 */
	public abstract boolean isWarnEnabled();

	/**
	 * Log a message at the WARN level.
	 *
	 * @param msg the message string to be logged
	 */
	public abstract void warn(String msg);

	/**
	 * Log a message at the WARN level according to the specified format
	 * and argument.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the WARN level. </p>
	 *
	 * @param format the format string 
	 * @param arg  the argument
	 */
	public abstract void warn(String format, Object arg);

	/**
	 * Log a message at the WARN level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the WARN level. </p>
	 *
	 * @param format the format string
	 * @param argArray an array of arguments
	 */
	public abstract void warn(String format, Object[] argArray);

	/**
	 * Log a message at the WARN level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the WARN level. </p>
	 *
	 * @param format the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 */
	public abstract void warn(String format, Object arg1, Object arg2);

	/**
	 * Log an exception (throwable) at the WARN level with an
	 * accompanying message. 
	 * 
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log 
	 */
	public abstract void warn(String msg, Throwable t);

	/**
	 * Similar to {@link #isWarnEnabled()} method except that the marker
	 * data is also taken into consideration.
	 *
	 * @param marker The marker data to take into consideration
	 */
	public abstract boolean isWarnEnabled(Marker marker);

	/**
	 * Log a message with the specific Marker at the WARN level.
	 * 
	 * @param marker The marker specific to this log statement
	 * @param msg the message string to be logged
	 */
	public abstract void warn(Marker marker, String msg);

	/**
	 * This method is similar to {@link #warn(String, Object)} method except that the 
	 * marker data is also taken into consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg the argument
	 */
	public abstract void warn(Marker marker, String format, Object arg);

	/**
	 * This method is similar to {@link #warn(String, Object, Object)}
	 * method except that the marker data is also taken into
	 * consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 */
	public abstract void warn(Marker marker, String format, Object arg1,
			Object arg2);

	/**
	 * This method is similar to {@link #warn(String, Object[])}
	 * method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param argArray an array of arguments
	 */
	public abstract void warn(Marker marker, String format, Object[] argArray);

	/**
	 * This method is similar to {@link #warn(String, Throwable)} method
	 * except that the marker data is also taken into consideration.
	 * 
	 * @param marker the marker data for this log statement
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log
	 */
	public abstract void warn(Marker marker, String msg, Throwable t);

	/**
	 * Is the logger instance enabled for the ERROR level?
	 * @return True if this Logger is enabled for the ERROR level,
	 * false otherwise.
	 */
	public abstract boolean isErrorEnabled();

	/**
	 * Log a message at the ERROR level.
	 *
	 * @param msg the message string to be logged
	 */
	public abstract void error(String msg);

	/**
	 * Log a message at the ERROR level according to the specified format
	 * and argument.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the ERROR level. </p>
	 *
	 * @param format the format string 
	 * @param arg  the argument
	 */
	public abstract void error(String format, Object arg);

	/**
	 * Log a message at the ERROR level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the ERROR level. </p>
	 *
	 * @param format the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 */
	public abstract void error(String format, Object arg1, Object arg2);

	/**
	 * Log a message at the ERROR level according to the specified format
	 * and arguments.
	 * 
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the ERROR level. </p>
	 *
	 * @param format the format string
	 * @param argArray an array of arguments
	 */
	public abstract void error(String format, Object[] argArray);

	/**
	 * Log an exception (throwable) at the ERROR level with an
	 * accompanying message. 
	 * 
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log
	 */
	public abstract void error(String msg, Throwable t);

	/**
	 * Similar to {@link #isErrorEnabled()} method except that the
	 * marker data is also taken into consideration.
	 *
	 * @param marker The marker data to take into consideration
	 */
	public abstract boolean isErrorEnabled(Marker marker);

	/**
	 * Log a message with the specific Marker at the ERROR level.
	 * 
	 * @param marker The marker specific to this log statement
	 * @param msg the message string to be logged
	 */
	public abstract void error(Marker marker, String msg);

	/**
	 * This method is similar to {@link #error(String, Object)} method except that the 
	 * marker data is also taken into consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg the argument
	 */
	public abstract void error(Marker marker, String format, Object arg);

	/**
	 * This method is similar to {@link #error(String, Object, Object)}
	 * method except that the marker data is also taken into
	 * consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param arg1  the first argument
	 * @param arg2  the second argument
	 */
	public abstract void error(Marker marker, String format, Object arg1,
			Object arg2);

	/**
	 * This method is similar to {@link #error(String, Object[])}
	 * method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format  the format string
	 * @param argArray an array of arguments
	 */
	public abstract void error(Marker marker, String format, Object[] argArray);

	/**
	 * This method is similar to {@link #error(String, Throwable)}
	 * method except that the marker data is also taken into
	 * consideration.
	 * 
	 * @param marker the marker data specific to this log statement
	 * @param msg the message accompanying the exception
	 * @param t the exception (throwable) to log
	 */
	public abstract void error(Marker marker, String msg, Throwable t);

}