package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Visit;

@Component("visitRepositoryV20")
public interface VisitRepository extends JpaRepository<Visit, String>,JpaSpecificationExecutor<Visit>{

}
