package cn.edu.nwpu.rj416.util.objects;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 列表的一页
 * 
 * @author MilesLiu
 *
 * @param <T>
 */
public class MListPage<T> {
	private int pageNo;      //页号
	private int pageSize;    //页大小
	private int pageCount;   //页计数（与页号近似）
	private int totalCount;  //总计数（总的列表的大小）
	private List<T> list;    //列表页表项（列表页存的东西）

	public <U> MListPage<U> map(Function<T, U> mapFunction) {
		MListPage<U> result = new MListPage<>();
		result.pageNo = pageNo;
		result.pageSize = pageSize;
		result.pageCount = pageCount;
		result.totalCount = totalCount;
		result.list = list.stream().map(mapFunction).collect(Collectors.toList());
		return result;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
