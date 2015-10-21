package cn.yi.gather.v11.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.IndexorderbyinfoRepository;
import cn.yi.gather.v11.entity.Indexorderbyinfo;
import cn.yi.gather.v11.service.IIndexorderbyinfoServiceV2;

/**
 * 个人/团队展示排序
 * @author Lee.J.Eric
 *
 */
@Service("indexorderbyinfoServiceV2")
public class IndexorderbyinfoServiceV2 implements IIndexorderbyinfoServiceV2{

	@Resource(name = "indexorderbyinfoRepositoryV2")
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
