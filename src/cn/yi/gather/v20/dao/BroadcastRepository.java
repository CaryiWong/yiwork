package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Broadcast;
import cn.yi.gather.v20.entity.Sysnetwork;
import cn.yi.gather.v20.entity.User;
@Component("broadcastRepositoryV20")
public interface BroadcastRepository extends JpaRepository<Broadcast, String>,JpaSpecificationExecutor<Broadcast>{

	public Broadcast findByUserAndSysnetwork(User user,Sysnetwork sysnetwork);
}
