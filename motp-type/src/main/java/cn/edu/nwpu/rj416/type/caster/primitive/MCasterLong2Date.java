package cn.edu.nwpu.rj416.type.caster.primitive;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.Date;



//长整型转日期类转换器
public class MCasterLong2Date implements MTypeCaster<Long, Date> {
	
	@Override
	public Date cast(Long value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		
		return new Date(value);
	}

}
