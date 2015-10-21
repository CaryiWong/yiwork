package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Indexuserinfo;
@Component("indexuserinfoRepositoryV2")
public interface IndexuserinfoRepository extends JpaRepository<Indexuserinfo, String>,JpaSpecificationExecutor<Indexuserinfo>{

}
