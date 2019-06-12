package cn.bobo.myBlog.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.bobo.myBlog.pojo.User;
import cn.bobo.myBlog.service.LoginInfoService;
import cn.bobo.myBlog.service.UserService;
import cn.bobo.myBlog.utils.CookieUtils;
import cn.bobo.myBlog.vo.BoboResult;

@Controller
public class CenterController {
	@Value("${LOGIN_USER}")
	private String LOGIN_USER;
	@Autowired
	UserService userService;
	@Autowired
	LoginInfoService loginInfoService;
	/** 跳转到userCenter页面  */
	@RequestMapping("/center/centerController")
	public String center(Integer userId,HttpServletRequest request) {
		request.setAttribute("userId", userId);
		return "userCenter";
	}
	/** 返回用户资料面板  */
	@RequestMapping("/center/userInfo")
	public String doInfo(Integer userId,HttpServletRequest request) {
		User user = userService.getUserById(userId);
		request.setAttribute("user", user);
		return "center/userInfo";
	}
	/** 返回修改密码面板  */
	@RequestMapping("/center/pwd")
	public String pwd(Integer userId,HttpServletRequest request) {
//		public String pwd(@PathVariable("userId") Integer userId,HttpServletRequest request) {
		//非本人登录无法修改密码
		String idCard = CookieUtils.getCookieValue(request, LOGIN_USER);
		if(!loginInfoService.isMe(userId, idCard)) {
			request.setAttribute("msg", "无法修改他人密码");
			return "404";
		}
		request.setAttribute("userId", userId);
		return "center/pwd";
	}
	//修改密码 参数：userId   newPwd   oldPwd
	@RequestMapping(value="/center/alertPwd",method=RequestMethod.POST)
	@ResponseBody
	public BoboResult alertPwd(Integer userId,String oldPwd,String newPwd ) {
		
		return userService.updPwd(userId, oldPwd.trim(), newPwd.trim());
	}
}
