package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.IndexorderbyinfoRepository;
import cn.yi.gather.v20.entity.Indexorderbyinfo;
import cn.yi.gather.v20.service.IIndexorderbyinfoService;

/**
 * 个人/团队展示排序
 * @author Lee.J.Eric
 *
 */
@Service("indexorderbyinfoServiceV20")
public class IndexorderbyinfoService implements IIndexorderbyinfoService{

	@Resource(name = "indexorderbyinfoRepositoryV20")
	private IndexorderbyinfoRepository repository;
	
	@Override
	public Indexorderbyinfo indexorderbyinfoSaveOrUpdate(
			Indexorderbyinfo indexorderbyinfo) {
		// TODO Auto-generated method stub
		return repository.save(indexorderbyinfo);
	}

	@Override
	public Indexorderbyinfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Indexorderbyinfo findByType(Integer type) {
		// TODO Auto-generated method stub
		return repository.findByIdtype(type);
	}

}
