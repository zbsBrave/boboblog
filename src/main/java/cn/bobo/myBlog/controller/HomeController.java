package cn.bobo.myBlog.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.bobo.myBlog.service.LoginInfoService;
import cn.bobo.myBlog.utils.CookieUtils;

/**
 * blog主页  控制器
 * @author zhang
 *
 */
@Controller
public class HomeController {
	@Value("${LOGIN_USER}")
	String LOGIN_USER;
	@Autowired
	LoginInfoService loginInfoService;
	/** 返回主页  */
	@RequestMapping("/{userId}/home")
	public String home(@PathVariable("userId") Integer userId,HttpServletRequest request) {
		request.setAttribute("userId", userId);
		String idCard = CookieUtils.getCookieValue(request, LOGIN_USER);
		if (loginInfoService.isMe(userId, idCard)) {
			return "server/home";
		}
		return "home";
	}
}
