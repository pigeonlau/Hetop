package cn.edu.nwpu.rj416.type.matcher;



import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidParameterException;
import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//类匹配器
public class ClassMatcher<E> implements MFormatable {
	
	private Map<String, ClassMatcherNode<E>> map = new HashMap<>(); //存放所有相关类结点的Map
	private List<ClassMatcherNode<E>> classNodeList = new ArrayList<>(); //一般类结点列表
	private List<ClassMatcherNode<E>> arrayNodeList = new ArrayList<>(); //数组类结点列表
	
	//添加结点
	public void put(Class<?> cls, E data) {
		if (cls == null) {
			throw new MInvalidParameterException();
		}
		
		String key = this.getKey(cls); //根据传参Class对象获取键
		ClassMatcherNode<E> node = this.map.get(key); //从Map中查找对应的键，获取相应结点
		if (node != null) {
			node.setData(data); //键对应的值若不为空，设置节点数据为传参数据后返回
			return;
		}
		
		node = new ClassMatcherNode<E>(cls, data); //若键对应值为空，根据传入的Class对象和节点数据构造节点
		
		if (cls.isArray()) {
			this.addToList(this.arrayNodeList, node); //结点属于数组类，将其添加进数组结点列表
		} else {
			this.addToList(this.classNodeList, node); //结点属于一般类，将其添加进一般类结点列表
		}

		this.map.put(key, node); //将新构造的结点添加到Map中
	}
	
	/**
	 * 准确查找
	 * @param cls
	 * @return
	 */
	//根据传参Class对象获取类匹配器中的具体结点
	public ClassMatcherNode<E> get(Class<?> cls) {
		if (cls == null) {
			return null;
		}
		
		String key = this.getKey(cls); //根据传参Class对象获取Map中的键
		ClassMatcherNode<E> node = this.map.get(key); //通过键获取值
		if (node == null) {
			return null; //不存在的键结点为空
		}
		
		return node; //存在返回取得的节点值
	}
	
	/**
	 * 获取指定类型的父类，包括指定类型本身
	 * @param cls
	 * @return
	 */
	public List<ClassMatcherNode<E>> getAllUpper(Class<?> cls) {
		if (cls == null) {
			return null;
		}
		if (cls.isArray()) {
			return this.findUpperClassNode(this.arrayNodeList, cls);
		} else {
			return this.findUpperClassNode(this.classNodeList, cls);
		}
	}
	
	/**
	 * 获取指定类型的子类，包括指定类型本身
	 * @param cls
	 * @return
	 */
	public List<ClassMatcherNode<E>> getAllSub(Class<?> cls) {
		if (cls == null) {
			return null;
		}
		if (cls.isArray()) {
			return this.findSubClassNode(this.arrayNodeList, cls);
		} else {
			return this.findSubClassNode(this.classNodeList, cls);
		}
	}
	
	//获取类的全名作为Map的键
	private String getKey(Class<?> clazz) {
		return clazz.getName();
	}
	
	//添加结点到指定列表中
	private void addToList(List<ClassMatcherNode<E>> list, ClassMatcherNode<E> node) {
		List<ClassMatcherNode<E>> subNodes = this.findSubClassNode(list, node.getClazz()); //在指定的列表中找到结点的所有子节点
		for (ClassMatcherNode<E> n : subNodes) {
			n.getParentNodes().add(node); //在子节点的父节点列表中都添加上传参结点
		}
		List<ClassMatcherNode<E>> parentNodes = this.findUpperClassNode(list, node.getClazz());//在指定的列表中找到结点的所有父节点
		for (ClassMatcherNode<E> n : parentNodes) {
			n.getSubNodes().add(node); //在父节点的子节点列表中都添加上传参结点
		}
		node.setParentNodes(parentNodes); //更新传参结点的父节点列表
		node.setSubNodes(subNodes); //更新传参结点的子节点列表
		list.add(node); //在指定列表中添加传参结点
	}
	
	/**
	 * 搜索所有符合指定类型的子类节点
	 * @param rawClass
	 * @param list
	 */
	private List<ClassMatcherNode<E>> findSubClassNode(
			List<ClassMatcherNode<E>> list,
			Class<?> cls) {
		
		List<ClassMatcherNode<E>> rstList = new ArrayList<>();
		
		for (ClassMatcherNode<E> n : list) {
			if (cls.isAssignableFrom(n.getClazz())) {
				rstList.add(n);
			} //遍历传入的结点列表，若指定类对象可以赋值给结点的类对象属性，说明结点是指定类型的父节点
		}
		
		return rstList;
	}
	

	/**
	 * 搜索所有符合指定类型的父类节点
	 * @param rawClass
	 * @param list
	 */
	private List<ClassMatcherNode<E>> findUpperClassNode(
			List<ClassMatcherNode<E>> list,
			Class<?> cls) {
		
		List<ClassMatcherNode<E>> rstList = new ArrayList<>();
		
		for (ClassMatcherNode<E> n : list) {
			if (n.getClazz().isAssignableFrom(cls)) {
				rstList.add(n);
			} //遍历传入的结点列表，若指定类对象可以赋值给结点的类对象属性，说明结点是指定类型的父节点
		}
		
		return rstList;
	}

	/*
	 * 类匹配器格式化
	 * 返回一个存储当前匹配器所有信息的列表，包括Map中的所有key，一般
	 * 类结点列表中的节点信息，以及数组类结点列表中的节点信息
	 */
	@Override
	public List<MFmtLine> format(int level) { //level为行缩进值
		List<MFmtLine> lines = new ArrayList<>();
		lines.add(MFmtLine.create(level).appendToken("ClassMap"));

		if (CollectionUtil.isNotEmpty(this.map)) {
			lines.add(MFmtLine.create(level + 1).appendToken("mapkeys:"));
			for (String s : this.map.keySet()) {
				lines.add(MFmtLine.create(level + 2).appendToken(s));
			}
		}
		
		if (CollectionUtil.isNotEmpty(this.classNodeList)) {
			lines.add(MFmtLine.create(level + 1).appendToken("classes:"));
			for (ClassMatcherNode<E> node : this.classNodeList) {
				lines.addAll(node.format(level + 2));
			}
		}
		
		if (CollectionUtil.isNotEmpty(this.arrayNodeList)) {
			lines.add(MFmtLine.create(level + 1).appendToken("arrays:"));
			for (ClassMatcherNode<E> node : this.arrayNodeList) {
				lines.addAll(node.format(level + 2));
			}
		}
		
		return lines;
	}
}
