package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Notice;
@Component("noticeRepositoryV20")
public interface NoticeRepository extends JpaRepository<Notice, String>,JpaSpecificationExecutor<Notice>{

}
