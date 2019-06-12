package cn.bobo.myBlog.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bobo.myBlog.pojo.Review;
import cn.bobo.myBlog.service.LoginInfoService;
import cn.bobo.myBlog.service.ReviewService;
import cn.bobo.myBlog.vo.BoboResult;
import cn.bobo.myBlog.vo.ReviewVo;
/**
 * 评论controller
 * 功能：
 * 	1，展示评论   /review/show  
 * 		参数： articleid文章id  
 *  2,发表评论   /review/publish
 *  	参数： articleid文章id  context评论内容    rtype类型，0代表评论，1代表回复
 *  	回复：fromId回复者，toId被回复者
 * @author zhang
 *
 */
@Controller
public class ReviewController {
	@Value("${LOGIN_USER}")
	String LOGIN_USER;
	@Autowired
	LoginInfoService loginInfoService;
	@Autowired
	ReviewService reviewService;
	@RequestMapping(value="/review/show")
	public String show(Integer articleId,HttpServletRequest request) {
		List<ReviewVo> list = reviewService.getReviewList(articleId);
		request.setAttribute("list", list);
		return "review";
	}
	@RequestMapping(value="/review/publish",method=RequestMethod.POST)
	@ResponseBody
	public BoboResult publish(Review review,HttpServletRequest request) {
		//1，判断是否登录
		int id = loginInfoService.isLogin(LOGIN_USER, request);
		if(id == -1) {
			return new BoboResult(400, "请登陆后再评论");
		}
		//2，设置发送评论的用户和时间
		review.setFromid(id);
		review.setRtime(new Date());
		//3，插入到数据库
		BoboResult result = reviewService.publish(review);
		//4,获取评论数目
		int count = reviewService.count(review.getArticleid());
		result.setMsg(count+"");
		return result;
	}
}
