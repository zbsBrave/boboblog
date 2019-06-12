package cn.bobo.myBlog.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cn.bobo.myBlog.dao.UserDao;
import cn.bobo.myBlog.dao.mapper.UserMapper;
import cn.bobo.myBlog.pojo.User;
import cn.bobo.myBlog.pojo.UserExample;
import cn.bobo.myBlog.pojo.UserExample.Criteria;
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	UserMapper userMapper;
	//通过id取得user
	@Override
	public User getUserById(Integer userid) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(userid);
	}
	@Override
	public List<User> getByUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByUser(user);
	}
	@Override
	public User getByNameAndPassword(String name, String password) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		List<User> list = userMapper.selectByUser(user);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public boolean seleteByName(String name) {
		// TODO Auto-generated method stub
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andNameEqualTo(name);
		List<User> list = userMapper.selectByExample(example);
		if(list!=null && list.size()>0) {
			return true;
		}
		return false;
	}

}
