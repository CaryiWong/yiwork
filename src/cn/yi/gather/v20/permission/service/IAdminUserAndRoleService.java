package cn.yi.gather.v20.permission.service;

import cn.yi.gather.v20.permission.entity.AdminUserAndRole;

public interface IAdminUserAndRoleService {
	
	public AdminUserAndRole SaveOrUpdate(AdminUserAndRole obj);
	
	public AdminUserAndRole findById(String id);
	
}
