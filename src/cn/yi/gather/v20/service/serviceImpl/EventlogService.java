package cn.yi.gather.v20.service.serviceImpl;

import cn.yi.gather.v20.dao.ActivityRepository;
import cn.yi.gather.v20.dao.EventlogRepository;
import cn.yi.gather.v20.dao.UserRepository;
import cn.yi.gather.v20.entity.*;
import cn.yi.gather.v20.entity.Activity.ActivityTypeValue;
import cn.yi.gather.v20.service.IEventlogService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.R.Act;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.*;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("eventlogServiceV20")
public class EventlogService implements IEventlogService{
	
	@Resource(name = "eventlogRepositoryV20")
	private EventlogRepository repository;
	
	@Resource(name = "userRepositoryV20")
	private UserRepository userRepository;
	
	@Resource(name = "entityManagerFactoryV20")
	private EntityManagerFactory emf;

	@Resource(name = "activityRepositoryV20")
	private ActivityRepository activityRepository;
	
	@Override
	public Eventlog eventlogSaveOrUpdate(Eventlog eventlog) {
		// TODO Auto-generated method stub
		repository.save(eventlog);
		return findById(eventlog.getId());
	}

	@Override
	public Eventlog findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}

	@Override
	public Page<Eventlog> findTrendsPage(Integer page, Integer size,final String eventtype) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "updatetime"));
		Specification<Eventlog> spec = new Specification<Eventlog>() {
			
			@Override
			public Predicate toPredicate(Root<Eventlog> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				if("future".equalsIgnoreCase(eventtype)){//即将
					ps.add(cb.greaterThan(root.<Date>get("updatetime"), new Date()));
					ps.add(cb.equal(root.<String>get("eventtype"), "create"));//新建
				}else if ("review".equalsIgnoreCase(eventtype)) {//回顾
					ps.add(cb.lessThan(root.<Date>get("updatetime"), new Date()));
					ps.add(cb.equal(root.<String>get("eventtype"), "review"));//回顾
				}else {
					if ("activity".equalsIgnoreCase(eventtype)) {//活动
						Predicate p0 = cb.equal(root.<String>get("acttype"), "activity");
						Predicate p1 = cb.equal(root.<String>get("acttype"), "gathering");
						ps.add(cb.or(p0,p1));
					}else if ("course".equalsIgnoreCase(eventtype)) {//课程
						ps.add(cb.equal(root.<String>get("acttype"), "course"));
					}
					Predicate p0 = cb.equal(root.<String>get("eventtype"), "create");//新建
					Predicate p1 = cb.equal(root.<String>get("eventtype"), "review");//回顾
					ps.add(cb.or(p0,p1));
				}
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr));
				return query.distinct(true).getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}
	
	

	@Override
	public Page<Eventlog> findTrendsPage(Integer page, Integer size,
			final String eventtype,final String userid,final String cityid) {
		// TODO Auto-generated method stub
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = format.format(new Date());
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		sql.append("select e.* from eventlog e inner join ");
		sql.append("( ");
			sql.append("select t.* from eventlog t ");
			sql.append("order by ");
			sql.append("case ");
			sql.append("when t.eventtype='review' then 6 ");
			if(userid==null)
				sql.append("when t.eventtype='create' then 5 ");
			sql.append("when t.user_id = ?1 and t.eventtype='join' then 4 ");
			sql.append("when t.user_id = ?1 and t.eventtype='sign' then 3 ");
			sql.append("when t.user_id = ?1 and t.eventtype='create' then 2 ");
			sql.append("when t.user_id != ?1 and t.eventtype='create' then 1 else 0 end ");
			sql.append("desc,");
			sql.append("t.updatetime asc ");
		sql.append(") as a ");
		sql.append("on e.id=a.id ");
		sql.append("left join activity at ");
		sql.append("on e.activity_id=at.id ");
		
		sql.append("left join article ar ");
		sql.append("on e.article_id=ar.id ");
		
		sql.append("where ");
		
		sql.append(" e.acttype!=\"invite\" and ");//不查邀请
		sql.append("case when e.acttype=\"activity\" then  at.checktype=1 and at.onsale=0 ");
		sql.append("when e.acttype=\"article\" then  ar.isdel=0 and ar.ischeck=1 and ar.isshelves=0 ");
		sql.append("else  e.id is not null end ");
		if("future".equalsIgnoreCase(eventtype)){//即将
			sql.append("and ").append("e.updatetime > '").append(now).append("' ");
		}else if ("review".equalsIgnoreCase(eventtype)) {//回顾
			sql.append("and ").append("e.updatetime < '").append(now).append("' ");
		}else {
			if ("activity".equalsIgnoreCase(eventtype)) {//活动
				sql.append("and ").append("e.acttype = 'activity' or e.acttype = 'gathering' ");
			}else if ("course".equalsIgnoreCase(eventtype)) {//课程
				sql.append("and ").append("e.acttype = 'course' ");
			}
		}
		if(cityid!=null){
			sql.append(" and e.city_id=?2 ");
		}
		sql.append("group by e.gathering_id,e.activity_id,e.course_id,e.notice_id,e.article_id ");
		sql.append("order by e.updatetime desc ");
//		System.out.println(sql.toString());
		countSql.append("select count(1) as total from (").append(sql).append(") as c");
		
		EntityManager em = emf.createEntityManager();
		Query query = em.createNativeQuery(sql.toString(),Eventlog.class);
		Query countQuery = em.createNativeQuery(countSql.toString());
		query.setParameter(1, userid);
		countQuery.setParameter(1, userid);
		if(cityid!=null){
			query.setParameter(2, cityid);
			countQuery.setParameter(2, cityid);
		}
		query.setFirstResult(page*size);
		query.setMaxResults(size);
		List<Eventlog> list = query.getResultList();
		BigInteger totalCount = (BigInteger)countQuery.getSingleResult();
		PageRequest pageRequest = new PageRequest(page,size);
		em.clear();
		em.close();
		return new PageImpl<Eventlog>(list, pageRequest, totalCount.longValue());
		
	}

	@Deprecated
	@Override
	public Eventlog findByUserAndGatheringAndEventtype(User user, Gathering gathering,String eventtype) {
		// TODO Auto-generated method stub
		return repository.findByUserAndGatheringAndEventtype(user, gathering,eventtype);
	}

	@Deprecated
	@Transactional
	@Override
	public void deleteByUserAndGatheringAndEventtype(User user, Gathering gathering,String eventtype) {
		// TODO Auto-generated method stub
		repository.delByUserGatheringEventtype(user.getId(), gathering.getId(),eventtype);
	}

	@Override
	public List<User> getSignListByGathering(final String gatheringid) {
		// TODO Auto-generated method stub
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				Root<Eventlog> event_root=query.from(Eventlog.class);
				ps.add(cb.equal(event_root.<Gathering>get("gathering").<String>get("id"), gatheringid));
				ps.add(cb.equal(event_root.<String>get("acttype"), "gathering"));
				ps.add(cb.equal(event_root.<String>get("eventtype"), "sign"));
				Join<Eventlog, User> uJoin=event_root.join(event_root.getModel().getSingularAttribute("user", User.class),JoinType.LEFT);
				ps.add(cb.equal(uJoin.<String>get("id"), root.<String>get("id")));
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr);
				return query.distinct(true).getRestriction();
			}
		};
		return userRepository.findAll(spec);
	}

	@Deprecated
	@Override
	public Page<User> getUserListByGatheringAndEventtype(final String gatheringid,final String eventtype, Integer page,
			Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				Root<Eventlog> event_root=query.from(Eventlog.class);
				ps.add(cb.equal(event_root.<Gathering>get("gathering").<String>get("id"), gatheringid));
				ps.add(cb.equal(event_root.<String>get("acttype"), "gathering"));
				ps.add(cb.equal(event_root.<String>get("eventtype"), eventtype));
				Join<Eventlog, User> uJoin=event_root.join(event_root.getModel().getSingularAttribute("user", User.class),JoinType.LEFT);
				ps.add(cb.equal(uJoin.<String>get("id"), root.<String>get("id")));
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr).orderBy(cb.desc(event_root.<Date>get("updatetime")));
				return query.distinct(true).getRestriction();
			}
		};
		return userRepository.findAll(spec,pageRequest);
	}

	@Override
	public Page<Eventlog> findMyTrendsPage(Integer page, Integer size,
			final String userid, final String actiontype) {
		// TODO Auto-generated method stub
		
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		sql.append("select e.* from eventlog e inner join ");
		sql.append("( ");
			sql.append("select t.* from eventlog t ");
			sql.append("order by ");
			sql.append("case ");
			sql.append("when t.eventtype='review' then 5 ");
			sql.append("when t.user_id = ?1 and t.eventtype='join' then 4 ");
			sql.append("when t.user_id = ?1 and t.eventtype='sign' then 3 ");
			sql.append("when t.user_id = ?1 and t.eventtype='create' then 2 ");
			sql.append("when t.user_id != ?1 and t.eventtype='create' then 1 else 0 end ");
			sql.append("desc,");
			sql.append("updatetime asc ");
		sql.append(") as a ");
		sql.append("on e.id=a.id ");
		sql.append("left join activity at ");
		sql.append("on e.activity_id=at.id ");
		sql.append("where ");
		sql.append(" e.acttype!=\"notice\" and ");//不查公告
		sql.append("case when e.acttype=\"activity\" then  at.checktype=1 and at.onsale=0 ");
		sql.append("else  e.id is not null end ");
		if(actiontype==null){//关注的、参加的
			sql.append(" and ").append("(e.eventtype='join' or e.eventtype='sign' or e.eventtype='focus' or e.eventtype='invite' or e.eventtype='create') ");
		}else {
			if ("join".equalsIgnoreCase(actiontype)) {//我参加的-----我出席的、我报名的
				sql.append(" and ").append("(e.eventtype='join' or e.eventtype='sign' or e.eventtype='invite') ");
			}else if ("focus".equalsIgnoreCase(actiontype)) {//我关注的
				sql.append(" and ").append("e.eventtype='focus' ");
			}else if("create".equalsIgnoreCase(actiontype)){//我发起的---我发起的、回顾的
				sql.append(" and ").append("(e.eventtype='create' or e.eventtype='review' or e.eventtype='invite') ");
			}
		}
		sql.append(" and e.user_id = ?1 ");
		
		sql.append("group by e.gathering_id,e.activity_id,e.course_id,e.invitation_id ");
		sql.append("order by e.updatetime desc ");
		countSql.append("select count(1) as total from (").append(sql).append(") as c");
		
		EntityManager em = emf.createEntityManager();
		Query query = em.createNativeQuery(sql.toString(),Eventlog.class);
		Query countQuery = em.createNativeQuery(countSql.toString());
		query.setParameter(1, userid);
		countQuery.setParameter(1, userid);
		query.setFirstResult(page*size);
		query.setMaxResults(size);
		List<Eventlog> list = query.getResultList();
		BigInteger totalCount = (BigInteger)countQuery.getSingleResult();
		PageRequest pageRequest = new PageRequest(page,size);
		em.clear();
		em.close();
		return new PageImpl<Eventlog>(list, pageRequest, totalCount.longValue());
		
//		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC,"updatetime"));
//		Specification<Eventlog> spec = new Specification<Eventlog>(){
//
//			@Override
//			public Predicate toPredicate(Root<Eventlog> root,
//					CriteriaQuery<?> query, CriteriaBuilder cb) {
//				// TODO Auto-generated method stub
//
//				List<Predicate> ps = new ArrayList<Predicate>();
//				if(actiontype==null){//关注的、参加的
//					Predicate p0 = cb.equal(root.<String>get("eventtype"), "join");
//					Predicate p1 = cb.equal(root.<String>get("eventtype"), "sign");
//					Predicate p2 = cb.equal(root.<String>get("eventtype"), "focus");
//					Predicate p3 = cb.equal(root.<String>get("eventtype"), "invite");
//					Predicate p4 = cb.equal(root.<String>get("eventtype"), "create");
//					ps.add(cb.or(p0,p1,p2,p3,p4));
//				}else {
//					if ("join".equalsIgnoreCase(actiontype)) {//我参加的-----我出席的、我报名的
//						Predicate p0 = cb.equal(root.<String>get("eventtype"), "join");
//						Predicate p1 = cb.equal(root.<String>get("eventtype"), "sign");
//						Predicate p2 = cb.equal(root.<String>get("eventtype"), "invite");
//						ps.add(cb.or(p0,p1,p2));
//					}else if ("focus".equalsIgnoreCase(actiontype)) {//我关注的
//						ps.add(cb.equal(root.<String>get("eventtype"), "focus"));
//					}else if("create".equalsIgnoreCase(actiontype)){//我发起的---我发起的、回顾的
//						Predicate p0 = cb.equal(root.<String>get("eventtype"), "create");
//						Predicate p1 = cb.equal(root.<String>get("eventtype"), "review");
//						Predicate p2 = cb.equal(root.<String>get("eventtype"), "invite");
//						ps.add(cb.or(p0,p1,p2));
//					}
//				}
//				ps.add(cb.equal(root.<User>get("user").<String>get("id"), userid));
//				List<Predicate> ps1 = new ArrayList<Predicate>(ps);
//				
//				//活动部分，只查审核通过并且是上架的
//				Root<Activity> aRoot = query.from(Activity.class);
//				
//				Join<Eventlog, Activity> join = root.join(root.getModel().getSingularAttribute("activity",Activity.class),JoinType.LEFT);
//				ps1.add(cb.equal(join.<String>get("id"), aRoot.<String>get("id")));
//				
//				//不查活动的数据
//				ps.add(cb.notEqual(root.get("acttype"), Activity.entityName));
//				
//				//只查活动的数据,只查审核通过并且是上架的
//				ps1.add(cb.equal(root.get("acttype"), Activity.entityName));
//				ps1.add(cb.and(cb.equal(aRoot.<Integer> get("checktype"), 1),cb.equal(aRoot.<Integer> get("onsale"), 0)));
//				
//				query.where(cb.or(cb.and(ps.toArray(new Predicate[ps.size()])),cb.and(ps1.toArray(new Predicate[ps1.size()]))));
////				.groupBy(root.<Activity> get("activity"),root.<Gathering> get("gathering"),root.<Course> get("course"),root.<Invitations>get("invitation"));
//				
//				return query.distinct(true).getRestriction();
//			}
//		};
//		return repository.findAll(spec, pageRequest);
	}

	@Override
	public void deleteByNotice(String notice) {
		// TODO Auto-generated method stub
		repository.delByNotice(notice);
	}

	@Override
	public void deleteByGathering(String gatheringid) {
		// TODO Auto-generated method stub
		repository.delByGathering(gatheringid);
	}

	@Deprecated
	@Override
	public Eventlog findByUserAndCourseAndEventtype(User user, Course course,
			String eventtype) {
		// TODO Auto-generated method stub
		return repository.findByUserAndCourseAndEventtype(user, course, eventtype);
	}

	@Deprecated
	@Override
	public void deleteByUserAndCourseAndEventtype(User user, Course course,
			String eventtype) {
		// TODO Auto-generated method stub
		repository.delByUserCourseEventtype(user.getId(), course.getId(), eventtype);
	}

	@Override
	public Page<User> findCourseStudentList(final String courseid, Integer page,
			Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				Root<Eventlog> event_root=query.from(Eventlog.class);
				ps.add(cb.equal(event_root.<Course>get("course").<String>get("id"), courseid));
				ps.add(cb.equal(event_root.<String>get("acttype"), Course.entityName));
				//报名&出席
				ps.add(cb.or(cb.equal(event_root.<String>get("eventtype"), "sign"),cb.equal(event_root.<String>get("eventtype"), "join")));
				
				Join<Eventlog, User> uJoin=event_root.join(event_root.getModel().getSingularAttribute("user", User.class),JoinType.LEFT);
				ps.add(cb.equal(uJoin.<String>get("id"), root.<String>get("id")));
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr).orderBy(cb.desc(event_root.<Date>get("updatetime")));
				return query.distinct(true).getRestriction();
			}
		};
		return userRepository.findAll(spec, pageRequest);
	}

	@Deprecated
	@Override
	public Eventlog findByUserAndActivityAndEventtype(User user, Activity activity,
			String eventtype) {
		// TODO Auto-generated method stub
		return repository.findByUserAndActivityAndEventtype(user, activity, eventtype);
	}

	@Deprecated
	@Override
	public void deleteByUserAndActivityAndEventtype(User user,
			Activity activity, String eventtype) {
		// TODO Auto-generated method stub
		repository.delByUserActivityEventtype(user.getId(), activity.getId(), eventtype);
	}

	@Deprecated
	@Override
	public Page<User> getSignListByActivity(final String activityid, Integer page,
			Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				Root<Eventlog> event_root=query.from(Eventlog.class);
				ps.add(cb.equal(event_root.<Activity>get("activity").<String>get("id"), activityid));
				ps.add(cb.equal(event_root.<String>get("eventtype"), "sign"));
				ps.add(cb.equal(event_root.<String>get("acttype"), Activity.entityName));
				Join<Eventlog, User> uJoin=event_root.join(event_root.getModel().getSingularAttribute("user", User.class),JoinType.LEFT);
				ps.add(cb.equal(uJoin.<String>get("id"), root.<String>get("id")));
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr).orderBy(cb.desc(event_root.<Date>get("updatetime")));
				return query.distinct(true).getRestriction();
			}
		};
		return userRepository.findAll(spec,pageRequest);
	}

	@Deprecated
	@Override
	public Page<User> getJoinListByActivity(final String activityid, Integer page,
			Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				Root<Eventlog> event_root=query.from(Eventlog.class);
				ps.add(cb.equal(event_root.<Activity>get("activity").<String>get("id"), activityid));
				ps.add(cb.equal(event_root.<String>get("eventtype"), "join"));
				ps.add(cb.equal(event_root.<String>get("acttype"), Activity.entityName));
				Join<Eventlog, User> uJoin=event_root.join(event_root.getModel().getSingularAttribute("user", User.class),JoinType.LEFT);
				ps.add(cb.equal(uJoin.<String>get("id"), root.<String>get("id")));
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr).orderBy(cb.desc(event_root.<Date>get("updatetime")));
				return query.distinct(true).getRestriction();
			}
		};
		return userRepository.findAll(spec, pageRequest);
	}

	@Override
	public Eventlog findByUserAndEntityAndActtypeAndEventtype(final String userid,
			final String entityid,final String acttype,final String eventtype) {
		// TODO Auto-generated method stub
		Specification<Eventlog> spec = new Specification<Eventlog>() {
			
			@Override
			public Predicate toPredicate(Root<Eventlog> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<User>get("user").<String>get("id"), userid));
				ps.add(cb.equal(root.<String>get("eventtype"), eventtype));
				ps.add(cb.equal(root.<String>get("acttype"), acttype));
				if(Gathering.entityName.equals(acttype)){
					ps.add(cb.equal(root.<Gathering>get("gathering").<String>get("id"), entityid));
				}else if (Activity.entityName.equals(acttype)) {
					ps.add(cb.equal(root.<Activity>get("activity").<String>get("id"), entityid));
				}else if (Course.entityName.equals(acttype)) {
					ps.add(cb.equal(root.<Course>get("course").<String>get("id"), entityid));
				}else if ("invite".equals(acttype)) {
					ps.add(cb.equal(root.<Invitations>get("invitation").<String>get("id"), entityid));
				}
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr);
				return query.getRestriction();
			}
		};
		return repository.findOne(spec);
	}

	@Override
	public void deleteByUserAndEntityAndFocustypeAndEventtype(String userid,
			String entityid, String acttype, String eventtype) {
		// TODO Auto-generated method stub
		Eventlog eventlog = findByUserAndEntityAndActtypeAndEventtype(userid, entityid, acttype, eventtype);
		if(eventlog!=null){
			repository.delete(eventlog);
		}
	}

	@Override
	public Page<User> findUserListByIdAndEventtypeAndActtype(final String id,
			final String eventtype, final String acttype, Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				Root<Eventlog> event_root=query.from(Eventlog.class);
				if(acttype.equals(Activity.entityName)){
					Activity act=activityRepository.findOne(id);
					//临时处理活动集 的报名列表获取形式2015-05-21
					if(act.getActivityType().equals(ActivityTypeValue.SET.value)){
						List<Activity> actlist=activityRepository.findByPid(id);
						if(actlist!=null){
							List<Predicate> ps2 = new ArrayList<Predicate>();
							for (Activity activity : actlist) {
								ps2.add(cb.equal(event_root.<Activity>get("activity").<String>get("id"), activity.getId()));
							}
							Predicate[] parr2 = new Predicate[ps2.size()]; 
							ps2.toArray(parr2);
							ps.add(cb.or(parr2));
						}
					}else{
						ps.add(cb.equal(event_root.<Activity>get("activity").<String>get("id"), id));
					}
				}else if (acttype.equals(Gathering.entityName)) {
					ps.add(cb.equal(event_root.<Gathering>get("gathering").<String>get("id"), id));
				}else if (acttype.equals(Course.entityName)) {
					ps.add(cb.equal(event_root.<Course>get("course").<String>get("id"), id));
				}else if(acttype.equals(Notice.entityName)){
					ps.add(cb.equal(event_root.<Notice>get("notice").<String>get("id"), id));
				}else if(acttype.equals("invite")){
					ps.add(cb.equal(event_root.<Invitations>get("invitation").<String>get("id"), id));
				}
				ps.add(cb.equal(event_root.<String>get("eventtype"), eventtype));
				ps.add(cb.equal(event_root.<String>get("acttype"), acttype));
				Join<Eventlog, User> uJoin=event_root.join(event_root.getModel().getSingularAttribute("user", User.class),JoinType.LEFT);
				ps.add(cb.equal(uJoin.<String>get("id"), root.<String>get("id")));
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr).orderBy(cb.desc(event_root.<Date>get("updatetime")));
				return query.distinct(true).getRestriction();
			}
		};
		return userRepository.findAll(spec, pageRequest);
	}

	@Override
	public Long countEntityAndActtypeAndEventType(final String entityid,
			final String acttype, final String eventtype) {
		// TODO Auto-generated method stub
		Specification<Eventlog> spec = new Specification<Eventlog>() {
			
			@Override
			public Predicate toPredicate(Root<Eventlog> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("eventtype"), eventtype));
				ps.add(cb.equal(root.<String>get("acttype"), acttype));
				if("gathering".equals(acttype)){
					ps.add(cb.equal(root.<Gathering>get("gathering").<String>get("id"), entityid));
				}else if ("activity".equals(acttype)) {
					ps.add(cb.equal(root.<Activity>get("activity").<String>get("id"), entityid));
				}else if ("course".equals(acttype)) {
					ps.add(cb.equal(root.<Course>get("course").<String>get("id"), entityid));
				}else if ("invite".equals(acttype)) {
					ps.add(cb.equal(root.<Invitations>get("invitation").<String>get("id"), entityid));
				}else if ("notice".equals(acttype)) {
					ps.add(cb.equal(root.<Notice>get("notice").<String>get("id"), entityid));
				}
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr);
				return query.getRestriction();
			}
		};
		return repository.count(spec);
	}

	@Override
	public List<Eventlog> findByActAndSign(String actid) {
		Activity activity=activityRepository.findOne(actid);
		return repository.findByActivityAndEventtype(activity, "sign");
	}
	
	
	/**
	 * 
	 * @param conn
	 * @param start_date
	 * @param end_date
	 * @param page
	 * @param page_size
	 * @return
	 * @throws Exception
	 */
	@Override
	public com.common.Page<User> getSignListByIdForPage(Connection conn,String actid, Integer page, Integer page_size) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<User> array_list = new ArrayList<User>();
		int total_count = 0;
		try {
			pstat = conn.prepareStatement("SELECT COUNT(*) as total_count FROM eventlog where activity_id  =? and eventtype='sign'");
			pstat.setString(1,actid);
			result = pstat.executeQuery();
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
			
			pstat = conn.prepareStatement(
					" select user.realname,user.nickname,user.root,user.sex,user.unum,user.telnum,eventlog.createtime,user.email "
					+ "  from eventlog left join user  on eventlog.user_id=user.id 	 where eventtype='sign' and eventlog.activity_id=? "
					+ " ORDER BY eventlog.createtime ASC"
					+ " LIMIT ?, ?");
			int offset = 0;
			if (page > 1) {  //此处有BUG 已修改  0708
				offset = (page) * page_size;
			}else if(page==1){  //此处有BUG 已修改
				offset =  page_size;
			}
			System.out.println("offset==========="+offset);
			pstat.setString(1, actid);
			pstat.setInt(2, offset);
			pstat.setInt(3, page_size);			
			result = pstat.executeQuery();
			while (result.next()) {
				User record = new User();
				record.setNickname(result.getString("nickname"));
				record.setSex(result.getInt("sex"));
				record.setUnum(result.getString("unum"));
				record.setEmail(result.getString("email"));
				record.setTelnum(result.getString("telnum"));
				//record.setCreatedate(result.getDate("createtime"));
				record.setCompanyurl(result.getString("createtime").substring(0,result.getString("createtime").length()-2));  //获取时间
				record.setRoot(result.getInt("root"));
				record.setRealname(result.getString("realname"));
				array_list.add(record);
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
		
		com.common.Page<User> paged_result = new com.common.Page<User>();
		paged_result.setCurrentPage(page);
		paged_result.setPageSize(page_size);
		paged_result.setCurrentCount(array_list.size());
		paged_result.setTotalCount(total_count);
		paged_result.setResult(array_list);
		return paged_result;
	
	}

	@Override
	public Page<Eventlog> searchTrends(Integer page, Integer size,final String user_id, String keyword,final String btn_name) {
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		sql.append("select e.* from eventlog e inner join ");
		sql.append("( ");
			sql.append("select t.* from eventlog t ");
			sql.append("order by ");
			sql.append("case ");
			sql.append("when t.eventtype='review' then 5 ");
			sql.append("when t.user_id = ?1 and t.eventtype='join' then 4 ");
			sql.append("when t.user_id = ?1 and t.eventtype='sign' then 3 ");
			sql.append("when t.user_id = ?1 and t.eventtype='create' then 2 ");
			sql.append("when t.user_id != ?1 and t.eventtype='create' then 1 else 0 end ");
			sql.append("desc,");
			sql.append("updatetime asc ");
		sql.append(") as a ");
		sql.append("on e.id=a.id ");
		sql.append("left join activity at ");
		sql.append("on e.activity_id=at.id ");
		sql.append("left join gathering g ");
		sql.append("on e.gathering_id=g.id ");
		sql.append("left join article art ");
		sql.append("on e.article_id = art.id ");
		sql.append("left join notice no ");
		sql.append("on e.notice_id = no.id ");
		sql.append("where ");//---条件---
		sql.append(" e.acttype!=\"invite\" and ");//不查邀请
		sql.append("case when e.acttype=\"activity\" then  at.checktype=1 and at.onsale=0 ");
		sql.append("when e.acttype=\"article\" then art.ischeck=1 and art.isshelves=0 and art.isdel=0 ");
		sql.append("else  e.id is not null end ");
		if(keyword!=null&&!keyword.equals("")){//关键字搜索
			keyword = "'%"+keyword+"%'";
			sql.append(" and ").append("(")
					.append(" at.title like ").append(keyword).append(" or g.title like ").append(keyword)
					.append(" or art.title like ").append(keyword).append(" or no.title like ").append(keyword)
					.append(") ");
		}
		if(btn_name !=null&&!btn_name.equals("")){//按钮搜索
			if(btn_name.equalsIgnoreCase("soon")){//即将
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = format.format(new Date());
				sql.append("and ").append("e.updatetime > '").append(now).append("' ");
			}else if(btn_name.equalsIgnoreCase("activity")){//活动
				sql.append("and ").append("e.acttype = 'activity' or e.acttype = 'gathering' ");
			}else if(btn_name.equalsIgnoreCase("news")){//社区新闻，动态
				sql.append("and ").append("e.acttype = 'notice' or e.acttype = 'article' ");
			}
		}
		sql.append("group by e.gathering_id,e.activity_id,e.course_id,e.notice_id,e.article_id ");
		sql.append("order by e.updatetime desc ");
		countSql.append("select count(1) as total from (").append(sql).append(") as c");

		System.out.println(sql.toString());

		EntityManager em = emf.createEntityManager();
		Query query = em.createNativeQuery(sql.toString(),Eventlog.class);
		Query countQuery = em.createNativeQuery(countSql.toString());
		query.setParameter(1, user_id);
		countQuery.setParameter(1, user_id);
		query.setFirstResult(page*size);
		query.setMaxResults(size);
		List<Eventlog> list = query.getResultList();
		BigInteger totalCount = (BigInteger)countQuery.getSingleResult();
		PageRequest pageRequest = new PageRequest(page,size);
		em.clear();
		em.close();
		return new PageImpl<Eventlog>(list, pageRequest, totalCount.longValue());
	}
}
