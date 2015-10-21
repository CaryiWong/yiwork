package cn.yi.gather.v20.permission.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.permission.entity.AdminRole;
import cn.yi.gather.v20.permission.entity.AdminUser;


@Component("adminUserRepositoryV20")
public interface AdminUserRepository extends JpaRepository<AdminUser,String>,JpaSpecificationExecutor<AdminUser>{
	@Query(value = "select o from AdminUser o where  userpassword =:password and username =:username and isdel!=1 ")
	public AdminUser findByusernameAndPWD(@Param("password")String password,@Param("username")String username);
}
