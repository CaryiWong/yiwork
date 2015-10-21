package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.YcommentRepository;
import cn.yi.gather.v20.entity.Ycomment;
import cn.yi.gather.v20.service.IYcommentService;

@Service("ycommentServiceV20")
public class YcommentService implements IYcommentService{

	@Resource(name = "ycommentRepositoryV20")
	private YcommentRepository repository;
	
	@Override
	public Ycomment save(Ycomment ycomment) {
		return repository.save(ycomment);
	}

	@Override
	public Page<Ycomment> getYcommentList(Integer page, Integer size) {
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createtime"));
		return repository.findAll(pageRequest);
	}

	@Override
	public Integer getCountNum(String type) {
		if("z".equals(type)){
			//select count(*) from ycomment where ytype='z'
			return repository.countNum("z");
		}else if("c".equals(type)){
			//select count(*) from ycomment where ytype='c' 
			return repository.countNum("c");
		}else if("coffee".equals(type)){
			//select sum(coffeenum) from ycomment
			return repository.sumCoffee();
		}
		return 0;
	}

	@Override
	public Ycomment findById(String id) {
		return repository.findOne(id);
	}

}
