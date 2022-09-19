package cn.edu.nwpu.rj416.type.caster.string;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//字符串转日期类转换器
public class MCasterString2Date implements MTypeCaster<String, Date> {

	//日期格式数组
	private static final String[] DATE_FORMATS = {
		"yyyy-MM-dd HH:mm:ss SSS",
		"yyyy-MM-dd HH:mm:ss",
		"yyyy-MM-dd HH:mm",
		"yyyy-MM-dd HH",
		"yyyy-MM-dd",
		"yyyy-MM",
		"yyyy"
	};
	
	@Override
	public Date cast(String value, Type destType) throws MTypeCastException {
		if (StringUtil.isEmpty(value)) {
			return null;
		}
		for (String fmt : DATE_FORMATS) { //遍历格式数组
			if (value.length() == fmt.length()) { //找与字符串长度相同的格式
				SimpleDateFormat sdf = new SimpleDateFormat(fmt); //生成对应格式的日期格式化对象
				try {
					return sdf.parse(value); //用日期格式化对象解析生成相应日期对象
				} catch (ParseException e) {
					return null;
				}
			}
		}
		
		return null;
	}

}
