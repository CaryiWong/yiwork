package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.UserAccountLog;

@Component("userAccountLogRepositoryV20")
public interface UserAccountLogRepository extends JpaRepository<UserAccountLog, String>,JpaSpecificationExecutor<UserAccountLog>{

}
