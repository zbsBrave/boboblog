package cn.bobo.myBlog.dao.mapper;

import org.apache.ibatis.annotations.Param;

import cn.bobo.myBlog.pojo.LoginInfo;

public interface LoginInfoMapper {
	LoginInfo getByIdcard(String idCard);
	LoginInfo getByUserId(int userId);
	int insert(@Param("userId") int userId,@Param("idCard") String idCard); 
	int deleteByIdCard(String idCard);
	int updateIdcardByUserid(@Param("userId") int userId,@Param("idCard") String idCard);
	int truncateTable();
	void delByUserId(int userId);
}
