package cn.bobo.myBlog.vo;

import cn.bobo.myBlog.pojo.Review;

/**
 * review的包装类
 * 在review类的基础上，新增加2个变量：fromName,toName,
 * @author zhang
 *
 */
public class ReviewVo extends Review{
	private String fromName;
	private String toName;
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	
}
