package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserFocus;
@Component("userFocusRepositoryV20")
public interface UserFocusRepository extends JpaRepository<UserFocus,String>,JpaSpecificationExecutor<UserFocus>{

	public UserFocus findByMeAndWho(User me,User who);
	
	public UserFocus findByMeAndWhoAndRelation(User me,User who,Integer relation);
	
	public Page<UserFocus> findPageByMe(Pageable pageable,User me);
	
	public Page<UserFocus> findPageByMeAndRelation(Pageable pageable,User me,Integer relation);
	
	public Page<UserFocus> findPageByWho(Pageable pageable,User who);
	
	public Page<UserFocus> findPageByWhoAndRelation(Pageable pageable,User who,Integer relation);
	
	public List<UserFocus> findByMe(User me);
	
	public List<UserFocus> findByMeAndRelation(User me,Integer relation);
}
