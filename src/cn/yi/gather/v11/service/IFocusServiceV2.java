package cn.yi.gather.v11.service;

import org.springframework.data.domain.Page;

import cn.yi.gather.v11.entity.Demands;
import cn.yi.gather.v11.entity.Focus;
import cn.yi.gather.v11.entity.User;

public interface IFocusServiceV2 {

	public Focus saveFocus(Focus focus);
	
	public void delFocus(User user,Demands demands);
	
	/**
	 * 查询需求关注列表   根据用户ID查询用户的关注列表    根据需求ID查询关注该需求的用户列表
	 * @param page
	 * @param size
	 * @param uid
	 * @param did
	 * @param orderby  1 正序 -1 倒序
	 * @return
	 */
	public Page<Focus> findFocusBy(Integer page, Integer size, String uid, String did, Integer orderby);
}
