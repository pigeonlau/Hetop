package cn.edu.nwpu.rj416.util.types;

import cn.edu.nwpu.rj416.util.basic.Assert;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Harry
 * @since Harame 3.0
 *
 */
//字符串工具
public abstract class StringUtil {

	/**
	 * UTF-8 charset name
	 * 
	 * @since 3.0
	 */
	private static final String UTF8_CHARSET_NAME = "UTF-8";

	/**
	 * UTF-8 charset
	 * 
	 * @since 3.0
	 */
	private static final Charset UTF8_CHARSET = Charset.forName(UTF8_CHARSET_NAME);

	/**
	 * The empty string ""
	 * 
	 * @since 3.0
	 */
	public static final String EMPTY = ""; //空字符串

	/**
	 * The left angle bracket character
	 * 
	 * @since 3.0
	 */
	public static final char LEFT_ANGLE_BRACKET = '<'; //左尖括号

	/**
	 * The right angle bracket character
	 * 
	 * @since 3.0
	 */
	public static final char RIGHT_ANGLE_BRACKET = '>'; //右尖括号

	/**
	 * The forward slash character
	 * 
	 * @since 3.0
	 */
	public static final char FORWARD_SLASH = '/'; //斜杠

	/**
	 * The back slash character
	 * 
	 * @since 3.0
	 */
	public static final char BACK_SLASH = '\\'; //双反斜

	/**
	 * Represents a failed index search
	 * 
	 * @since 3.0
	 */
	private static final int INDEX_NOT_FOUND = -1; //索引未找到标志

