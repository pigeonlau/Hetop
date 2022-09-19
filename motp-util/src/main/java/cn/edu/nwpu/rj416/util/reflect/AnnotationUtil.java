package cn.edu.nwpu.rj416.util.reflect;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;

//注解工具（通过反射获取注解）
public class AnnotationUtil {
	
	//获取指定类的指定注解
	public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationType) {
		List<Annotation> rst = new ArrayList<>();
		List<Annotation> processList = AnnotationUtil.getAllAnnotation(clazz, annotationType);//获取指定类除元注解的所有注解表
		List<Annotation> pendingList = new ArrayList<>();
		
		do {
			for (Annotation a : processList) {
				Class<? extends Annotation> aType = a.annotationType(); //遍历获取注解类型
				if (aType == annotationType) {
					rst.add(a); //注解类型匹配，添加至process列表
				} else { //注解类型不匹配
					List<Annotation> aList = AnnotationUtil.getAllAnnotation(aType, annotationType);//获取该注解类除元注解外的全部注解
					for (Annotation sa : aList) {
						Class<? extends Annotation> saType = sa.annotationType();//遍历获取注解该注解的注解类型
						if (saType == annotationType) {
							rst.add(sa); //类型匹配，添加至process列表
						} else {
							pendingList.add(sa); //不匹配，添加至pending列表
						}
					}
				}
			}
			if (pendingList.isEmpty()) {
				break;
			}
			processList.clear();
			processList.addAll(pendingList);
			pendingList.clear();
		} while (true);
		
		if (rst.isEmpty()) {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		T targetAnnotation =  (T)rst.get(rst.size() - 1);
		
		return targetAnnotation;
	}
	
	//获取指定类除元注解外的全部注解
	private static List<Annotation> getAllAnnotation(Class<?> clazz, Class<? extends Annotation> annotationType){
		List<Annotation> rst = new ArrayList<>(); //容乃匹配注解的列表容器
		Annotation[] annotations = clazz.getAnnotations(); //获取指定类的所有注解
		for (Annotation a : annotations) {
			Class<? extends Annotation> aType = a.annotationType(); //遍历获取每一个注解的注解类型
			if (aType == annotationType) {
				rst.add(a); //若注解类型与传参注解类型匹配，将其添加至列表
				continue;
			}
			
			if (aType == Target.class || aType == Retention.class 
					|| aType == Documented.class || aType == Inherited.class) {
				continue;
			} //注解类型不匹配且为元注解中的一种，跳过一次循环
			
			rst.add(a); //既不是匹配类型的注解也不是元注解，也要添加至结果列表
		}
		
		return rst; //返回结果列表
	}
}
