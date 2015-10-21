package cn.yi.gather.v11.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v11.dao.UserRepository;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.ILabelsServiceV2;
import cn.yi.gather.v11.service.IUserServiceV2;

/**
 * 用户业务
 * @author Lee.J.Eric
 * @time 2014年5月28日下午7:04:14
 */
@Service("userServiceV2")
public class UserServiceV2 implements IUserServiceV2{
	
	@Resource(name = "userRepositoryV2")
	private UserRepository repository;
	
	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;

	@Override
	public User findByusernameAndPWD(String password, String username) {
		// TODO Auto-generated method stub
		return repository.findByusernameAndPWD(password, username);
	}

	@Override
	public User userSaveOrUpdate(User user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}

	@Override
	public Boolean checkForEmail(String email) {
		// TODO Auto-generated method stub
		User user = repository.findByEmail(email);
		if(user==null)
			return false;
		return true;
	}

	@Override
	public Boolean checkForNickname(String nickname) {
		// TODO Auto-generated method stub
		User user = repository.findByNicknameOrEmailOrTelnum(nickname, nickname, nickname);
		if(user==null)
			return false;
		return true;
	}

	@Override
	public Boolean checkForTelephone(String tel) {
		// TODO Auto-generated method stub
		User user = repository.findByTelnum(tel);
		if(user==null)
			return false;
		return true;
	}

	@Override
	public Boolean checkForUnum(String unum) {
		// TODO Auto-generated method stub
		User user = repository.findByUnum(unum);
		if(user==null)
			return false;
		return true;
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<User> getIndexUser(Integer page, Integer size,Integer ifindex) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size);
		return repository.findPageByIfindex(pageRequest,ifindex);
	}

	@Override
	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}

	@Override
	public Page<User> getVipList(Integer page, Integer size,
			Integer orderby, final Integer listtype, final String keyword) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		String orderString[] = new String[2];
		if(listtype==0){//会员展示
			orderString[0] = "vipshowsettime";
			orderString[1] = "createdate1";
		}else if (listtype==1) {//空间展示
			orderString[0] = "spacesettime";
			orderString[1] = "createdate1";
		}else {
			orderString[0] = "id";
			orderString[1] = "createdate1";
		}
		if(orderby==1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, orderString));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, orderString));
		}
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = cb.equal(root.<Integer>get("isdel"), 0);
				Predicate p4 = cb.between(root.<Integer>get("root"), 1, 2);
				Predicate p3 = null;
				if(listtype==0){//会员展示
					p3 = cb.equal(root.<Integer>get("ifvipshow"), 1);
				}else if(listtype == 1){//空间会员
					p3 = cb.equal(root.<Integer>get("ifspace"), 1);
				}else {
					p3 = cb.or(cb.equal(root.<Integer>get("ifvipshow"), 1),cb.equal(root.<Integer>get("ifspace"), 1));
				}	
				if(StringUtils.isNotBlank(keyword)){
					Predicate p1 = cb.like(root.<String>get("nickname"), "%"+keyword+"%");
					Predicate p2 = cb.like(root.<String>get("realname"), "%"+keyword+"%");
					query.where(cb.and(p0,p4,p3,cb.or(p1,p2)));
				}else {
					query.where(cb.and(p0,p4,p3));
				}
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public User findByIdAndPassword(String id, String password) {
		// TODO Auto-generated method stub
		return repository.findByIdAndPassword(id, password);
	}

	@Override
	public com.common.Page<User> getUserListForPage(Integer page,Integer size,
			final Integer charge, final Integer sex, final String userNO, final String keyword,
			final List<Long> labelList,final Integer spaceshow,final Integer vipshow) {
		// TODO Auto-generated method stub
		com.common.Page<User> result = new com.common.Page<User>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "unum"));
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				if(spaceshow!=null&&spaceshow!=-1){
					ps.add(cb.equal(root.<Integer>get("ifspace"), spaceshow));
				}
				if(vipshow!=null&&vipshow!=-1){
					ps.add(cb.equal(root.<Integer>get("ifvipshow"), vipshow));
				}
				if(charge!=null&&charge!=-1){
					ps.add(cb.equal(root.<Integer>get("root"), charge));
				}
				if(sex!=null&&sex!=-1){
					ps.add(cb.equal(root.<Integer>get("sex"), sex));
				}
				if(userNO!=null&&!userNO.isEmpty()){
					ps.add(cb.like(cb.lower(root.<String>get("unum")), "%"+userNO.toLowerCase()+"%"));
				}
				if(StringUtils.isNotBlank(keyword)){
					Predicate or0 = cb.like(cb.lower(root.<String>get("email")), "%"+keyword.toLowerCase()+"%");
					Predicate or1 = cb.like(cb.lower(root.<String>get("telnum")), "%"+keyword.toLowerCase()+"%");
					Predicate or2 = cb.like(cb.lower(root.<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					ps.add(cb.or(or0,or1,or2));
				}
				if(labelList!=null&&labelList.size()>0){
					List<Labels> list = labelsServiceV2.getLabelsByList(labelList);
					Predicate[] ors = new Predicate[list.size()];
					for (int i = 0; i < list.size(); i++) {
						ors[i] = cb.isMember(list.get(i), root.<List<Labels>>get("label"));
					}
					ps.add(cb.or(ors));
				}
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr));
				return query.distinct(true).getRestriction();
			}
		};
		Page<User> users = repository.findAll(spec, pageRequest);
	
		result.setCurrentPage(page);
		result.setCurrentCount(users.getNumber());
		result.setPageSize(users.getSize());
		result.setResult(users.getContent());
		result.setTotalCount((int)users.getTotalElements());
		return result;
	}

	@Override
	public List<User> getUserListByRoot(final Integer root) {
		// TODO Auto-generated method stub
		Sort sort = new Sort(Direction.DESC, "createdate1");
		Specification<User> spec = new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> userRoot, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = null;
				if(root!=-1){
					p0 = cb.equal(userRoot.<Integer>get("root"), root);
				}else {
					p0 = cb.isNotNull(userRoot.<String>get("id"));
				}
				query.where(p0);
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, sort);
	}

	@Override
	public Page<User> findUserList(Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size);
		return repository.findAll(pageRequest);
	}

	@Override
	public User findByAccount(String keyword) {
		// TODO Auto-generated method stub
		return repository.findByNicknameOrEmailOrTelnum(keyword, keyword, keyword);
	}

	@Transactional
	@Override
	public void checkoutalluser() {
		// TODO Auto-generated method stub
		repository.checkoutalluser();
	}

}
