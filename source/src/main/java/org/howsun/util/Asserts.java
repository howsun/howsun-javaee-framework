/**Assert.java*/
package org.howsun.util;

import java.util.Collection;
import java.util.Map;

import org.howsun.core.exception.AssertException;
import org.howsun.core.exception.AuthException;
import org.howsun.core.exception.ValidationException;


/**
 * Assert：
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-28
 * @version v0.1
 */
public abstract class Asserts {

	/**
	 * Asserts that two objects are equal. If they are not
	 * an AssertionFailedError is thrown with the given message.
	 */
	static public void assertEquals(String message, Object expected, Object actual) {
		if (expected == null && actual == null)
			return;
		if (expected != null && expected.equals(actual))
			return;
		failNotEquals(message, expected, actual);
	}
	/**
	 * Asserts that two objects are equal. If they are not
	 * an AssertionFailedError is thrown.
	 */
	static public void assertEquals(Object expected, Object actual) {
	    assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two Strings are equal.
	 */
	static public void assertEquals(String message, String expected, String actual) {
		if (expected == null && actual == null)
			return;
		if (expected != null && expected.equals(actual))
			return;
		throw new AssertException(format(message, expected, actual));
	}
	/**
	 * Asserts that two Strings are equal.
	 */
	static public void assertEquals(String expected, String actual) {
	    assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two doubles are equal concerning a delta.  If they are not
	 * an AssertionFailedError is thrown with the given message.  If the expected
	 * value is infinity then the delta value is ignored.
	 */
	static public void assertEquals(String message, double expected, double actual, double delta) {
		if (Double.compare(expected, actual) == 0)
			return;
		if (!(Math.abs(expected-actual) <= delta))
			failNotEquals(message, new Double(expected), new Double(actual));
	}
	/**
	 * Asserts that two doubles are equal concerning a delta. If the expected
	 * value is infinity then the delta value is ignored.
	 */
	static public void assertEquals(double expected, double actual, double delta) {
	    assertEquals(null, expected, actual, delta);
	}
	/**
	 * Asserts that two floats are equal concerning a positive delta. If they
	 * are not an AssertionFailedError is thrown with the given message. If the
	 * expected value is infinity then the delta value is ignored.
	 */
	static public void assertEquals(String message, float expected, float actual, float delta) {
		if (Float.compare(expected, actual) == 0)
			return;
		if (!(Math.abs(expected - actual) <= delta))
				failNotEquals(message, new Float(expected), new Float(actual));
	}
	/**
	 * Asserts that two floats are equal concerning a delta. If the expected
	 * value is infinity then the delta value is ignored.
	 */
	static public void assertEquals(float expected, float actual, float delta) {
		assertEquals(null, expected, actual, delta);
	}
	/**
	 * Asserts that two longs are equal. If they are not
	 * an AssertionFailedError is thrown with the given message.
	 */
	static public void assertEquals(String message, long expected, long actual) {
	    assertEquals(message, new Long(expected), new Long(actual));
	}
	/**
	 * Asserts that two longs are equal.
	 */
	static public void assertEquals(long expected, long actual) {
	    assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two booleans are equal. If they are not
	 * an AssertionFailedError is thrown with the given message.
	 */
	static public void assertEquals(String message, boolean expected, boolean actual) {
    		assertEquals(message, Boolean.valueOf(expected), Boolean.valueOf(actual));
  	}
	/**
	 * Asserts that two booleans are equal.
 	 */
	static public void assertEquals(boolean expected, boolean actual) {
		assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two bytes are equal. If they are not
	 * an AssertionFailedError is thrown with the given message.
	 */
  	static public void assertEquals(String message, byte expected, byte actual) {
		assertEquals(message, new Byte(expected), new Byte(actual));
	}
	/**
   	 * Asserts that two bytes are equal.
	 */
	static public void assertEquals(byte expected, byte actual) {
		assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two chars are equal. If they are not
	 * an AssertionFailedError is thrown with the given message.
	 */
  	static public void assertEquals(String message, char expected, char actual) {
    		assertEquals(message, new Character(expected), new Character(actual));
  	}
	/**
	 * Asserts that two chars are equal.
	 */
  	static public void assertEquals(char expected, char actual) {
		assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two shorts are equal. If they are not
	 * an AssertionFailedError is thrown with the given message.
	 */
	static public void assertEquals(String message, short expected, short actual) {
    		assertEquals(message, new Short(expected), new Short(actual));
	}
  	/**
	 * Asserts that two shorts are equal.
	 */
	static public void assertEquals(short expected, short actual) {
		assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two ints are equal. If they are not
	 * an AssertionFailedError is thrown with the given message.
	 */
  	static public void assertEquals(String message, int expected, int actual) {
		assertEquals(message, new Integer(expected), new Integer(actual));
  	}
  	/**
   	 * Asserts that two ints are equal.
	 */
  	static public void assertEquals(int expected, int actual) {
  		assertEquals(null, expected, actual);
	}

	/**
	 * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
	 * if the test result is <code>false</code>.
	 * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if expression is <code>false</code>
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert a boolean expression, throwing <code>AssertException</code>
	 * if the test result is <code>false</code>.
	 * <pre class="code">Assert.isTrue(i &gt; 0);</pre>
	 * @param expression a boolean expression
	 * @throws AssertException if expression is <code>false</code>
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}


	public static void isFalse(boolean expression) {
		isFalse(expression, "[Assertion failed] - this expression must be false");
	}

	public static void isFalse(boolean expression, String message) {
		if (expression) {
			throw new AssertException(message);
		}
	}
	/**
	 * Assert that an object is <code>null</code> .
	 * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws AssertException if the object is not <code>null</code>
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert that an object is <code>null</code> .
	 * <pre class="code">Assert.isNull(value);</pre>
	 * @param object the object to check
	 * @throws AssertException if the object is not <code>null</code>
	 */
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	/**
	 * Assert that an object is not <code>null</code> .
	 * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws AssertException if the object is <code>null</code>
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert that an object is not <code>null</code> .
	 * <pre class="code">Assert.notNull(clazz);</pre>
	 * @param object the object to check
	 * @throws AssertException if the object is <code>null</code>
	 */
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	/**
	 * Assert that the given String is not empty; that is,
	 * it must not be <code>null</code> and not the empty String.
	 * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
	 * @param text the String to check
	 * @param message the exception message to use if the assertion fails
	 * @see Strings#hasLength
	 */
	public static void hasLength(String text, String message) {
		if (!Strings.hasLength(text)) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert that the given String is not empty; that is,
	 * it must not be <code>null</code> and not the empty String.
	 * <pre class="code">Assert.hasLength(name);</pre>
	 * @param text the String to check
	 * @see Strings#hasLength
	 */
	public static void hasLength(String text) {
		hasLength(text,
				"[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	/**
	 * Assert that the given String has valid text content; that is, it must not
	 * be <code>null</code> and must contain at least one non-whitespace character.
	 * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
	 * @param text the String to check
	 * @param message the exception message to use if the assertion fails
	 * @see Strings#hasText
	 */
	public static void hasText(String text, String message) {
		if (!Strings.hasText(text)) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert that the given String has valid text content; that is, it must not
	 * be <code>null</code> and must contain at least one non-whitespace character.
	 * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
	 * @param text the String to check
	 * @see Strings#hasText
	 */
	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

	public static void hasErrors(boolean expression) {
		if(expression)
			throw new AssertException("有错误");
	}


	/**
	 * Assert that the given text does not contain the given substring.
	 * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @param message the exception message to use if the assertion fails
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (Strings.hasLength(textToSearch) && Strings.hasLength(substring) &&
				textToSearch.indexOf(substring) != -1) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * <pre class="code">Assert.doesNotContain(name, "rod");</pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 */
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				"[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
	}


	/**
	 * Assert that an array has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * <pre class="code">Assert.notEmpty(array, "The array must have elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws AssertException if the object array is <code>null</code> or has no elements
	 */
	public static void notEmpty(Object[] array, String message) {
		if (array == null || array.length == 0) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert that an array has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * <pre class="code">Assert.notEmpty(array);</pre>
	 * @param array the array to check
	 * @throws AssertException if the object array is <code>null</code> or has no elements
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that an array has no null elements.
	 * Note: Does not complain if the array is empty!
	 * <pre class="code">Assert.noNullElements(array, "The array must have non-null elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws AssertException if the object array contains a <code>null</code> element
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new AssertException(message);
				}
			}
		}
	}

	/**
	 * Assert that an array has no null elements.
	 * Note: Does not complain if the array is empty!
	 * <pre class="code">Assert.noNullElements(array);</pre>
	 * @param array the array to check
	 * @throws AssertException if the object array contains a <code>null</code> element
	 */
	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}

	/**
	 * Assert that a collection has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
	 * @param collection the collection to check
	 * @param message the exception message to use if the assertion fails
	 * @throws AssertException if the collection is <code>null</code> or has no elements
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (Collections.isEmpty(collection)) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert that a collection has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
	 * @param collection the collection to check
	 * @throws AssertException if the collection is <code>null</code> or has no elements
	 */
	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that a Map has entries; that is, it must not be <code>null</code>
	 * and must have at least one entry.
	 * <pre class="code">Assert.notEmpty(map, "Map must have entries");</pre>
	 * @param map the map to check
	 * @param message the exception message to use if the assertion fails
	 * @throws AssertException if the map is <code>null</code> or has no entries
	 */
	public static void notEmpty(Map<?,?> map, String message) {
		if (Collections.isEmpty(map)) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert that a Map has entries; that is, it must not be <code>null</code>
	 * and must have at least one entry.
	 * <pre class="code">Assert.notEmpty(map);</pre>
	 * @param map the map to check
	 * @throws AssertException if the map is <code>null</code> or has no entries
	 */
	public static void notEmpty(Map<?,?> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}


	/**
	 * Assert that the provided object is an instance of the provided class.
	 * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
	 * @param clazz the required class
	 * @param obj the object to check
	 * @throws AssertException if the object is not an instance of clazz
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @param message a message which will be prepended to the message produced by
	 * the function itself, and which may be used to provide context. It should
	 * normally end in a ": " or ". " so that the function generate message looks
	 * ok when prepended to it.
	 * @throws AssertException if the object is not an instance of clazz
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new AssertException(message +
					"Object of class [" + (obj != null ? obj.getClass().getName() : "null") +
					"] must be an instance of " + type);
		}
	}

	/**
	 * Assert that <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * @param superType the super type to check
	 * @param subType the sub type to check
	 * @throws AssertException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	/**
	 * Assert that <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * @param superType the super type to check against
	 * @param subType the sub type to check
	 * @param message a message which will be prepended to the message produced by
	 * the function itself, and which may be used to provide context. It should
	 * normally end in a ": " or ". " so that the function generate message looks
	 * ok when prepended to it.
	 * @throws AssertException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new AssertException(message + subType + " is not assignable to " + superType);
		}
	}

	static public void fail(String message) {
		throw new AssertException(message);
	}

	static public void fail() {
		fail(null);
	}

	static public void failNotEquals(String message, Object expected, Object actual) {
		fail(format(message, expected, actual));
	}


	/**
	 * Assert a boolean expression, throwing <code>IllegalStateException</code>
	 * if the test result is <code>false</code>. Call isTrue if you wish to
	 * throw AssertException on an assertion failure.
	 * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalStateException if expression is <code>false</code>
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new AssertException(message);
		}
	}

	/**
	 * Assert a boolean expression, throwing {@link IllegalStateException}
	 * if the test result is <code>false</code>.
	 * <p>Call {@link #isTrue(boolean)} if you wish to
	 * throw {@link AssertException} on an assertion failure.
	 * <pre class="code">Assert.state(id == null);</pre>
	 * @param expression a boolean expression
	 * @throws IllegalStateException if the supplied expression is <code>false</code>
	 */
	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - this state invariant must be true");
	}

	public static String format(String message, Object expected, Object actual) {
		String formatted= "";
		if (message != null)
			formatted= message+" ";
		return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
	}

	/**
	 * 登录认证
	 * @param expression
	 * @param message
	 */
	public static void validate(boolean expression, String message){
		if (!expression) {
			throw new ValidationException(message);
		}
	}

	/**
	 * 登录认证
	 * @param expression
	 * @param message
	 */
	public static void auth(boolean expression, String message){
		if (!expression) {
			throw new AuthException(message);
		}
	}

	public static void regex(String text, String regex, String message){
		boolean result = text.matches(regex);
		isTrue(result, message);
	}
}
