package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.WorkSpaceInfo;

@Component("workSpaceInfoRepository")
public interface WorkSpaceInfoRepository extends JpaRepository<WorkSpaceInfo, String>,JpaSpecificationExecutor<WorkSpaceInfo>{

	public WorkSpaceInfo findBySpacecode(String code);
	
}
