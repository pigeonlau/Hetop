package cn.edu.nwpu.rj416.type.caster.date;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.Date;


//日期类转长整型类转换器
public class MCasterDate2Long implements MTypeCaster<Date, Long> {

	@Override
	public Long cast(Date value, Type destType) throws MTypeCastException {
		return value.getTime();
	}

	
}
