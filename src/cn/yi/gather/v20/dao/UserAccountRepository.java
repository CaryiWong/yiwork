package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.UserAccount;

@Component("userAccountRepositoryV20")
public interface UserAccountRepository extends JpaRepository<UserAccount, String>,JpaSpecificationExecutor<UserAccount>{

}
