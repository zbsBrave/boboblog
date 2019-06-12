package cn.bobo.myBlog.controller;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.bobo.myBlog.pojo.User;
import cn.bobo.myBlog.service.LoginInfoService;
import cn.bobo.myBlog.service.UserService;
import cn.bobo.myBlog.utils.CookieUtils;
import cn.bobo.myBlog.vo.BoboResult;

/**
 * 登陆模块  
 * 功能： 登陆 注册  退出
 * 登陆控制：
 * 1,数据库logininfo表：userID和唯一IDcard
 * 2，cookie ： key是LOGIN_USER  value是IDcard
 * 在其他模块验证是否登录：先通过cookie取出IDcard，再去数据库logininfo表中验证对应的IDcard是否存在
 * 					  存在即代表已登录
 * @author zhang
 *
 */
@Controller
public class LoginController {
	@Value("${LOGIN_USER}")
	String LOGIN_USER;
	@Autowired
	UserService userService;
	@Autowired
	LoginInfoService loginInfoService;
	/** 返回登陆页 */
	@RequestMapping("/login/login")
	public String loginhome(HttpServletRequest request) {
		//先判断是否已经登陆，用于防止同一个页面多次登陆
		int id = loginInfoService.isLogin(LOGIN_USER, request);
		if(id != -1) {//已登录，则直接跳到主页
			return "redirect:/"+id+"/home";
		}
		return "login";
	}
	/** 返回注册页 */
	@RequestMapping("/login/register")
	public String login(@PathVariable("loginPage") String loginPage) {
		return "register";
	}
	/** 登陆 */
	@RequestMapping(value = "/login/doLogin",method = RequestMethod.POST)
	@ResponseBody
	public BoboResult doLogin(String name,String password,HttpSession session,HttpServletResponse response) {
		//1，首先验证登陆信息
		BoboResult result = userService.doLogin(name.trim(), password.trim());
		//生产一个唯一的IDcard
		String idCard = LOGIN_USER + UUID.randomUUID().toString();
		//2,如果登陆信息验证成功，即status=200，需要先验证用户是否在其他地方登陆
		if(result.getStatus() == 200) {
			User user = ((User)result.getData());
			//2.1，判断是否已登录
			if(loginInfoService.checkByUserId(user.getId(), idCard)) {//已登录
				result.setStatus(403);
				result.setMsg("账号已登录，如非本人操作请更改密码");
			}
			//2.2，将IDcard及对应的user存入session
			session.setAttribute(LOGIN_USER, idCard);
			session.setAttribute(idCard, user);
			//2.2，把IDcard存入cookie    ( LOGIN_USER , idCard)
			CookieUtils.addCookie(response, LOGIN_USER, idCard, -1);
		}
		return result;
	}
	@RequestMapping(value="/login/doRegister",method=RequestMethod.POST)
	@ResponseBody
	public BoboResult register(User user,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		BoboResult result = userService.register(user);
		//如果注册成功，更新logininfo
		if(result.getStatus()==200) {
			String idCard = LOGIN_USER + UUID.randomUUID().toString();
			loginInfoService.insert(user.getId(), idCard);
			//存入信息到session 和 cookie
			session.setAttribute(idCard, user);
			CookieUtils.addCookie(response, LOGIN_USER, idCard, -1);
		}
		
		return result;
	}
	/** 退出:退出后返回到未登录主页  */
	@RequestMapping("/login/out")
	public String out(HttpServletRequest request,HttpServletResponse response) {
		//判断是否登录
		int userId = loginInfoService.isLogin(LOGIN_USER, request);
		if(userId == -1) {
			return "login";
		}
		//去loginInfo删除登陆信息
		loginInfoService.delByUserId(userId);
		//删除cookie
		CookieUtils.addCookie(response, LOGIN_USER, null, -1);
		//跳转到未登录主页
		return "forward:/" +userId + "/home";
	}
}
