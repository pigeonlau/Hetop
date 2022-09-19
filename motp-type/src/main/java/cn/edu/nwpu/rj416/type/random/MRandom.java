package cn.edu.nwpu.rj416.type.random;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

//自定义随机类
public class MRandom {
	private static final int CHAR_RANDOM_RANGE = 0x9fa5 - 0x4e00 + 1;
	private Random r = new Random();
	
	//生成随机布尔值
	public boolean nextBoolean() {
		return r.nextBoolean();
	}
	
	//生成随机字节码值
	public byte nextByte() {
		return (byte)r.nextInt();
	}
	
	//规定最大值，生成随机字节码值
	public byte nextByte(byte max) {
		return (byte)r.nextInt(max);
	}

	//生成随机短整型数
	public short nextShort() {
		return (short)r.nextInt();
	}
	
	//规定最大值，生成随机短整型数
	public short nextShort(short bound) {
		return (short)r.nextInt(bound);
	}
	
	//生成随机整型数
	public int nextInt() {
		return r.nextInt();
	}
	
	//规定最大值，生成随机整型数
	public int nextInt(int bound) {
		return r.nextInt(bound);
	}
	
	//生成随机长整型数
	public long nextLong() {
		return r.nextLong();
	}
	
	//生成随机浮点数
	public float nextFloat() {
		return r.nextFloat();
	}
	
	//生成随机双精度浮点数
	public double nextDouble() {
		return r.nextDouble();
	}
	
	//生成随机字符型数
	public char nextChar() {
		return (char) (0x4e00 + r.nextInt(CHAR_RANDOM_RANGE));
	}
	
	//生成随机日期对象
	public Date nextDate() {
		long n = System.currentTimeMillis(); //以系统当前时间作为种子
		n -= r.nextInt(); //减去一个随机数
		Date d = new Date(n); //然后构造日期对象
		return d;
	}
	
	//生成指定长度的随机布尔数组
	public boolean[] nextBooleanArray(int len) {
		boolean[] arr = new boolean[len];
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextBoolean();
			}
		}
		return arr;
	}
	
	//生成指定长度的随机字节码数组
	public byte[] nextByteArray(int len) {
		byte[] arr = new byte[len];
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				arr[i] = this.nextByte();
			}
		}
		return arr;
	}
	
	//生成指定长度的随机短整型数组
	public short[] nextShortArray(int len) {
		short[] arr = new short[len];
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				arr[i] = this.nextShort();
			}
		}
		return arr;
	}
	
	//生成指定长度的随机整型数组
	public int[] nextIntArray(int len) {
		int[] arr = new int[len];
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				arr[i] = this.nextInt();
			}
		}
		return arr;
	}
	
	//生成指定长度的随机长整型数组
	public long[] nextLongArray(int len) {
		long[] arr = new long[len];
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				arr[i] = this.nextLong();
			}
		}
		return arr;
	}
	
	//生成指定长度的随机浮点型数组
	public float[] nextFloatArray(int len) {
		float[] arr = new float[len];
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				arr[i] = this.nextFloat();
			}
		}
		return arr;
	}
	
	//生成指定长度的随机双精度浮点型数组
	public double[] nextDoubleArray(int len) {
		double[] arr = new double[len];
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				arr[i] = this.nextDouble();
			}
		}
		return arr;
	}
	
	//生成指定长度的随机字符型数组
	public char[] nextCharArray(int len) {
		char[] arr = new char[len];
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				arr[i] = this.nextChar();
			}
		}
		return arr;
	}

	//生成随机字符串（随机长度但不超过100）
	public String nextString() {
		String value = "";
		int len = r.nextInt(100);
		char[] charArr = this.nextCharArray(len);
		value = new String(charArr);
		return value;
	}

	//规定最大长度，生成随机字符串
	public String nextString(int maxLen) {
		String value = "";
		int len = r.nextInt(maxLen);
		char[] charArr = this.nextCharArray(len);
		value = new String(charArr);
		return value;
	}

	//生成随机高精度数
	public BigDecimal nextBigDecimal() {
		BigDecimal base = new BigDecimal(r.nextLong());
		BigDecimal decimal = new BigDecimal(r.nextDouble());
		return base.add(decimal);
	}
	
	//规定小数位数范围，生成随机高精度数（向下舍位）
	public BigDecimal nextBigDecimal(double max, int scale) {
		if (max <= 0) {
			return BigDecimal.ZERO;
		}
		double dv = max * r.nextDouble();
		
		return new BigDecimal(dv).setScale(scale, BigDecimal.ROUND_DOWN);
	}
	
	public BigDecimal nextBigDecimal(BigDecimal max, int scale) {
		double dv = max.doubleValue();
		return this.nextBigDecimal(dv, scale);
	}

	//生成随机大整数
	public BigInteger nextBigInteger() {
		BigInteger bi = new BigInteger(
			String.format("%010d%010d%010d%010d", 
					r.nextInt(0x7FFFFFFF), 
					r.nextInt(0x7FFFFFFF), 
					r.nextInt(0x7FFFFFFF), 
					r.nextInt(0x7FFFFFFF)));
		return bi;
	}
}
