package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Appversion;

@Component("appversionRepositoryV20")
public interface AppversionRepository extends JpaRepository<Appversion, String>,JpaSpecificationExecutor<Appversion>{

}
