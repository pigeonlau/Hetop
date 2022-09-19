package cn.edu.nwpu.rj416.type.caster.murl;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//URL转字符串类转换器
public class MCasterMurl2String implements MTypeCaster<MUrl, String> {

	@Override
	public String cast(MUrl value, Type destType) throws MTypeCastException {
		return value.getUrlStr();
	}

}
