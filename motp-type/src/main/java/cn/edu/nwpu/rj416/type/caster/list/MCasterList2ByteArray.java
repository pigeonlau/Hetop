package cn.edu.nwpu.rj416.type.caster.list;


import cn.edu.nwpu.rj416.type.Macaw;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.List;



//列表转字节数组转换器
public class MCasterList2ByteArray implements MTypeCaster<List<?>, byte[]> {

	@Override
	public byte[] cast(List<?> value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		if (value.isEmpty()) {
			return new byte[0];
		}
		
		byte[] arr = new byte[value.size()];
		for (int i = 0; i < value.size(); i++) {
			arr[i] = Macaw.cast(value.get(i), byte.class);
		}
		return arr;
	}


}
