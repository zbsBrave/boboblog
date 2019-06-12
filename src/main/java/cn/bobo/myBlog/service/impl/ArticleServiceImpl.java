package cn.bobo.myBlog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bobo.myBlog.dao.ArticleDao;
import cn.bobo.myBlog.dao.mapper.ArticleMapper;
import cn.bobo.myBlog.dao.mapper.StarMapper;
import cn.bobo.myBlog.pojo.Article;
import cn.bobo.myBlog.pojo.ArticleExample;
import cn.bobo.myBlog.pojo.ArticleExample.Criteria;
import cn.bobo.myBlog.pojo.Star;
import cn.bobo.myBlog.service.ArticleService;
import cn.bobo.myBlog.vo.BoboResult;
import cn.bobo.myBlog.vo.PageVo;
@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleDao articleDao;
	@Autowired
	ArticleMapper articleMapper;
	@Autowired
	StarMapper starMapper;
	@Override
	public List<Article> getAllArticleByUseridAndType(Integer userId, String type,PageVo<Article> pageVo) {
		// TODO Auto-generated method stub
		return articleDao.getAllArticleByUseridAndType(userId, type,pageVo);
	}
	/** 得到对应userId的totalCount  */
	@Override
	public int geTotalCount(int userId) {
		// TODO Auto-generated method stub
		return articleDao.geTotalCount(userId);
	}
	@Override
	public Article getArticleByArticleid(Integer articleid) {
		if(articleid !=null) {
			Article article = articleMapper.selectByPrimaryKey(articleid);
			return article;
		}
		return null;
	}
	@Override
	public List<String> geTypes(int userId) {
		// TODO Auto-generated method stub
		return articleDao.geTypes(userId);
	}
	@Override
	public int addArticle(Article article) {
		// TODO Auto-generated method stub
		article.setArticleTime(new Date());
		articleMapper.insertSelective(article);
		int articleid = article.getArticleid();
		return articleid;
	}
	@Override
	public boolean update(Article article) {
		try {
			int x = articleMapper.updateByPrimaryKeySelective(article);
			if(x != 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("===========article更新发生异常================");
		}
		return false;
	}
	@Override
	public boolean deleteById(Integer articleid) {
		// TODO Auto-generated method stub
		int x = articleMapper.deleteByPrimaryKey(articleid);
		if(x == 0) {
			return false;
		}
		return true;
	}
	//查询文章
	@Override
	public List<Article> search(String search) {
		// TODO Auto-generated method stub
		if (search==null || search.isEmpty()) {
			return null;
		}
		ArticleExample example = new ArticleExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andTitleLike("%"+search+"%");
		List<Article> selectByExample = articleMapper.selectByExample(example);
		return selectByExample;
	}
	@Override
	public BoboResult zan(Integer userId,Integer articleId,Integer starCount) {
		//1， 先判断该用户有没有给文章点过攒
		Star star = starMapper.selByUserIdAndArticleId(userId, articleId);
		if(star == null) {
			//2.1没有点赞，需要向star表插入数据
			star = new Star();
			star.setArticleId(articleId);
			star.setUserId(userId);
			starMapper.insertSelective(star);
			//2.2更新article表的star
			Article article = new Article();
			article.setStar(starCount+1);
			article.setArticleid(articleId);
			articleMapper.updateByPrimaryKeySelective(article);
			//3，返回
			return new BoboResult(200, (starCount+1)+"");
		}
		return new BoboResult(400, "已经点过赞");
	}
	@Override
	public Article getByArticleId(Integer articleId) {
		return articleMapper.selectByPrimaryKey(articleId);
	}

}
