package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Dcomment;

@Component("dcommentRepositoryV20")
public interface DcommentRepository extends JpaRepository<Dcomment, String>,JpaSpecificationExecutor<Dcomment>{
	
	public List<Dcomment> findByPid(String pid);
}
