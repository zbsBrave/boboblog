package cn.bobo.myBlog.service.impl;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.bobo.myBlog.dao.mapper.LoginInfoMapper;
import cn.bobo.myBlog.pojo.LoginInfo;
import cn.bobo.myBlog.service.LoginInfoService;
import cn.bobo.myBlog.utils.CookieUtils;
@Service
public class LoginInfoServiceImpl implements LoginInfoService {
	@Autowired
	LoginInfoMapper loginInfoMapper;
	/**
	 * 验证用户是否在其他地方登陆：true代表在其他地方登陆，false代表没有
	 * 如果在其他地方登陆，要更新对应userID的身份证IDcard,
	 * 如果未在其他地方登陆，生产一个对应的loginInfo
	 */
	@Override
	public boolean checkByUserId(int userId,String idCard) {
		// TODO Auto-generated method stub
		LoginInfo logininfo = loginInfoMapper.getByUserId(userId);
		if(logininfo != null) {//查到数据代表用户在其他地方登陆，要更新对应userID的身份证IDcard
			loginInfoMapper.updateIdcardByUserid(userId, idCard);
			return true;
		}
		//未在其他地方登陆，则生产一个对应的loginInfo
		loginInfoMapper.insert(userId, idCard);
		return false;
	}
	@Override
	public int insert(int userId, String idCard) {
		// TODO Auto-generated method stub
		return loginInfoMapper.insert(userId, idCard);
	}
	/**
	 * 服务器加载servlet时运行
	 * 用于清空login_info表即登陆用户信息
	 */
	@PostConstruct
	@Override
	public int truncateTable() {
		// TODO Auto-generated method stub
		return loginInfoMapper.truncateTable();
	}
	@Override
	public boolean isMe(Integer userId, String idCard) {
		// TODO Auto-generated method stub
		if(userId == null || idCard == null) {
			return false;
		}
		LoginInfo info = loginInfoMapper.getByUserId(userId);
		if(info != null && idCard.equals(info.getIdCard())) {
			return true;
		}
		return false;
	}
	@Override
	public void delByUserId(Integer userId) {
		// TODO Auto-generated method stub
		loginInfoMapper.delByUserId(userId);
	}
	@Override
	public int isLogin(String LOGIN_USER,HttpServletRequest request) {
		// 首先判断能不能从cookie取出 IDCard
		String idCard = CookieUtils.getCookieValue(request, LOGIN_USER);
		if(idCard == null) {
			return -1;
		}
		//其次去数据库查看idCard有没有过期
		LoginInfo byIdcard = loginInfoMapper.getByIdcard(idCard);
		if(byIdcard != null) {
			return byIdcard.getUserId();
		}
		return -1;
	}

}
