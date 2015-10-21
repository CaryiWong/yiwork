package cn.yi.gather.v20.permission.service;


import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.permission.entity.AdminUser;

public interface IAdminUserService {
	
	public AdminUser SaveOrUpdate(AdminUser obj);
	
	public AdminUser findById(String id);
	
	public Page<AdminUser> getPage(Integer page, Integer size);
	
	public List<AdminUser> selectAllUser();
	
	public com.common.Page<AdminUser> adminUserList(
			com.common.Page<AdminUser> page);
	
	 
	public AdminUser findByusernameAndPWD(String password, String username);
	 
}
