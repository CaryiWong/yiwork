package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Demands;
import cn.yi.gather.v11.entity.Focus;
import cn.yi.gather.v11.entity.User;
@Component("focusRepositoryV2")
public interface FocusRepository extends JpaRepository<Focus, String>,JpaSpecificationExecutor<Focus>{

	public Focus findFocusByUserAndDemands(User user, Demands demands);
}
