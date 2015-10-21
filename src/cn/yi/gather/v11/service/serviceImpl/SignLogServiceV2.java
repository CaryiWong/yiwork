package cn.yi.gather.v11.service.serviceImpl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v11.dao.SignLogRepository;
import cn.yi.gather.v11.entity.SignLog;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.ISignLogServiceV2;
import cn.yi.gather.v11.service.IUserServiceV2;

/**
 * 签到/签出
 * @author Lee.J.Eric
 *
 */
@Service("signLogServiceV2")
public class SignLogServiceV2 implements ISignLogServiceV2{

	@Resource(name = "signLogRepositoryV2")
	private SignLogRepository repository;
	
	@Resource(name = "userServiceV2")
	private IUserServiceV2 userServiceV2;
	
	@Override
	public SignLog signLogSaveOrUpdate(SignLog signLog) {
		// TODO Auto-generated method stub
		return repository.save(signLog);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean userSignLog(String userid, String signtype) {
		// TODO Auto-generated method stub
			User user = userServiceV2.findById(userid);
			SignLog signLog = new SignLog(user, signtype);
			repository.save(signLog);
			user.setSpacesettime(new Date());
			if(signtype.equalsIgnoreCase("in")){//签到
				user.setIfspace(1);
			}else if (signtype.equalsIgnoreCase("out")) {//签出
				user.setIfspace(0);
			}
			userServiceV2.userSaveOrUpdate(user);
			return true;
	}

	
}
