package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.SignLog;

@Component("signLogRepositoryV20")
public interface SignLogRepository extends JpaRepository<SignLog,String>{

	
	
}
