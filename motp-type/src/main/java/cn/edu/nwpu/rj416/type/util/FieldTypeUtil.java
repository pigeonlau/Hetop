package cn.edu.nwpu.rj416.type.util;





import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidParameterException;
import cn.edu.nwpu.rj416.util.exception.runtime.MUnbelievableException;
import cn.edu.nwpu.rj416.util.reflect.TypeDetail;
import cn.edu.nwpu.rj416.util.reflect.TypeParameter;

import java.lang.reflect.*;


/**
 * @author MilesLiu
 *
 * 2020年5月13日 下午6:53:58
 */
public abstract class FieldTypeUtil {
	
	
	private static Type getTypeByClass(Class<?> targetClass, Class<?> declaringClass, Class<?> cls) {
		return cls;
	}
	
	/**
	 * 如果属性的类型是ParameterizedType</br>
	 * 说明其定义类似以下例子</br>
	 * </br>
	 * class A<T> {</br>
	 *    List<String> nameList;</br>
	 *    List<T> list;</br>
	 *    Map<String, T> map;</br>
	 * }</br>
	 * </br>
	 * 这时应该逐个检查每个类型参数</br>
	 * 类型参数可能包含全部5种类型:</br>
	 * Class, ParameterizedType, GenericArrayType, TypeVariable, WildcardType</br>
	 * </br>
	 * @param targetClass
	 * @param declaringClass
	 * @param tv
	 * @return
	 */
	private static Type getTypeByTypeVariable(Class<?> targetClass, Class<?> declaringClass, TypeVariable<?> tv) {
		
		TypeDetail parentDetail = TypeUtil.parseTypeAs(targetClass, declaringClass);
		TypeParameter tp = parentDetail.getTypeParamterByName(tv.getName());
		if (tp == null || tp.getDetail() == null) {
			throw new MUnbelievableException();
		}
		
		return tp.getDetail().getType();
	}
	
	private static Type getTypeByParameterizedType(Class<?> targetClass, Class<?> declaringClass, ParameterizedType pt) {
		
		Type[] args = new Type[pt.getActualTypeArguments().length];
		for (int i = 0; i < pt.getActualTypeArguments().length; i++) {
			Type ata = pt.getActualTypeArguments()[i];
			Type t = FieldTypeUtil.getType(targetClass, declaringClass, ata);
			args[i] = t;
		}
		
		ParameterizedTypeImpl pti = new ParameterizedTypeImpl();
		pti.setActualTypeArguments(args);  
		pti.setRawType(pt.getRawType());
		pti.setOwnerType(pt.getOwnerType());
		
		return pti;
	}
	
	private static Type getTypeByWildcardType(Class<?> targetClass, Class<?> declaringClass, WildcardType type) {
		Type[] upperBounds = type.getUpperBounds();
		if (upperBounds == null || upperBounds.length == 0) {
			return Object.class;
		} 
		Type t = FieldTypeUtil.getType(targetClass, declaringClass, upperBounds[0]);
		return t;
	}

	private static Type getTypeByGenericArrayType(Class<?> targetClass, Class<?> declaringClass,
			GenericArrayType type) {
		Type t = FieldTypeUtil.getType(targetClass, declaringClass, type.getGenericComponentType());
		GenericArrayTypeImpl gati = new GenericArrayTypeImpl();
		gati.setGenericComponentType(t);
		return gati;
	}

	private static Type getType(Class<?> targetClass, Class<?> declaringClass, Type type) {
		
		if (type instanceof Class) {
			return FieldTypeUtil.getTypeByClass(targetClass, declaringClass, (Class<?>)type);
		} else if (type instanceof TypeVariable) {
			return FieldTypeUtil.getTypeByTypeVariable(targetClass, declaringClass, (TypeVariable<?>)type);
		} else if (type instanceof ParameterizedType) {
			return FieldTypeUtil.getTypeByParameterizedType(targetClass, declaringClass, (ParameterizedType)type);
		} else if (type instanceof GenericArrayType) {
			return FieldTypeUtil.getTypeByGenericArrayType(targetClass, declaringClass, (GenericArrayType)type);
		} else if (type instanceof WildcardType) {
			return FieldTypeUtil.getTypeByWildcardType(targetClass, declaringClass, (WildcardType)type);
		} else {
			throw new MUnbelievableException("未知的Type子类", type.getClass());
		}
	}

	/**
	 * 根据Field和目标Class判断Field的属性
	 * @param clazz 目标Class
	 * @param field 
	 * @return
	 */
	public static Type getFieldType(Class<?> clazz, Field field) {
		if (field == null) {
			throw new MInvalidParameterException("field不能为null");
		}
		Type type = field.getGenericType();
		Class<?> declaringClass = field.getDeclaringClass();
		
		return FieldTypeUtil.getType(clazz, declaringClass, type);
	}
	
	private static class ParameterizedTypeImpl implements ParameterizedType {
		
		private Type rawType;
		private Type[] actualTypeArguments;
		private Type ownerType;

		@Override
		public Type[] getActualTypeArguments() {
			return this.actualTypeArguments;
		}

		@Override
		public Type getRawType() {
			return this.rawType;
		}

		@Override
		public Type getOwnerType() {
			return this.ownerType;
		}

		@Override
		public String getTypeName() {
			String str = this.getRawType().getTypeName() + "<";
			boolean first = true;
			for (Type t : this.actualTypeArguments) {
				if (!first) {
					str += ", ";
				} else {
					first = false;
				}
				str += t.getTypeName();
			}
			str += ">";
			return str;
		}
		
		public void setRawType(Type rawType) {
			this.rawType = rawType;
		}

		public void setActualTypeArguments(Type[] actualTypeArguments) {
			this.actualTypeArguments = actualTypeArguments;
		}

		public void setOwnerType(Type ownerType) {
			this.ownerType = ownerType;
		}
		
	}
	
	private static class GenericArrayTypeImpl implements GenericArrayType {
		private Type genericComponentType;
		@Override
		public Type getGenericComponentType() {
			return this.genericComponentType;
		}
		public void setGenericComponentType(Type genericComponentType) {
			this.genericComponentType = genericComponentType;
		}
		@Override
		public String getTypeName() {
			return this.genericComponentType.getTypeName() + "[]";
		}
		
		
	}
}
