package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.ClasssortRepository;
import cn.yi.gather.v20.entity.Classsort;
import cn.yi.gather.v20.service.IClasssortService;

@Service("classsortServiceV20")
public class ClasssortService implements IClasssortService{
	
	private static Logger log = Logger.getLogger(ClasssortService.class);
	
	@Resource(name = "classsortRepositoryV20")
	private ClasssortRepository repository;

	@Override
	public List<Classsort> getAllClasssort() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<Classsort> getClasssortByids(String ids) {
		// TODO Auto-generated method stub
		String[] idStrings = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < idStrings.length; i++) {
			idList.add(Long.valueOf(idStrings[i]));
		}
		return repository.findByIdIn(idList);
	}

	@Override
	public List<Classsort> getClasssortsByList(List<Long> ids) {
		// TODO Auto-generated method stub
		return repository.findByIdIn(ids);
	}

	@Override
	public List<Classsort> getClasssortsByPid(Long pid) {
		// TODO Auto-generated method stub
		return repository.findByPidOrderByIdAsc(pid);
	}

	@Override
	public Classsort getClasssortsByID(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Classsort> getAllSecClassSort() {
		// TODO Auto-generated method stub
		return repository.findByPidNotOrderByIdAsc(888888888888L);
	}

}
