/**ObjectUtils.java*/
package org.howsun.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

/**
 * 对象工具
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-28
 * @version v0.1
 */
public abstract class Objects {

	private static final int INITIAL_HASH = 7;
	private static final int MULTIPLIER = 31;

	private static final String EMPTY_STRING = "";
	private static final String NULL_STRING = "null";
	private static final String ARRAY_START = "{";
	private static final String ARRAY_END = "}";
	private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
	private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

	/**
	 * 检查对象是否为数组类型，包括基本类型数组和对象类型数组
	 * @param obj the object to check
	 */
	public static boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}

	/**
	 * 确定是否在给定的数组是空的：即null或长度为零。
	 * @param array the array to check
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * 检查是否在给定的数组包含给定的元素。
	 * @param array the array to check (may be <code>null</code>,
	 * in which case the return value will always be <code>false</code>)
	 * @param element the element to check for
	 * @return whether the element has been found in the given array
	 */
	public static boolean containsElement(Object[] array, Object element) {
		if (array == null) {
			return false;
		}
		for (Object arrayEle : array) {
			if (nullSafeEquals(arrayEle, element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Append the given Object to the given array, returning a new array
	 * consisting of the input array contents plus the given Object.
	 * @param array the array to append to (can be <code>null</code>)
	 * @param obj the Object to append
	 * @return the new array (of the same component type; never <code>null</code>)
	 */
	@SuppressWarnings("unchecked")
	public static Object[] addObjectToArray(Object[] array, Object obj) {
		Class compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		}
		else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[newArr.length - 1] = obj;
		return newArr;
	}

	/**
	 * Convert the given array (which may be a primitive array) to an
	 * object array (if necessary of primitive wrapper objects).
	 * <p>A <code>null</code> source value will be converted to an
	 * empty Object array.
	 * @param source the (potentially primitive) array
	 * @return the corresponding object array (never <code>null</code>)
	 * @throws IllegalArgumentException if the parameter is not an array
	 */
	@SuppressWarnings("unchecked")
	public static Object[] toObjectArray(Object source) {
		if (source instanceof Object[]) {
			return (Object[]) source;
		}
		if (source == null) {
			return new Object[0];
		}
		if (!source.getClass().isArray()) {
			throw new IllegalArgumentException("Source is not an array: " + source);
		}
		int length = Array.getLength(source);
		if (length == 0) {
			return new Object[0];
		}
		Class wrapperType = Array.get(source, 0).getClass();
		Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return newArray;
	}


	//---------------------------------------------------------------------
	// Convenience methods for content-based equality/hash-code handling
	//---------------------------------------------------------------------

	/**
	 * Determine if the given objects are equal, returning <code>true</code>
	 * if both are <code>null</code> or <code>false</code> if only one is
	 * <code>null</code>.
	 * <p>Compares arrays with <code>Arrays.equals</code>, performing an equality
	 * check based on the array elements rather than the array reference.
	 * @param o1 first Object to compare
	 * @param o2 second Object to compare
	 * @return whether the given objects are equal
	 * @see java.util.Arrays#equals
	 */
	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		return false;
	}

	/**
	 * 返回对象的Hash码
	 * Return as hash code for the given object; typically the value of
	 * <code>{@link Object#hashCode()}</code>. If the object is an array,
	 * this method will delegate to any of the <code>nullSafeHashCode</code>
	 * methods for arrays in this class. If the object is <code>null</code>,
	 * this method returns 0.
	 * @see #nullSafeHashCode(Object[])
	 * @see #nullSafeHashCode(boolean[])
	 * @see #nullSafeHashCode(byte[])
	 * @see #nullSafeHashCode(char[])
	 * @see #nullSafeHashCode(double[])
	 * @see #nullSafeHashCode(float[])
	 * @see #nullSafeHashCode(int[])
	 * @see #nullSafeHashCode(long[])
	 * @see #nullSafeHashCode(short[])
	 */
	public static int nullSafeHashCode(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj.getClass().isArray()) {
			if (obj instanceof Object[]) {
				return nullSafeHashCode((Object[]) obj);
			}
			if (obj instanceof boolean[]) {
				return nullSafeHashCode((boolean[]) obj);
			}
			if (obj instanceof byte[]) {
				return nullSafeHashCode((byte[]) obj);
			}
			if (obj instanceof char[]) {
				return nullSafeHashCode((char[]) obj);
			}
			if (obj instanceof double[]) {
				return nullSafeHashCode((double[]) obj);
			}
			if (obj instanceof float[]) {
				return nullSafeHashCode((float[]) obj);
			}
			if (obj instanceof int[]) {
				return nullSafeHashCode((int[]) obj);
			}
			if (obj instanceof long[]) {
				return nullSafeHashCode((long[]) obj);
			}
			if (obj instanceof short[]) {
				return nullSafeHashCode((short[]) obj);
			}
		}
		return obj.hashCode();
	}

	/**
	 * 返回数组内容的Hask码，当数组为空时返回0
	 * @param array
	 * @return
	 */
	public static int nullSafeHashCode(Object[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + nullSafeHashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array.
	 * If <code>array</code> is <code>null</code>, this method returns 0.
	 */
	public static int nullSafeHashCode(boolean[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array.
	 * If <code>array</code> is <code>null</code>, this method returns 0.
	 */
	public static int nullSafeHashCode(byte[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array.
	 * If <code>array</code> is <code>null</code>, this method returns 0.
	 */
	public static int nullSafeHashCode(char[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array.
	 * If <code>array</code> is <code>null</code>, this method returns 0.
	 */
	public static int nullSafeHashCode(double[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array.
	 * If <code>array</code> is <code>null</code>, this method returns 0.
	 */
	public static int nullSafeHashCode(float[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array.
	 * If <code>array</code> is <code>null</code>, this method returns 0.
	 */
	public static int nullSafeHashCode(int[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array.
	 * If <code>array</code> is <code>null</code>, this method returns 0.
	 */
	public static int nullSafeHashCode(long[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	/**
	 * Return a hash code based on the contents of the specified array.
	 * If <code>array</code> is <code>null</code>, this method returns 0.
	 */
	public static int nullSafeHashCode(short[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	/**
	 * Return the same value as <code>{@link Boolean#hashCode()}</code>.
	 * @see Boolean#hashCode()
	 */
	public static int hashCode(boolean bool) {
		return bool ? 1231 : 1237;
	}

	/**
	 * Return the same value as <code>{@link Double#hashCode()}</code>.
	 * @see Double#hashCode()
	 */
	public static int hashCode(double dbl) {
		long bits = Double.doubleToLongBits(dbl);
		return hashCode(bits);
	}

	/**
	 * Return the same value as <code>{@link Float#hashCode()}</code>.
	 * @see Float#hashCode()
	 */
	public static int hashCode(float flt) {
		return Float.floatToIntBits(flt);
	}

	/**
	 * Return the same value as <code>{@link Long#hashCode()}</code>.
	 * @see Long#hashCode()
	 */
	public static int hashCode(long lng) {
		return (int) (lng ^ (lng >>> 32));
	}


	//---------------------------------------------------------------------
	// Convenience methods for toString output
	//---------------------------------------------------------------------

	/**
	 * Return a String representation of an object's overall identity.
	 * @param obj the object (may be <code>null</code>)
	 * @return the object's identity as String representation,
	 * or an empty String if the object was <code>null</code>
	 */
	public static String identityToString(Object obj) {
		if (obj == null) {
			return EMPTY_STRING;
		}
		return obj.getClass().getName() + "@" + getIdentityHexString(obj);
	}

	/**
	 * Return a hex String form of an object's identity hash code.
	 * @param obj the object
	 * @return the object's identity code in hex notation
	 */
	public static String getIdentityHexString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}

	/**
	 * Return a content-based String representation if <code>obj</code> is
	 * not <code>null</code>; otherwise returns an empty String.
	 * <p>Differs from {@link #nullSafeToString(Object)} in that it returns
	 * an empty String rather than "null" for a <code>null</code> value.
	 * @param obj the object to build a display String for
	 * @return a display String representation of <code>obj</code>
	 * @see #nullSafeToString(Object)
	 */
	public static String getDisplayString(Object obj) {
		if (obj == null) {
			return EMPTY_STRING;
		}
		return nullSafeToString(obj);
	}

	/**
	 * Determine the class name for the given object.
	 * <p>Returns <code>"null"</code> if <code>obj</code> is <code>null</code>.
	 * @param obj the object to introspect (may be <code>null</code>)
	 * @return the corresponding class name
	 */
	public static String nullSafeClassName(Object obj) {
		return (obj != null ? obj.getClass().getName() : NULL_STRING);
	}

	/**
	 * Return a String representation of the specified Object.
	 * <p>Builds a String representation of the contents in case of an array.
	 * Returns <code>"null"</code> if <code>obj</code> is <code>null</code>.
	 * @param obj the object to build a String representation for
	 * @return a String representation of <code>obj</code>
	 */
	public static String nullSafeToString(Object obj) {
		if (obj == null) {
			return NULL_STRING;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		if (obj instanceof Object[]) {
			return nullSafeToString((Object[]) obj);
		}
		if (obj instanceof boolean[]) {
			return nullSafeToString((boolean[]) obj);
		}
		if (obj instanceof byte[]) {
			return nullSafeToString((byte[]) obj);
		}
		if (obj instanceof char[]) {
			return nullSafeToString((char[]) obj);
		}
		if (obj instanceof double[]) {
			return nullSafeToString((double[]) obj);
		}
		if (obj instanceof float[]) {
			return nullSafeToString((float[]) obj);
		}
		if (obj instanceof int[]) {
			return nullSafeToString((int[]) obj);
		}
		if (obj instanceof long[]) {
			return nullSafeToString((long[]) obj);
		}
		if (obj instanceof short[]) {
			return nullSafeToString((short[]) obj);
		}
		String str = obj.toString();
		return (str != null ? str : EMPTY_STRING);
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(Object[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(String.valueOf(array[i]));
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(boolean[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}

			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(byte[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(char[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append("'").append(array[i]).append("'");
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(double[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}

			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(float[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}

			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(int[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(long[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	/**
	 * Return a String representation of the contents of the specified array.
	 * <p>The String representation consists of a list of the array's elements,
	 * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are separated
	 * by the characters <code>", "</code> (a comma followed by a space). Returns
	 * <code>"null"</code> if <code>array</code> is <code>null</code>.
	 * @param array the array to build a String representation for
	 * @return a String representation of <code>array</code>
	 */
	public static String nullSafeToString(short[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(ARRAY_START);
			}
			else {
				sb.append(ARRAY_ELEMENT_SEPARATOR);
			}
			sb.append(array[i]);
		}
		sb.append(ARRAY_END);
		return sb.toString();
	}

	public static boolean isTheClass(Object obj, Class<?> cls){
		if(obj.getClass().equals(cls) || cls.equals(Object.class)){
			return true;
		}

		Class<?> superClass = null;
		while(!(superClass = obj.getClass().getSuperclass()).equals(Object.class)){
			if(superClass.equals(cls)){
				return true;
			}
		}
		for(Class<?> interfaceClass : obj.getClass().getInterfaces()){
			if(interfaceClass.equals(cls)){
				return true;
			}
		}
		return false;
	}

	
	
	
	
	
	//-----------------------------------------------对象系列化----------------------------------------------------
	
	public final static String CHARSET_ISO88591 = "ISO-8859-1";
	
	/**
	  * @Title: serialize
	  * @Description: java对象序列化 <br>
	  * eg:<br>
	  *   Map<String,String> map = new HashMap<String,String>();<br>
	  *   map.put("test","序列化");<br>
	  *   String serializedMapStr=SerializeUtil.serialize(map);
	  * @param original 要进行序列化的java对象
	  * @return String 序列化的后的值
	  * @throws IOException
	  * 
	  * 
	  */
	public static String serialize(Object original) throws IOException {
		if(null==original) return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		ObjectOutputStream oos = new ObjectOutputStream(baos);  
		oos.writeObject(original);  
		byte[] str = baos.toByteArray();
		oos.close();
		baos.close();
		return new String(str,CHARSET_ISO88591);
	}
	
	/**
	  * @Title: deserialize
	  * @Description: 序列化的String对象的反序列化<br>
	  * 需要自己进行强制转换得到最终类型<br> 
	  * eg:<br>
	  *   Map newmap = (Map)SerializeUtil.deserialize(serializedMapStr); 
	  * @param serializedstr 经序列化处理过的信息
	  * @return Object 反序列化后生成的Object。<br>
	  * @throws IOException
	  * @throws UnsupportedEncodingException
	  * @throws ClassNotFoundException
	  * 
	  */
	public static Object deserialize(String serializedstr) throws UnsupportedEncodingException, IOException, ClassNotFoundException{
		if(null==serializedstr)return null;
		BufferedInputStream bis=new BufferedInputStream(new ByteArrayInputStream(serializedstr.getBytes(CHARSET_ISO88591)));
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object obj = ois.readObject();
		ois.close();
		bis.close();
		return obj;
	}
	
	public static byte[] objectToByteArray(Object original) throws IOException {
		if (null == original)
			return null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try (ObjectOutputStream oout = new ObjectOutputStream(bout);) {
        	oout.writeObject(original);
        }
        return bout.toByteArray();
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(nullSafeClassName(1l));
	}
}
