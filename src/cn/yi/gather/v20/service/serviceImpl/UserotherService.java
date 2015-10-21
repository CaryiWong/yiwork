package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.UserotherRepository;
import cn.yi.gather.v20.entity.Userother;
import cn.yi.gather.v20.service.IUserotherService;

@Service("userotherServiceV20")
public class UserotherService implements IUserotherService{

	@Resource(name = "userotherRepositoryV20")
	private UserotherRepository repository;

	@Override
	public Userother saveOrUpdateUserother(Userother userother) {
		return repository.save(userother);
	}

	@Override
	public Userother findUserother(String id) {
		return repository.findOne(id);
	}
	
	@Override
	public List<Userother> findAllUserothers() {
		// TODO Auto-generated method stub
		return repository.findAll(new Sort(Direction.ASC,"id"));
	}
}
