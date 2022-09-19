package cn.edu.nwpu.rj416.util.astype;

import java.math.BigDecimal;

public interface AsNumber<T> extends AsType<T> {
	/**
	 * 当前对象转为double数据类型
	 * @return 
	 */
	Double castToDouble();
	/**
	 * 当前对象转为高精度类型
	 * @return
	 */
	BigDecimal castToBigDecimal();
}
