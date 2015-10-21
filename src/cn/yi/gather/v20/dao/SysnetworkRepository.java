package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Sysnetwork;
@Component("sysnetworkRepositoryV20")
public interface SysnetworkRepository extends JpaRepository<Sysnetwork, String>,JpaSpecificationExecutor<Sysnetwork>{

}
