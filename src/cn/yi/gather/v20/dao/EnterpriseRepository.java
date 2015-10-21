package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Enterprise;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Tribe_partner;

@Component("enterpriseRepositoryV20")
public interface EnterpriseRepository extends JpaRepository<Enterprise, String>,JpaSpecificationExecutor<Enterprise>{

}
