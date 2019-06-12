package cn.bobo.myBlog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.bobo.myBlog.pojo.Article;
import cn.bobo.myBlog.service.ArticleService;
import cn.bobo.myBlog.service.LoginInfoService;
import cn.bobo.myBlog.service.ReviewService;
import cn.bobo.myBlog.service.UserService;
import cn.bobo.myBlog.utils.CookieUtils;
import cn.bobo.myBlog.vo.BoboResult;
import cn.bobo.myBlog.vo.PageVo;
/**
 * 文章controller
 * 功能 ： 
 * 	主页：/{userId}/article
 * 	文章详情页：/article/articlePage 参数： 页面articleid
 * 	写文章：/article/add			 参数：登陆用户的id：request域中的参数userId
 * 	删除文章：/article/delete      参数：页面articleid
 * 	更新文章：/article/update		参数：页面articleid
 * 	搜索：/article/search			参数：页面search
 * 	点赞：/article/zan			参数：页面Integer articleId,Integer starCount
 * 	关注：/article/guanzhu		参数：页面Integer articleId
 * @author zhang
 *
 */

@Controller
public class ArticleController {
	@Value("${PAGE_LIMIT}")
	private int PAGE_LIMIT;
	@Value("${LOGIN_USER}")
	private String LOGIN_USER;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private LoginInfoService loginInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReviewService reviewService;
	
	/**
	 * article主页1，返回article页面,需要文章类型userId,可以在这里设计全局limit   = PAGE_LIMIT
	 */
	@RequestMapping("/{userId}/article")
	public String articleHome(@PathVariable("userId") Integer userId,HttpServletRequest request) {
		List<String> articleTypes = articleService.geTypes(userId);
		//把所有type对象存入request域，发送给页面
		request.setAttribute("articleTypes", articleTypes);
		//设置每页数据量limit,得到totalCount
		PageVo<Article> pageVo = new PageVo<>();
		pageVo.setLimit(PAGE_LIMIT);
		pageVo.setTotalCount(articleService.geTotalCount(userId));
		request.setAttribute("pageVo", pageVo);
		//返回userID
		request.setAttribute("userId", userId);
		//判断是否为本人登录的页面
		String idCard = CookieUtils.getCookieValue(request, LOGIN_USER);
		if(loginInfoService.isMe(userId, idCard)) {
			return "server/article";
		}
		return "article";
	}
	/**
	 * article主页2:接受ajax请求，刷新分页数据
	 * 请求参数 currentPage当前页，limit 分页，type 文章类型， userId 用户id
	 */
	@RequestMapping("/article/pageList")
	@ResponseBody
	public PageVo<Article> getArticleList(int currentPage,int limit,String type,int userId) {
		type = type.trim();
		PageVo<Article> pageVo = new PageVo<>();
		pageVo.setLimit(limit);
		pageVo.setCurrentPage(currentPage);
		List<Article> list = articleService.getAllArticleByUseridAndType(userId, type, pageVo);
		pageVo.setList(list);
		return pageVo;
	}
	/** 文章详情页面 ： 得到对应articleid的article 和user  */
	@RequestMapping("/article/articlePage")
	public String getArticlePage(Integer articleid,Model model,HttpServletRequest request) {
		if(articleid == null) {
			articleid = (int) request.getAttribute("articleid"); 
		}
		Article article = articleService.getArticleByArticleid(articleid);
		model.addAttribute("article", article);
		model.addAttribute("user", userService.getUserById(article.getUserId()));
		//返回评论人数
		int count = reviewService.count(articleid);
		request.setAttribute("count", count);
		return "articlePage";
	}
	/** 新增文章1:  文章编辑页面  */
	@RequestMapping("/article/add")
	public String edit(HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		//返回types
		List<String> types = articleService.geTypes(userId);
		request.setAttribute("types", types);
		request.setAttribute("userId", userId);
		return "/server/articleAdd";
	}
	// 新增文章2:  去数据库增加article  参数userId type title content blogtext
	@RequestMapping(value="/article/add2",method=RequestMethod.POST)
	public String add(Article article,HttpServletRequest request) {
		//插入成功返回主键
		int articleid = articleService.addArticle(article);
		//跳转到文章详情页面
		request.setAttribute("articleid", articleid);
		return "forward:/article/articlePage";
	}
	/** 更新文章1：返回文章编辑页面  参数articleid  */
	@RequestMapping("/article/update")
	public String update(Integer articleid,HttpServletRequest request) {
		Article article = articleService.getArticleByArticleid(articleid);
		if(article == null) {
			request.setAttribute("result", new BoboResult(404, "更新文章出错"));
			return "404";
		}
		request.setAttribute("article", article);
		List<String> types = articleService.geTypes(article.getUserId());
		request.setAttribute("types", types);
		return "/server/articleUpd";
	}
	// 更新文章2：更新文章数据  参数article  
	@RequestMapping(value="/article/update2",method=RequestMethod.POST)
	public String update2(Article article,HttpServletRequest request) {
		boolean update = articleService.update(article);
		if(!update) {
			request.setAttribute("result", new BoboResult(404, "更新文章出错"));
			return "404";
		}
		//跳转到文章详情页面
		request.setAttribute("articleid", article.getArticleid());
		return "forward:/article/articlePage";
	}
	/** 删除文章  参数articleid */
	@RequestMapping("/article/delete")
	@ResponseBody
	public BoboResult del(Integer articleid,HttpServletRequest request) {
		boolean deleteById = articleService.deleteById(articleid);
		if(!deleteById) {
			request.setAttribute("result", new BoboResult(404, "更新文章出错"));
			return new BoboResult(400, "删除失败");
		}
		return new BoboResult(200, "成功");
	}
	/** 搜索文章 */
	@RequestMapping(value="/article/search",method=RequestMethod.POST)
	public String search(String search,HttpServletRequest request) {
		//通过title搜索文章
		List<Article> list = articleService.search(search.trim());
		request.setAttribute("list", list);
		return "content";
	}
	/** 文章点赞  */
	@RequestMapping(value="/article/zan",method=RequestMethod.POST)
	@ResponseBody
	public BoboResult zan(Integer articleId,Integer starCount,HttpServletRequest request) {
		//1,先判断用户是否登录
		int userId = loginInfoService.isLogin(LOGIN_USER, request);
		if(userId == -1) {//未登录
			return new BoboResult(403,"请登陆后再点赞");
		}
		//2，点赞   status=200代表未点赞   status=400代表已经点过赞
		BoboResult result = articleService.zan(userId, articleId, starCount);
		
		return result;
	}
	/** 关注  */
	@RequestMapping(value="/article/guanzhu",method=RequestMethod.POST)
	@ResponseBody
	public BoboResult guanzhu(Integer articleId,HttpServletRequest request) {
		//1,先判断用户是否登录
		int fansId = loginInfoService.isLogin(LOGIN_USER, request);
		if(fansId == -1) {//未登录
			return new BoboResult(403,"请登陆后再关注 ");
		}
		//2，通过articleId得到文章作者的id
		Article article = articleService.getByArticleId(articleId);
		//3，如果是同一人，就不用关注
		if(fansId == article.getUserId()) {
			return new BoboResult(402, "这是您自己写的文章啦");
		}
		//3，关注    status=200关注成功   status=400已经关注
		BoboResult result = userService.focus(fansId, article.getUserId());
		return result;
	}
}
