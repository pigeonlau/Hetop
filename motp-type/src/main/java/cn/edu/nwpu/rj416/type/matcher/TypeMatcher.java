package cn.edu.nwpu.rj416.type.matcher;



import cn.edu.nwpu.rj416.type.util.TypeUtil;
import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidParameterException;
import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//类型匹配器（see: ClassMatcher）
public class TypeMatcher<E> implements MFormatable {
	
	private Map<String, TypeMatcherNode<E>> map = new HashMap<>();
	private List<TypeMatcherNode<E>> typeNodeList = new ArrayList<>();
	
	public void put(Type cls, E data) {
		if (cls == null) {
			throw new MInvalidParameterException();
		}
		
		String key = this.getKey(cls);
		TypeMatcherNode<E> node = this.map.get(key);
		if (node != null) {
			node.setData(data);
			return;
		}
		
		node = new TypeMatcherNode<E>(cls, data);
		this.addToList(this.typeNodeList, node);
		this.map.put(key, node);
	}
	
	/**
	 * 获取或创建一个类型节点
	 * @param cls
	 * @return
	 */
	public TypeMatcherNode<E> getOrCreateNode(Type cls) {
		if (cls == null) {
			throw new MInvalidParameterException();
		}
		
		String key = this.getKey(cls);
		TypeMatcherNode<E> node = this.map.get(key);
		
		if (node != null) {
			return node;
		}
		
		node = new TypeMatcherNode<E>(cls, null);
		this.addToList(this.typeNodeList, node);
		this.map.put(key, node);
		return node;
	}
	
	/**
	 * 准确查找
	 * @param cls
	 * @return
	 */
	public TypeMatcherNode<E> get(Type cls) {
		if (cls == null) {
			return null;
		}
		
		String key = this.getKey(cls);
		TypeMatcherNode<E> node = this.map.get(key);
		if (node == null) {
			return null;
		}
		
		return node;
	}
	
	/**
	 * 获取指定类型的父类，包括指定类型本身
	 * @param cls
	 * @return
	 */
	public List<TypeMatcherNode<E>> getAllUpper(Type cls) {
		if (cls == null) {
			return null;
		}
		return this.findUpperClassNode(this.typeNodeList, cls);
	}
	
	/**
	 * 获取指定类型的子类，包括指定类型本身
	 * @param cls
	 * @return
	 */
	public List<TypeMatcherNode<E>> getAllSub(Type cls) {
		if (cls == null) {
			return null;
		}
		return this.findSubClassNode(this.typeNodeList, cls);
	}
	
	private String getKey(Type t) {
		return t.getTypeName();
	}
	
	private void addToList(List<TypeMatcherNode<E>> list, TypeMatcherNode<E> node) {
		List<TypeMatcherNode<E>> subNodes = this.findSubClassNode(list, node.getMatchType());
		for (TypeMatcherNode<E> n : subNodes) {
			n.getParentNodes().add(node);
		}
		List<TypeMatcherNode<E>> parentNodes = this.findUpperClassNode(list, node.getMatchType());
		for (TypeMatcherNode<E> n : parentNodes) {
			n.getSubNodes().add(node);
		}
		node.setParentNodes(parentNodes);
		node.setSubNodes(subNodes);
		list.add(node);
	}
	
	/**
	 * 搜索所有符合指定类型的子类节点
	 * @param rawClass
	 * @param list
	 */
	private List<TypeMatcherNode<E>> findSubClassNode(
			List<TypeMatcherNode<E>> list,
			Type cls) {
		
		List<TypeMatcherNode<E>> rstList = new ArrayList<>();
		
		for (TypeMatcherNode<E> n : list) {
			if (TypeUtil.isAssignableTo(n.getMatchType(), cls)) {
				rstList.add(n);
			}
		}
		
		return rstList;
	}
	

	/**
	 * 搜索所有符合指定类型的父类节点
	 * @param rawClass
	 * @param list
	 */
	private List<TypeMatcherNode<E>> findUpperClassNode(
			List<TypeMatcherNode<E>> list,
			Type cls) {
		
		List<TypeMatcherNode<E>> rstList = new ArrayList<>();
		
		for (TypeMatcherNode<E> n : list) {
			if (TypeUtil.isAssignableTo(cls, n.getMatchType())) {
				rstList.add(n);
			}
		}
		
		return rstList;
	}

	@Override
	public List<MFmtLine> format(int level) {
		List<MFmtLine> lines = new ArrayList<>();
		lines.add(MFmtLine.create(level).appendToken("ClassMap"));

		if (CollectionUtil.isNotEmpty(this.map)) {
			lines.add(MFmtLine.create(level + 1).appendToken("mapkeys:"));
			for (String s : this.map.keySet()) {
				lines.add(MFmtLine.create(level + 2).appendToken(s));
			}
		}
		
		if (CollectionUtil.isNotEmpty(this.typeNodeList)) {
			lines.add(MFmtLine.create(level + 1).appendToken("types:"));
			for (TypeMatcherNode<E> node : this.typeNodeList) {
				lines.addAll(node.format(level + 2));
			}
		}
		
		return lines;
	}
}
