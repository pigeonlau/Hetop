package cn.edu.nwpu.rj416.util.reflect;

import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//泛型类型细节
public class TypeDetail implements MFormatable {
	private Type type; //对应泛型类型
	private boolean array = false; //是否数组标志
	private Class<?> rawType; //原生类型
	private List<TypeParameter> typeParameters; //参数列表
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Class<?> getRawType() {
		return rawType;
	}
	public void setRawType(Class<?> rawType) {
		this.rawType = rawType;
	}
	public List<TypeParameter> getTypeParameters() {
		return typeParameters;
	}
	public void setTypeParameters(List<TypeParameter> typeParameters) {
		this.typeParameters = typeParameters;
	}
	
	//通过泛型参数名获取泛型参数对象
	public TypeParameter getTypeParamterByName(String name) {
		if (CollectionUtil.isEmpty(typeParameters)) {
			return null;
		}
		
		for (TypeParameter tp : this.typeParameters) {
			if (tp.getName().equals(name)) {
				return tp;
			}
		}
		return null;
	}
	
	//泛型参数赋值
	public void assignTypeParameters(TypeDetail subType) {
		
		if (CollectionUtil.isEmpty(this.typeParameters)) {
			return; //递归出口，泛型参数的参数列表为空
		}
		
		for (TypeParameter tp : this.typeParameters) {
			if (!tp.isAssigned()) { //若参数未被赋值
				TypeParameter actualArg = subType.getTypeParamterByName(tp.getName());//将传参的泛型参数属性赋给参数表中的参数

				if (actualArg != null) {
					tp.setDetail(actualArg.getDetail());
				}
			}
			
			if (tp.getDetail() == null) {
				continue;
			}
			
			tp.getDetail().assignTypeParameters(subType);//递归调用将参数的参数也赋值
		}
	}
	
	/**
	 * 指定类型中的泛型参数，包含泛型参数中的泛型参数
	 * @param name 泛型参数名称
	 * @param paramDetail 泛型参数实际的类型
	 * @return
	 */
	public boolean updateTypeParamterByName(String name, TypeDetail paramDetail) {
		if (CollectionUtil.isNotEmpty(this.typeParameters)) {
			for (TypeParameter tp : this.typeParameters) {
				if (tp.getName().equals(name)) {
					tp.setDetail(paramDetail);
					return true;
				}
			}
		}
		
		return false;
	}
	
	//通过参数名获取参数的原生类对象
	public Class<?> getTypeParamterRawTypeByName(String name){
		TypeParameter tp = this.getTypeParamterByName(name);
		if (tp == null) {
			return null;
		}
		if (tp.getDetail() == null) {
			return null;
		}
		return tp.getDetail().getRawType();
	}

	//覆写可格式化接口方法
	@Override
	public List<MFmtLine> format(int level) { //level为缩进值
		List<MFmtLine> lines = new ArrayList<>();
		
		MFmtLine line1 = new MFmtLine(level);
		if (this.rawType == null) {
			line1.appendToken("?");
		} else {
			line1.appendToken(this.rawType.toString());
		}
		lines.add(line1);
		if (CollectionUtil.isNotEmpty(this.typeParameters)) {
			line1.appendToken("<");
			
			for (int i = 0; i < this.typeParameters.size(); i++) {
				TypeParameter tp = this.typeParameters.get(i);
				if (i != 0) {
					line1.appendToken(",");
				}
				line1.appendToken(tp.getName());
			}
			
			line1.appendToken(">");

			for (TypeParameter tp : this.typeParameters) {
				lines.addAll(tp.format(level + 1));
			}
		}
		
		return lines;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.rawType != null) {
			sb.append(this.rawType.getTypeName());
		} else {
			sb.append("?");
		}
		if (this.typeParameters != null && !this.typeParameters.isEmpty()) {
			sb.append('<');
			for (TypeParameter tp : this.typeParameters) {
				if (tp.getDetail() != null) {
					sb.append(tp.getDetail().toString());
				} else {
					sb.append(tp.getName());
				}
			}
			sb.append('>');
		}
		
		return sb.toString();
	}
	public boolean isArray() {
		return array;
	}
	public void setArray(boolean array) {
		this.array = array;
	}
	
	
}
