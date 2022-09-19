package cn.edu.nwpu.rj416.type.caster.array;

import cn.ducis.macaw.core.type.astype.cast.MTypeCastException;
import cn.ducis.macaw.core.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author MilesLiu
 *
 * 2020年3月16日 下午1:14:04
 */
//double数组转列表转换器
public class MCasterDoubleArrayToList implements MTypeCaster<double[], List<Double>> {

	@Override
	public List<Double> cast(double[] value, Type destType) throws MTypeCastException {
		List<Double> list = new ArrayList<>();
		for (double v : value) {
			list.add(v);
		}
		return list;
	}


}
