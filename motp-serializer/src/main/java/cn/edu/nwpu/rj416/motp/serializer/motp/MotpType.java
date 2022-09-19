package cn.edu.nwpu.rj416.motp.serializer.motp;

public class MotpType {
	public static final byte VOID = 0x00;
	public static final byte INT8 = 0x01;
	public static final byte INT16 = 0x02;
	public static final byte INT32 = 0x03;
	public static final byte INT64 = 0x04;
	public static final byte FLOAT = 0x05;
	public static final byte DOUBLE = 0x06;
	public static final byte CHAR = 0x07;

	public static final byte INT_VL = 0x08;
	
	public static final byte STRING = 0x10;
	public static final byte ENUM = 0x11;
	public static final byte FILE = 0x12;
	public static final byte BIG_DECIMAL = 0x13;
	public static final byte BOOLEAN = 0x14;
	
	public static final byte BYTE_ARR = 0x20;
	public static final byte INT16_ARR = 0x22;
	public static final byte INT32_ARR = 0x23;
	public static final byte INT64_ARR = 0x24;
	public static final byte FLOAT_ARR = 0x25;
	public static final byte DOUBLE_ARR = 0x26;
	public static final byte CHAR_ARR = 0x27;

	public static final byte OBJECT = 0x30;
	public static final byte LIST = 0x32;
	public static final byte MAP = 0x33;
	public static final byte SET = 0x34;
}
