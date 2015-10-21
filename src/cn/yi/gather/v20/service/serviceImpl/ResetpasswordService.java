package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.ResetpasswordRepository;
import cn.yi.gather.v20.entity.Resetpassword;
import cn.yi.gather.v20.service.IResetpasswordService;

/**
 * 重置密码
 * @author Lee.J.Eric
 * @time 2014年5月30日下午5:17:03
 */
@Service("resetpasswordServiceV20")
public class ResetpasswordService implements IResetpasswordService{
	
	@Resource(name = "resetpasswordRepositoryV20")
	private ResetpasswordRepository repository;

	@Override
	public Resetpassword createResetpassword(Resetpassword resetpassword) {
		// TODO Auto-generated method stub
		return repository.save(resetpassword);
	}

	@Override
	public Resetpassword updateResetpassword(Resetpassword resetpassword) {
		// TODO Auto-generated method stub
		return repository.save(resetpassword);
	}

	@Override
	public Resetpassword searchResetpassword(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
