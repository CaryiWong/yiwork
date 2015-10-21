package cn.yi.gather.v11.service.serviceImpl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.JoinapplicationRepository;
import cn.yi.gather.v11.entity.Joinapplication;
import cn.yi.gather.v11.service.IJoinapplicationServiceV2;

/**
 * 入驻申请
 * @author Lee.J.Eric
 *
 */
@Service("joinapplicationServiceV2")
public class JoinapplicationServiceV2 implements IJoinapplicationServiceV2{

	@Resource(name = "joinapplicationRepositoryV2")
	private JoinapplicationRepository repository;
	
	
	@Override
	public Joinapplication joinapplicationSaveOrUpdate(Joinapplication joinapplication) {
		// TODO Auto-generated method stub
		return repository.save(joinapplication);
	}
	@Override
	public Joinapplication findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
	@Override
	public com.common.Page<Joinapplication> findJoinapplicationList(Integer page,
			Integer size, String keyword) {
		// TODO Auto-generated method stub
		com.common.Page<Joinapplication> result = new com.common.Page<Joinapplication>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		Page<Joinapplication> list = null;
		if(StringUtils.isNotBlank(keyword)){
			list = repository.findPageByTeamnameContainingIgnoreCase(pageRequest, "%"+keyword+"%");
		}else {
			list = repository.findAll(pageRequest);
		}
		result.setCurrentPage(page);
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount((int)list.getTotalElements());
		return result;
	}

}
