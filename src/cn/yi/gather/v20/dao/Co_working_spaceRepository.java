package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Co_working_space;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Tribe_partner;

@Component("coworkingspaceRepositoryV20")
public interface Co_working_spaceRepository extends JpaRepository<Co_working_space, String>,JpaSpecificationExecutor<Co_working_space>{

}
