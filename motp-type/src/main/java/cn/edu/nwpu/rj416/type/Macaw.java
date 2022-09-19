package cn.edu.nwpu.rj416.type;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;

import java.lang.reflect.Type;

/**
 * @ClassName Macaw
 * @Description TODO
 * @Author pigeonliu
 * @Date 2022/9/16 21:49
 */
public class Macaw {

    private static final MacawCasterImpl caster = new MacawCasterImpl();

    public static final <T> T cast(
            Object value,
            Class<T> destType) throws MTypeCastException {

        if (caster == null) {
            throw new MTypeCastException(
                    "未找到类型转换器的实现");
        }

        return caster.cast(value, destType);

    }

    /**
     * 将一个对象转换成指定类型，如果不能转换，抛出异常
     * @param <T>
     * @param value
     * @param destClass
     * @return
     * @throws MTypeCastException
     */
    public static final Object cast(
            Object value,
            Type destType) throws MTypeCastException {

        if (caster == null) {
            throw new MTypeCastException(
                    "未找到类型转换器的实现");
        }

        return caster.cast(value, destType);

    }
}
