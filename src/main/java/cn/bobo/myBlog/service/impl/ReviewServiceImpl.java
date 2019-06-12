package cn.bobo.myBlog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bobo.myBlog.dao.mapper.ReviewMapper;
import cn.bobo.myBlog.pojo.Review;
import cn.bobo.myBlog.pojo.ReviewExample;
import cn.bobo.myBlog.pojo.ReviewExample.Criteria;
import cn.bobo.myBlog.service.ReviewService;
import cn.bobo.myBlog.vo.BoboResult;
import cn.bobo.myBlog.vo.ReviewVo;
@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	@Override
	public List<ReviewVo> getReviewList(Integer articleId) {
		// TODO Auto-generated method stub
		if(articleId == null) {
			return null;
		}
		List<ReviewVo> list = reviewMapper.getAllReviewVoType0(articleId);
		return list;
	}
	@Override
	public BoboResult publish(Review review) {
		// TODO Auto-generated method stub
		reviewMapper.insertSelective(review);
		return new BoboResult(200, "评论成功");
	}
	@Override
	public int count(Integer articleId) {
		// TODO Auto-generated method stub
		ReviewExample example = new ReviewExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andArticleidEqualTo(articleId);
		List<Review> list = reviewMapper.selectByExample(example);
		if(list !=null) {
			return list.size();
		}
		return 0;
	}

}
