package cn.edu.nwpu.rj416.type;



import cn.edu.nwpu.rj416.motp.reflectasm.ConstructorAccess;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.util.ObjectUtil;
import cn.edu.nwpu.rj416.type.util.TypeUtil;
import cn.edu.nwpu.rj416.util.reflect.TypeDetail;

import java.lang.reflect.Type;
import java.util.List;

//转换器实现类
public class MacawCasterImpl{
	

	private CastPathManager castPathManager = CastPathManager.getInstance();
	
	/**
	 * 将value转换为destType指定的类型
	 * @param value
	 * @param destType </br>
	 * 当destType类型为<b>Class</b>时:</br>
	 * &nbsp;&nbsp;表示转换的目标类型为非泛型对象或非泛型数组</br>
	 * 当destType类型为<b>ParameterizedType</b>时:</br>
	 * &nbsp;&nbsp;表示转换的目标类型为泛型对象</br>
	 * 当destType类型为<b>GenericArrayType</b>时:</br>
	 * &nbsp;&nbsp;表示转换的目标类型为泛型数组，例如List&lt;String&gt;[]
	 * 
	 * @return
	 */


	public Object cast(Object value, Type destType) {
		return this.doCast(value, destType);
	}
	

	public <T> T cast(Object value, Class<T> destClass) {
		@SuppressWarnings("unchecked")
		T rst = (T)this.doCast(value, destClass);
		return rst;
	}

	//判断两个类型能否转换

	public boolean canCast(Class<?> fromClass, Type destType) {
		if (fromClass == null) {
			throw new MTypeCastException(
					String.format("转换源类型不能为null"));
		}
		
		if (destType == null) {
			throw new MTypeCastException(
					String.format("转换目标类型不能为null"));
		}
		
		if (fromClass == destType) {
			return true;
		}
		
		CastPath path = castPathManager.getCastPath(fromClass, destType);
		
		if (path == null) {
			return false;
		}
		
		return true;
	}


	public boolean canCast(Object value, Type destType) {
		if (destType == null) {
			throw new MTypeCastException(
					String.format("转换目标类型不能为null"));
		}
		if (value == null) {
			return true;
		}
		
		
		CastPath path = castPathManager.getCastPath(value.getClass(), destType);
		
		if (path == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 处理null值的转换 
	 * @param destType
	 * @return
	 * @throws MTypeCastException
	 */
	//目标类型是基本数据类型时返回基本数据类型的对象，其他均返回null
	private Object castNullToDestType(Type destType)  {

		Class<?> rawType = TypeUtil.getRawType(destType);
		if (rawType == null) {
			return null;
		}
		
		if (rawType.isPrimitive()) {
			Object destValue = ObjectUtil.createObjectByClass(rawType);
			return destValue;
		}
		
		return null;
	}

	//类型转换
	private Object doCast(Object value, Type destType) {
		if (destType == null) { //转换的目标类型不能为空
			throw new MTypeCastException(
					String.format("转换目标类型不能为null"));
		}
		
		if (value == null) { //要转换的对象为null，调用处理null转换的方法
			return this.castNullToDestType(destType);
		}
		Type toType = destType;
		boolean toPrimitive = TypeUtil.isPrimitive(destType); //判断要转换的目标类型是不是基本类型
		if (toPrimitive) {
			toType = TypeUtil.primitiveToWrapper((Class<?>)destType);
		}//是基本类型，将其转换为基本类型的包装类
		
		Class<?> valueCls = value.getClass(); //获取要转换实例对象的Class对象
		
		if (List.class.isAssignableFrom(valueCls)) {
			/*
			 * 若要转换的对象属于实现了List接口的列表类
			 */
			TypeDetail td = TypeUtil.parseTypeAs(toType, List.class); //将转换目标类型尝试转换为List类，并获取其类型细节
			Type componentType = td.getTypeParameters().get(0).getDetail().getType(); //获取目标类型的组件类型
			@SuppressWarnings("unchecked")
			//List<Object> toList = (List<Object>)ObjectUtil.createObjectByType(toType);
            List<Object> toList = (List<Object>) ConstructorAccess.get(toType.getClass()).newInstance();
			//创建要转换类型的空对象
			List<?> fromList = (List<?>)value;
			for (Object o : fromList) {
				Object v = Macaw.cast(o, componentType);
				toList.add(v); //将要转换的列表类对象中的所有元素转换成目标列表的组件类型然后添加进目标类型的空表中
			}
			return toList;
		} else if (TypeUtil.isAssignableTo(value.getClass(), toType)) {
			return value; //若目标类型是待转换类型的父类或者属于同样类型，不作转换，直接返回待转换对象本身
		}
		
		//其他情况，从管理所有转换路径的类中获取从转换类型到目标类型的转换路径
		CastPath path = castPathManager.getCastPath(value.getClass(), toType);
		
		if (path == null) { //若路径为空，调用处理null转换的方法
			//throw new CastPathNotFoundException(value.getClass(), destType);
			return this.castNullToDestType(destType);
		}
		
		//路径不为空
		Object rst = null;
		try {
			rst = path.cast(value, destType); //尝试根据转换路径进行转换
		} catch (Exception e) {

		}
		
		if (rst == null && toPrimitive) { //转换结果为空并且目标类型属于基本类型
		    return ConstructorAccess.get(destType.getClass()).newInstance();
			//return ObjectUtil.createObjectByClass((Class<?>)destType); //返回基本类型的默认值对象
		}
		
		return rst; //转换成功，返回转换结果
	}
}
