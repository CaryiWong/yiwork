package cn.yi.gather.v20.service.serviceImpl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.DemandsRepository;
import cn.yi.gather.v20.dao.UserRepository;
import cn.yi.gather.v20.entity.Dcomment;
import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Demands.DemandDate;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IDemandsService;
import cn.yi.gather.v20.service.ILabelsService;

@Service("demandsServiceV20")
public class DemandsService implements IDemandsService{

	@Resource(name = "demandsRepositoryV20")
	private DemandsRepository demandsRepository;
	
	@Resource(name = "userRepositoryV20")
	private UserRepository userRepository;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;

	@Override
	public Demands createDemand(Demands demands) {
		demandsRepository.save(demands);
		return demandsRepository.findOne(demands.getId());
	}

	@Override
	public Demands getDemandinfo(String id) {
		return demandsRepository.findOne(id);
	}

	@Override
	public Demands updateDemand(Demands demands) {
		return demandsRepository.save(demands);
	}

	@Override
	public Page<Demands> getMyDemands(Integer page, Integer size,
			Integer orderby,final String uid) {
		PageRequest pageRequest=new PageRequest(page, size);
		if(orderby==1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "publishdate0"));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "publishdate0"));
		}
		Specification<Demands> sp=new Specification<Demands>() {
			@Override
			public Predicate toPredicate(Root<Demands> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p0 =cb.equal(root.<User>get("publishuser").get("id"), uid);
				query.where(cb.and(p0));
				return query.getRestriction();
			}
		};
		return demandsRepository.findAll(sp,pageRequest);
	}


	@Override
	public Page<Demands> getAllDemands(Integer page, Integer size,
			String orderby) {
		PageRequest pageRequest=new PageRequest(page, size);
		if("asc".equals(orderby)){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "publishdate0"));
		}else if("desc".equals(orderby)){
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "publishdate0"));
		}
		Specification<Demands> sp=new Specification<Demands>() {
			@Override
			public Predicate toPredicate(Root<Demands> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p0 =cb.equal(root.<Integer>get("isdel"), 0);
				Predicate p1 =cb.equal(root.<Integer>get("onsale"), 0);
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE,  -DemandDate.EFFECTIVE.value);
				Predicate p2 =cb.between(root.<Date>get("publishdate0"),calendar.getTime(),new Date());
				query.where(cb.and(p0,p1,p2));
				return query.getRestriction();
			}
		};
		return demandsRepository.findAll(sp,pageRequest);
	}

	@Deprecated
	@Override
	public Page<Demands> getDemandsBy(Integer page, Integer size,
			final Integer listtype,final String keyword,final String uid,Integer orderby) {
			// listtype -1全部，0  寻人  1 视频制作  2 发起活动  3 其他 4视频以外的
		PageRequest pageRequest=new PageRequest(page, size,new Sort(Direction.ASC, "publishdate0"));
		if(orderby!=null&&orderby==-1)
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "publishdate0"));
		Specification<Demands> sp=new Specification<Demands>() {
			@Override
			public Predicate toPredicate(Root<Demands> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("ischeck"), 1));
				if(StringUtils.isNotBlank(keyword)){ //搜索
					Predicate p0=cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					Predicate p1=cb.like(cb.lower(root.<User>get("publishuser").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					Labels labels = null;
					if(labels!=null){
						Predicate p2 = cb.isMember(labels,root.<List<Labels>>get("label"));
						Predicate p3 = cb.isMember(labels,root.<List<Labels>>get("makelabel"));
						Predicate p4 = cb.isMember(labels,root.<List<Labels>>get("formlabel"));
						Predicate p5 = cb.isMember(labels,root.<List<Labels>>get("peoplelabel"));
						Predicate p6 = cb.isMember(labels,root.<List<Labels>>get("findlabel"));
						Predicate p=cb.or(p0,p1,p2,p3,p4,p5,p6);
						ps.add(p);
					}else{
						Predicate p=cb.or(p0,p1);
						ps.add(p);
					}
				}
				if(listtype!=-1){
					if(listtype==4){
						Predicate p0=cb.notEqual(root.<Integer>get("demandstype"), 1);
						ps.add(p0);
					}else{
						Predicate p0=cb.equal(root.<Integer>get("demandstype"), listtype);
						ps.add(p0);
					}
				}
				if(uid!=null&&uid.length()>0){
					Predicate p0=cb.equal(root.<User>get("publishuser").<String>get("id"), uid);
					ps.add(p0);
				}
				if(ps!=null&&ps.size()>0){
					Predicate[] par=new Predicate[ps.size()];
					for (int i = 0; i < ps.size(); i++) {
						par[i]=ps.get(i);
					}
					query.where(par);
					return query.getRestriction();
				}else{
					return null;
				}
			}
		};
		if(sp!=null){
			return demandsRepository.findAll(sp,pageRequest);
		}else{
			return demandsRepository.findAll(pageRequest);
		}
	}

	@Override
	public Page<Demands> getDemandsByAdmin(Integer page, Integer size,
			final Integer status,final Integer demandtype,final String keyword,final String groups,final Integer ischeck) {
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "publishdate0"));
		Specification<Demands> sp=new Specification<Demands>() {
			@Override
			public Predicate toPredicate(Root<Demands> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicates=new ArrayList<Predicate>();
				if(keyword!=null&&keyword.length()>0){
					Predicate p0=cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					Predicate p1=cb.like(cb.lower(root.<User>get("publishuser").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					//Labels labels = labelsService.getLabelsByLabeltypeAndZname(0, keyword);
					Labels labels =null;
					if(labels!=null){
						Predicate p2 = cb.isMember(labels,root.<List<Labels>>get("label"));
						Predicate p3 = cb.isMember(labels,root.<List<Labels>>get("makelabel"));
						Predicate p4 = cb.isMember(labels,root.<List<Labels>>get("formlabel"));
						Predicate p5 = cb.isMember(labels,root.<List<Labels>>get("peoplelabel"));
						Predicate p6 = cb.isMember(labels,root.<List<Labels>>get("findlabel"));
						Predicate p=cb.or(p0,p1,p2,p3,p4,p5,p6);
						predicates.add(p);
					}else{
						Predicate p=cb.or(p0,p1);
						predicates.add(p);
					}
				}
				if(groups!=null&&groups.length()>0){
					List<Labels> labels = labelsService.getLabelsByids(groups);
					if(labels!=null&&labels.size()>0){
						Predicate[] par=new Predicate[labels.size()];
						for (int i = 0; i < labels.size(); i++) {
							par[i]=cb.isMember(labels.get(i),root.<List<Labels>>get("label"));
						}
						Predicate p=cb.or(par);
						predicates.add(p);
					}
				}
				if(demandtype!=-1){
					if(demandtype==4){
						Predicate p0=cb.notEqual(root.<Integer>get("demandstype"), 1);
						predicates.add(p0);
					}else{
						Predicate p0=cb.equal(root.<Integer>get("demandstype"), demandtype);
						predicates.add(p0);
					}
				}
				if(status!=null&&status!=-1){
					Predicate p0=cb.equal(root.<Integer>get("status"), status);
					predicates.add(p0);
				}
				if(ischeck!=null&&ischeck!=-1){
					Predicate p0=cb.equal(root.<Integer>get("ischeck"), ischeck);
					predicates.add(p0);
				}
				if(predicates!=null&&predicates.size()>0){
					Predicate[] par=new Predicate[predicates.size()];
					for (int i = 0; i < predicates.size(); i++) {
						par[i]=predicates.get(i);
					}
					query.where(par);
					return query.getRestriction();
				}else{
					return null;
				}
			}
		};
		if(sp!=null){
			return demandsRepository.findAll(sp,pageRequest);
		}else{
			return demandsRepository.findAll(pageRequest);
		}
	}

	@Override
	public Page<Demands> myDemandList(Integer page, Integer size,
			final String userid,final Integer status,final Integer flag, String listorderby) {
		PageRequest pageRequest=new PageRequest(page, size);
		if("asc".equals(listorderby)){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "publishdate0"));
		}else if("desc".equals(listorderby)){
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "publishdate0"));
		}
		if(1==flag){// 我发起
			Specification<Demands> spe=new Specification<Demands>() {
				@Override
				public Predicate toPredicate(Root<Demands> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					List<Predicate> ps=new ArrayList<Predicate>();
					//status 需求状态：-1：全部   1：正在解决 2：解决完成 3：未上架 4：未审核 5：已取消/已关闭
					if(-1==status){
						
					}else if(1==status){
						ps.add(cb.equal(root.<Integer>get("status"), 1));
					}else if(2==status){
						ps.add(cb.equal(root.<Integer>get("status"), 2));
					}else if(3==status){
						ps.add(cb.equal(root.<Integer>get("onsale"), 1));
					}else if(4==status){
						ps.add(cb.equal(root.<Integer>get("ischeck"), 0));
					}else if(5==status){
						ps.add(cb.equal(root.<Integer>get("isdel"), 1));
					}
					ps.add(cb.equal(root.<User>get("publishuser").<String>get("id"), userid));
					Predicate[] parr = new Predicate[ps.size()]; 
					ps.toArray(parr);
					query.where(parr);
					return query.getRestriction();
				}
			};
			return demandsRepository.findAll(spe,pageRequest);
		}else if(2==flag){//我参与
			Specification<Demands> spe=new Specification<Demands>() {
				@Override
				public Predicate toPredicate(Root<Demands> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					List<Predicate> ps=new ArrayList<Predicate>();
					//status 需求状态：-1：全部   1：正在解决 2：解决完成 3：未上架 4：未审核 5：已取消/已关闭
					if(-1==status){
						
					}else if(1==status){
						ps.add(cb.equal(root.<Integer>get("status"), 1));
					}else if(2==status){
						ps.add(cb.equal(root.<Integer>get("status"), 2));
					}else if(3==status){
						ps.add(cb.equal(root.<Integer>get("onsale"), 1));
					}else if(4==status){
						ps.add(cb.equal(root.<Integer>get("ischeck"), 0));
					}else if(5==status){
						ps.add(cb.equal(root.<Integer>get("isdel"), 1));
					}
					ps.add(cb.notEqual(root.<User>get("publishuser").<String>get("id"), userid));
					Root<Dcomment> dc_root=query.from(Dcomment.class);
					ps.add(cb.equal(dc_root.<User>get("user").<String>get("id"), userid));
					Join<Dcomment,Demands> ddjoin=dc_root.join(dc_root.getModel().getSingularAttribute("demands",Demands.class),JoinType.LEFT);
					ps.add(cb.equal(root.<String>get("id"),ddjoin.<String>get("id")));
					Predicate[] parr = new Predicate[ps.size()]; 
					ps.toArray(parr);
					query.where(parr);
					return query.distinct(true).getRestriction();
				}
			};
			return demandsRepository.findAll(spe,pageRequest);
		}
		return null;
	}

	@Override
	public List<Demands> findjoblist() {
		Specification<Demands> sp=new Specification<Demands>() {
			@Override
			public Predicate toPredicate(Root<Demands> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p0 =cb.equal(root.<Integer>get("isdel"), 0);
				Predicate p1 =cb.equal(root.<Integer>get("onsale"), 0);
				
				Calendar sdate = Calendar.getInstance();
				Calendar edate = Calendar.getInstance();
				
				sdate.add(Calendar.DATE,  -DemandDate.REMIND.value);
				edate.add(Calendar.DATE, -(DemandDate.REMIND.value-1));
				Predicate p2 =cb.between(root.<Date>get("publishdate0"),sdate.getTime(),edate.getTime());//第10天
				sdate.add(Calendar.DATE, -DemandDate.REMIND.value);
				edate.add(Calendar.DATE, -DemandDate.REMIND.value);
				Predicate p3 =cb.between(root.<Date>get("publishdate0"),sdate.getTime(),edate.getTime());//第20天
				sdate.add(Calendar.DATE, -DemandDate.REMIND.value);
				edate.add(Calendar.DATE, -DemandDate.REMIND.value);
				Predicate p4 =cb.between(root.<Date>get("publishdate0"),sdate.getTime(),edate.getTime());//第30天
				
				query.where(cb.and(p0,p1,cb.or(p2,p3,p4)));
				
				return query.getRestriction();
			}
		};
		return demandsRepository.findAll(sp);
	}
	
	@Override
	public Page<Demands> searchDemandList(Integer page, Integer size,String orderby,final String searchkey,final String demandtype) {
		PageRequest pageRequest=new PageRequest(page, size);
		if("asc".equals(orderby)){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "publishdate0"));
		}else if("desc".equals(orderby)){
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "publishdate0"));
		}
		Specification<Demands> sp=new Specification<Demands>() {
			@Override
			public Predicate toPredicate(Root<Demands> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("isdel"), 0));
				ps.add(cb.equal(root.<Integer>get("onsale"), 0));
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -DemandDate.EFFECTIVE.value);
				ps.add(cb.between(root.<Date>get("publishdate0"),calendar.getTime(),new Date()));
				if(searchkey!=null&&searchkey.length()>0){
					ps.add(cb.like(cb.lower(root.<String>get("texts")), "%"+searchkey.toLowerCase()+"%"));
				}
				if(demandtype!=null&&demandtype.length()>0){
					ps.add(cb.equal(root.<String>get("demandtype"), demandtype));
				}
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return demandsRepository.findAll(sp,pageRequest);
	}

}
