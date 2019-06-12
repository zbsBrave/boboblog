package cn.bobo.myBlog.service;

import java.util.List;

import cn.bobo.myBlog.pojo.Article;
import cn.bobo.myBlog.vo.BoboResult;
import cn.bobo.myBlog.vo.PageVo;

public interface ArticleService {
	/**
	 * 得到指定作者、指定type的所有article
	 */
	List<Article> getAllArticleByUseridAndType(Integer userId,String type,PageVo<Article> pageVo);
	/** 得到对应userId的totalCount  */
	int geTotalCount(int userId);
	/** 得到对应userId的所有type  */
	List<String> geTypes(int userId);
	//返回值是articleid
	int addArticle(Article article);
	//返回值是true 成功
	boolean update(Article article);
	Article getArticleByArticleid(Integer articleid);
	boolean deleteById(Integer articleid);
	List<Article> search(String search);
	/**  status=200 代表成功点赞  status=400代表该用户已经给文章点过攒 */
	BoboResult zan(Integer userId,Integer articleId,Integer starCount);
	/**  通过articleId得到article  */
	Article getByArticleId(Integer articleId);
}
