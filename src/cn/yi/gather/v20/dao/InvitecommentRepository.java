package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Invitecomment;
@Component("invitecommentRepositoryV20")
public interface InvitecommentRepository extends JpaRepository<Invitecomment, String>,JpaSpecificationExecutor<Invitecomment>{

}
