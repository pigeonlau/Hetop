package cn.edu.nwpu.rj416.type.caster.array;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author MilesLiu
 *
 * 2020年3月16日 下午1:14:04
 */
//字节码数组转列表转换器
public class MCasterByteArrayToList implements MTypeCaster<byte[], List<Byte>> {

	@Override
	public List<Byte> cast(byte[] value, Type destType) throws MTypeCastException {
		List<Byte> list = new ArrayList<>();
		for (byte v : value) {
			list.add(v);
		}
		return list;
	}


}
