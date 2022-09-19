package cn.edu.nwpu.rj416.type.astype.number;


import util.astype.AsInteger;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface AsBigInteger extends AsInteger<BigInteger> {

	@Override
	default Double castToDouble() {
		BigInteger v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		
		return v.doubleValue();
	}

	@Override
	default BigDecimal castToBigDecimal() {
		BigInteger v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return new BigDecimal(v);
	}

	@Override
	default Byte castToByte() {
		BigInteger v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.byteValue();
	}

	@Override
	default Short castToShort() {
		BigInteger v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.shortValue();
	}

	@Override
	default Integer castToInteger() {
		BigInteger v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.intValue();
	}

	@Override
	default Long castToLong() {
		BigInteger v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.longValue();
	}

	@Override
	default BigInteger castToBigInteger() {
		BigInteger v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return BigInteger.valueOf(v.longValue());
	}
}
