package cn.yi.gather.v20.permission.service;

import java.util.List;




import cn.yi.gather.v20.permission.entity.AdminPermission;
import cn.yi.gather.v20.permission.entity.AdminUser;


public interface IAdminPerService {
	
	public AdminPermission SaveOrUpdate(AdminPermission obj);
	
	public AdminPermission findById(String id);
	
	public List<AdminPermission> selectAllPermit();
	
	public List<AdminPermission> selectPermitByParent(String id);
	
	/**
	 * 根据角色找权限
	 * @param id
	 * @return
	 */
	public List<AdminPermission> selectPermitByRoleId(String roleId, String isleaf);
	
	/**
	 * 根据
	 * @param roleId
	 * @param parent
	 * @param isleaf
	 * @return
	 */
	public List<AdminPermission> selectPermitByRoleIdAndParent(String roleId, String parent, String isleaf);
	
	/**
	 * 查询父类根据权限ID
	 * @param permitId
	 * @return
	 */
	public AdminPermission selectParentByPermitId(String permitId);
	
	/**
	 * 根据角色ID查询父类权限列表
	 * @param roleId
	 * @return
	 */
	public List<AdminPermission> selectParentByRoleId(String roleId);
	/**
	 * 根据角色ID查找用户列表
	 * @param roleid
	 * @return
	 */
	public List<AdminUser> searchUserByRoleId(String roleid);
}
