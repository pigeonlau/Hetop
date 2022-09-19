package cn.edu.nwpu.rj416.type.astype.number;



import cn.edu.nwpu.rj416.util.astype.AsInteger;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface AsInt64 extends AsInteger<Long> {

	@Override
	default Double castToDouble() {
		Long v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		
		return v.doubleValue();
	}

	@Override
	default BigDecimal castToBigDecimal() {
		Long v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return new BigDecimal(v);
	}

	@Override
	default Byte castToByte() {
		Long v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.byteValue();
	}

	@Override
	default Short castToShort() {
		Long v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.shortValue();
	}

	@Override
	default Integer castToInteger() {
		Long v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.intValue();
	}

	@Override
	default Long castToLong() {
		Long v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return v.longValue();
	}

	@Override
	default BigInteger castToBigInteger() {
		Long v = this.dumpToAsType();
		if (v == null) {
			return null;
		}
		return BigInteger.valueOf(v.longValue());
	}

}
