package cn.bobo.myBlog.dao.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cn.bobo.myBlog.dao.ArticleDao;
import cn.bobo.myBlog.dao.mapper.ArticleMapper;
import cn.bobo.myBlog.pojo.Article;
import cn.bobo.myBlog.pojo.ArticleExample;
import cn.bobo.myBlog.pojo.ArticleExample.Criteria;
import cn.bobo.myBlog.vo.PageVo;
@Repository
public class ArticleDaoImpl implements ArticleDao {
	@Autowired
	ArticleMapper articleMapper;
	/** 得到指定作者、type 的 article */
	@Override
	public List<Article> getAllArticleByUseridAndType(Integer userId, String type,PageVo<Article> pageVo) {
		ArticleExample example = new ArticleExample();
		//先安装articleTime升序排序，再安装id排序
		example.setOrderByClause("article_time desc,articleId desc");
		//设置分页
		int limit = pageVo.getLimit();
		if( limit!=0) {
			int start = (pageVo.getCurrentPage() - 1) * limit ;
			example.setStart(start);
			example.setLimit(pageVo.getLimit());
		}
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		if(type != null && !"".equals(type)) {
			criteria.andTypeEqualTo(type);
		}
		//设置总记录数
		int countByExample = articleMapper.countByExample(example);
		pageVo.setTotalCount(countByExample);
		//返回list
		List<Article> list = articleMapper.selectByExampleWithBLOBs(example);
		return list;
	}
	/** 得到对应userId的totalCount  */
	@Override
	public int geTotalCount(int userId) {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		int totalCount = articleMapper.countByExample(example);
		return totalCount;
	}
	@Override
	public Article getArticleByArticleid(Integer articleid) {
		Article selectByPrimaryKey = articleMapper.selectByPrimaryKey(articleid);
		return selectByPrimaryKey;
	}
	@Override
	public List<String> geTypes(int userId) {
		// TODO Auto-generated method stub
		return articleMapper.geTypes(userId);
	}

}
