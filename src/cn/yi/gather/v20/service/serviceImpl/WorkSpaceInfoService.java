package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.WorkSpaceInfoRepository;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Co_working_space;
import cn.yi.gather.v20.entity.WorkSpaceInfo;
import cn.yi.gather.v20.entity.WorkSpaceInfo.SpaceStatus;
import cn.yi.gather.v20.service.IWorkSpaceInfoService;

@Service("workSpaceInfoService")
public class WorkSpaceInfoService implements IWorkSpaceInfoService{

	@Resource(name="workSpaceInfoRepository")
	private WorkSpaceInfoRepository repository;
	
	@Override
	public WorkSpaceInfo saveOrUpdate(WorkSpaceInfo info) {
		return repository.save(info);
	}

	@Override
	public WorkSpaceInfo find(String id) {
		return repository.findOne(id);
	}

	@Override
	public List<WorkSpaceInfo> findAll() {
		return repository.findAll();
	}

	@Override
	public List<WorkSpaceInfo> findAll(final Integer spacestatus) {
		// TODO Auto-generated method stub
		Specification<WorkSpaceInfo> spec = new Specification<WorkSpaceInfo>() {
			@Override
			public Predicate toPredicate(Root<WorkSpaceInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("spacestatus"), spacestatus));
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Override
	public WorkSpaceInfo findByCode(String code) {
		return repository.findBySpacecode(code);
	}
	
	/**
	 * 根据状态 关键字分页该列表
	 */
	public com.common.Page<WorkSpaceInfo> workspaceList(final Integer page,final Integer size,final Integer status,final String keyword){
		
		com.common.Page<WorkSpaceInfo> result = new com.common.Page<WorkSpaceInfo>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createtime"));
		Specification<WorkSpaceInfo> spec = new Specification<WorkSpaceInfo>() {	
			@Override
			public Predicate toPredicate(Root<WorkSpaceInfo> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				//ps.add(cb.equal(root.<Integer>get("spacestatus"), status));  //状态
				Predicate p1=null;
				if(keyword!=null){
					Predicate or0 = cb.like(cb.lower(root.<String>get("spacename")), "%"+keyword.toLowerCase()+"%");
					Predicate or1 = cb.like(cb.lower(root.<String>get("spacecode")), "%"+keyword.toLowerCase()+"%");
					Predicate or2 = cb.like(cb.lower(root.<String>get("spaceaddress")), "%"+keyword.toLowerCase()+"%");
					Predicate or3 = cb.like(cb.lower(root.<String>get("spacetel")), "%"+keyword.toLowerCase()+"%");  //2015.5.5新增姓名搜索@kcm
					p1 = cb.or(or0,or1,or2,or3);
					ps.add(p1);
				}
				
				query.where(ps.toArray(new Predicate[ps.size()])).orderBy(cb.desc(root.get("createtime")));
				return query.getRestriction();
			}
		};
		Page<WorkSpaceInfo> users = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(users.getNumber());
		result.setPageSize(users.getSize());
		result.setResult(users.getContent());
		System.out.println(users.getSize());
		result.setTotalCount((int)users.getTotalElements());
		return result;	
	}
	
}
