package cn.bobo.myBlog.dao;

import java.util.List;

import cn.bobo.myBlog.pojo.User;

public interface UserDao {
	User getUserById(Integer userid);
	List<User> getByUser(User user);
	User getByNameAndPassword(String name,String password);
	boolean seleteByName(String name);
	
}
