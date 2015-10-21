package cn.yi.gather.v11.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.UserFocusRepository;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.entity.UserFocus;
import cn.yi.gather.v11.service.IUserFocusServiceV2;

/**
 * 用户关注
 * @author Lee.J.Eric
 * @time 2014年6月3日下午5:49:30
 */
@Service("userFocusServiceV2")
public class UserFocusServiceV2 implements IUserFocusServiceV2{
	
	@Resource(name = "userFocusRepositoryV2")
	private UserFocusRepository repository;

	@Override
	public UserFocus findByMeAndWho(User me, User who) {
		// TODO Auto-generated method stub
		return repository.findByMeAndWho(me, who);
	}

	@Override
	public UserFocus userFocusSaveOrUpdate(UserFocus userFocus) {
		// TODO Auto-generated method stub
		return repository.save(userFocus);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}

	@Override
	public Page<UserFocus> getuserFocusList(User me, Integer order,
			Integer flag, Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = null;
		if(order==1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "createdate"));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		}
		if(flag==0){//0主动关注
			return repository.findPageByMe(pageRequest, me);
		}else {//1被关注
			return repository.findPageByWho(pageRequest, me);
		}
	}

	@Override
	public List<UserFocus> findByMe(User me) {
		// TODO Auto-generated method stub
		return repository.findByMe(me);
	}
	
}
