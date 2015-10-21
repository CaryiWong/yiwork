package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.ActivityJoinRepository;
import cn.yi.gather.v20.dao.UserRepository;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.entity.EmailTemplate;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.entity.WorkSpaceInfo;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.IAppversionService;
import cn.yi.gather.v20.service.IEmailTemplateService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IVipNumLogService;

import com.common.R;
import com.tools.utils.EmailEntity;
import com.tools.utils.EmailUtil;

/**
 * 用户业务
 * @author Lee.J.Eric
 * @time 2014年5月28日下午7:04:14
 */
@Service("userServiceV20")
public class UserService implements IUserService{
	
	@Resource(name = "userRepositoryV20")
	private UserRepository repository;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "activityJoinServiceV20")
	private IActivityJoinService activityJoinService;
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "activityJoinRepositoryV20")
	private ActivityJoinRepository actjoinrepository;
	
	@Resource(name = "vipNumLogServiceV20")
	private IVipNumLogService vipNumLogService;
	
	@Resource(name = "entityManagerFactoryV20")
	private EntityManagerFactory emf;
	
	@Resource(name = "emailTemplateServiceV20")
	private IEmailTemplateService emailTemplateService;
	
	@Resource(name = "appversionServiceV20")
	private IAppversionService appversionService;

	@Override
	public User findByusernameAndPWD(String password, String username) {
		// TODO Auto-generated method stub
		return repository.findByusernameAndPWD(password, username);
	}

	@Override
	public User userSaveOrUpdate(User user) {
		// TODO Auto-generated method stub
		repository.save(user);
		return findById(user.getId());
	}

	@Override
	public void saveOrUpdate(List<User> entities) {
		// TODO Auto-generated method stub
		repository.save(entities);
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
	public Boolean checkForIdnum(String idnum) {
		// TODO Auto-generated method stub
		if(idnum==null||idnum.equals("")){
			return true;
		}
		User user = repository.findByIcnum(idnum);
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
			orderString[1] = "createdate";
		}else if (listtype==1) {//空间展示
			orderString[0] = "spacesettime";
			orderString[1] = "createdate";
		}else {
			orderString[0] = "id";
			orderString[1] = "createdate";
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
			final List<Long> labelList) {
		// TODO Auto-generated method stub
		com.common.Page<User> result = new com.common.Page<User>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "unum"));
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = null;
				Predicate p1 = null;
				Predicate p2 = null;
				Predicate p3 = null;
				Predicate p4 = null;
				if(charge!=null&&charge!=-1){
					p0 = cb.equal(root.<Integer>get("root"), charge);
				}else {
					p0 = cb.isNotNull(root.<String>get("id"));
				}
				if(sex!=null&&sex!=-1){
					p1 = cb.equal(root.<Integer>get("sex"), sex);
				}else {
					p1 = cb.isNotNull(root.<String>get("id"));
				}
				if(userNO!=null&&!userNO.isEmpty()){
					p2 = cb.like(cb.lower(root.<String>get("unum")), "%"+userNO.toLowerCase()+"%");
				}else {
					p2 = cb.isNotNull(root.<String>get("id"));
				}
				if(StringUtils.isNotBlank(keyword)){
					Predicate or0 = cb.like(cb.lower(root.<String>get("email")), "%"+keyword.toLowerCase()+"%");
					Predicate or1 = cb.like(cb.lower(root.<String>get("telnum")), "%"+keyword.toLowerCase()+"%");
					Predicate or2 = cb.like(cb.lower(root.<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					Predicate or3 = cb.like(cb.lower(root.<String>get("realname")), "%"+keyword.toLowerCase()+"%");  //2015.5.5新增姓名搜索@kcm
					p3 = cb.or(or0,or1,or2,or3);
				}else {
					p3 = cb.isNotNull(root.<String>get("id"));
				}
				if(labelList!=null&&labelList.size()>0){
					List<Labels> list = labelsService.getLabelsByList(labelList);
					Predicate[] ors = new Predicate[list.size()];
					for (int i = 0; i < list.size(); i++) {
						ors[i] = cb.isMember(list.get(i), root.<List<Labels>>get("label"));
					}
					p4 = cb.or(ors);
				}else {
					p4 = cb.isNotNull(root.<String>get("id"));
				}
				query.where(cb.and(p0,p1,p2,p3,p4));
				return query.getRestriction();
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
		Sort sort = new Sort(Direction.DESC, "createdate");
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

	@Override
	public Page<User> findUserList(Integer page, Integer size,final String listtype,
			final String listwhere,final String keyword,final String listorderby) {
		PageRequest pageRequest = new PageRequest(page, size);
		if(!"act".equals(listwhere)){
			String orderString[] = new String[1];
			orderString[0] = "createdate";
			if("asc".equals(listorderby)){
				pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, orderString));
			}else if("desc".equals(listorderby)){
				pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, orderString));
			}
		}
		if("label".equals(listwhere)){
			Labels labels= labelsService.findByID(Long.parseLong(keyword));
			if(labels!=null){
			final String label_type= labels.getLabeltype();
			Specification<User> spec = new Specification<User>(){
				@Override
				public Predicate toPredicate(Root<User> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> ps = new ArrayList<Predicate>();
					if("all".equals(listtype)){
						ps.add(cb.notEqual(root.<Integer>get("root"), 0));
					}else if("vip".equals(listtype)){
						ps.add(cb.between(root.<Integer>get("root"), 1, 2));
					}
					if(label_type.equals("job")){
						Join<User,Labels> lbJoin=root.join("job");
						ps.add(cb.equal(lbJoin.<Long>get("id"), Long.parseLong(keyword)));
					}else if(label_type.equals("favourite")){
						Join<User,Labels> lbfavourite=root.join("favourite");
						ps.add(cb.equal(lbfavourite.<Long>get("id"), Long.parseLong(keyword)));
					}else if(label_type.equals("focus")){
						Join<User,Labels> lbfocus=root.join("focus");
						ps.add(cb.equal(lbfocus.<Long>get("id"), Long.parseLong(keyword)));
					}
							
					Predicate[] parr = new Predicate[ps.size()]; 
					ps.toArray(parr);
					query.where(parr);
					return query.distinct(true).getRestriction();
					}
				};
				return repository.findAll(spec, pageRequest);
			}
		}else if("act".equals(listwhere)){
			Specification<User> spec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> ps = new ArrayList<Predicate>();
					Root<Eventlog> e_root=query.from(Eventlog.class);
					ps.add(cb.equal(e_root.get("acttype"), "activity"));//是活动
					ps.add(cb.equal(e_root.get("eventtype"), "join"));//是参加
					ps.add(cb.equal(e_root.<Activity>get("activity").<String>get("id"), keyword));//活动ID
					Join<Eventlog,User> ujoin=e_root.join(e_root.getModel().getSingularAttribute("user",User.class),JoinType.LEFT);
					ps.add(cb.equal(ujoin.<String>get("id"), root.<String>get("id")));
					//Root<ActivityJoin> rootact=query.from(ActivityJoin.class);
					//ps.add(cb.equal(rootact.<Activity>get("activity").<String>get("id"), keyword));
					//Join<ActivityJoin,User> userJoin =rootact.join(rootact.getModel().getSingularAttribute("user",User.class),JoinType.LEFT);
					//ps.add(cb.equal(userJoin.<String>get("id"), root.<String>get("id")));
					if("all".equals(listtype)){
						ps.add(cb.notEqual(root.<Integer>get("root"), 0));
					}else if("vip".equals(listtype)){
						ps.add(cb.between(root.<Integer>get("root"), 1, 2));
					}
					Predicate[] parr = new Predicate[ps.size()]; 
					ps.toArray(parr);
					if("asc".equals(listorderby)){
						query.where(parr).orderBy(cb.asc(e_root.<Date>get("updatetime")));
					}else if("desc".equals(listorderby)){
						query.where(parr).orderBy(cb.desc(e_root.<Date>get("updatetime")));
					}else{
						query.where(parr);
					}
					return query.distinct(true).getRestriction();
				}
			};
			return repository.findAll(spec, pageRequest);
		}else if("search".equals(listwhere)){
			Specification<User> spec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					List<Predicate> ps=new ArrayList<Predicate>();
					ps.add(cb.equal(root.<Integer>get("isdel"), 0));
					if("all".equals(listtype)){
						ps.add(cb.notEqual(root.<Integer>get("root"), 0));
					}else if("vip".equals(listtype)){
						ps.add(cb.between(root.<Integer>get("root"), 1, 2));
					}
					if(StringUtils.isNotBlank(keyword)){
						Predicate p1 = cb.like(root.<String>get("nickname"), "%"+keyword+"%");
						Predicate p2 = cb.like(root.<String>get("realname"), "%"+keyword+"%");
						ps.add(cb.or(p1,p2));
					}
					Predicate[] parr=new Predicate[ps.size()];
					ps.toArray(parr);
					query.where(cb.and(parr));
					return query.getRestriction();
				}
			};
			return repository.findAll(spec, pageRequest);
		}else if("nowhere".equals(listwhere)){
			Specification<User> spec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					List<Predicate> ps=new ArrayList<Predicate>();
					ps.add(cb.equal(root.<Integer>get("isdel"), 0));
					if("all".equals(listtype)){
						ps.add(cb.notEqual(root.<Integer>get("root"), 0));
					}else if("vip".equals(listtype)){
						ps.add(cb.between(root.<Integer>get("root"), 1, 2));
					}
					Predicate[] parr=new Predicate[ps.size()];
					ps.toArray(parr);
					query.where(cb.and(parr));
					return query.getRestriction();
				}
			};
			return repository.findAll(spec, pageRequest);
		}
		return null;
	}

	@Override
	public Page<User> spaceuserlist(Integer page, Integer size,
			final String listtype, String listorderby) {
		PageRequest pageRequest = new PageRequest(page, size);
		String orderString[] = new String[1];
		orderString[0] = "spacesettime";
		if("asc".equals(listorderby)){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, orderString));
		}else if("desc".equals(listorderby)){
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, orderString));
		}
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("isdel"), 0));
				ps.add(cb.equal(root.<Integer>get("ifspace"), 1));
				if("all".equals(listtype)){
					ps.add(cb.notEqual(root.<Integer>get("root"), 0));
				}else if("vip".equals(listtype)){
					ps.add(cb.between(root.<Integer>get("root"), 1, 2));
				}
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
		
	}

	@Transactional
	@Override
	public void checkoutalluser() {
		// TODO Auto-generated method stub
		repository.checkoutalluser();
	}

	@Override
	public User findByTelnum(String telnum) {
		// TODO Auto-generated method stub
		return repository.findByTelnum(telnum);
	}
	
	@Override
	public User findByUnum(String unum) {
		// TODO Auto-generated method stub
		return repository.findByUnum(unum);
	}

	@Override
	public long countOnlineUser() {
		// TODO Auto-generated method stub
		Specification<User> spec = new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				query.where(cb.equal(root.<Integer>get("ifspace"), 1));
				return query.getRestriction();
			}
		};
		return repository.count(spec);
	}

	@Override
	public List<User> findByRootLTAndVipenddateLT(final Integer r,final Date date) {
		// TODO Auto-generated method stub
		Specification<User> spec = new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.lt(root.<Integer>get("root"), r));//会员
				ps.add(cb.lessThan(root.<Date>get("vipenddate"), date));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Transactional
	@Override
	public void userOverdue(final Date endDate) {
		// TODO Auto-generated method stub
		Specification<User> spec = new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.lessThan(root.<Integer>get("root"), UserRoot.OVREDUE.getCode()));
				ps.add(cb.lessThan(root.<Date>get("vipenddate"), endDate));
				ps.add(cb.notEqual(root.<String>get("unum"), "00000"));//小Yi永不过期
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		List<User> users = repository.findAll(spec);
//		List<String> numLogs = new ArrayList<String>();//会员号回收
		for (int i = 0; i < users.size(); i++) {
			users.get(i).setRoot(UserRoot.OVREDUE.getCode());//过期会员权限
//			numLogs.add(users.get(i).getUnum());
//			users.get(i).setUnum(null);//不收回旧号
		}
