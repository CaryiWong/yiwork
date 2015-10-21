package cn.yi.gather.v20.service;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Invitations;

public interface IinvitationsService {

	public Invitations saveOrUpdateInvitations(Invitations invitations);
	
	public Invitations findInvitations(String id);
	
	public Page<Invitations> invitationsList(Integer page,Integer size,String uid,String iuid,String orderby);
	public Page<Invitations> invitationsMyList(Integer page,Integer size,String uid,String orderby);
}
