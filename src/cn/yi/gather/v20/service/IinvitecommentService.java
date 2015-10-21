package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Invitecomment;

public interface IinvitecommentService {

	public Invitecomment saveOrUpdateInvitecomment(Invitecomment invitecomment);
	
	public List<Invitecomment> invitecommentlist(String id);
	
	public Page<Invitecomment> invitecommentlist(Integer page,Integer size,String id,String orderby);
}
