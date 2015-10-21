package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.CommunityInfo;

@Component("communityRepository")
public interface CommunityRepository extends JpaRepository<CommunityInfo, String>,JpaSpecificationExecutor<CommunityInfo>{

	public List<CommunityInfo> findByIsbanner(Integer num);
	
}
