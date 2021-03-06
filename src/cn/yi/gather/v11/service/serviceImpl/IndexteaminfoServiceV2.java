package cn.yi.gather.v11.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.IndexteaminfoRepository;
import cn.yi.gather.v11.entity.Indexteaminfo;
import cn.yi.gather.v11.service.IIndexteaminfoServiceV2;
/**
 * 团队展示
 * @author Lee.J.Eric
 *
 */
@Service("indexteaminfoServiceV2")
public class IndexteaminfoServiceV2 implements IIndexteaminfoServiceV2 {

	@Resource(name = "indexteaminfoRepositoryV2")
	private IndexteaminfoRepository repository;
	
	@Override
	public Indexteaminfo indexteaminfoSaveOrUpdate(Indexteaminfo indexteaminfo) {
		// TODO Auto-generated method stub
		return repository.save(indexteaminfo);
	}

	@Override
	public Indexteaminfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Indexteaminfo> getByIDList(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}

	@Override
	public List<Indexteaminfo> findByIdList(List<String> ids) {
		// TODO Auto-generated method stub
		List<Indexteaminfo> list = new ArrayList<Indexteaminfo>();
		for (String id : ids) {
			list.add(findById(id));
		}
		return list;
	}


}
