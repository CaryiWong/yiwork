package cn.yi.gather.v20.permission.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jaxen.function.StringFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.yi.gather.v20.dao.Co_working_spaceRepository;
import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.permission.dao.AdminPermissionRepository;
import cn.yi.gather.v20.permission.dao.AdminRoleRepository;
import cn.yi.gather.v20.permission.dao.AdminUserRepository;
import cn.yi.gather.v20.permission.entity.AdminPermission;
import cn.yi.gather.v20.permission.entity.AdminRole;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.permission.service.IAdminPerService;
import cn.yi.gather.v20.permission.service.IAdminRoleService;
import cn.yi.gather.v20.permission.service.IAdminUserService;

@Service("adminPerService")
public class AdminPerServiceImpl implements IAdminPerService{
	@Resource(name = "adminPerRepositoryV20")
	private AdminPermissionRepository repository;

	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	@Override
	public AdminPermission SaveOrUpdate(AdminPermission obj) {
		// TODO Auto-generated method stub
		return repository.save(obj);
	}

	@Override
	public AdminPermission findById(String id) {
		return repository.findOne(id);
	}

	@Override
	public List<AdminPermission> selectAllPermit() {
		String sql = "select * from admin_permission where isleaf=0 and parent='#'";
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs = null;
		List<AdminPermission> permitList = new ArrayList<AdminPermission>();
		
		try {
			conn =dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				AdminPermission permit= new AdminPermission();
				permit.setIsleaf((rs.getString("ISLEAF")));
				permit.setParent((rs.getString("PARENT")));
				permit.setPermitid(rs.getInt("PERMITID"));
				permit.setPermitname((rs.getString("PERMITNAME")));
				permit.setUrl((rs.getString("URL")));
				permitList.add(permit);
			}

		} catch (SQLException e) {
			 
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub
		return permitList;
	}

	/**
	 * 查询子类的父类
	 */
	public AdminPermission selectParentByPermitId(String permitId){
		String sql = "select * from admin_permission where permitid="+permitId;
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs = null;
		AdminPermission permit = new AdminPermission();
		try {
			conn =dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				permit.setIsleaf((rs.getString("ISLEAF")));
				permit.setParent((rs.getString("PARENT")));
				permit.setPermitid(rs.getInt("PERMITID"));
				permit.setPermitname((rs.getString("PERMITNAME")));
				permit.setUrl((rs.getString("URL")));
			}

		} catch (SQLException e) {
			 
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub
		return permit;
	}
	
	
	/**
	 * 根据父ID查询权限返回列表
	 */
	@Override
	public List<AdminPermission> selectPermitByParent(String parent) {
		//查询出父类 拼接成parent='#3#13#'
		AdminPermission parentObj=this.selectParentByPermitId(parent);
		String str=parentObj.getParent();
		
		str=str+parent+"#";
		
		//String sql = "select * from admin_permission where isleaf!=2 and parent=''+(select parent from admin_permission where permitid="+parent+")+'"+parent+"#'";parent
		String sql = "select * from admin_permission where isleaf!=2 and parent= '"+str+"'";
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs = null;
		List<AdminPermission> permitList = new ArrayList<AdminPermission>();
		
		try {
			conn =dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				AdminPermission permit= new AdminPermission();
				permit.setIsleaf((rs.getString("ISLEAF")));
				permit.setParent((rs.getString("PARENT")));
				permit.setPermitid(rs.getInt("PERMITID"));
				permit.setPermitname((rs.getString("PERMITNAME")));
				permit.setUrl((rs.getString("URL")));
				permitList.add(permit);
			}

		} catch (SQLException e) {
			 
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub
		//System.out.println("长度"+permitList.size()+" sql="+sql);
		return permitList;
	}

	/**
	 * 根据角色找权限列表
	 */
	@Override
	public List<AdminPermission> selectPermitByRoleId(String roleId, String isleaf) {
		String sql = "select p.* from admin_permission p,admin_role_permission rp where rp.roleid="
				+ roleId
				+ " and rp.permitid=p.permitid"
				+ " and p.isleaf="
				+ isleaf+" and p.parent='#'";
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs = null;
		List<AdminPermission> permitList = new ArrayList<AdminPermission>();
		
		try {
			conn =dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				AdminPermission permit= new AdminPermission();
				permit.setIsleaf((rs.getString("ISLEAF")));
				permit.setParent((rs.getString("PARENT")));
				permit.setPermitid(rs.getInt("PERMITID"));
				permit.setPermitname((rs.getString("PERMITNAME")));
				permit.setUrl((rs.getString("URL")));
				permitList.add(permit);
			}

		} catch (SQLException e) {
			 
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub
		return permitList;
	}

	/**
	 * 根据角色，父类，查询权限
	 */
	public List<AdminPermission> selectPermitByRoleIdAndParent(String roleId, String parent, String isleaf){
		String sql="select p.* from admin_permission p, admin_role_permission rp where rp.roleId="+roleId+" and p.parent='"+parent+"' and p.isleaf="+isleaf+" and rp.permitId=p.permitid";
		
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs = null;
		List<AdminPermission> permitList = new ArrayList<AdminPermission>();
		
		try {
			conn =dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				AdminPermission permit= new AdminPermission();
				permit.setIsleaf((rs.getString("ISLEAF")));
				permit.setParent((rs.getString("PARENT")));
				permit.setPermitid(rs.getInt("PERMITID"));
				permit.setPermitname((rs.getString("PERMITNAME")));
				permit.setUrl((rs.getString("URL")));
				permitList.add(permit);
			}

		} catch (SQLException e) {
			 
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub
	
		return permitList;
	}
	
	/**
	 * 根据角色ID查找用户列表
	 * @param roleid
	 * @return
	 */
	public List<AdminUser> searchUserByRoleId(String roleid){
		String sql="select * from admin_user where roleid="+roleid;
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs = null;
		List<AdminUser> userList = new ArrayList<AdminUser>();
		
		try {
			conn =dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				AdminUser user= new AdminUser();
				user.setUsername(rs.getString("username"));
				user.setConnectphone(rs.getString("connectphone"));
				user.setDescription(rs.getString("description"));
				user.setCreatetime(rs.getDate("createtime"));
				user.setUsertype(rs.getInt("usertype"));
				userList.add(user);
			}

		} catch (SQLException e) {
			 
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		return userList;
	}
	 
	/**
	 * 根据角色ID查询父类权限列表
	 * @param roleId
	 * @return
	 */
	public List<AdminPermission> selectParentByRoleId(String roleId){
		String sql = "select pe.* from admin_role_permission  per inner join admin_permission pe on per.permitid =pe.permitid where  pe.parent='#' and per.roleid="+roleId;
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs = null;
		
		List<AdminPermission> permitList = new ArrayList<AdminPermission>();
		try {
			conn =dataSource.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				AdminPermission permit = new AdminPermission();
				permit.setIsleaf((rs.getString("ISLEAF")));
				permit.setParent((rs.getString("PARENT")));
				permit.setPermitid(rs.getInt("PERMITID"));
				permit.setPermitname((rs.getString("PERMITNAME")));
				permit.setUrl((rs.getString("URL")));
				permitList.add(permit);
			}

		} catch (SQLException e) {
			 
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// TODO Auto-generated method stub
		return permitList;
	}
	
}