//		vipNumLogService.recycleVipNO(numLogs);//暂不回收会员号
		repository.save(users);
	}
	
	
	@Override
	public void userExpire() {
		// TODO Auto-generated method stub
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.HOUR_OF_DAY, 0);
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.lessThan(root.<Integer>get("root"), UserRoot.OVREDUE.getCode()));
				ps.add(cb.notEqual(root.<String>get("unum"), "00000"));//小Yi永不过期
				
				Date da_begin = c.getTime();
				c.add(Calendar.DATE, 1);
				Date da_end = c.getTime();
				
				c.add(Calendar.DATE, -1);//
				c.add(Calendar.WEEK_OF_YEAR, 1);//提前一个星期提醒
				Date we_begin = c.getTime();
				c.add(Calendar.DATE, 1);
				Date we_end = c.getTime();
				
				c.add(Calendar.WEEK_OF_YEAR, -1);//
				c.add(Calendar.DATE, -1);
				c.add(Calendar.MONTH, 1);//提前一个月提醒
				Date mon_begin = c.getTime();
				c.add(Calendar.DATE, 1);
				Date mon_end = c.getTime();
				
				ps.add(cb.or(
						cb.and(cb.greaterThanOrEqualTo(root.<Date>get("vipenddate"),da_begin), cb.lessThan(root.<Date>get("vipenddate"), da_end)),
						cb.and(cb.greaterThanOrEqualTo(root.<Date>get("vipenddate"),we_begin), cb.lessThan(root.<Date>get("vipenddate"), we_end)),
						cb.and(cb.greaterThanOrEqualTo(root.<Date>get("vipenddate"),mon_begin), cb.lessThan(root.<Date>get("vipenddate"), mon_end))
						));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		List<User> users = repository.findAll(spec);
		if(users!=null&&users.size()>0){
			EmailTemplate et = emailTemplateService.findByCode("expire");//得到email模版
			Appversion ios_app = appversionService.getLastedAppversionByPlatform("ios", 1, "1.0");
			Appversion and_app = appversionService.getLastedAppversionByPlatform("android", 1, "1.0");
			for (User user : users) {
				try {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(user.getVipenddate());
					List<String> receivers = new ArrayList<String>();
					receivers.add(user.getEmail());
					final EmailEntity mail = new EmailEntity();
					mail.setCharset("utf8");
					mail.setHostName(R.Common.EMAIL_YIGATHER_SMTP);
					mail.setAccount(R.Common.EMAIL_YIGATHER_ACCOUNT);
					mail.setPassword(R.Common.EMAIL_YIGATHER_PASSWORD);
					mail.setFromName("一起开工社区");
					mail.setReceivers(receivers);
					mail.setTLS(true);
					mail.setTitle("一起开工社区会员续费通知   Please renew your Yi-Gather membership");
					String contentString = et.getContents();
					contentString = contentString.replaceAll("\\{\\{iosLink\\}\\}", ios_app.getUrl());
					contentString = contentString.replaceAll("\\{\\{androidLink\\}\\}", and_app.getUrl());
					contentString = contentString.replaceAll("\\{\\{xiaoyiCode\\}\\}", R.Common.BASEPATH+"images/email_xiaoyi_code.jpg");
					contentString = contentString.replaceAll("\\{\\{logo\\}\\}", R.Common.BASEPATH+"images/email_logo.jpg");
					contentString = contentString.replaceAll("\\{\\{line\\}\\}", R.Common.BASEPATH+"images/email_line.jpg");
					contentString = contentString.replaceAll("\\{\\{year\\}\\}", calendar.get(Calendar.YEAR)+"");
					contentString = contentString.replaceAll("\\{\\{month\\}\\}", (calendar.get(Calendar.MONTH)+1)+"");
					contentString = contentString.replaceAll("\\{\\{day\\}\\}", calendar.get(Calendar.DATE)+"");
					contentString = contentString.replaceAll("\\{\\{userId\\}\\}", user.getUnum());
					contentString = contentString.replaceAll("\\{\\{userName\\}\\}", user.getNickname());
					contentString = contentString.replaceAll("\\{\\{iosCode\\}\\}", R.Common.BASEPATH+"v20/download/generate_qrcode?w=300&h=300&path="+ios_app.getUrl());
					contentString = contentString.replaceAll("\\{\\{androidCode\\}\\}", R.Common.BASEPATH+"v20/download/generate_qrcode?w=300&h=300&path="+and_app.getUrl());
					mail.setContent(contentString);
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							EmailUtil emailUtil = new EmailUtil();
							emailUtil.sendEmail(mail);
						}
					}).start();
					
				} catch (Exception e) {
					// TODO: handle exception
//					e.printStackTrace();
					Log.error("userExpire---exception:"+e);
				}
			}
		}
	}

	
	@Override
	public void newUserEmail(User user) {
		// TODO Auto-generated method stub
		EmailTemplate et = emailTemplateService.findByCode("new");//得到email模版
		Appversion ios_app = appversionService.getLastedAppversionByPlatform("ios", 1, "1.0");
		Appversion and_app = appversionService.getLastedAppversionByPlatform("android", 1, "1.0");
		try {
			List<String> receivers = new ArrayList<String>();
			receivers.add(user.getEmail());
			final EmailEntity mail = new EmailEntity();
			mail.setCharset("utf8");
			mail.setHostName(R.Common.EMAIL_YIGATHER_SMTP);
			mail.setAccount(R.Common.EMAIL_YIGATHER_ACCOUNT);
			mail.setPassword(R.Common.EMAIL_YIGATHER_PASSWORD);
			mail.setFromName("一起开工社区");
			mail.setReceivers(receivers);
			mail.setTLS(true);
			mail.setTitle("欢迎来到一起开工社区-Welcome to The Yi-Gather Community");
			String contentString = et.getContents();
			contentString = contentString.replaceAll("\\{\\{iosLink\\}\\}", ios_app.getUrl());
			contentString = contentString.replaceAll("\\{\\{androidLink\\}\\}", and_app.getUrl());
			contentString = contentString.replaceAll("\\{\\{xiaoyiCode\\}\\}", R.Common.BASEPATH+"images/email_xiaoyi_code.jpg");
			contentString = contentString.replaceAll("\\{\\{logo\\}\\}", R.Common.BASEPATH+"images/email_logo.jpg");
			contentString = contentString.replaceAll("\\{\\{line\\}\\}", R.Common.BASEPATH+"images/email_line.jpg");
			contentString = contentString.replaceAll("\\{\\{userId\\}\\}", user.getUnum());
			contentString = contentString.replaceAll("\\{\\{userName\\}\\}", user.getNickname());
			contentString = contentString.replaceAll("\\{\\{iosCode\\}\\}", R.Common.BASEPATH+"v20/download/generate_qrcode?w=300&h=300&path="+ios_app.getUrl());
			contentString = contentString.replaceAll("\\{\\{androidCode\\}\\}", R.Common.BASEPATH+"v20/download/generate_qrcode?w=300&h=300&path="+and_app.getUrl());
			mail.setContent(contentString);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					EmailUtil emailUtil = new EmailUtil();
					emailUtil.sendEmail(mail);
				}
			}).start();
			
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			Log.error("newUserEmail---exception:"+e);
		}
	}

	
	
	@Override
	public void renewalEmail(User user) {
		// TODO Auto-generated method stub
		EmailTemplate et = emailTemplateService.findByCode("renewal");//得到email模版
		Appversion ios_app = appversionService.getLastedAppversionByPlatform("ios", 1, "1.0");
		Appversion and_app = appversionService.getLastedAppversionByPlatform("android", 1, "1.0");
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(user.getVipenddate());
			List<String> receivers = new ArrayList<String>();
			receivers.add(user.getEmail());
			final EmailEntity mail = new EmailEntity();
			mail.setCharset("utf8");
			mail.setHostName(R.Common.EMAIL_YIGATHER_SMTP);
			mail.setAccount(R.Common.EMAIL_YIGATHER_ACCOUNT);
			mail.setPassword(R.Common.EMAIL_YIGATHER_PASSWORD);
			mail.setFromName("一起开工社区");
			mail.setReceivers(receivers);
			mail.setTLS(true);
			mail.setTitle("一起开工社区会籍续费成功，感谢您对我们的支持！");
			String contentString = et.getContents();
			contentString = contentString.replaceAll("\\{\\{iosLink\\}\\}", ios_app.getUrl());
			contentString = contentString.replaceAll("\\{\\{androidLink\\}\\}", and_app.getUrl());
			contentString = contentString.replaceAll("\\{\\{xiaoyiCode\\}\\}", R.Common.BASEPATH+"images/email_xiaoyi_code.jpg");
			contentString = contentString.replaceAll("\\{\\{logo\\}\\}", R.Common.BASEPATH+"images/email_logo.jpg");
			contentString = contentString.replaceAll("\\{\\{line\\}\\}", R.Common.BASEPATH+"images/email_line.jpg");
			contentString = contentString.replaceAll("\\{\\{year\\}\\}", calendar.get(Calendar.YEAR)+"");
			contentString = contentString.replaceAll("\\{\\{month\\}\\}", (calendar.get(Calendar.MONTH)+1)+"");
			contentString = contentString.replaceAll("\\{\\{day\\}\\}", calendar.get(Calendar.DATE)+"");
			contentString = contentString.replaceAll("\\{\\{userId\\}\\}", user.getUnum());
			contentString = contentString.replaceAll("\\{\\{userName\\}\\}", user.getNickname());
			contentString = contentString.replaceAll("\\{\\{iosCode\\}\\}", R.Common.BASEPATH+"v20/download/generate_qrcode?w=300&h=300&path="+ios_app.getUrl());
			contentString = contentString.replaceAll("\\{\\{androidCode\\}\\}", R.Common.BASEPATH+"v20/download/generate_qrcode?w=300&h=300&path="+and_app.getUrl());
			mail.setContent(contentString);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					EmailUtil emailUtil = new EmailUtil();
					emailUtil.sendEmail(mail);
				}
			}).start();
			
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			Log.error("newUserEmail---exception:"+e);
		}
	}

	public User randomuser(final String userid) {
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("isdel"), 0));
				ps.add(cb.or(cb.equal(root.<Integer>get("root"), UserRoot.VIP.getCode()),cb.equal(root.<Integer>get("root"), UserRoot.MANAGER.getCode())));
