package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Tribe;

@Component("tribeRepositoryV20")
public interface TribeRepository extends JpaRepository<Tribe, String>,JpaSpecificationExecutor<Tribe>{

}
