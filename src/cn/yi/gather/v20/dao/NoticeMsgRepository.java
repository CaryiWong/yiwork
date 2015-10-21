package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.NoticeMsg;


@Component("noticeMsgRepositoryV20")
public interface NoticeMsgRepository extends JpaRepository<NoticeMsg, String>,JpaSpecificationExecutor<NoticeMsg>{

}
