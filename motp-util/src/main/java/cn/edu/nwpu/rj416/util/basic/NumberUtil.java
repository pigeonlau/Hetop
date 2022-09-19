/**
 * 
 */
package cn.edu.nwpu.rj416.util.basic;

import cn.edu.nwpu.rj416.util.types.ArrayUtil;
import cn.edu.nwpu.rj416.util.types.StringUtil;

/**
 * @author Harry
 * @since 3.0
 *
 */
public final class NumberUtil { //数字工具

	/**
	 * Full byte flag {@value #FULL_BYTE_FLAG}
	 * 
	 * @since 3.0
	 */
	private static final int FULL_BYTE_FLAG = 0xFF;

	/**
	 * Positive sign {@value #POSITIVE_SIGN}
	 * 
	 * @since 3.0
	 */
	private static final char POSITIVE_SIGN = '+';

	/**
	 * Negative sign {@value #NEGATIVE_SIGN}
	 * 
	 * @since 3.0
	 */
	private static final char NEGATIVE_SIGN = '-';

	/**
	 * Decimal point {@value #DECIMAL_POINT}
	 * 
	 * @since 3.0
	 */
	private static final char DECIMAL_POINT = '.';

	/**
	 * Checks if the string is a number.
	 * <p>
	 * Will returns false if the string is null or empty.
	 * 
	 * @param string the string to check
	 * @return true if the string is a number, otherwise false
	 * @since 3.0
	 */
	public static boolean isNumber(String string) { //检查一个字符串是不是数字
		if (StringUtil.isNotEmpty(string)) { //字符串非空
			char[] chars = string.toCharArray(); //将字符串分解到一个字符数组
			int length = chars.length - 1; 
			int offset = 0;
			char c = chars[offset];
			if (c == NEGATIVE_SIGN || c == POSITIVE_SIGN) {
				if (length == 0) {
					return false;
				}
				offset++;
			} //字符串的首位要是正负号，从符号位的下一个位置开始判断是否数字
			boolean hasDot = false;
			for (; length > 0; offset++, length--) {
				c = chars[offset];
				if (c == DECIMAL_POINT) {
					if (hasDot || length == 1) {
						return false;
					}
					hasDot = true; //字符串中要是有小数点且小数点后面没有字符了，返回false，还有字符，继续循环
				} else if (c >= '0' && c <= '9') { //字符是数字，跳过
					continue;
				}

			}
			return true; //全部循环完毕，说明该字符串是数字，返回true
		}
		return false; //字符串为空直接返回false
	}

	/**
	 * Returns a string representation of the hexadecimal number converted by the
	 * byte array.
	 * <p>
	 * Will returns null if the byte array is null or empty.
	 * <p>
	 * Principle analysis: <br>
	 * 1 byte has 8 bits, 1 hexadecimal number uses 4 bits, so we can use 1 byte to
	 * represent 2 hexadecimal numbers.
	 * 
	 * @param bytes the byte array
	 * @return a string representation of the hexadecimal number
	 * @since 3.0
	 */
	public static String toHexString(byte[] bytes) { //将字节数组转换成十六进制字符串
		if (ArrayUtil.isNotEmpty(bytes)) { //字节数组非空
			StringBuilder sb = new StringBuilder(bytes.length * 2);//一个字节要用两个十六进制字符，所以是字节数组长度的两倍
			String hex = null;
			for (byte b : bytes) {
				hex = Integer.toHexString(b & FULL_BYTE_FLAG);//都是有符号数，和FF按位与，保证补码的一致性
				if (hex.length() < 2) {
					sb.append("0"); //字节数据类型是8位，可以表示两位十六进制数，转换后若不足两位，补0
				} //先append 0可以使0在高位
				sb.append(hex); //连接字符串
			}
			return sb.toString();
		}
		return null; //数组为空直接返回null
	}

	/**
	 * Returns a newly allocated byte array converted by the number.
	 * 
	 * @param number the number
	 * @return a newly allocated byte array
	 * @since 3.0
	 */
	public static byte[] toByteArray(int number) { //将一个32位的int数存到一个大小为4的字节数组中
		byte[] bytes = new byte[4];
		bytes[3] = (byte) (number & FULL_BYTE_FLAG); //低8位存在最后
		bytes[2] = (byte) (number >> 8 & FULL_BYTE_FLAG);
		bytes[1] = (byte) (number >> 16 & FULL_BYTE_FLAG);
		bytes[0] = (byte) (number >> 24 & FULL_BYTE_FLAG); //高8位存在第一个位置
		return bytes;
	}

	/**
	 * Returns a newly allocated byte array converted by the number.
	 * 
	 * @param number the number
	 * @return a newly allocated byte array
	 * @since 3.0
	 */
	public static byte[] toByteArray(long number) { //64位长整型存到字节数组
		byte[] bytes = new byte[8];
		bytes[7] = (byte) (number & FULL_BYTE_FLAG);
		bytes[6] = (byte) (number >> 8 & FULL_BYTE_FLAG);
		bytes[5] = (byte) (number >> 16 & FULL_BYTE_FLAG);
		bytes[4] = (byte) (number >> 24 & FULL_BYTE_FLAG);
		bytes[3] = (byte) (number >> 32 & FULL_BYTE_FLAG);
		bytes[2] = (byte) (number >> 40 & FULL_BYTE_FLAG);
		bytes[1] = (byte) (number >> 48 & FULL_BYTE_FLAG);
		bytes[0] = (byte) (number >> 56 & FULL_BYTE_FLAG); //也是先放高位，再放低位
		return bytes;
	}

	/**
	 * Returns a int converted by the byte array.
	 * 
	 * @param bytes the byte array
	 * @return a {@code int}
	 * @since 3.0
	 */
	public static int toInt(byte[] bytes) { //将字节数组存放的整型数转回int
		int number = 0;
		for (int i = 0; i < 4; i++) {
			number <<= 8;
			number |= bytes[i] & FULL_BYTE_FLAG; //按位与，相当于做了拼接操作
		}
		return number;
	}

	/**
	 * Returns a long converted by the byte array.
	 * <p>
	 * Will returns 0, if the byte array is null or empty.
	 * 
	 * @param bytes the byte array
	 * @return a {@code long}
	 * @since 3.0
	 */
	public static long toLong(byte[] bytes) { //将字节数组存放的长整型数转回long
		long number = 0;
		if (ArrayUtil.isNotEmpty(bytes)) {
			for (int i = 0; i < bytes.length; i++) {
				number <<= 8;
				number |= bytes[i] & FULL_BYTE_FLAG;
			}
		}
		return number;
	}
}
