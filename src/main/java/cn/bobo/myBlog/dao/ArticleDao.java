package cn.bobo.myBlog.dao;
/**
 * 文章dao
 * @author zhang
 *
 */

import java.util.List;
import cn.bobo.myBlog.pojo.Article;
import cn.bobo.myBlog.vo.PageVo;

public interface ArticleDao {
	/**
	 * 得到指定作者、指定type的所有article
	 */
	List<Article> getAllArticleByUseridAndType(Integer userId,String type,PageVo<Article> pageVo);
	/**  根据对应userID用户的所有article数，即totalCount  */
	int geTotalCount(int userId);
	/**  根据articleid得到article  */
	Article getArticleByArticleid(Integer articleid);
	/** 得到对应userId的所有type  */
	List<String> geTypes(int userId);
}
