package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.YiGatherAccountLog;

@Component("yiGatherAccountLogRepositoryV20")
public interface YiGatherAccountLogRepository extends JpaRepository<YiGatherAccountLog, String>,JpaSpecificationExecutor<YiGatherAccountLog>{

}
