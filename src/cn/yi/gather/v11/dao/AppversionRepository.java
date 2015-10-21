package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Appversion;
@Component("appversionRepositoryV2")
public interface AppversionRepository extends JpaRepository<Appversion, String>,JpaSpecificationExecutor<Appversion>{

}
