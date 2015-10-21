package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.YigatherItemInventoryLog;

@Component("yigatherItemInventoryLogRepositoryV20")
public interface YigatherItemInventoryLogRepository extends JpaRepository<YigatherItemInventoryLog, String>,JpaSpecificationExecutor<YigatherItemInventoryLog>{

}
