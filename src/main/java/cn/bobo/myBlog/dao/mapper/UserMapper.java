package cn.bobo.myBlog.dao.mapper;

import cn.bobo.myBlog.pojo.User;
import cn.bobo.myBlog.pojo.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	int updPwdByIdAndPwd(@Param("userId")int userId,@Param("oldPwd")String oldPwd,@Param("newPwd")String newPwd);
	List<User> getFans(int userId);
	List<User> getFollows(int fansId);
	List<User> selectByUser(User user);
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}