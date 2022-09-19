package cn.edu.nwpu.rj416.util.reflect;

import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;

import java.util.ArrayList;
import java.util.List;

//自定义泛型参数类
public class TypeParameter implements MFormatable {
	private TypeDetail detail; //参数类型细节
	private String name; //泛型参数名
	public TypeDetail getDetail() {
		return detail;
	}
	public void setDetail(TypeDetail detail) {
		this.detail = detail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isAssigned() {
		if (this.detail == null) {
			return false;
		}
		if (this.detail.getRawType() == null) {
			return false;
		}
		return true;
	}
	
	//覆写可格式化接口格式化方法
	@Override
	public List<MFmtLine> format(int level) {
		
		if (this.detail != null) {
			List<MFmtLine> typeLines = this.detail.format(level);
			typeLines.get(0).prependToken(":").prependToken(this.name);
			return typeLines;
		} else {
			List<MFmtLine> lines = new ArrayList<>();
			MFmtLine line = new MFmtLine(level);
			line.appendToken(this.name).appendToken(":").appendToken("?");
			return lines;
		}	
	}
}
