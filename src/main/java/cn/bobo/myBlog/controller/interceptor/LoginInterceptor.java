package cn.bobo.myBlog.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.bobo.myBlog.service.LoginInfoService;
/**
 * 用于拦截一些需要登录才能访问的请求
 * 比如：  个人中心：/center/**
 * 		 /login/out
 * @author zhang
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	LoginInfoService loginInfoService;
	@Value("${LOGIN_USER}")
	private String LOGIN_USER;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断是否登录 ，-1代表未登录
		int userId = loginInfoService.isLogin(LOGIN_USER, request);
		if(userId == -1) {
			response.sendRedirect(request.getContextPath()+"/login/login");
			return false;
		}
		//已登录则放行，并把userID放入request域
		request.setAttribute("userId", userId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
