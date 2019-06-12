package cn.bobo.myBlog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bobo.myBlog.pojo.User;
import cn.bobo.myBlog.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	//获取用户的粉丝
	@RequestMapping("/user/fans")
	public String fans(Integer userId,HttpServletRequest request) {
		List<User> list = userService.fans(userId);
		request.setAttribute("list", list);
		return "fans";
	}
	//获取用户关注的人
	@RequestMapping("/user/follow")
	public String follow(Integer userId,HttpServletRequest request) {
		List<User> list = userService.getFollows(userId);
		request.setAttribute("list", list);
		return "follow";
	}
}
