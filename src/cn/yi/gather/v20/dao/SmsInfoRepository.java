package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Focus;
import cn.yi.gather.v20.entity.SmsInfo;
import cn.yi.gather.v20.entity.User;

@Component("smsinfoRepositoryV20")
public interface SmsInfoRepository extends JpaRepository<SmsInfo, String>{

}
