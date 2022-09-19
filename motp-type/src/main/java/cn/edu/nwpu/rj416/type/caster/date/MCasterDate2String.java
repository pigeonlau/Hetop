package cn.edu.nwpu.rj416.type.caster.date;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;



//日期类转字符串类转换器
public class MCasterDate2String implements MTypeCaster<Date, String> {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss SSS";

	@Override
	public String cast(Date value, Type destType) throws MTypeCastException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(value);
	}

	
}
