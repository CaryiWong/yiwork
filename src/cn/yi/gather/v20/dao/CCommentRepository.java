package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.CComment;

@Component("cCommentRepositoryV20")
public interface CCommentRepository extends JpaRepository<CComment, String>,JpaSpecificationExecutor<CComment>{

}
