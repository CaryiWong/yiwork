package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.SignLog;

@Component("signLogRepositoryV2")
public interface SignLogRepository extends JpaRepository<SignLog,String>{

}
