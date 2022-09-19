package cn.edu.nwpu.rj416.type.astype.cast;

import java.lang.reflect.Type;

/**
 * 一个单向类型转换器
 * @author MilesLiu
 *
 * @param <F>
 * @param <T>
 */
public interface MTypeCaster<F, T> {
	public T cast(F value, Type destType) throws MTypeCastException;
}
