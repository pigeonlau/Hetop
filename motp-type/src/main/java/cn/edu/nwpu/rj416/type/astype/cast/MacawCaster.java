package cn.edu.nwpu.rj416.type.astype.cast;

import java.lang.reflect.Type;

/**
 * 实现类型之间互转
 * @author MilesLiu
 * Date 2020-03-14 17:18
 */
public interface MacawCaster {
	
	public Object cast(Object value, Type destType);
	
	public <T> T cast(Object value, Class<T> destClass);
	
	public boolean canCast(Class<?> fromClass, Type destType);
	
	public boolean canCast(Object value, Type destType);
}
