package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Tribe_partner;

@Component("tribe_partnerRepositoryV20")
public interface Tribe_partnerRepository extends JpaRepository<Tribe_partner, String>,JpaSpecificationExecutor<Tribe_partner>{

}
