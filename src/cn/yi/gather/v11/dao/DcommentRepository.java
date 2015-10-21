package cn.yi.gather.v11.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Dcomment;
@Component("dcommentRepositoryV2")
public interface DcommentRepository extends JpaRepository<Dcomment, String>,JpaSpecificationExecutor<Dcomment>{
	
	public List<Dcomment> findByPid(String pid);
}
