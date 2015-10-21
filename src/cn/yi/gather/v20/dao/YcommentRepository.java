package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Ycomment;

@Component("ycommentRepositoryV20")
public interface YcommentRepository extends JpaRepository<Ycomment, String>,JpaSpecificationExecutor<Ycomment>{

	@Query("select count(*) from Ycomment where ytype=:ytype")
	public Integer countNum(@Param("ytype")String ytype);
	
	@Query("select COALESCE(sum(coffeenum),0) from Ycomment")
	public Integer sumCoffee();
	
}
