package cn.bobo.myBlog.dao.mapper;

import cn.bobo.myBlog.pojo.Review;
import cn.bobo.myBlog.pojo.ReviewExample;
import cn.bobo.myBlog.vo.ReviewVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReviewMapper {
	List<ReviewVo> getAllReviewVoType0(int articleId);
	List<ReviewVo> getAllReviewVoType1(int articleId);
    int countByExample(ReviewExample example);

    int deleteByExample(ReviewExample example);

    int deleteByPrimaryKey(Integer reviewId);

    int insert(Review record);

    int insertSelective(Review record);

    List<Review> selectByExample(ReviewExample example);

    Review selectByPrimaryKey(Integer reviewId);

    int updateByExampleSelective(@Param("record") Review record, @Param("example") ReviewExample example);

    int updateByExample(@Param("record") Review record, @Param("example") ReviewExample example);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}