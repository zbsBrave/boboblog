package cn.bobo.myBlog.service;

import javax.servlet.http.HttpServletRequest;

public interface LoginInfoService {
	//验证用户是否在其他地方登陆：如果在其他地方登陆，要更新对应userID的身份证IDcard. true代表在其他地方登陆，false代表没有
	boolean checkByUserId(int userId,String idCard);
	int insert(int userId,String idCard);
	int truncateTable();
	//验证是否登录 ,已登录返回userId，未登录返回-1
	int isLogin(String LOGIN_USER,HttpServletRequest request);
	//验证是否为本人页面
	boolean isMe(Integer userId,String idCard);
	void delByUserId(Integer userId);
}
