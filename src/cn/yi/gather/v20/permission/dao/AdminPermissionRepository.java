package cn.yi.gather.v20.permission.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.permission.entity.AdminPermission;
import cn.yi.gather.v20.permission.entity.AdminRole;
import cn.yi.gather.v20.permission.entity.AdminUser;


@Component("adminPerRepositoryV20")
public interface AdminPermissionRepository extends JpaRepository<AdminPermission,String>,JpaSpecificationExecutor<AdminPermission>{
	 
}
