package cn.yi.gather.v20.permission.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.yi.gather.v20.dao.Co_working_spaceRepository;
import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.permission.dao.AdminPermissionRepository;
import cn.yi.gather.v20.permission.dao.AdminRoleAndPerRepository;
import cn.yi.gather.v20.permission.dao.AdminRoleRepository;
import cn.yi.gather.v20.permission.dao.AdminUserRepository;
import cn.yi.gather.v20.permission.entity.AdminPermission;
import cn.yi.gather.v20.permission.entity.AdminRole;
import cn.yi.gather.v20.permission.entity.AdminRoleAndPer;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.permission.service.IAdminRoleService;
import cn.yi.gather.v20.permission.service.IAdminUserService;

@Service("adminRoleService")
public class AdminRoleServiceImpl implements IAdminRoleService{
	@Resource(name = "adminRoleRepositoryV20")
	private AdminRoleRepository repository;

	@Resource(name = "adminRoleAndPerRepositoryV20")
	private AdminRoleAndPerRepository roleAndPerrepository;
	
	@Resource(name = "adminPerRepositoryV20")
	private AdminPermissionRepository adminPerRepository;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	
	
	@Override
	public AdminRole SaveOrUpdate(AdminRole obj) {
		// TODO Auto-generated method stub
		return repository.save(obj);
	}

	@Override
	public AdminRole findById(String id) {
		// TODO Auto-generated method stub
		AdminRole admin=null;
		List<AdminRole> adminRoleList= repository.findAll();
		for(AdminRole adminRole:adminRoleList){
			int roleid =Integer.parseInt(id);
			if(adminRole.getRoleid()==roleid){
				admin=adminRole;
			}
		}
		return admin;
	}
	/**
	 * 查询出未删除的角色列表
	 */
	@Override
	public List<AdminRole> selectRoleAll() {
		
		List<AdminRole> list= repository.findAll();
		List<AdminRole> result=new ArrayList<AdminRole>();
		for(AdminRole role:list){
			if(role.getIsdel()!=1){ //0未删除 1删除 新改的
				result.add(role);
			}
		}
		return result;
	}
	
	/**
	 * 根据角色ID以及实体， 权限列表修改角色
	 * @param rolename
	 * @param description
	 * @param roleId
	 * @param permitList
	 */
	@Override
	public void updateRoleByRoleid(AdminRole adminrole,String permitList){
		String sql="delete from admin_role_permission where roleid="+adminrole.getRoleid();
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs = null;
		String[] permitLists = permitList.split(",");
 
		try {
			conn =dataSource.getConnection();
			stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			
			for(String perid:permitLists){
				AdminRoleAndPer obj = new AdminRoleAndPer();
				AdminPermission  permit=null;
			
				
				List<AdminPermission> pList = adminPerRepository.findAll();
				for(AdminPermission per:pList){
					int result = Integer.parseInt(perid);
					if(per.getPermitid()==result){
						permit=per;
					}
				}

				obj.setAdminrole(adminrole);
				obj.setAdminpermission(permit);
				obj.setStatus(1+"");
				roleAndPerrepository.save(obj);
			}
			

		} catch (SQLException e) {
			 
		} finally {
			try {
				//rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		
	}
	 
	 
}
