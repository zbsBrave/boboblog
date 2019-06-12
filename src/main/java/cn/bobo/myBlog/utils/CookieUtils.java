package cn.bobo.myBlog.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if(maxAge > 0) cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	public static String getCookieValue(HttpServletRequest request,String cookieName) {
		Cookie[] list = request.getCookies();
		if(list == null || cookieName ==null) {
			return null;
		}
		for(int i = 0; i < list.length ; i++) {
			Cookie cookie = list[i];
			if(cookieName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
