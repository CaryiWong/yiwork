package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.ItemInstanceLog;

@Component("itemInstanceLogRepositoryV20")
public interface ItemInstanceLogRepository extends JpaRepository<ItemInstanceLog, String>{

}
