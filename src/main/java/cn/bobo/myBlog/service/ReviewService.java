package cn.bobo.myBlog.service;

import java.util.List;

import cn.bobo.myBlog.pojo.Review;
import cn.bobo.myBlog.vo.BoboResult;
import cn.bobo.myBlog.vo.ReviewVo;

public interface ReviewService {
	//得到文章的所有评论
	List<ReviewVo> getReviewList(Integer articleId);
	//发表评论
	BoboResult publish(Review review);
	//文章评论人数
	int count(Integer articleId);
}
