package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Focus;
import cn.yi.gather.v20.entity.User;

@Component("focusRepositoryV20")
public interface FocusRepository extends JpaRepository<Focus, String>,JpaSpecificationExecutor<Focus>{

	public Focus findFocusByUserAndDemands(User user, Demands demands);
}
