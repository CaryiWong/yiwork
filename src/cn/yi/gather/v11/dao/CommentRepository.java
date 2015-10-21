package cn.yi.gather.v11.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Comment;
@Component("commentRepositoryV2")
public interface CommentRepository extends JpaRepository<Comment, String>,JpaSpecificationExecutor<Comment>{

	public List<Comment> findByPid(String pid);
	
}
