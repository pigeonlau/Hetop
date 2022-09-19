package cn.edu.nwpu.rj416.util.basic;
/**
 * 
 */

import cn.edu.nwpu.rj416.util.types.CollectionUtil;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.util.Collection;

/**
 * @author Harry
 * @since 3.0
 *
 */
public final class Assert {

	/**
	 * Asserts that an object is null
	 * 
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object is not null
	 * @since 3.0
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);//传入对象非空则抛出非法参数异常
		}
	}

	/**
	 * Asserts that an object is not null
	 * 
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object is null
	 * @since 3.0
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);//传入对象为空则抛出非法参数异常
		}
	}

	/**
	 * Asserts that a {@code string} is not null and length is more than 0.
	 * 
	 * @param string the string to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the string is null or length is 0
	 * @since 3.0
	 */
	public static void notEmpty(String string, String message) {
		if (StringUtil.isEmpty(string)) {
			throw new IllegalArgumentException(message);//传入字符串为空则抛出非法参数异常
		}
	}

	/**
	 * Asserts that a {@code Collection} is not null and size is more than 0.
	 * 
	 * @param collection the collection to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the collection is null or size is 0
	 * @since 3.0
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtil.isEmpty(collection)) {
			throw new IllegalArgumentException(message);//容器对象为空，则抛出非法参数异常
		}
	}

	/**
	 * Asserts that a {@code number} is more than 0.
	 * 
	 * @param number the number to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the number is less than or equal to 0
	 * @since 3.0
	 */
	public static void isPositiveNumber(int number, String message) {
		if (number <= 0) {
			throw new IllegalArgumentException(message);//传入int数值不超过0，则抛出非法参数异常
		}
	}

	/**
	 * Asserts that a {@code number} is not less than 0.
	 * 
	 * @param number the number to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the number is less than 0
	 * @since 3.0
	 */
	public static void notNegativeNumber(int number, String message) {
		if (number < 0) {
			throw new IllegalArgumentException(message);//传入int数值小于0，则抛出非法参数异常
		}
	}

	/**
	 * Asserts that a {@code number} is more than 0.
	 * 
	 * @param number the number to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the number is less than or equal to 0
	 * @since 3.0
	 */
	public static void isPositiveNumber(long number, String message) {
		if (number <= 0) {
			throw new IllegalArgumentException(message);//长整形参数，方法重载
		}
	}

	/**
	 * Asserts that a {@code number} is not less than 0.
	 * 
	 * @param number the number to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the number is less than 0
	 * @since 3.0
	 */
	public static void notNegativeNumber(long number, String message) {
		if (number < 0) {
			throw new IllegalArgumentException(message);
		}
	}
}
