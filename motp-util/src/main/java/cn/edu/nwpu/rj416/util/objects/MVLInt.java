package cn.edu.nwpu.rj416.util.objects;

import cn.edu.nwpu.rj416.util.objects.interval.LongInterval;
import cn.edu.nwpu.rj416.util.astype.AsInteger;
import cn.edu.nwpu.rj416.util.exception.runtime.MOverflowException;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 变长的Int， 根据具体值，用尽可能少的字节数存储
 * 
 * 基本规则：</br>
 * 
 * 最高位0：总计7位， 范围-2^6 ~ 2^6-1， 1字节</br>
 * 
 * 最高位10 : 总计14位， 范围-2^13 ~ 2^13-1， 2字节</br>
 * 
 * 最高位110 : 总计29位， 范围-2^28 ~ 2^28-1, 4字节</br>
 * 
 * 最高位1110 : 总计60位， 范围-2^59 ~ 2^59-1, 8字节</br>
 * 
 * @author MilesLiu
 * 2019年11月27日 下午4:58:21
 */
public class MVLInt implements AsInteger<Long> {
	//-2^6 ~ 2^6范围的长整型间隔类常量属性（1字节）
	private static final LongInterval Bit1 = new LongInterval(-1L << 6, true, 1L << 6, false);
	//-2^13 ~ 2^13-1范围的长整型间隔类常量属性（2字节）
	private static final LongInterval Bit2 = new LongInterval(-1L << 13, true, 1L << 13, false);
	//-2^28 ~ 2^28-1范围的长整型间隔类常量属性（4字节）
	private static final LongInterval Bit4 = new LongInterval(-1L << 28, true, 1L << 28, false);
	//-2^59 ~ 2^59-1范围的长整型间隔类常量属性（8字节）
	private static final LongInterval Bit8 = new LongInterval(-1L << 59, true, 1L << 59, false);
	
	//掩码（按位与后方便存放到字节码数组中）
	private static final long Bit1Mask = 0b01111111L;//二进制，低7（8-1）位全1
	private static final long Bit2Mask = 0x3FFFL; //十六进制，低14（16-2）位全1
	private static final long Bit4Mask = 0x1FFFFFFFL; //低29（32-3）位全1
	private static final long Bit8Mask = 0x0FFFFFFFFFFFFFFFL; //低60（64-4）位全1
	
	//比特位标志
	public static final int Bit2Flag = 0b10000000;
	public static final int Bit4Flag = 0b11000000;
	public static final int Bit8Flag = 0b11100000;
	
	//获取字节码数组中存放的变长int的真正的值，用其值构造变长int类对象（pos为具体存放位置的下标）
	public static MVLInt valueOf(byte[] buffer, int pos) {
		byte b1 = buffer[pos];
		byte byteValue = buffer[pos];
		if ((b1 & 0b10000000) == 0) {
			byteValue = (byte)(byteValue << 1);
			byteValue = (byte)(byteValue >> 1);//先左移再右移获取1字节变长int的真正值（左移一位去掉最高位的0，再右移最高位补符号位）
			return new MVLInt(byteValue, 1); //构造变长int类对象，长度为一字节
		}
		
		/*
		 * 至少有2个字节
		 */
		short shortValue = 0;
		shortValue |= (byteValue);
		shortValue <<= 8;
		shortValue |= (buffer[pos + 1] & 0xFF); //获取buffer中存放的连续两个字节
		
		if ((b1 & 0b01000000) == 0) {
			shortValue = (short)(shortValue << 2);
			shortValue = (short)(shortValue >> 2);
			return new MVLInt(shortValue, 2); //构造两字节变长int
		}

		/*
		 * 至少有4个字节
		 */
		int intValue = 0;
		intValue |= shortValue;
		intValue <<= 8;
		intValue |= (buffer[pos + 2] & 0xFF);
		intValue <<= 8;
		intValue |= (buffer[pos + 3] & 0xFF);
		intValue &= Bit4Mask; //获取buffer中存放的连续四个字节
		
		if ((b1 & 0b00100000) == 0) {
			intValue = (int)(intValue << 3);
			intValue = (int)(intValue >> 3);
			return new MVLInt(intValue, 4);//构造四字节变长int
		}

		/*
		 * 至少有8个字节
		 */
		long longValue = 0;
		longValue |= intValue;
		longValue <<= 8;
		longValue |= (buffer[pos + 4] & 0xFF);
		longValue <<= 8;
		longValue |= (buffer[pos + 5] & 0xFF);
		longValue <<= 8;
		longValue |= (buffer[pos + 6] & 0xFF);
		longValue <<= 8;
		longValue |= (buffer[pos + 7] & 0xFF);
		longValue &= Bit8Mask;  //获取buffer中存放的连续八个字节

		longValue = (longValue << 4);
		longValue = (longValue >> 4);
		return new MVLInt(longValue, 8);//构造八字节变长int
	}
	
	
	private long value; //真值（变长int数真正的值大小）
	private int len;  //长度（多少字节）

	
	public MVLInt() {
		super();
	}
	
