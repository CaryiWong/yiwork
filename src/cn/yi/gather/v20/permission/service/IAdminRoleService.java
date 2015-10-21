package cn.yi.gather.v20.permission.service;


import java.util.List;

import cn.yi.gather.v20.permission.entity.AdminRole;


public interface IAdminRoleService {
	
	public AdminRole SaveOrUpdate(AdminRole obj);
	
	public AdminRole findById(String id);
	
	public List<AdminRole> selectRoleAll();
	
	/**
	 * 根据角色ID以及实体， 权限列表修改角色
	 * @param rolename
	 * @param description
	 * @param roleId
	 * @param permitList
	 */
	public void updateRoleByRoleid(AdminRole role,String permitList);
}
