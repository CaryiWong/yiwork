package cn.yi.gather.v20.dao;

import java.util.List;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.Labels;

@Component("littleActivityRepositoryV20")
public interface LittleActivityRepository extends JpaRepository<Gathering, String>,
		JpaSpecificationExecutor<Gathering> {
	
 
}
