package cn.bobo.myBlog.vo;


/**
 * 用于响应客户端
 * @author zhang
 *
 */
public class BoboResult {
	//200 = 成功 ,400 = 失败,403 = 用户已登录
	private int status;
	//响应信息
	private String msg;
	//响应数据
	private Object data;
	public BoboResult() {
		
	}
	public BoboResult(int status,String msg) {
		this.status = status;
		this.msg = msg;
	}
	public BoboResult(int status,String msg,Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	public static BoboResult ok(Object data) {
		return new BoboResult(200, "成功", data);
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
