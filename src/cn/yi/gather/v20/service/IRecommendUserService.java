package cn.yi.gather.v20.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.User;

/**
 * 推荐业务接口
 * @author  kcm
 *  2015.4.21 
 */
public interface IRecommendUserService {
	
	/**
	 * 根据用户ID 推荐会员
	 * @return
	 */
	public Page<User> getRecommendByUser(String login_id);
}
