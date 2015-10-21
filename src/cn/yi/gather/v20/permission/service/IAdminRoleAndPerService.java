package cn.yi.gather.v20.permission.service;


import cn.yi.gather.v20.permission.entity.AdminRoleAndPer;


public interface IAdminRoleAndPerService {
	
	public AdminRoleAndPer SaveOrUpdate(AdminRoleAndPer obj);
	
	public AdminRoleAndPer findById(String id);
	
}
