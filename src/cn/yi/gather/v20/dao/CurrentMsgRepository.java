package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.CurrentMsg;

@Component("CurrentMsgRepositoryV20")
public interface CurrentMsgRepository extends JpaRepository<CurrentMsg, String>,JpaSpecificationExecutor<CurrentMsg>{

}
