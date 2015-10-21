package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.GComment;
@Component("gCommentRepositoryV20")
public interface GCommentRepository extends JpaRepository<GComment, String>,JpaSpecificationExecutor<GComment>{

}
