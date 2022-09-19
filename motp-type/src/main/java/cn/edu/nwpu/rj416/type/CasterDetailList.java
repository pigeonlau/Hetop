package cn.edu.nwpu.rj416.type;



import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @author MilesLiu
 *
 * 2020年3月17日 下午6:06:41
 */
//转型细节解析列表
public class CasterDetailList implements MFormatable {
	private List<CasterDetail> list = new ArrayList<>(); //存放转型细节对象的列表

	public List<CasterDetail> getList() {
		return list;
	}

	public void setList(List<CasterDetail> list) {
		this.list = list;
	}

	//格式化后会返回一个列表中存放所有转型细节对象格式化后的信息的列表
	@Override
	public List<MFmtLine> format(int level) {
		List<MFmtLine> lines = new ArrayList<>();

		if (CollectionUtil.isNotEmpty(this.list)) {
			for (CasterDetail node : this.list) {
				lines.addAll(node.format(level));
			}
		}
		
		return lines;
	}
	
	
}
