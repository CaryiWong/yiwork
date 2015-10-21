package cn.yi.gather.v22.yg100.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v22.yg100.entity.WxUser;

@Component("wxUserRepository")
public interface WxUserRepository extends JpaRepository<WxUser, String>,JpaSpecificationExecutor<WxUser> {

	public WxUser findByOpenid(String openid);
	
	public WxUser findByTel(String tel);
}
