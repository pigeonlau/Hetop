package cn.edu.nwpu.rj416.type.matcher;


import cn.edu.nwpu.rj416.type.FormatUtil;
import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 类节点
 * @author MilesLiu
 *
 * 2020年3月17日 下午4:00:30
 */
public class TypeMatcherNode<E> implements MFormatable {
	private Type matchType;
	private List<TypeMatcherNode<E>> parentNodes = new ArrayList<>();
	private List<TypeMatcherNode<E>> subNodes = new ArrayList<>();
	private E data;
	
	public TypeMatcherNode(Type t, E data) {
		super();
		this.matchType = t;
		this.data = data;
	}
	
	public Type getMatchType() {
		return matchType;
	}
	public void setMatchType(Type clazz) {
		this.matchType = clazz;
	}
	public List<TypeMatcherNode<E>> getParentNodes() {
		return parentNodes;
	}
	public void setParentNodes(List<TypeMatcherNode<E>> parentNodes) {
		this.parentNodes = parentNodes;
	}

	public List<TypeMatcherNode<E>> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(List<TypeMatcherNode<E>> subNodes) {
		this.subNodes = subNodes;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}

	@Override
	public List<MFmtLine> format(int level) {
		List<MFmtLine> lines = new ArrayList<>();
		lines.add(MFmtLine.create(level).appendToken(this.matchType.getTypeName()));

		lines.add(MFmtLine.create(level + 1).appendToken("data:"));
		lines.addAll(FormatUtil.format(this.data, level + 2));
		
		if (CollectionUtil.isNotEmpty(this.parentNodes)) {
			lines.add(MFmtLine.create(level + 1).appendToken("upperNodes:"));
			for (TypeMatcherNode<E> node : this.parentNodes) {
				lines.add(MFmtLine.create(level + 2).appendToken(node.getMatchType().getTypeName()));
			}
		}
		
		if (CollectionUtil.isNotEmpty(this.subNodes)) {
			lines.add(MFmtLine.create(level + 1).appendToken("subNodes:"));
			for (TypeMatcherNode<E> node : this.subNodes) {
				lines.add(MFmtLine.create(level + 2).appendToken(node.getMatchType().getTypeName()));
			}
		}
		
		return lines;
	}
}