	public MVLInt(long v) { //根据真值的大小自动判断构造几字节的变长int数
		this();
		this.setValue(v);
	}

	public MVLInt(long v, int len) { //可以指定长度构造变长int数
		this();
		this.value = v;
		this.len = len;
	}
	
	public long getValue() {
		return value;
	}

	//根据传入值的大小自动设置构造函数真值以及长度大小
	public void setValue(long v) {
		if (Bit1.match(v)) { //属于一字节范围
			this.value = v;
			this.len = 1;
		} else if (Bit2.match(v)) { //属于两字节范围
			this.value = v;
			this.len = 2;
		} else if (Bit4.match(v)) { //属于四字节范围
			this.value = v;
			this.len = 4;
		} else if (Bit8.match(v)) { //属于八字节范围
			this.value = v;
			this.len = 8;
		} else {
			throw new MOverflowException("数值过大"); //超出八字节范围抛出溢出异常
		}
		
	}
	
	//将变长int数的真正的值存放到字节码数组中
	public byte[] getBytes() {
		byte[] buffer = new byte[this.len];
		long bits = this.value;
		if (this.len == 1) {
			bits = this.value & Bit1Mask;
			buffer[0] = (byte)(bits & 0xFF);
		} else if (this.len == 2) {
			bits = this.value & Bit2Mask;
			buffer[0] = (byte)((bits >> 8) & 0xFF | Bit2Flag);
			buffer[1] = (byte)(bits & 0xFF);
		} else if (this.len == 4) {
			bits = this.value & Bit4Mask;
			buffer[0] = (byte)((bits >> 24) & 0xFF | Bit4Flag);
			buffer[1] = (byte)((bits >> 16) & 0xFF);
			buffer[2] = (byte)((bits >> 8) & 0xFF);
			buffer[3] = (byte)(bits & 0xFF);
		} else if (this.len == 8) {
			bits = this.value & Bit8Mask;
			buffer[0] = (byte)((bits >> 56) & 0xFF | Bit8Flag);
			buffer[1] = (byte)((bits >> 48) & 0xFF);
			buffer[2] = (byte)((bits >> 40) & 0xFF);
			buffer[3] = (byte)((bits >> 32) & 0xFF);
			buffer[4] = (byte)((bits >> 24) & 0xFF);
			buffer[5] = (byte)((bits >> 16) & 0xFF);
			buffer[6] = (byte)((bits >> 8) & 0xFF);
			buffer[7] = (byte)(bits & 0xFF);
		}
		return buffer;
	}
	
	/**
	 * 获取所需字节数
	 * @return
	 */
	public int getLen() {
		return this.len;
	}

	@Override
	public Double castToDouble() { //转成double型
		return (double)this.value;
	}

	@Override
	public BigDecimal castToBigDecimal() { //转成高精度小数型
		return new BigDecimal(this.value);
	}

	@Override
	public Byte castToByte() { //转成字节型
		return (byte)this.value;
	}

	@Override
	public Short castToShort() { //转成短整型
		return (short)this.value;
	}

	@Override
	public Integer castToInteger() { //转成整型
		return (int)this.value;
	}

	@Override
	public Long castToLong() { //转成长整型
		return this.value;
	}

	@Override
	public BigInteger castToBigInteger() { //转成大整数型
		return BigInteger.valueOf(this.value);
	}

	@Override
	public Long dumpToAsType() {
		return this.value;
	}

	@Override
	public void restoreFromAsType(Long v) {
		this.setValue(v);
	}
}
