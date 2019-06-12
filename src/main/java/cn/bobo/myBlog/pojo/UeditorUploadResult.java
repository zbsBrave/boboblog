package cn.bobo.myBlog.pojo;
/**
 * ueditor 在上传图片uploadimage、文件uploadfile、视频uploadvideo时 的返回值
 * @author zhang
 *
 */
public class UeditorUploadResult {
	private String state;		//状态  SUCCESS
	private String url;			//upload/demo.jpg
	private String title;		//demo.jpg
	private String original;	//demo.jpg
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	
}
