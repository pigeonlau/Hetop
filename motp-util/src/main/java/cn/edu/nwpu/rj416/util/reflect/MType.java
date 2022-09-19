package cn.edu.nwpu.rj416.util.reflect;

import java.util.List;

//自定义泛型Type类
public class MType {
	private boolean array = false; //是否泛型数组标志（GenericArrayType）
	private boolean generic = false; //是否泛型对象标志（ParameterizedType）
	private Class<?> clazz; //该类型对应的Class对象
	private MType componentType; //组件类型
	private List<MType> typeParameters; //参数类型列表
	
	public boolean isArray() {
		return array;
	}
	public void setArray(boolean array) {
		this.array = array;
	}
	public boolean isGeneric() {
		return generic;
	}
	public void setGeneric(boolean generic) {
		this.generic = generic;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public List<MType> getTypeParameters() {
		return typeParameters;
	}
	public void setTypeParameters(List<MType> typeParameters) {
		this.typeParameters = typeParameters;
	}
	public MType getComponentType() {
		return componentType;
	}
	public void setComponentType(MType componentType) {
		this.componentType = componentType;
	}
	
}
