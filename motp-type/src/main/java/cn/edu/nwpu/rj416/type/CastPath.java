package cn.edu.nwpu.rj416.type;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.util.types.ArrayUtil;

import java.lang.reflect.Type;


/**
 * 类型转换路径，包含1次或n次转换
 * @author MilesLiu
 *
 * 2020年3月13日 下午5:40:11
 */
public class CastPath {
	private MTypeCaster<?,?>[] castPath; //记录转换时所使用的转换器数组

	public MTypeCaster<?,?>[] getCastPath() {
		return castPath;
	}

	public void setCastPath(MTypeCaster<?,?>[] castPath) {
		this.castPath = castPath;
	}
	
	/**
	 * 根据转换路径完成类型转换
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object cast(Object value, Type destType) { //给定要转换的对象和最终要转换的类型
		if (ArrayUtil.isEmpty(this.castPath)) {
			return value;
		}
		
		Object rst = value;
		for (MTypeCaster caster : this.castPath) {
			rst = caster.cast(rst, destType);//note:转换器的第二个参数在转换中没有用到，起一个标记作用
		} //根据路径完成转换（无法转换会抛出异常）
		
		return rst;
	}
}
