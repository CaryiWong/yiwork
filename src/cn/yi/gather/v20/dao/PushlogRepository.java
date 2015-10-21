package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Pushlog;
@Component("pushlogRepositoryV20")
public interface PushlogRepository extends JpaRepository<Pushlog, String>,JpaSpecificationExecutor<Pushlog>{

}
