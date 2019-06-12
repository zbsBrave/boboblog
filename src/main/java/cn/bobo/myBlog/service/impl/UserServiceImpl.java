package cn.bobo.myBlog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import cn.bobo.myBlog.dao.UserDao;
import cn.bobo.myBlog.dao.mapper.FansMapper;
import cn.bobo.myBlog.dao.mapper.UserMapper;
import cn.bobo.myBlog.pojo.Fans;
import cn.bobo.myBlog.pojo.FansExample;
import cn.bobo.myBlog.pojo.FansExample.Criteria;
import cn.bobo.myBlog.pojo.User;
import cn.bobo.myBlog.service.UserService;
import cn.bobo.myBlog.vo.BoboResult;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	UserMapper userMapper;
	@Autowired
	FansMapper fansMapper;
	@Override
	public User getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return userDao.getUserById(userId);
	}
	@Override
	public BoboResult doLogin(String name, String password) {
		//首先验证登陆信息是否正确
		User user = userDao.getByNameAndPassword(name, password);
		if (user != null) {
			//验证成功 清空password
			user.setPassword(null);
			return new BoboResult(200, "登陆成功", user);
		}
		return new BoboResult(400, "用户名或密码错误");
	}
	@Override
	public BoboResult register(User user) {
		// 如果名字重复
		if(userDao.seleteByName(user.getName())) {
			return new BoboResult(400, "用户名已注册");
		}
		userMapper.insertSelective(user);
		return new BoboResult(200, "成功", user);
	}
	@Override
	public BoboResult updPwd(Integer userId, String oldPwd, String newPwd) {
		if(userId == null || StringUtils.isEmpty(oldPwd)) {
			return new BoboResult(400, "密码修改失败");
		}
		int x = userMapper.updPwdByIdAndPwd(userId, oldPwd, newPwd);
		if(x > 0) {
			return new BoboResult(200, "密码修改成功");
		}
		return new BoboResult(400, "密码修改失败");
	}
	@Override
	public BoboResult focus(Integer fansId, Integer userId) {
		// 1，先查看该userID有没有该粉丝
		FansExample example = new FansExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andFansIdEqualTo(fansId);
		createCriteria.andUserIdEqualTo(userId);
		List<Fans> list = fansMapper.selectByExample(example);
		//如果有该粉丝，代表已关注
		if(list != null && list.size() >0) {
			return new BoboResult(400, "您已经关注该作者");
		}
		//2，如果没关注，要更新fans表
		Fans fans = new Fans();
		fans.setFansId(fansId);
		fans.setUserId(userId);
		fansMapper.insertSelective(fans);
		return new BoboResult(200, "关注成功");
	}
	@Override
	public List<User> fans(Integer userId) {
		// TODO Auto-generated method stub
		List<User> list = userMapper.getFans(userId);
		return list;
	}
	@Override
	public List<User> getFollows(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.getFollows(userId);
	}

}
