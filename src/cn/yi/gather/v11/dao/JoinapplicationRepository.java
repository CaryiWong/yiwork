package cn.yi.gather.v11.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Joinapplication;
@Component("joinapplicationRepositoryV2")
public interface JoinapplicationRepository extends JpaRepository<Joinapplication, String>{
	
	
	public Page<Joinapplication> findPageByTeamnameContainingIgnoreCase(Pageable pageable,String teamname);

}
