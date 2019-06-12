package cn.bobo.myBlog.service;

import java.util.List;

import cn.bobo.myBlog.pojo.User;
import cn.bobo.myBlog.vo.BoboResult;

public interface UserService {
	User getUserById(Integer userId);
	BoboResult doLogin(String name,String password);
	BoboResult register(User user);
	BoboResult updPwd(Integer userId,String oldPwd,String newPwd);
	//关注功能  fansId:关注者的id   userId被关注者的id   status=200关注成功  status=400已关注
	BoboResult focus(Integer fansId,Integer userId);
	//获取粉丝
	List<User> fans(Integer userId);
	//获取自己关注的人
	List<User> getFollows(Integer userId);
}
