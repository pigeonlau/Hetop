package cn.edu.nwpu.rj416.type.util;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.util.astype.AsType;
import cn.edu.nwpu.rj416.util.exception.runtime.MUnbelievableException;
import cn.edu.nwpu.rj416.util.reflect.TypeDetail;
import cn.edu.nwpu.rj416.util.reflect.TypeParameter;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeUtil {
	
	/**
	 * 获得一个类型(Type)的详细描述
	 * @param type
	 * @return
	 */
	public static TypeDetail parseType(Type type) {
		if (type == null) {
			return null;
			//throw new MTypeCastException("类型不能为空");
		}
		TypeDetail detail = new TypeDetail();
		detail.setType(type);
		
		if (type instanceof Class) {
			detail.setRawType((Class<?>)type);
		} else if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType)type;
			detail.setRawType((Class<?>)pt.getRawType());
			detail.setTypeParameters(new ArrayList<>());
			TypeVariable<?> args[] = detail.getRawType().getTypeParameters();
			Type actualArgs[] = pt.getActualTypeArguments();
			for (int i = 0; i < args.length; i++) {
				String name = args[i].getName();
				Type argType = actualArgs[i];
				
				TypeParameter param = new TypeParameter();
				param.setName(name);
				param.setDetail(parseType(argType));
				detail.getTypeParameters().add(param);
				
			}
			
		} else if (type instanceof GenericArrayType) {
			GenericArrayType gat = (GenericArrayType)type;
			TypeDetail compDetail = parseType(gat.getGenericComponentType());
			detail.setArray(true);
			detail.setRawType(compDetail.getRawType());
			detail.setTypeParameters(compDetail.getTypeParameters());
		} else if (type instanceof TypeVariable) {
			return detail;
		} else if (type instanceof WildcardType) {
			return detail;
		} else {
			throw new MUnbelievableException();
		}
		
		return detail;
	}
	
	
	/**
	 * 获得一个属性(Field)的类型描述
	 * 等价于（TypeUtil.parseType(field.getGenericType())）
	 * @param field
	 * @return
	 */
	public static TypeDetail parseType(Field field) {
		Type gt = field.getGenericType();
		return parseType(gt);
	}

	
	/**
	 * 获得一个对象(Object)的类型描述
	 * 等价于（TypeUtil.parseType(object.getClass())）
	 * @param o
	 * @return
	 */
	public static TypeDetail parseType(Object o) {
		return parseType(o.getClass());
	}

	/**
	 * 将一个类型转换为其父类，并获取基于父类的类型描述</br>
	 * 转换时会综合考虑泛型</br>
	 * 例如针对以下类定义:</br>
	 * interface AsType&lt;T&gt;</br>
	 * interface AsNumber&lt;T&gt; extends AsType&lt;T&gt;</br>
	 * interface AsInteger&lt;T&gt; extends AsNumber&lt;T&gt;</br>
	 * interface AsInt32 extends AsInteger&lt;Integer&gt;</br>
	 * class AsIntObject implements AsInt32</br>
	 * </br>
	 * parseTypeAs(AsIntObject.class, AsType.class) : AsType&lt;Integer&gt;</br>
	 * parseTypeAs(AsIntObject.class, AsNumber.class):  AsNumber&lt;Integer&gt;</br>
	 * parseTypeAs(AsIntObject.class, AsInteger.class):  AsInteger&lt;Integer&gt;</br>
	 * parseTypeAs(AsIntObject.class, AsInt32.class):  AsInt32</br>
	 * </br>
	 * @param type
	 * @param destClass
	 * @return
	 */
	public static TypeDetail parseTypeAs(Type type, Class<?> destClass) {
		if (destClass == null) {
			throw new MTypeCastException("目标类型不能为空");
		}
		
		TypeDetail detail = TypeUtil.parseType(type);
		
		if (!destClass.isAssignableFrom(detail.getRawType())) {
			throw new MTypeCastException(
					String.format(
							"无法完成从%s至%s的类型转换", 
							detail.getRawType().getName(), 
							destClass.getName()));
		}
		/*
		 * 逐级向上转换，直到转换至destClass 
		 */
		while (detail.getRawType() != destClass) {
			Class<?> clazz = detail.getRawType();
			TypeDetail superType = TypeUtil.parseType(clazz.getGenericSuperclass());
			if (superType == null 
					|| !destClass.isAssignableFrom(superType.getRawType())) {
				/*
				 * 如果destClass是interface，需要通过接口获得superType
				 */
				Type[] interfaces = clazz.getGenericInterfaces();
				for (Type interfaceType : interfaces) {
					Class<?> interfaceRawType = TypeUtil.getRawType(interfaceType);
					if (!destClass.isAssignableFrom(interfaceRawType)) {
						continue;
					}
					superType = TypeUtil.parseType(interfaceType);
					break;
				}
			}
			if (superType == null) {
				//这种情况应该不会出现
				throw new MUnbelievableException();
			}
			
			/*
			 * 将子类的类型参数补充给父类
			 */
			superType.assignTypeParameters(detail);
			
			detail = superType;
		}
		
		return detail;
	}
	
	/**
	 * 将一个属性的类型转换为其父类，并获取基于父类的类型描述</br>
	 * 等价于 {@link TypeUtil#parseTypeAs parseTypeAs(field.getGenericType(), destClass)}
	 * @param field
	 * @param destClass
	 * @return
	 */
	public static TypeDetail parseTypeAs(Field field, Class<?> destClass) {
		return TypeUtil.parseTypeAs(field.getGenericType(), destClass);
	}

	/**
	 * 将一个对象的类型转换为其父类，并获取基于父类的类型描述</br>
	 * 等价于 {@link TypeUtil#parseTypeAs parseTypeAs(object.getClass(), destClass)}
	 * @param field
	 * @param destClass
	 * @return
	 */
	public static TypeDetail parseTypeAs(Object object, Class<?> destClass) {
		return TypeUtil.parseTypeAs(object.getClass(), destClass);
	}
	
	/**
	 * 将一个类型的泛型变量，转换为基本类型定义（List, Map, Set, AsType）
	 * @param detail
	 */
	private static void convertParameterToOriginal(TypeDetail detail) {
		if (CollectionUtil.isEmpty(detail.getTypeParameters())) {
			return;
		}
		
		for (TypeParameter tp : detail.getTypeParameters()) {
			if (tp.getDetail().getRawType() == null) {
				continue;
			}
			Class<?> clazz = tp.getDetail().getRawType();
			if (AsType.class.isAssignableFrom(clazz)) {
				if (clazz == AsType.class) {
					continue;
				}
				TypeDetail pd = TypeUtil.parseTypeAs(tp.getDetail().getType(), AsType.class);
				TypeUtil.convertParameterToOriginal(pd);
				tp.setDetail(pd);
			} else if (List.class.isAssignableFrom(clazz)) {
				if (clazz == List.class) {
					continue;
				}
				TypeDetail pd = TypeUtil.parseTypeAs(tp.getDetail().getType(), List.class);
				TypeUtil.convertParameterToOriginal(pd);
				tp.setDetail(pd);
			} else if (Map.class.isAssignableFrom(clazz)) {
				if (clazz == Map.class) {
					continue;
				}
				TypeDetail pd = TypeUtil.parseTypeAs(tp.getDetail().getType(), Map.class);
				TypeUtil.convertParameterToOriginal(pd);
				tp.setDetail(pd);
			} else if (Set.class.isAssignableFrom(clazz)) {
				if (clazz == Set.class) {
					continue;
				}
				TypeDetail pd = TypeUtil.parseTypeAs(tp.getDetail().getType(), Set.class);
				TypeUtil.convertParameterToOriginal(pd);
				tp.setDetail(pd);
			} else {
				TypeUtil.convertParameterToOriginal(tp.getDetail());
			}
		}
	}
	
	/**
	 * 将一个类型及泛型定义，全部转为基本类型</br>
	 * 所有List的子类会被转换为List</br>
	 * 所有Map的子类会被转换为Map</br>
	 * 所有Set的子类会被转换为Set</br>
	 * 所有AsType的子类会被转换为AsType</br>
	 * </br>
	 * @param type
	 */
	public static TypeDetail parseTypeAsOriginal(Type type) {
		if (type == null) {
			throw new MTypeCastException("目标类型不能为空");
		}
		TypeDetail detail = null;
		
		if (type instanceof Class) {
			Class<?> clazz = (Class<?>)type;
			if (AsType.class.isAssignableFrom(clazz)) {
				detail = TypeUtil.parseTypeAs(type, AsType.class);
				TypeUtil.convertParameterToOriginal(detail);
			} else if (List.class.isAssignableFrom(clazz)) {
				detail = TypeUtil.parseTypeAs(type, List.class);
				TypeUtil.convertParameterToOriginal(detail);
			} else if (Map.class.isAssignableFrom(clazz)) {
				detail = TypeUtil.parseTypeAs(type, Map.class);
				TypeUtil.convertParameterToOriginal(detail);
			} else if (Set.class.isAssignableFrom(clazz)) {
				detail = TypeUtil.parseTypeAs(type, Set.class);
				TypeUtil.convertParameterToOriginal(detail);
			} else {
				detail = TypeUtil.parseType(type);
			}
		} else if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType)type;
			Class<?> rawTypeClass = (Class<?>)pt.getRawType();
			if (AsType.class.isAssignableFrom(rawTypeClass)) {
				detail = TypeUtil.parseTypeAs(type, AsType.class);
				TypeUtil.convertParameterToOriginal(detail);
			} else if (List.class.isAssignableFrom(rawTypeClass)) {
				detail = TypeUtil.parseTypeAs(type, List.class);
				TypeUtil.convertParameterToOriginal(detail);
			} else if (Map.class.isAssignableFrom(rawTypeClass)) {
				detail = TypeUtil.parseTypeAs(type, Map.class);
				TypeUtil.convertParameterToOriginal(detail);
			} else if (Set.class.isAssignableFrom(rawTypeClass)) {
				detail = TypeUtil.parseTypeAs(type, Set.class);
				TypeUtil.convertParameterToOriginal(detail);
			} else {
				detail = new TypeDetail();
				detail.setRawType((Class<?>)pt.getRawType());
				detail.setTypeParameters(new ArrayList<>());
				TypeVariable<?> args[] = detail.getRawType().getTypeParameters();
				Type actualArgs[] = pt.getActualTypeArguments();
				for (int i = 0; i < args.length; i++) {
					String name = args[i].getName();
					Type argType = actualArgs[i];
					
					TypeParameter param = new TypeParameter();
					param.setName(name);
					param.setDetail(parseTypeAsOriginal(argType));
					detail.getTypeParameters().add(param);
					
				}
			}
		} else if (type instanceof GenericArrayType) {
			detail = new TypeDetail();
			GenericArrayType gat = (GenericArrayType)type;
			detail.setRawType(Array.class);
			detail.setTypeParameters(new ArrayList<>());
			
			TypeParameter param = new TypeParameter();
			param.setName("");
			param.setDetail(parseTypeAsOriginal(gat.getGenericComponentType()));
			
		} else if (type instanceof TypeVariable) {
			detail = new TypeDetail();
			detail.setType(type);
		}
		
		return detail;
	}
	
	/**
	 * 等价于 {@link TypeUtil#parseTypeAsOriginal parseTypeAsOriginal(field.getGenericType(), destClass)}
	 * @param field
	 * @param destClass
	 * @return
	 */
	public static TypeDetail parseTypeAsOriginal(Field field) {
		Type gt = field.getGenericType();
		return TypeUtil.parseTypeAsOriginal(gt);
	}
	
	/**
	 * 等价于 {@link TypeUtil#parseTypeAsOriginal parseTypeAsOriginal(object.getClass(), destClass)}
	 * @param field
	 * @param destClass
	 * @return
	 */
	public static TypeDetail parseTypeAsOriginal(Object object) {
		Type gt = object.getClass();
		return TypeUtil.parseTypeAsOriginal(gt);
	}
	
	/**
	 * 将一个类型描述，转换为基于父类的类型描述
	 * @param detail 当前类型描述
	 * @param destClass 目标父类
	 * @return
	 */
	public static TypeDetail convert(TypeDetail detail, Class<?> destClass) {
		if (destClass == null) {
			throw new MTypeCastException("目标类型不能为空");
		}
		
		if (!destClass.isAssignableFrom(detail.getRawType())) {
			throw new MTypeCastException(
					String.format(
							"无法完成从%s至%s的类型转换", 
							detail.getRawType().getName(), 
							destClass.getName()));
		}
		
		return TypeUtil.parseTypeAs(detail.getType(), destClass);
	}
	

	
	/**
	 * 根据一个Type获得Class
	 * @param type
	 * @return 
	 * 如果Type本身是一个Class， 则返回本身</br>
	 * 如果Type是一个泛型类型（ParameterizedType），则返回泛型类型的RawType</br>
	 * 其他情况返回null</br>
	 */
	public static Class<?> getRawType(Type type){
		if (type instanceof Class) {
			/*
			 * 一般类定义
			 */
			return (Class<?>)type;
		} else if (type instanceof ParameterizedType) {
			/*
			 * 参数化泛型类定义
			 */
			ParameterizedType pt = (ParameterizedType)type;
			Type ptRawType = pt.getRawType();
			return getRawType(ptRawType);
		} else {
			/*
			 * 包括以下情况
			 * GenericArrayType ： 
			 *    泛型数组 
			 * WildcardType：
			 *    泛型定义中含有上下限限制的类型参数<? extends SomeClass> 
			 *    或 <? super SomeClass>的定义
			 * TypeVariable：
			 *    泛型定义中的类型参数
			 */
			return null;
		}
	}
	
	public static Type[] getTypeArgs(Type type) {
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType)type;

			Type[] typeArgs = pt.getActualTypeArguments();
			return typeArgs;
		} else {
			return new Type[0];
		}
	}
	
	/**
	 * 判断一个类型是否可以赋值给一个Class
	 * @param fromType
	 * @param toClass
	 * @return
	 */
	private static boolean isAssignableToClass(Type fromType, Class<?> toClass) {

		if (fromType instanceof Class) {
			Class<?> fromClass = (Class<?>)fromType;
			return toClass.isAssignableFrom(fromClass);
		} else if (fromType instanceof ParameterizedType) {
			ParameterizedType fromParamType = (ParameterizedType)fromType;
			if (fromParamType.getRawType() instanceof Class) {
				return toClass.isAssignableFrom((Class<?>)fromParamType.getRawType());
			} else {
				return false;
			}
		} else if (fromType instanceof GenericArrayType) {
			return TypeUtil.equals(toClass, Object.class);
		} else if (fromType instanceof WildcardType) {
			WildcardType wct = (WildcardType)fromType;
			Type[] upperBounds = wct.getUpperBounds();
			if (upperBounds.length == 1) {
				return TypeUtil.isAssignableToClass(upperBounds[0], toClass);
			}
			
			return false;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断一个类型是否可以赋值给一个泛型类型
	 * @param fromType
	 * @param toParamType
	 * @return
	 */
	private static boolean isAssignableToParameterizedType(Type fromType, ParameterizedType toParamType) {
		Class<?> toClass = (Class<?>)toParamType.getRawType();
		if (fromType instanceof Class) {
			/*
			 * 如果目标是泛型，但数据源没有泛型定义
			 * 那么首先RawType应当匹配，同时，目标的泛型定义必须为?或者Object
			 */
			Class<?> fromClass = (Class<?>)fromType;
			if (toParamType.getRawType() instanceof Class) {
				if (!toClass.isAssignableFrom(fromClass)) {
					return false;
				}
				Type[] actualTypes = toParamType.getActualTypeArguments();
				for (Type at : actualTypes) {
					if (at instanceof Class) {
						Class<?> atClass = (Class<?>)at;
						if (atClass.isAssignableFrom(Object.class)) {
							return false;
						}
					} else if (at instanceof WildcardType) {
						WildcardType atWct = (WildcardType)at;
						Type[] upperBounds = atWct.getUpperBounds();
						if (upperBounds.length > 0 
								&& !TypeUtil.equals(upperBounds[0], Object.class)) {
							return false;
						}
					}
				}
				return true;
			} else {
				return false;
			}
		} else if (fromType instanceof ParameterizedType) {
			/*
			 * 如果目标是泛型，数据源也是泛型
			 * 首先判断RawType，然后检查泛型参数
			 */
			ParameterizedType fromParamType = (ParameterizedType)fromType;
			if (fromParamType.getRawType() instanceof Class) {
				if (!toClass.isAssignableFrom((Class<?>)fromParamType.getRawType())){
					return false;
				}
				
				TypeDetail fromTypeDetail = TypeUtil.parseTypeAs(fromType, toClass);
				List<TypeParameter> fromTypeParams = fromTypeDetail.getTypeParameters();
				Type[] toTypeParams = toParamType.getActualTypeArguments(); 
				if (fromTypeParams.size() != toTypeParams.length) {
					return false;
				}
				for (int i = 0; i < toTypeParams.length; i++) {
					if (!TypeUtil.isAssignableTo(
							fromTypeParams.get(i).getDetail().getType(), 
							toTypeParams[i])) {
						return false;
					}
				}
				return true;
			} else {
				throw new MUnbelievableException();
			}
		} else if (fromType instanceof GenericArrayType) {
			return TypeUtil.equals(toClass, Object.class);
		} else {
			return false;
		}
	}

	private static boolean isAssignableToGenericArrayType(Type fromType, GenericArrayType toGenericArray) {
		if (fromType instanceof GenericArrayType) {
			GenericArrayType fromGenericArray = (GenericArrayType)fromType;
			return TypeUtil.isAssignableTo(
					fromGenericArray.getGenericComponentType(), 
					toGenericArray.getGenericComponentType());
		} else {
			return false;
		}
	}

	/**
	 * 判断一个类型是否符合一个类型约束
	 * @param fromType
	 * @param toWct
	 * @return
	 */
	private static boolean isAssignableToWildcardType(Type fromType, WildcardType toWct) {
		Type[] upperBounds = toWct.getUpperBounds();
		if (upperBounds.length == 1) {
			return TypeUtil.isAssignableTo(fromType, upperBounds[0]);
		}
		
		Type[] lowerBounds = toWct.getLowerBounds();
		if (lowerBounds.length == 1) {
			return TypeUtil.isAssignableTo(lowerBounds[0], fromType);
		}
		
		return true;
	}
	
	public static boolean isAssignableTo(Type fromType, Type toType) {
		
		if (toType instanceof Class) {
			
			return TypeUtil.isAssignableToClass(fromType, (Class<?>)toType);
			
		} else if (toType instanceof ParameterizedType) {
			
			return TypeUtil.isAssignableToParameterizedType(fromType, (ParameterizedType)toType);
			
		} else if (toType instanceof GenericArrayType) {
			
			return TypeUtil.isAssignableToGenericArrayType(fromType, (GenericArrayType)toType);
			
		} else if (toType instanceof WildcardType) {
			
			return TypeUtil.isAssignableToWildcardType(fromType, (WildcardType)toType);
		}
		return false;
	}
	
	public static boolean equals(Type t1, Type t2) {
		if (t1 == null) {
			return t2 == null;
		}
		if (t2 == null) {
			return false;
		}
		return t1.getTypeName().equals(t2.getTypeName());
	}
	
	public static boolean isPrimitive(Type type) {
		if (type instanceof Class) {
			Class<?> clazz = (Class<?>)type;
			return clazz.isPrimitive();
		}
		return false;
	}
	
	public static boolean isPrimitiveWrapper(Type type) {
		if (type instanceof Class) {
			Class<?> clazz = (Class<?>)type;
			String clazzName = clazz.getName();
			if (clazzName.equals("java.lang.Integer")) {
				return true;
			}

			if (clazzName.equals("java.lang.Short")) {
				return true;
			}

			if (clazzName.equals("java.lang.Byte")) {
				return true;
			}

			if (clazzName.equals("java.lang.Long")) {
				return true;
			}

			if (clazzName.equals("java.lang.Float")) {
				return true;
			}

			if (clazzName.equals("java.lang.Double")) {
				return true;
			}

			if (clazzName.equals("java.lang.Character")) {
				return true;
			}

			if (clazzName.equals("java.lang.Boolean")) {
				return true;
			}
		}
		return false;
	}
	
	public static Class<?> primitiveToWrapper(Class<?> clazz) {
		String clazzName = clazz.getName();
		
		if (clazzName.equals("int")) {
			return Integer.class;
		}
		if (clazzName.equals("short")) {
			return Short.class;
		}
		if (clazzName.equals("byte")) {
			return Byte.class;
		}
		if (clazzName.equals("long")) {
			return Long.class;
		}
		if (clazzName.equals("boolean")) {
			return Boolean.class;
		}
		if (clazzName.equals("float")) {
			return Float.class;
		}
		if (clazzName.equals("double")) {
			return Double.class;
		}
		if (clazzName.equals("char")) {
			return Character.class;
		}
		
		return null;
	}
}
