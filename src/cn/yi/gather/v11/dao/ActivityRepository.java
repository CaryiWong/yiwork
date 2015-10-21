package cn.yi.gather.v11.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Activity;
import cn.yi.gather.v11.entity.Labels;
@Component("activityRepositoryV2")
public interface ActivityRepository extends JpaRepository<Activity, String>,
		JpaSpecificationExecutor<Activity> {
	
	
	public List<Activity> findByChecktypeAndOnsaleAndIsbanner(Integer checktype,Integer onsale,Integer isbanner);
	
	public Page<Activity> findByLabelIn(Pageable pageable,Labels label);
	
	public Long countByIsbanner(Integer isbanner);
}