	/**
	 * Number dictionary
	 * 
	 * @since 3.0
	 */
	private static final char[] NUMBER_DICTIONARY = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};

	/**
	 * String dictionary
	 * 
	 * @since 3.0
	 */
	private static final char[] STRING_DICTIONARY = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	private static final Random RANDOM = new Random();

	/**
	 * Email format pattern.
	 * 
	 * @since 3.0
	 */
	//电子邮件地址正则表达式
	private static final Pattern EMAIL_FORMAT_PATTERN = Pattern.compile("^[\\w\\.-]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$");

	/**
	 * Cellphone number format pattern.
	 * 
	 * @since 3.0
	 */
	//手机号码正则表达式
	private static final Pattern CELLPHONE_NUMBER_FORMAT_PATTERN = Pattern.compile("^1[0-9]{10}$");

	/**
	 * Creates a new {@code String} by decoding the specified bytes using the {@value#UTF8_CHARSET_NAME} charset.
	 * 
	 * @param bytes the bytes to be decoded into characters
	 * @return a new {@code String}
	 * @since 3.0
	 */
	//通过UTF-8解码一个字节码数组，创建一个字符串对象
	public static String newString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		
		int len = 0;
		for (len = 0; len < bytes.length; len++) { //UTF-8编码中单字节和ASCII码一一对应
			if (bytes[len] == 0) {                 //因此一个字节为0，即8位全零表示null
				break;                             //表示一个字符串的结尾（类似于c语言中字符串结尾是'\0'）
			}
		}
		return new String(bytes, 0, len, UTF8_CHARSET);
	}

	/**
	 * Creates a new {@code String} by decoding the specified subarray of bytes using the
	 * {@value#UTF8_CHARSET_NAME} charset.
	 * 
	 * @param bytes the bytes to be decoded into characters
	 * @param offset the index of the first byte to decode
	 * @param length the number of bytes to decode
	 * @return a new {@code String}
	 * @since 3.0
	 */
	//通过UTF-8解码一个字节码数组，创建一个字符串对象（可指定偏移量）
	public static String newString(byte[] bytes, int offset, int length) {
		return new String(bytes, offset, length, UTF8_CHARSET);
	}

	/**
	 * Checks if the string is null or length is 0
	 * 
	 * @param string the string to check
	 * @return true if the string is null or length is 0, otherwise false
	 * @since 3.0
	 */
	//字符串判空
	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

	/**
	 * Checks if the string is not null and length is more than 0.
	 * 
	 * @param string the string to check
	 * @return true if the string is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	//字符串判不空
	public static boolean isNotEmpty(String string) {
		return string != null && !string.isEmpty();
	}

	/**
	 * Checks if any string has null or length is 0.
	 * 
	 * @param string the string to check
	 * @param strings the strings to check
	 * @return true if any string has null or length is 0, otherwise false
	 * @since 3.0
	 */
	//判断一个字符串或一个字符串组中是否有空串
	public static boolean hasEmpty(String string, String... strings) {
		if (isEmpty(string)) {
			return true;
		}
		for (String s : strings) {
			if (isEmpty(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a string has not null or length is more than 0.
	 * 
	 * @param string the string to check
	 * @param strings the strings to check
	 * @return {@code true} if a string has not null or length is more than 0, otherwise {@code false}
	 * @since 3.0
	 */
	//判断一个字符串或一个字符串组中所有字符串是否非空
	public static boolean hasNotEmpty(String string, String... strings) {
		if (isNotEmpty(string)) {
			return true;
		}
		for (String s : strings) {
			if (isNotEmpty(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Trims all leading and trailing whitespace of a string
	 * 
	 * @param string the string to trim
	 * @return the trimmed string, null if the string is null
	 * @since 3.0
	 */
	//去掉字符串首尾空格
	public static String trim(String string) {
		return string == null ? null : string.trim();
	}

	/**
	 * Checks if a string is equal to the other string.
	 * <p>
	 * Will returns true if two strings both are null.
	 * 
	 * @param string1 the string
	 * @param string2 the other string
	 * @return true if two strings are equal, otherwise false
	 * @since 3.0
	 */
	//字符串判等
	public static boolean equal(String string1, String string2) {
		return string1 == null ? string2 == null : string1.equals(string2);
	}

	/**
	 * Checks if a string isn't equal to the other string.
	 * 
	 * @param string1 the string
	 * @param string2 the string
	 * @return true if two strings aren't equal, otherwise false
	 * @since 3.0
	 */
	//字符串判不等
	public static boolean notEqual(String string1, String string2) {
		return !equal(string1, string2);
	}

	/**
	 * Encodes the string into a sequence of bytes using the {@link #UTF8_CHARSET UTF-8 charset}, storing the result
	 * into a new byte array.
	 * <p>
	 * Will return null if the string is null.
	 * 
	 * @param string the string
	 * @return a byte array
	 * @since 3.0
	 */
	//以UTF-8编码一个字符串并存放到字节码数组中
	public static byte[] getBytes(String string) {
		if (string != null) {
			return string.getBytes(UTF8_CHARSET);
		}
		return null;
	}

	/**
	 * Removes all whitespace of a string
	 * 
	 * @param string the string to remove
	 * @return the string without any whitespace, null if the string is null
	 * @since 3.0
	 */
	//移除字符串中的所有空格
	public static String removeWhitespace(String string) {
		if (isEmpty(string)) {
			return string;
		}//空串直接返回
		StringBuilder sb = new StringBuilder();
		int length = string.length();
		char c;
		for (int i = 0; i < length; i++) { //遍历字符串
			c = string.charAt(i);
			if (Character.isWhitespace(c)) {
				continue; //判断字符串中的每一个字符是否是空格，是则跳过一次循环，不连接到新的字符串中
			}
			sb.append(c); //非空格使用append方法连接起来组成新的字符串
		}
		return sb.toString();
	}

	/**
	 * Removes all substring from the string.
	 * 
	 * @param string the string to check
	 * @param substring the substring to remove
	 * @return the string with all substring removed
	 * @since 3.0
	 */
	//移除一个字符串中的指定子串
	public static String remove(String string, String substring) {
		if (hasEmpty(string, substring)) {
			return string;
		}//有空串返回原串
		return string.replace(substring, "");//将子串部分用空串替代完成移除
	}

	/**
	 * Removes the substring from the start of string.
	 * 
	 * @param string the string to check
	 * @param substring the substring to remove
	 * @return the string with the start substring removed
	 * @since 3.0
	 */
	//字符串若以指定子串开始，移除之
	public static String removeStart(String string, String substring) {
		if (hasEmpty(string, substring)) {
			return string;
		}
		return string.startsWith(substring) ? string.substring(substring.length()) : string;
	}

	/**
	 * Removes the substring from the end of string.
	 * 
	 * @param string the string to check
	 * @param substring the substring to remove
	 * @return the string with end substring removed
	 * @since 3.0
	 */
	//字符串若以指定子串结尾，移除之
	public static String removeEnd(String string, String substring) {
		if (hasEmpty(string, substring)) {
			return string;
		}
		return string.endsWith(substring) ? string.substring(0, string.length() - substring.length()) : string;
	}

	/**
	 * Checks if a string contains the specified substring
	 * 
	 * @param string the string to check
	 * @param substring the substring to search
	 * @return true if the string contains substring, otherwise false
	 * @since 3.0
	 */
	//检查字符串中是否包含指定子串
	public static boolean contain(String string, String substring) {
		if (hasEmpty(string, substring)) {
			return false;
		}
		return string.indexOf(substring) != INDEX_NOT_FOUND;//子串在待查找串中的索引可以找到即为包含
	}

	/**
	 * Tokenizes the {@code string} into a {@link String string} list via a {@link StringTokenizer}.
	 * <p>
	 * Will trims tokens and ignores empty tokens.
	 * 
	 * @param string the string to tokenize
	 * @param delimiters the delimiter characters
	 * @return a list of the tokens
	 * @since 3.0
	 */
	//以指定分割符分割字符串并存到List中
	public static List<String> tokenize(String string, String delimiters) {
		return tokenize(string, delimiters, true, true);
	}

	/**
	 * Tokenizes the {@code string} into a {@link String string} list via a {@link StringTokenizer}.
	 * 
	 * @param string the string to tokenize
	 * @param delimiters the delimiter characters
	 * @param trimToken trim the tokens via {@link String#trim()}
	 * @param ignoreEmptyToken ignore the empty tokens from the result array(only applies to tokens that are empty after
	 *            trimming)
	 * @return a list of the tokens
	 * @since 3.0
	 */
	//以指定分割符分割字符串并存到List中（可选是否去掉分割后字符串单元的首尾空格，是否忽略空字符串单元）
	public static List<String> tokenize(String string, String delimiters, boolean trimToken, boolean ignoreEmptyToken) {
		if (isEmpty(string)) {
			return null;
		}
		StringTokenizer tokenizer = new StringTokenizer(string, delimiters);//底层使用StringTokenizer类实现
		List<String> tokens = new ArrayList<String>();
		String token;
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (trimToken) {
				token = token.trim();
			}//开启后会去掉分割后的首尾空格
			if (ignoreEmptyToken && token.isEmpty()) {
				continue;
			}//开启后会不将空串添加到字符串列表中
			tokens.add(token);
		}
		return tokens;
	}

	/**
	 * Generates a random string.
	 * 
	 * @param length the random string's length
	 * @return a random string
	 * @since 3.0
	 */
	//生成一个指定长度的随机字符串
	public static String generateRandomString(int length) {
		Assert.isPositiveNumber(length, "Argument length must be a positive number");
		StringBuilder randomString = new StringBuilder();
		for (int i = 0; i < length; i++) {
			randomString.append(STRING_DICTIONARY[RANDOM.nextInt(STRING_DICTIONARY.length)]);
		}
		return randomString.toString();
	}

	/**
	 * Generates a random string which only containing number.
	 * <p>
	 * For example: "0123", "342".
	 * 
	 * @param length the random string's length
	 * @return a random number string
	 * @since 3.0
	 */
	//生成指定长度的纯数字随机字符串
	public static String generateRandomNumberString(int length) {
		Assert.isPositiveNumber(length, "Argument length must be a positive number");
		StringBuilder randomNumber = new StringBuilder();
		for (int i = 0; i < length; i++) {
			randomNumber.append(NUMBER_DICTIONARY[RANDOM.nextInt(NUMBER_DICTIONARY.length)]);
		}
		return randomNumber.toString();
	}

	/**
	 * Unescapes a string using java string rule.
	 * 
	 * @param str the string
	 * @return a unescaped string
	 * @since 3.0
	 */
	//反转义java字符串
	public static String unescapeJava(String str) {
		if (isEmpty(str)) {
			return str;
		}
		StringBuilder unescapeStr = new StringBuilder();
		boolean hadSlash = false;
		for (char c : str.toCharArray()) {
			if (hadSlash) {
				hadSlash = false;
				switch (c) {
					case '\'':
						unescapeStr.append('\'');
						break;
					case '"':
						unescapeStr.append('"');
						break;
					case '\\':
						unescapeStr.append('\\');
						break;
					case 'r':
						unescapeStr.append('\r');//将原本的双反斜加r替换成单反斜加r
						break;
					case 'f':
						unescapeStr.append('\f');
						break;
					case 't':
						unescapeStr.append('\t');
						break;
					case 'n':
						unescapeStr.append('\n');
						break;
					case 'b':
						unescapeStr.append('\b');
						break;
					default:
						unescapeStr.append(c);
						break;
				}
				continue;
			} else if (c == '\\') { //java中双反斜是转义但其他语言一般是单反斜
				hadSlash = true;
				continue;
			}
			unescapeStr.append(c);
		}
		return unescapeStr.toString();
	}

	/**
	 * Checks if a string is email format.
	 * <p>
	 * Will returns {@code false} if the string is null or empty.
	 * 
	 * @param str the string to check
	 * @return {@code true} if the string is email format, otherwise {@code false}
	 * @since 3.0
	 */
	//检查一个字符串是否是电子邮件地址
	public static boolean isEmail(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return EMAIL_FORMAT_PATTERN.matcher(str).matches();
	}

	/**
	 * Checks if a string is cellphone number format.
	 * <p>
	 * Will returns {@code false} if the string is null or empty.
	 * 
	 * @param str the string to check
	 * @return {@code true} if the string is cellphone number format, otherwise {@code false}
	 * @since 3.0
	 */
	//检查一个字符串是否是手机号码
	public static boolean isCellphoneNumber(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return CELLPHONE_NUMBER_FORMAT_PATTERN.matcher(str).matches();
	}
	
	
	//将一个字符串转成长整型（非数字串返回0）
	public static long toLong(String str) {
		if(StringUtil.isEmpty(str)) {
			return 0;
		}
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	//将一个字符串转成整型（非数字串返回0）
	public static int toInt(String str) {
		if(StringUtil.isEmpty(str)) {
			return 0;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	//将一个字符串转成字节型（非数字串返回0）
	public static byte toByte(String str) {
		if(StringUtil.isEmpty(str)) {
			return 0;
		}
		try {
			return Byte.parseByte(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	//将一个字符串转成短整型（非数字串返回0）
	public static short toShort(String str) {
		if(StringUtil.isEmpty(str)) {
			return 0;
		}
		try {
			return Short.parseShort(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	//将一个字符串转成浮点型（非数字串返回0）
	public static float toFloat(String str) {
		if(StringUtil.isEmpty(str)) {
			return 0;
		}
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	//将一个字符串转成双精度浮点型（非数字串返回0）
	public static double toDouble(String str) {
		if(StringUtil.isEmpty(str)) {
			return 0.0;
		}
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}
	
	//将一个字符串转成浮点型（非数字串返回false）
	public static boolean toBoolean(String str) {
		if(StringUtil.isEmpty(str)) {
			return false;
		}
		try {
			return Boolean.parseBoolean(str);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	//将一个字符串转成高精度小数型（空串返回null）
	public static BigDecimal toBigDecimal(String str) {
		if(StringUtil.isEmpty(str)) {
			return null;
		}
		return new BigDecimal(str);
	}

	//将一个字符串以指定格式转成日期Date型（空串返回null）
	public static Date toDate(String str, String format) {
		if(StringUtil.isEmpty(str)) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(str); //用日期格式化对象解析字符串完成转换
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Replace all occurrences of a substring within a string with another string.
	 * @param inString {@code String} to examine
	 * @param oldPattern {@code String} to replace
	 * @param newPattern {@code String} to insert
	 * @return a {@code String} with the replacements
	 */
	//以一个新的子串替换一个字符串中所有匹配的旧的子串
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (StringUtil.isEmpty(inString) || StringUtil.isEmpty(oldPattern) || newPattern == null) {
			return inString;
		}
		int index = inString.indexOf(oldPattern);//获取待替换旧子串的索引
		if (index == -1) {
			// no occurrence -> can return input as-is
			return inString;
		}//找不到待替换串，返回原串

		int capacity = inString.length(); //获取原串的长度
		if (newPattern.length() > oldPattern.length()) {
			capacity += 16;
		} //新子串长度大于待替换旧子串长度，在原串长度基础上加16
		StringBuilder sb = new StringBuilder(capacity);

		int pos = 0;  // our position in the old string
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));//截取旧子串索引位置到连接开始的位置
			sb.append(newPattern);//连接新子串（替代掉了旧子串）
			pos = index + patLen; //连接的开始位置是旧子串索引加上其长度
			index = inString.indexOf(oldPattern, pos);//获取全部匹配到待替换旧子串的索引
		}

		// append any characters to the right of a match
		sb.append(inString.substring(pos));
		return sb.toString();
	}
	
	public static String toString(Object o) {
		if (o == null) {
			return "null";
		}
		
		if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(o);
		}
		
		return o.toString();
	}
}
