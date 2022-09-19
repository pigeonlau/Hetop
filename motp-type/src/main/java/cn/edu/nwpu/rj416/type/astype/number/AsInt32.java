package cn.edu.nwpu.rj416.type.astype.number;



import cn.edu.nwpu.rj416.util.astype.AsInteger;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface AsInt32 extends AsInteger<Integer> {

	@Override
	default Double castToDouble() {
		Integer v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		
		return v.doubleValue();
	}

	@Override
	default BigDecimal castToBigDecimal() {
		Integer v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return new BigDecimal(v);
	}

	@Override
	default Byte castToByte() {
		Integer v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.byteValue();
	}

	@Override
	default Short castToShort() {
		Integer v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.shortValue();
	}

	@Override
	default Integer castToInteger() {
		Integer v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.intValue();
	}

	@Override
	default Long castToLong() {
		Integer v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.longValue();
	}

	@Override
	default BigInteger castToBigInteger() {
		Integer v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return BigInteger.valueOf(v.longValue());
	}
}
