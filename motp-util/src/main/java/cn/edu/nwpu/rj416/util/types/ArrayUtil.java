package cn.edu.nwpu.rj416.util.types;

import cn.edu.nwpu.rj416.util.basic.Assert;

import java.lang.reflect.Array;

/**
 * @author Harry
 * @since Harame3.0
 *
 */
public abstract class ArrayUtil { //数组工具（抽象类，全部静态方法，类名调用）

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(byte[] array) { //字节数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(byte[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(short[] array) { //短整型数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(short[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(int[] array) { //整形数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(int[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(long[] array) { //长整型数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(long[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(float[] array) { //浮点型数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(float[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(double[] array) { //双精度浮点型数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(double[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(boolean[] array) { //布尔型数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(boolean[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(char[] array) { //字符型数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(char[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Checks if the array is null or length is 0
	 * 
	 * @param array the array to check
	 * @return true if the array is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static <T> boolean isEmpty(T[] array) { //泛型数组判空
		return array == null || array.length == 0;
	}

	/**
	 * Checks if the array is not null and length is more than 0
	 * 
	 * @param array the array to check
	 * @return true if the array is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static <T> boolean isNotEmpty(T[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * Creates a new array with the specified component type and length.
	 * 
	 * @param componentType the {@code class} object representing the component type of the new array
	 * @param length the length of the new array
	 * @return the new array
	 * @throws IllegalArgumentException if the {@code componentType} is null or the {@code length} is negative
	 * @since 3.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<T> componentType, int length) {  //创建一个泛型数组
		Assert.notNull(componentType, "Argument componentType cannot be null");
		Assert.notNegativeNumber(length, "Argument length cannot be negative");
		return (T[]) Array.newInstance(componentType, length);
	}
}
