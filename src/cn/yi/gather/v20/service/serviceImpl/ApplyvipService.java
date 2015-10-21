package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.ApplyvipRepository;
import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.service.IApplyvipService;

/**
 * 会员申请
 * @author Lee.J.Eric
 * @time 2014年6月10日下午3:06:21
 */
@Service("applyvipServiceV20")
public class ApplyvipService implements IApplyvipService{
	@Resource(name = "applyvipRepositoryV20")
	private ApplyvipRepository repository;

	@Override
	public Applyvip applyvipSaveOrUpdate(Applyvip applyvip) {
		// TODO Auto-generated method stub
		return repository.save(applyvip);
	}

	@Override
	public Applyvip findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
