package cn.edu.nwpu.rj416.type.caster.string;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.type.caster.murl.MUrl;

import java.lang.reflect.Type;



//字符串转标准URL类转换器
public class MCasterString2Murl implements MTypeCaster<String, MUrl> {

	@Override
	public MUrl cast(String value, Type destType) throws MTypeCastException {
		return MUrl.parse(value);
	}

}
