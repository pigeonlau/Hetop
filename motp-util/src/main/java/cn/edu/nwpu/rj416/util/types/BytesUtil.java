package cn.edu.nwpu.rj416.util.types;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

//字节数组工具（将其他数据类型转成字节数组存放）
public abstract class BytesUtil {
	//将长整型数转成字节码存放到8个单元大小的字节码数组中（8bit*8=64bit）
	public static void putLong(byte[] bytes, int offset, long value) {
		bytes[offset + 7] = (byte)(value & 0xFF); //和FF按位与，可以只留下低8位
		bytes[offset + 6] = (byte)((value >> 8) & 0xFF);
		bytes[offset + 5] = (byte)((value >> 16) & 0xFF);
		bytes[offset + 4] = (byte)((value >> 24) & 0xFF);
		bytes[offset + 3] = (byte)((value >> 32) & 0xFF);
		bytes[offset + 2] = (byte)((value >> 40) & 0xFF);
		bytes[offset + 1] = (byte)((value >> 48) & 0xFF);
		bytes[offset + 0] = (byte)((value >> 56) & 0xFF);//通过右移操作每8位存放在一个数组元素中（高位对应低数组序号）
	}

	//从存放长整型的字节数组中提取长整型数
	public static long getLong(byte[] bytes, int offset) {
		long value = 0;
		value |= (bytes[offset] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 1] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 2] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 3] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 4] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 5] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 6] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 7] & 0xFF);
		return value;
	}
	
	//将整型数转成字节码存放到4个单元大小的字节码数组中（4bit*8=32bit）
	public static void putInt(byte[] bytes, int offset, int value) {
		bytes[offset + 3] = (byte)(value & 0xFF);
		bytes[offset + 2] = (byte)((value >> 8) & 0xFF);
		bytes[offset + 1] = (byte)((value >> 16) & 0xFF);
		bytes[offset + 0] = (byte)((value >> 24) & 0xFF);
	}
	
	//从存放整型的字节数组中提取整型数
	public static int getInt(byte[] bytes, int offset) {
		int value = 0;
		value |= (bytes[offset] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 1] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 2] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 3] & 0xFF);
		return value;
	}
	
	//将浮点数存放到字节数组中
	public static void putFloat(byte[] bytes, int offset, float value) {
		int n = Float.floatToRawIntBits(value); //先用IEEE 754标准处理浮点数，以整型数据记录
		putInt(bytes, offset, n); //再将整型数据存放到字节数组中
	}
	
	//从存放浮点数的字节数组中提取浮点数
	public static float getFloat(byte[] bytes, int offset) {
		int value = getInt(bytes, offset);
		return Float.intBitsToFloat(value);
	}
	
	//将双精度浮点数存放到字节数组中
	public static void putDouble(byte[] bytes, int offset, double value) {
		long n = Double.doubleToRawLongBits(value);
		putLong(bytes, offset, n);
	}
	
	//从存放双精度浮点数的字节数组中提取双精度浮点数
	public static double getDouble(byte[] bytes, int offset) {
		long value = getLong(bytes, offset);
		return Double.longBitsToDouble(value);
	}
	
	//将短整型数转成字节码存放到2个单元大小的字节码数组中（2bit*8=16bit）
	public static void putShort(byte[] bytes, int offset, short value) {
		bytes[offset + 1] = (byte)(value & 0xFF);
		bytes[offset] = (byte)((value >> 8) & 0xFF);
	}
	
	//从存放短整型的字节数组中提取短整型数
	public static short getShort(byte[] bytes, int offset) {
		short value = 0;
		value |= (bytes[offset] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 1] & 0xFF);
		return value;
	}
	
	//将字符型数转成字节码存放到2个单元大小的字节码数组中（2bit*8=16bit）
	public static void putChar(byte[] bytes, int offset, char value) {
		bytes[offset + 1] = (byte)(value & 0xFF);
		bytes[offset] = (byte)((value >> 8) & 0xFF);
	}
	
	//从存放字符型的字节数组中提取字符型数值
	public static char getChar(byte[] bytes, int offset) {
		char value = 0;
		value |= (bytes[offset] & 0xFF);
		value <<= 8;
		value |= (bytes[offset + 1] & 0xFF);
		return value;
	}
	
	//将字符串以指定字符集编码存放到字节数组中（指定复制的长度和偏移位置）
	public static void putString(byte[] bytes, int offset, String value, String charset, int len) throws UnsupportedEncodingException {
		byte[] strBytes = value.getBytes(charset);
		if (strBytes.length >= len) { //指定长度不够或刚好够字符串转成字节码后的长度，只将指定长度保存到数组中
			System.arraycopy(strBytes, 0, bytes, offset, len);//offset即字节数组开始存放字符串编码开始的下标
		}else {
			System.arraycopy(strBytes, 0, bytes, offset, strBytes.length);
			Arrays.fill(bytes, offset + strBytes.length, offset + len, (byte)0);
		} //指定长度够保存字符串编码，将其剩余位置填充0的字节码
	}
	
	//将缓存字节码数组存放到指定字节码数组的指定段
	public static void putByteArray(byte[] bytes, int offset, byte[] buffer, int len) {
		if (buffer.length >= len) {
			System.arraycopy(buffer, 0, bytes, offset, len);
		}else {
			System.arraycopy(buffer, 0, bytes, offset, buffer.length);
			Arrays.fill(bytes, offset + buffer.length, offset + len, (byte)0);
		}
	}
	
	//从存放字符串指定字符集编码的字节码数组中提取源字符串
	public static String getString(byte[] bytes, int offset, String charset, int len) throws UnsupportedEncodingException {
		byte[] strBytes = new byte[len];
		System.arraycopy(bytes, offset, strBytes, 0, len);
		String str = new String(strBytes, charset);
		return str;
	}
	
	//判断两个字节码数组是否相等（内容以及长度上比较）
	public static boolean equals(byte[] bytes1, byte[] bytes2) {
		if (bytes1 == null) {
			if (bytes2 != null) {
				return false;
			}else {
				return true; //同为空，也相等
			}
		}
		
		if (bytes2 == null) {
			return false;
		}
		
		if (bytes1.length != bytes2.length) {
			return false;
		}
		
		for (int i = 0; i < bytes1.length; i++) {
			if (bytes1[i] != bytes2[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	//短整型数组转成字节码数组（长度变为原来的两倍）
	public static byte[] toBytes(short[] arr) {
		byte[] buffer = new byte[arr.length * 2];
		
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putShort(buffer, i * 2, arr[i]);
		} //获取短整型数组中的每一个元素，再调用处理短整型转数组的方法
		
		return buffer;
	}
	
	//短整型数组转成字节码数组（可以指定开始存放的位置）
	public static void putShortArr(byte[] bytes, int offset, short[] arr) {
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putShort(bytes, offset + i * 2, arr[i]);
		}
	}
	
	//从存放短整型数组的字节码数组中提取原数组
	public static short[] getShortArr(byte[] bytes, int offset, int len) {
		short[] arr = new short[len];
		for (int i = 0; i < len; i++) {
			arr[i] = BytesUtil.getShort(bytes, offset + i * 2);
		}
		return arr;
	}
	
	//字符数组转成字节码数组（长度变为原来的两倍）
	public static byte[] toBytes(char[] arr) {
		byte[] buffer = new byte[arr.length * 2];
		
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putChar(buffer, i * 2, arr[i]);
		}
		
		return buffer;
	}
	
	//字符数组转成字节码数组（可以指定开始存放的位置）
	public static void putCharArr(byte[] bytes, int offset, char[] arr) {
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putChar(bytes, offset + i * 2, arr[i]);
		}
	}
	
	//从存放字符数组的字节码数组中提取原数组
	public static char[] getCharArr(byte[] bytes, int offset, int len) {
		char[] arr = new char[len];
		for (int i = 0; i < len; i++) {
			arr[i] = BytesUtil.getChar(bytes, offset + i * 2);
		}
		return arr;
	}
	
	//整型数组转成字节码数组（长度变为原来的四倍）
	public static byte[] toBytes(int[] arr) {
		byte[] buffer = new byte[arr.length * 4];
		
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putInt(buffer, i * 4, arr[i]);
		}
		
		return buffer;
	}
	
	//整型数组转成字节码数组（可以指定开始存放的位置）
	public static void putIntArr(byte[] bytes, int offset, int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putInt(bytes, offset + i * 4, arr[i]);
		}
	}
	
	//从存放整型数组的字节码数组中提取原数组
	public static int[] getIntArr(byte[] bytes, int offset, int len) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = BytesUtil.getInt(bytes, offset + i * 4);
		}
		return arr;
	}
	
	//长整型数组转成字节码数组（长度变为原来的八倍）
	public static byte[] toBytes(long[] arr) {
		byte[] buffer = new byte[arr.length * 8];
		
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putLong(buffer, i * 8, arr[i]);
		}
		
		return buffer;
	}
	
	//长整型数组转成字节码数组（可以指定开始存放的位置）
	public static void putLongArr(byte[] bytes, int offset, long[] arr) {
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putLong(bytes, offset + i * 8, arr[i]);
		}
	}
	
	//从存放长整型数组的字节码数组中提取原数组
	public static long[] getLongArr(byte[] bytes, int offset, int len) {
		long[] arr = new long[len];
		for (int i = 0; i < len; i++) {
			arr[i] = BytesUtil.getLong(bytes, offset + i * 8);
		}
		return arr;
	}
	
	//浮点型数组转成字节码数组（长度变为原来的四倍）
	public static byte[] toBytes(float[] arr) {
		byte[] buffer = new byte[arr.length * 4];
		
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putFloat(buffer, i * 4, arr[i]);
		}
		
		return buffer;
	}
	
	//浮点型数组转成字节码数组（可以指定开始存放的位置）
	public static void putFloatArr(byte[] bytes, int offset, float[] arr) {
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putFloat(bytes, offset + i * 4, arr[i]);
		}
	}
	
	//从存放浮点型数组的字节码数组中提取原数组
	public static float[] getFloatArr(byte[] bytes, int offset, int len) {
		float[] arr = new float[len];
		for (int i = 0; i < len; i++) {
			arr[i] = BytesUtil.getFloat(bytes, offset + i * 4);
		}
		return arr;
	}
	
	//双精度浮点型数组转成字节码数组（长度变为原来的八倍）
	public static byte[] toBytes(double[] arr) {
		byte[] buffer = new byte[arr.length * 8];
		
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putDouble(buffer, i * 8, arr[i]);
		}
		
		return buffer;
	}
	
	//双精度浮点型数组转成字节码数组（可以指定开始存放的位置）
	public static void putDoubleArr(byte[] bytes, int offset, double[] arr) {
		for (int i = 0; i < arr.length; i++) {
			BytesUtil.putDouble(bytes, offset + i * 8, arr[i]);
		}
	}
	
	//从存放双精度浮点型数组的字节码数组中提取原数组
	public static double[] getDoubleArr(byte[] bytes, int offset, int len) {
		double[] arr = new double[len];
		for (int i = 0; i < len; i++) {
			arr[i] = BytesUtil.getDouble(bytes, offset + i * 8);
		}
		return arr;
	}
	
}
