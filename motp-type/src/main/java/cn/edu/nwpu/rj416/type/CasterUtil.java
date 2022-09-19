package cn.edu.nwpu.rj416.type;


import cn.edu.nwpu.rj416.type.util.TypeUtil;

public class CasterUtil {
	/**
	 * 判断sub是否是base的细化实现
	 * @param base
	 * @param sub
	 * @return
	 */
	//根据两个转换细节对象的fromType和toType是否可以相互赋值判断
	public static boolean match(CasterDetail base, CasterDetail sub) {
		if (!TypeUtil.isAssignableTo(sub.getFromType(), base.getFromType())) {
			return false;
		}
		if (!TypeUtil.isAssignableTo(sub.getToType(), base.getToType())) {
			return false;
		}
		
		return true;
	}
}
