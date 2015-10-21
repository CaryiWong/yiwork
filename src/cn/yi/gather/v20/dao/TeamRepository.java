package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.TeamInfo;

@Component("teamRepository")
public interface TeamRepository extends JpaRepository<TeamInfo, String>,JpaSpecificationExecutor<TeamInfo> {

	public List<TeamInfo> findByIsbanner(Integer num);
	
}
