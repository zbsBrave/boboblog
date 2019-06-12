package cn.bobo.myBlog.vo;

import java.util.List;

/**
 * 必须要totalCount  limit，可以set
 * totalCount根据数据库查询可得，可以set
 * totalPage 计算可得，不应该set
 * currentPage 默认=1，不能<=0
 * @author zhang
 *
 */


public class PageVo<T> {
	private int totalCount;//总记录数
	private int totalPage;//总页数
	private int currentPage=1;//当前页,默认第一页
	private int limit;//每页记录数
	private List<T> list;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		if(totalCount % limit == 0)
			return totalCount / limit;
		return totalCount / limit +1;
	}
	public int getCurrentPage() {
		if(currentPage <= 0)
			return 1;
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	
}
