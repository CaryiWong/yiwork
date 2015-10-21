package cn.yi.gather.v11.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.entity.UserFocus;
@Component("userFocusRepositoryV2")
public interface UserFocusRepository extends JpaRepository<UserFocus,String>,JpaSpecificationExecutor<UserFocus>{

	public UserFocus findByMeAndWho(User me,User who);
	
	public Page<UserFocus> findPageByMe(Pageable pageable,User me);
	
	public Page<UserFocus> findPageByWho(Pageable pageable,User who);
	
	public List<UserFocus> findByMe(User me);
}
