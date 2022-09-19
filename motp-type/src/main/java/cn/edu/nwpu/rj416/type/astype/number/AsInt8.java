package cn.edu.nwpu.rj416.type.astype.number;



import cn.edu.nwpu.rj416.util.astype.AsInteger;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface AsInt8 extends AsInteger<Byte> {

	@Override
	default Double castToDouble() {
		Byte v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		
		return v.doubleValue();
	}

	@Override
	default BigDecimal castToBigDecimal() {
		Byte v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return new BigDecimal(v);
	}

	@Override
	default Byte castToByte() {
		return this.dumpToAsType();
	}

	@Override
	default Short castToShort() {
		Byte v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.shortValue();
	}

	@Override
	default Integer castToInteger() {
		Byte v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.intValue();
	}

	@Override
	default Long castToLong() {
		Byte v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.longValue();
	}

	@Override
	default BigInteger castToBigInteger() {
		Byte v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return BigInteger.valueOf(v.longValue());
	}

}
