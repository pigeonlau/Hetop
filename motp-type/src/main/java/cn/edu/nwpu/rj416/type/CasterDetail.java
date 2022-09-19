package cn.edu.nwpu.rj416.type;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.type.util.ObjectUtil;
import cn.edu.nwpu.rj416.type.util.TypeUtil;
import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidValueException;
import cn.edu.nwpu.rj416.util.reflect.TypeDetail;
import cn.edu.nwpu.rj416.util.reflect.TypeParameter;
import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 对MTypeCaster解析的结果
 * @author MilesLiu
 * Date 2020-03-14 16:37
 */
//转型细节解析
public class CasterDetail implements MFormatable {
	private MTypeCaster<?,?> caster; //表明对应哪一个类型转换器
	private Type fromType; //记录转换前的类型
	private Type toType; //记录转换后的类型
	
	//创建CasterDetail对象的方法（传参为一个类型转换器）
	public static final CasterDetail create(Class<MTypeCaster<?,?>> casterClass) {
		TypeDetail typeDetail = TypeUtil.parseTypeAs(casterClass, MTypeCaster.class); //根据传参转换器生成其类型细节对象
		CasterDetail detail = new CasterDetail();
		detail.setCaster(ObjectUtil.createObjectByClass(casterClass)); //将要创建的caster属性设置为传参的转换器对象
		TypeParameter tpF = typeDetail.getTypeParamterByName("F"); //通过参数名获取转换前参数类型对象
		if (tpF == null) {
			throw new MInvalidValueException(
					"无法处理的MTypeCaster定义：%s", casterClass.getTypeName());
		}
		detail.setFromType(tpF.getDetail().getType()); //将fromType属性设置为类型转换器传参转换前类型对象

		TypeParameter tpT = typeDetail.getTypeParamterByName("T"); //通过参数名获取转换后参数类型对象
		if (tpT == null) {
			throw new MInvalidValueException(
					"无法处理的MTypeCaster定义：%s", casterClass.getTypeName());
		}
		detail.setToType(tpT.getDetail().getType()); //将toType属性设置为类型转换器传参转换后类型对象
		
		return detail; //返回根据传参转换器相关属性生成的CasterDetail对象
	}
	
	//私有的无参构造函数（防止其他类直接创建转型解析对象）
	private CasterDetail() {
		super();
	}

	public MTypeCaster<?, ?> getCaster() {
		return caster;
	}
	public void setCaster(MTypeCaster<?, ?> caster) {
		this.caster = caster;
	}
	public Type getFromType() {
		return fromType;
	}
	public void setFromType(Type fromType) {
		this.fromType = fromType;
	}
	public Type getToType() {
		return toType;
	}
	public void setToType(Type toType) {
		this.toType = toType;
	}

	/*
	 * 格式化后返回一个存储三行转型细节信息的列表
	 * 例： CasterDetail : MCasterArrayToList （表明那种类型的转换器）
	 * 	  fromType : java.lang.String[]  （记录转换前的类型）
	 *    toType : java.util.List<java.lang.String> （记录转换后的类型）
	 * */
	@Override
	public List<MFmtLine> format(int level) {
		List<MFmtLine> lines = new ArrayList<>();
		lines.add(MFmtLine.create(level)
				.appendToken("CasterDetail : ")
				.appendToken(this.caster.getClass().getName()));

		lines.add(MFmtLine.create(level + 1)
				.appendToken("fromType:")
				.appendToken(this.fromType.getTypeName()));
		
		lines.add(MFmtLine.create(level + 1)
				.appendToken("toType:")
				.appendToken(this.toType.getTypeName()));
		
		return lines;
	}
	
	
}
