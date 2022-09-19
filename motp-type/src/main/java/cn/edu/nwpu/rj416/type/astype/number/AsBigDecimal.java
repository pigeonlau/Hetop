package cn.edu.nwpu.rj416.type.astype.number;



import cn.edu.nwpu.rj416.util.astype.AsNumber;

import java.math.BigDecimal;

public interface AsBigDecimal extends AsNumber<BigDecimal> {

	@Override
	default Double castToDouble() {
		return this.dumpToAsType().doubleValue();
	}

	@Override
	default BigDecimal castToBigDecimal() {
		return this.dumpToAsType();
	}
}
