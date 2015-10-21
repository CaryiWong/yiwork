package cn.yi.gather.v20.permission.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.permission.entity.AdminRole;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.permission.entity.AdminUserAndRole;


@Component("adminUserAndRoleRepositoryV20")
public interface AdminUserAndRoleRepository extends JpaRepository<AdminUserAndRole,String>,JpaSpecificationExecutor<AdminUserAndRole>{
	 
}
