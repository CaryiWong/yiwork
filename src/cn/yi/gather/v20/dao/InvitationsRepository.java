package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Invitations;
@Component("invitationsRepositoryV20")
public interface InvitationsRepository extends JpaRepository<Invitations, String>,JpaSpecificationExecutor<Invitations>{

}
