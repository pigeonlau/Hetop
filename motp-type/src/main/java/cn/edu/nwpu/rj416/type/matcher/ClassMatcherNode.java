package cn.edu.nwpu.rj416.type.matcher;



import cn.edu.nwpu.rj416.type.FormatUtil;
import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 类节点
 * @author MilesLiu
 *
 * 2020年3月17日 下午4:00:30
 */
public class ClassMatcherNode<E> implements MFormatable {
	private Class<?> clazz; //记录节点自身类的属性
	private List<ClassMatcherNode<E>> parentNodes = new ArrayList<>(); //父节点列表
	private List<ClassMatcherNode<E>> subNodes = new ArrayList<>(); //子节点列表
	private E data; //节点数据
	
	public ClassMatcherNode(Class<?> clazz, E data) {
		super();
		this.clazz = clazz;
		this.data = data;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public List<ClassMatcherNode<E>> getParentNodes() {
		return parentNodes;
	}
	public void setParentNodes(List<ClassMatcherNode<E>> parentNodes) {
		this.parentNodes = parentNodes;
	}

	public List<ClassMatcherNode<E>> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(List<ClassMatcherNode<E>> subNodes) {
		this.subNodes = subNodes;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}

	/*
	 * 格式化方法
	 * 返回一个存放当前结点所有相关信息的列表
	 */
	@Override
	public List<MFmtLine> format(int level) { //level为行缩进值
		List<MFmtLine> lines = new ArrayList<>();
		lines.add(MFmtLine.create(level).appendToken(this.clazz.getName()));

		lines.add(MFmtLine.create(level + 1).appendToken("data:"));
		lines.addAll(FormatUtil.format(this.data, level + 2));
		
		if (CollectionUtil.isNotEmpty(this.parentNodes)) {
			lines.add(MFmtLine.create(level + 1).appendToken("upperNodes:"));
			for (ClassMatcherNode<E> node : this.parentNodes) {
				lines.add(MFmtLine.create(level + 2).appendToken(node.getClazz().getName()));
			}
		}
		
		if (CollectionUtil.isNotEmpty(this.subNodes)) {
			lines.add(MFmtLine.create(level + 1).appendToken("subNodes:"));
			for (ClassMatcherNode<E> node : this.subNodes) {
				lines.add(MFmtLine.create(level + 2).appendToken(node.getClazz().getName()));
			}
		}
		
		return lines;
	}
}
