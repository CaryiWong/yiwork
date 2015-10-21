package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.YqServiceInfo;

@Component("yqServiceRepository")
public interface YqServiceRepository extends JpaRepository<YqServiceInfo, String>,JpaSpecificationExecutor<YqServiceInfo>{

	public List<YqServiceInfo> findByIsbanner(Integer num);
	
}
