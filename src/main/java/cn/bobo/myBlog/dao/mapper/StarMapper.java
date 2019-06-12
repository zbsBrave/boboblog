package cn.bobo.myBlog.dao.mapper;

import cn.bobo.myBlog.pojo.Star;
import cn.bobo.myBlog.pojo.StarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StarMapper {
	Star selByUserIdAndArticleId(@Param("userId")int userId,@Param("articleId")int articleId);
    int countByExample(StarExample example);

    int deleteByExample(StarExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Star record);

    int insertSelective(Star record);

    List<Star> selectByExample(StarExample example);

    Star selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Star record, @Param("example") StarExample example);

    int updateByExample(@Param("record") Star record, @Param("example") StarExample example);

    int updateByPrimaryKeySelective(Star record);

    int updateByPrimaryKey(Star record);
}