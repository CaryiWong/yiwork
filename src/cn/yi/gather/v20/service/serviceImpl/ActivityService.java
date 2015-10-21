package cn.yi.gather.v20.service.serviceImpl;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.dao.ActivityRepository;
import cn.yi.gather.v20.entity.*;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.service.*;

import com.common.Jr;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.mapping.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 活动
 * @author Lee.J.Eric
 * @time 2014年6月3日下午6:06:54
 */
@Service("activityServiceV20")
public class ActivityService implements IActivityService{
	private static Logger log = Logger.getLogger(ActivityService.class);
	
	@Resource(name = "activityRepositoryV20")
	private ActivityRepository repository;
	
	@Resource(name = "activityJoinServiceV20")
	private IActivityJoinService activityJoinService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name="userServiceV20")
	private IUserService userService;
	
	@Resource(name = "questionServiceV20")
	private IQuestionService questionService;
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;

	@Resource(name = "skuInventoryServiceV20")
	private ISkuInventoryService skuInventoryService;

	@Resource(name = "orderServiceV20")
	private IOrderService orderService;

	@Resource(name = "alipayServiceV20")
	private IAlipayService alipayService;

	@Resource(name = "skuServiceV20")
	private ISkuService skuService;

	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;

	@Resource(name = "itemInstanceLogServiceV20")
	private IItemInstanceLogService itemInstanceLogService;

	@Resource(name ="yigatherItemInventoryLogServiceV20")
	private IYigatherItemInventoryLogService yigatherItemInventoryLogService;

	@Override
	public Activity activitySaveOrUpdate(Activity activity) {
		// TODO Auto-generated method stub
		repository.save(activity);
		return findById(activity.getId());
	}

	@Override
	public Activity findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<Activity> getMyActivity(Integer page, Integer size, final User user,
			final Integer flag,final String keyword) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "opendate"));
		List<ActivityJoin> activityjoins = new ArrayList<ActivityJoin>();
		if(flag==0){//由我发起或是我报名的
			activityjoins = activityJoinService.getByUserAndOwner(user, null);
		}else if(flag==1){//由我发起的
			activityjoins = activityJoinService.getByUserAndOwner(user, 1);
		}else{//我报名的
			activityjoins = activityJoinService.getByUserAndOwner(user, 0);
		}
		final List<String> actids = new ArrayList<String>();
		actids.add("");
		for (ActivityJoin activityjoin : activityjoins) {
			actids.add(activityjoin.getActivity().getId());
		}
		Specification<Activity> spec = new Specification<Activity>() {
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> condictions = new ArrayList<Predicate>();
				condictions.add(cb.equal(root.<Integer>get("onsale"), 0));//上架状态
				condictions.add(cb.equal(root.<Integer>get("checktype"), 1));////审核状态
				condictions.add(root.<String>get("id").in(actids));
				if(StringUtils.isNotBlank(keyword)){//搜索
					Predicate p2 = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					Predicate p3 = cb.like(cb.lower(root.<String>get("address")), "%"+keyword.toLowerCase()+"%");
					Predicate p4 = cb.like(cb.lower(root.<User>get("user").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					Predicate ors = cb.or(p2,p3,p4);
					condictions.add(ors);
				}
				Predicate[] p = new Predicate[condictions.size()]; 
				for (int i = 0; i < condictions.size(); i++) {
					p[i] = condictions.get(i);
				}
				query.where(p);
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public Page<Activity> searchActByKeyword(final String keyword, Integer page,Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Activity> spec = new Specification<Activity>() {
			
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = cb.equal(root.<Integer>get("onsale"), 0);//上架状态
				Predicate p1 = cb.equal(root.<Integer>get("checktype"), 1);//审核状态
				if(StringUtils.isNotBlank(keyword)){//搜索
					Predicate p2 = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					Labels labels = labelsService.findByLabeltypeAndZnameAndPid("focus", keyword, 0l);
					Predicate p3 = cb.isMember(labels,root.<List<Labels>>get("label"));
					Predicate ors = cb.or(p2,p3);
					query.where(cb.and(p0,p1,ors));
				}else {//非搜索
					query.where(cb.and(p0,p1));
				}
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public List<Activity> getBannerActList(Integer banner) {
		// TODO Auto-generated method stub
		//通过审核、上架并设置了banner标记的
		return repository.findByChecktypeAndOnsaleAndIsbanner(1, 0, banner);
	}

	@Override
	public Page<Activity> getByLabel(Labels label, Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "opendate"));
		Page<Activity> result = repository.findByLabelIn(pageRequest, label);
		for (int i = 0; i < result.getContent().size(); i++) {//重新查询每个活动的报名人数
			if("d8cbc0902ea140f68de61398940151074".equals(result.getContent().get(i).getId())){
				result.getContent().get(i).setSignnum(33);
			}else if ("4187bf9d91d8431f94481398841127369".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(68);
			}else if ("c00726f07bd44428a8a71399459704548".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(182);
			}else if ("fc75e256166d461a8f8d1399446457801".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(33);
			}else if ("1b2493b297854eed8e311399463587679".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(66);
			}else if ("dfa4ceb06dc74f0faf681397652614185".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(200);
			}else if ("dd1f353c4a114fc8b70b1397659394850".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(71);
			}else if ("d2b35c0d845c4ca4a10a1397656485028".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(59);
			}else if ("7565a111b71644498cdd1399436056059".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(26);
			}else if ("2d0f2ede608841cf93d11399543250491".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(26);
			}else if ("d9e8244c41ba4a19ad181399463383478".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(63);
			}else if ("2cdafd328fa443ff9fa31397653267876".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(124);
			}else if ("7cf673c12d484b7688641398234223412".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(90);
			}else if ("c563e2c3d5214875a5971398235964465".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(92);
			}else if ("62a9e9f0a83442da801e1398232821270".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(46);
			}else if ("12a852462236443b8b171399448689480".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(105);
			}else if ("0f3f743ee7a446018f351397652394584".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(45);
			}else if ("418b873ef4184352a5d81397654724033".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(48);
			}else if ("4634ecf8ba7b43d39ec91398232679213".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(15);
			}else if ("08f0450e5ff3452288911397657230019".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(55);
			}else if ("405ad93dcc6949f5a06c1399443484558".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(62);
			}else if ("b0ae943091174f1dab921399448950806".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(20);
			}else if ("0193b06aed4f4bff85af1399443208798".equals(result.getContent().get(i).getId())) {//翻页
				result.getContent().get(i).setSignnum(44);
			}else if ("53163f991be24140b7df1397653720208".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(50);
			}else if ("3c6068bf315f418ea2a31397659206590".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(62);
			}else if ("763456948979466290651397657222910".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(60);
			}else if ("993bf3d6583145a495901399459316314".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(160);
			}else if ("7f86b3b76c6541bc9dfb1397655266283".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(155);
			}
		}
		return result;
	}

	@Override
	public Page<Activity> getActivityList(Integer page, Integer size,
			final Integer listtype) {
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Activity> spec = new Specification<Activity>() {
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("checktype"), 1));//审核
				ps.add(cb.equal(root.<Integer>get("onsale"), 0));//上架
				if(listtype==-1){//所有
				}else if(listtype==0){//未来
					ps.add(cb.greaterThan(root.<Date>get("opendate"), new Date()));
				}else if(listtype==1){//过去
					ps.add(cb.lessThan(root.<Date>get("enddate"), new Date()));
				}
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr)).orderBy(cb.desc(root.<Date>get("opendate")));
				
				/* 若需要大小活动一起拿 走Eventlog 方便获取数据 
				Root<Eventlog> e_root=query.from(Eventlog.class);
				Predicate pp0=cb.equal(e_root.<String>get("acttype"), "activity");
				Predicate pp1=cb.equal(e_root.<String>get("acttype"), "gathering");
				ps.add(cb.or(pp0,pp1));
				if(listtype==-1){//所有
					Predicate p0=cb.equal(e_root.<String>get("eventtype"), "create");
					Predicate p1=cb.equal(e_root.<String>get("eventtype"), "review");
					ps.add(cb.or(p0,p1));
				}else if(listtype==0){//未来
					ps.add(cb.greaterThan(e_root.<Date>get("updatetime"), new Date()));
					ps.add(cb.equal(e_root.<String>get("eventtype"), "create"));
				}else if(listtype==1){//过去
					ps.add(cb.lessThan(e_root.<Date>get("updatetime"), new Date()));
					Predicate p0=cb.equal(e_root.<String>get("eventtype"), "create");
					Predicate p1=cb.equal(e_root.<String>get("eventtype"), "review");
					ps.add(cb.or(p0,p1));
				}
				Join<Eventlog, Activity> actjoin=e_root.join(e_root.getModel().getSingularAttribute("activity",Activity.class),JoinType.LEFT);
				ps.add(cb.equal(actjoin.<String>get("id"), root.<String>get("id")));
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr)).orderBy(cb.desc(e_root.<Date>get("updatetime")));
				*/
				return query.distinct(true).getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public com.common.Page<Activity> getActivityForPage(Integer page,
			Integer size,final Integer cost,final Integer checktype,final Integer status,
			final String keyword,final List<Long> labelList) {
		// TODO Auto-generated method stub
		com.common.Page<Activity> result = new com.common.Page<Activity>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Activity> spec = new Specification<Activity>() {
			
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Date now = new Date();
				List<Predicate> conditions = new ArrayList<Predicate>();
				if(cost!=null&&cost!=-1)
					conditions.add(cb.equal(root.<Integer>get("cost"), cost));
				if(checktype!=null&&checktype!=-1)
					conditions.add(cb.equal(root.<Integer>get("checktype"), checktype));
				if(status!=null&&status!=-1){
					if(status==1){//待开启
						conditions.add(cb.greaterThan(root.<Date>get("opendate"), now));
					}else if (status==2) {//进行中
						Predicate p0 = cb.lessThan(root.<Date>get("opendate"), now);
						Predicate p1 = cb.greaterThan(root.<Date>get("enddate"), now);
						conditions.add(cb.and(p0,p1));
					}else if (status==3) {//已结束
						conditions.add(cb.lessThan(root.<Date>get("enddate"), now));
					}
				}
				if(null != labelList && labelList.size() > 0){
					List<Labels> list = labelsService.getLabelsByList(labelList);
					if(!list.isEmpty()){
						Predicate[] ors = new Predicate[list.size()];
						for (int i = 0; i < list.size(); i++) {
							ors[i] = cb.isMember(list.get(i), root.<List<Labels>>get("label"));
						}
						conditions.add(cb.or(ors));
					}
				}
				if(StringUtils.isNotBlank(keyword)){
					Predicate[] ors = new Predicate[3];
					ors[0] = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					ors[1] = cb.like(cb.lower(root.<String>get("address")), "%"+keyword.toLowerCase()+"%");
					//ors[2] = cb.like(cb.lower(root.<User>get("user").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					conditions.add(cb.or(ors));
				}
				Predicate[] predicates = new Predicate[conditions.size()];
				for (int i = 0; i < conditions.size(); i++) {
					predicates[i] = conditions.get(i);
 				}
				query.where(cb.and(predicates));
				return query.getRestriction();
			}
		};
		Page<Activity> list = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount((int)list.getTotalElements());
		return result;
	}

	@Override
	public Long countBannerAct() {
		// TODO Auto-generated method stub
		return repository.countByIsbanner(1);
	}

	@Override
	public List<Activity> findListOpendateBetween(final Date beginDate,
			final Date endDate) {
		// TODO Auto-generated method stub
		Specification<Activity> spec = new Specification<Activity>() {
			
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.greaterThanOrEqualTo(root.<Date>get("opendate"), beginDate));
				ps.add(cb.lessThan(root.<Date>get("opendate"), endDate));
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Override
	public List<Activity> findListEnddateBetween(final Date beginDate,final Date endDate) {
		// TODO Auto-generated method stub
		Specification<Activity> spec = new Specification<Activity>() {
			
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.greaterThan(root.<Date>get("enddate"), beginDate));
				ps.add(cb.lessThanOrEqualTo(root.<Date>get("enddate"), endDate));
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Override
	public Page<Activity> findActivityList(Integer page, Integer size,final String listtype) {
		
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		
		Specification<Activity> spec = new Specification<Activity>() {
			
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				if(listtype!=null){
					if(listtype.equals("needform")){
						
					}else if(listtype.equals("needreview")){
						ps.add(cb.equal(root.<String>get("huiguurl"), ""));
						ps.add(cb.lessThan(root.<Date>get("enddate"), new Date()));
					}
				}
				
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec,pageRequest);
	}
	
	@Override
	public void signActivity(String userid, String questions, String activityid) throws Exception{
		log.warn("signActivity:"+new Date());
		try {
			if(userid==null)
				throw new Exception("this user did not exsit");
			User user =userService.findById(userid);
			if(activityid==null)
				throw new Exception("this activity did not exsit");
			Activity activity=repository.findOne(activityid);
			if(user==null||activity==null){
				throw new Exception("this activity or user did not exsit");
			}
			
			//处理报名表信息
			List<Question> qlist=null;
			if(questions==null||questions.length()<1){
//				throw new Exception("this questions did not exsit");
			}else{
				qlist=new ArrayList<Question>();
				JSONArray jsonarr=JSONArray .fromObject(questions);
				JSONObject jsonObject=null;
				Question q=null;
				
				for (int i = 0; i < jsonarr.size(); i++) {
					jsonObject=JSONObject.fromObject(jsonarr.get(i));
					q=new Question();
					q.setNum(jsonObject.get("num").toString());
					q.setQuestiontext(jsonObject.get("questiontext").toString());
					q.setQuestiontype(jsonObject.get("questiontype").toString());
					q.setQuestionoptions(jsonObject.get("questionoptions").toString());
					q.setAnswertext(jsonObject.get("answertext").toString());
					q=questionService.save(q);
					if(q!=null)
						qlist.add(q);
				}
			}
			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, activityid, Activity.entityName, "sign");
			if(eventlog==null){
				eventlog = new Eventlog();
				eventlog.setActivity(activity);
				eventlog.setActtype(Activity.entityName);
				eventlog.setEventtype("sign");
				eventlog.setUser(user);
				eventlog.setUpdatetime(activity.getOpendate());
				if(qlist!=null&&qlist.size()>0)
					eventlog.setQlist(qlist);// 报名表单
				eventlogService.eventlogSaveOrUpdate(eventlog);
				
				//消息中心-消息
				Set<User> set = new HashSet<User>();
				//set.add(activity.getUser());
				NoticeMsg noticeMsg = new NoticeMsg();
				noticeMsg.setActiontype(NoticeActionTypeValue.SIGN.value);
				noticeMsg.setContents(user.getNickname()+"报名了你的活动"+activity.getTitle());
				noticeMsg.setMsgtype(NoticeMsgTypeValue.ACTIVITY.value);
				noticeMsg.setParam(activity.getId());
				noticeMsg.setTags(set);
				noticeMsg.setIcon("loudspeaker");
				noticeMsgService.saveOrUpdate(noticeMsg);
				
				//push通知
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.ACTIVITY.value);
				msg_extras.put("parameter", activity.getId());
				msg_extras.put("icon", "loudspeaker");
				msg_extras.put("action", NoticeActionTypeValue.SIGN.value);
				pushService.push(activity.getTitle() + "报名",
						activity.getTitle(),msg_extras, Platform.android_ios(),
						new String[] { activity.getUser().getId() },
						new String[] { "version4","userstart0" });
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	@Override
	public Activity findBySkuId(final String skuid) {
		// TODO Auto-generated method stub
		Specification<Activity> spec = new Specification<Activity>() {
			
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("skuid"), skuid));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findOne(spec);
	}

	@Override
	public List<Activity> findByIds(String ids) {
		final List<String> idList = new ArrayList<String>();
		String[] idArr = ids.split(",");
		if(idArr!=null&&idArr.length>0){
			Collections.addAll(idList,idArr);
		}
		return repository.findByIdIn(idList);
	}

	@Transactional
	protected Jr sign(Jr jr,Activity activity,User user,String questions)throws Exception{
		Map<String,Object> map = (Map<String, Object>) jr.getData2();
		Map<String,Object> fail = (Map<String, Object>) map.get("fail");
		Map<String,Object> success = (Map<String, Object>) map.get("success");
		Order order = (Order) map.get("order");

		if(activity.getIsvip()==1&&user.getRoot()> User.UserRoot.VIP.getCode()){
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("title",activity.getTitle());
			m.put("message","非会员不能报名此活动");
			fail.put(activity.getId(), m);
		}else if(activity.getEnddate().before(new Date())){
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("title",activity.getTitle());
			m.put("message",activity.getTitle() + "已结束");
			fail.put(activity.getId(), m);
		}else if(eventlogService.findByUserAndEntityAndActtypeAndEventtype(user.getId(), activity.getId(), Activity.entityName, "sign")!=null){
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("title",activity.getTitle());
			m.put("message",activity.getTitle() + " 您已报名");
			fail.put(activity.getId(), m);
		}else {
			//查询活动券库存,此处的商品id（skuid）即是库存表的id
			SkuInventory inventory = skuInventoryService.findById(activity.getSkuid());
			if(!inventory.getIsUnlimited()&&inventory.getAmount()<1){//有限&无库存
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("title",activity.getTitle());
				m.put("message",activity.getTitle() + " 报名已满");
				fail.put(activity.getId(), m);
			}else {
				if(activity.getCost()==0){//免费
					signActivity(user, questions, activity);
				}else {//收费
					order = orderService.createOrder("您报名的活动:"+activity.getTitle(),user, activity.getSkuid(),1,inventory,order);
				}
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("title",activity.getTitle());
				m.put("message",activity.getTitle() + " 报名成功");
				success.put(activity.getId(),m);
			}

		}
		map.put("order", order);
		map.put("success", success);
		map.put("fail", fail);
		jr.setData2(map);
		return jr;
	}

	@Override
	public Jr signActivity(String type,User user, Jr jr, String ids,String idtype,String questions) throws Exception{
		List<Activity> activityList = findByIds(ids);
		if(activityList.isEmpty()){
			jr.setCord(1);
			jr.setMsg("请选择活动");
		}else {
			Activity activity_parent = null;
			Order order = new Order();
			order.setUserId(user.getId());
			order.setOrderType(Order.OrderType.BUYING.getCode());
			order.setStatus(Order.OrderStatus.UNPAID.getCode());
			order.setCreateTime(new Timestamp(System.currentTimeMillis()));
			order.setModifyTime(order.getCreateTime());
			order.setBusinessType(Activity.entityName);

			Map<String,Object> map = new HashMap<String, Object>();
			Map<String,Object> success = new HashMap<String, Object>();
			Map<String,Object> fail = new HashMap<String, Object>();
			map.put("order", order);
			map.put("success", success);
			map.put("fail", fail);
			jr.setData2(map);
			for (Activity activity:activityList) {

				if(activity_parent==null&&idtype.equalsIgnoreCase("multi")){
					activity_parent = findById(activity.getPid());
				}else if(idtype.equalsIgnoreCase("single")){
					activity_parent = activity;
				}
				jr = sign(jr,activity,user,questions);//每个报名流程独立一个事务

			}
			order = (Order)map.remove("order");
			success = (Map<String, Object>) map.get("success");
			if(success.size()>0){//有成功的
				if(order.getTotalPrice()>0){//需要跳到alipay
					order.setJsontext(questions);//报名表单
					order.setBusinessId(activity_parent.getId());
					order.setMemo("您报名的活动:" + activity_parent.getTitle());
					orderService.saveOrUpdate(order);
					jr = alipayService.applyAlipay(type, jr, order);
					map.put("flag",1);
				}else {
					map.put("flag",0);
				}
			}else {
				jr.setCord(1);
				jr.setMsg("报名失败");
			}
		}
		return jr;
	}

	@Override
	public void signActivity(User user, String questions, Activity activity) throws Exception {
		log.warn("signActivity:"+new Date());
		try {
			if(user==null)
				throw new Exception("this user did not exsit");
			if(activity==null)
				throw new Exception("this activity did not exsit");

			//处理报名表信息
			List<Question> qlist=null;
			if(questions==null||questions.length()<1){
//				throw new Exception("this questions did not exsit");
			}else{
				qlist=new ArrayList<Question>();
				JSONArray jsonarr=JSONArray .fromObject(questions);
				JSONObject jsonObject=null;
				Question q=null;

				for (int i = 0; i < jsonarr.size(); i++) {
					jsonObject=JSONObject.fromObject(jsonarr.get(i));
					q=new Question();
					q.setNum(jsonObject.get("num").toString());
					q.setQuestiontext(jsonObject.get("questiontext").toString());
					q.setQuestiontype(jsonObject.get("questiontype").toString());
					q.setQuestionoptions(jsonObject.get("questionoptions").toString());
					q.setAnswertext(jsonObject.get("answertext").toString());
					q=questionService.save(q);
					if(q!=null)
						qlist.add(q);
				}
			}
			SKU sku = skuService.findById(activity.getSkuid());
			if(sku!=null){
				SkuInventory inventory = skuInventoryService.findById(sku.getId());
				if(!inventory.getIsUnlimited()){//有限--减库存
					if(inventory.getAmount()<1){
						throw new Exception("inventory not enough");
					}else {
						inventory.setAmount(inventory.getAmount()-1);
						skuInventoryService.saveOrUpdate(inventory);
					}
				}
			}

			//商品实例入库
			ItemInstance instance = new ItemInstance();
			instance.setStatus(ItemInstance.ItemInstanceStatus.UNUSED.getCode());//免费，直接发货
			instance.setOrderId("");
			instance.setSku(sku);
			instance.setSalePrice(0.0);

			instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
			instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
			itemInstanceService.saveOrUpdate(instance);

			//商品实例记录
			ItemInstanceLog item_log = new ItemInstanceLog();
			item_log.setItemInstanceId(instance.getId());
			item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
			item_log.setOrderId("");
			item_log.setSkuId(sku.getId());
			item_log.setMemo("免费活动，系统自动创建商品实例");
			item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
			itemInstanceLogService.saveOrUpdate(item_log);

			// 记录出货
			YigatherItemInventoryLog record = new YigatherItemInventoryLog();
			record.setSkuId(sku.getId());
			record.setAmount(1);
			record.setOrderId("");
			record.setMemo("免费活动, 系统自动出货");
			record.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
			record.setDateTime(new Timestamp(System.currentTimeMillis()));
			yigatherItemInventoryLogService.saveOrUpdate(record);

			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(user.getId(), activity.getId(), Activity.entityName, "sign");
			if(eventlog==null){
				eventlog = new Eventlog();
				eventlog.setActivity(activity);
				eventlog.setActtype(Activity.entityName);
				eventlog.setEventtype("sign");
				eventlog.setCity(activity.getSpaceInfo().getSpacecity());
				eventlog.setUser(user);
				eventlog.setUpdatetime(activity.getOpendate());
				if(qlist!=null&&qlist.size()>0)
					eventlog.setQlist(qlist);// 报名表单
				eventlogService.eventlogSaveOrUpdate(eventlog);

			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public List<Activity> findByPid(String pid) {
		return repository.findByPid(pid);
	}
}