package cn.edu.nwpu.rj416.util.astype;

import java.math.BigInteger;

public interface AsInteger<T> extends AsNumber<T> {
	Byte castToByte();
	Short castToShort();
	Integer castToInteger();
	Long castToLong();
	BigInteger castToBigInteger();
}
