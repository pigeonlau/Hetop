package cn.edu.nwpu.rj416.util.types;

import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidParameterException;
import cn.edu.nwpu.rj416.util.objects.MListPage;

import java.util.ArrayList;
import java.util.List;

//列表List工具（获取列表页）
public abstract class ListUtil {
	//指定列表页大小和页号，获取列表页
	public static <T> MListPage<T> getPage(List<T> list, int pageSize, int pageNo) {
		MListPage<T> page = new MListPage<T>(); //创建列表页对象
		if (pageSize <= 0) {
			throw new MInvalidParameterException("pageSize不能小于1");
		}
		if (pageNo == 0) {
			throw new MInvalidParameterException("pageNo不能小于1");
		}
		page.setPageSize(pageSize); //设置页大小
		page.setPageNo(pageNo); //设置页号
		if (CollectionUtil.isEmpty(list)) { //列表为空，设置总计数和页计数为0，列表页表项赋空ArrayList对象
			page.setTotalCount(0);
			page.setPageCount(0);
			page.setList(new ArrayList<>());
			return page;
		}
		page.setTotalCount(list.size()); //列表不空，将总计数设置为列表大小
		
		int pageCount = list.size() / pageSize; //列表总大小除以页大小得到页计数
		if (list.size() % pageSize > 0) {
			pageCount += 1;
		} //有余数页计数要加一
		page.setPageCount(pageCount); //设置页计数
		
		if (pageNo > pageCount) {
			page.setList(new ArrayList<>());
			return page;
		} //页号大于页计数，说明是新的一页，将列表页表项置空后返回
		
		int start = (pageNo - 1) * pageSize;
		int end = start + pageSize;
		if (end > list.size()) {
			end = list.size();
		}
		
		page.setList(list.subList(start, end));//页号不大于页计数，截取页号这一页的表项赋给创建的列表页对象返回
		return page;
	}
}
