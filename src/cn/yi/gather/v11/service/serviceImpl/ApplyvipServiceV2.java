package cn.yi.gather.v11.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.ApplyvipRepository;
import cn.yi.gather.v11.entity.Applyvip;
import cn.yi.gather.v11.service.IApplyvipServiceV2;

/**
 * 会员申请
 * @author Lee.J.Eric
 * @time 2014年6月10日下午3:06:21
 */
@Service("applyvipServiceV2")
public class ApplyvipServiceV2 implements IApplyvipServiceV2{
	@Resource(name = "applyvipRepositoryV2")
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