//				ps.add(cb.equal(root.<Integer>get("root"),2));
				ps.add(cb.equal(root.<Integer>get("ifspace"),1));//分享咖啡->在空间会员
				ps.add(cb.notEqual(root.<String>get("id"),userid));
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr));
				return query.getRestriction();
			}
		};
		 
		List<User> ulist= repository.findAll(spec);
		if(ulist!=null&&ulist.size()>0){
			Random random = new Random();
			int num=random.nextInt(ulist.size());
			return ulist.get(num);
		}
		return null;
	}
	
	@Override
	public List<User> findUserBylabel(final Long lbid,final String lbtype) {
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
			List<Predicate> ps = new ArrayList<Predicate>();
			
			if(lbtype.equals("job")){
				Join<User,Labels> lbJoin=root.join("job");
				ps.add(cb.equal(lbJoin.<Long>get("id"), lbid));
			}else if(lbtype.equals("favourite")){
				Join<User,Labels> lbfavourite=root.join("favourite");
				ps.add(cb.equal(lbfavourite.<Long>get("id"), lbid));
			}else if(lbtype.equals("focus")){
				Join<User,Labels> lbfocus=root.join("focus");
				ps.add(cb.equal(lbfocus.<Long>get("id"), lbid));
			}
					
			Predicate[] parr = new Predicate[ps.size()]; 
			ps.toArray(parr);
			query.where(parr);
			return query.distinct(true).getRestriction();
			}
		};
		 
		return repository.findAll(spec);
	}
	
	@Override
	public void updateLabelsAllUsers(final Long lbid,final Integer uptype,final Long taglbid,String labeltype) {
		EntityManager em = emf.createEntityManager();
		StringBuffer sql = new StringBuffer();
		if(uptype==1){
		sql.append("update ");
		if(labeltype.equals("job")){
			sql.append(" ref_user_job ");
		}else if(labeltype.equals("focus")){
			sql.append(" ref_user_focus ");
		}else if(labeltype.equals("favourite")){
			sql.append(" ref_user_favourite ");
		}
		sql.append(" set labels_id=");
		sql.append("'");
		sql.append(taglbid);
		sql.append("'");
		sql.append(" where labels_id=");
		sql.append("'");
		sql.append(lbid);
		sql.append("'");
		}else{
			sql.append("delete from ");
			if(labeltype.equals("job")){
				sql.append(" ref_user_job ");
			}else if(labeltype.equals("focus")){
				sql.append(" ref_user_focus ");
			}else if(labeltype.equals("favourite")){
				sql.append(" ref_user_favourite ");
			}
			sql.append(" where labels_id=");
			sql.append("'");
			sql.append(lbid);
			sql.append("'");
		}
		em.getTransaction().begin();
		Query query1 = em.createNativeQuery(sql.toString(),Labels.class);
		query1.executeUpdate();
		em.getTransaction().commit();
		em.clear();
		em.close();
		 
	}
	
	@Override
	public com.common.Page<User> getUserListForPageAndSpace(Integer page,Integer size,
			final Integer charge, final Integer sex, final String userNO, final String keyword,
			final List<Long> labelList ,final String spaceid) {
		// TODO Auto-generated method stub
		com.common.Page<User> result = new com.common.Page<User>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "unum"));
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = null;
				Predicate p1 = null;
				Predicate p2 = null;
				Predicate p3 = null;
				Predicate p4 = null;
				Predicate p5 = null;
				if(charge!=null&&charge!=-1){
					p0 = cb.equal(root.<Integer>get("root"), charge);
				}else {
					p0 = cb.isNotNull(root.<String>get("id"));
				}
				if(sex!=null&&sex!=-1){
					p1 = cb.equal(root.<Integer>get("sex"), sex);
				}else {
					p1 = cb.isNotNull(root.<String>get("id"));
				}
				if(userNO!=null&&!userNO.isEmpty()){
					p2 = cb.like(cb.lower(root.<String>get("unum")), "%"+userNO.toLowerCase()+"%");
				}else {
					p2 = cb.isNotNull(root.<String>get("id"));
				}
				if(StringUtils.isNotBlank(keyword)){
					Predicate or0 = cb.like(cb.lower(root.<String>get("email")), "%"+keyword.toLowerCase()+"%");
					Predicate or1 = cb.like(cb.lower(root.<String>get("telnum")), "%"+keyword.toLowerCase()+"%");
					Predicate or2 = cb.like(cb.lower(root.<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					Predicate or3 = cb.like(cb.lower(root.<String>get("realname")), "%"+keyword.toLowerCase()+"%");  //2015.5.5新增姓名搜索@kcm
					p3 = cb.or(or0,or1,or2,or3);
				}else {
					p3 = cb.isNotNull(root.<String>get("id"));
				}
				if(labelList!=null&&labelList.size()>0){
					List<Labels> list = labelsService.getLabelsByList(labelList);
					Predicate[] ors = new Predicate[list.size()];
					for (int i = 0; i < list.size(); i++) {
						ors[i] = cb.isMember(list.get(i), root.<List<Labels>>get("label"));
					}
					p4 = cb.or(ors);
				}else {
					p4 = cb.isNotNull(root.<String>get("id"));
				}
				
				p5=cb.equal(root.<WorkSpaceInfo>get("spaceInfo").get("id"), spaceid);
				//cb.like(cb.lower(root.<User>get("user").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
				query.where(cb.and(p0,p1,p2,p3,p4,p5));
				return query.getRestriction();
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
	
}
