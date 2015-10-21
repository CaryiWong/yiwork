package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.dao.CourseRepository;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.Question;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.ICourseService;
import cn.yi.gather.v20.service.IEventlogService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.INoticeMsgService;
import cn.yi.gather.v20.service.IQuestionService;
import cn.yi.gather.v20.service.IUserService;

@Service("courseServiceV20")
public class CourseService implements ICourseService{
	@Resource(name = "courseRepositoryV20")
	private CourseRepository repository;
	
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
	
	@Override
	public Course courseSaveOrUpdate(Course course) {
		// TODO Auto-generated method stub
		return repository.save(course);
	}

	@Override
	public Course findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Course> findListOpendateBetween(final Date beginDate,
			final Date endDate) {
		// TODO Auto-generated method stub
		Specification<Course> spec = new Specification<Course>() {
			
			@Override
			public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query,
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
	public void singCourse(String userid, String questions, String courseid) throws Exception{
		// TODO Auto-generated method stub
		try {
		if(userid==null)
			throw new Exception("this user did not exsit");
		User user =userService.findById(userid);
		if(courseid==null)
			throw new Exception("this course did not exsit");
		Course course = repository.findOne(courseid);
		if(user==null||course==null){
			throw new Exception("this activity or user did not exsit");
		}
		
		//处理报名表信息
		List<Question> qlist=null;
		if(questions==null||questions.length()<1){
//			throw new Exception("this questions did not exsit");
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
		Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, courseid,Course.entityName, "sign");
		if(eventlog==null){
			eventlog = new Eventlog();
			eventlog.setCourse(course);
			eventlog.setActtype(Course.entityName);
			eventlog.setEventtype("sign");
			eventlog.setUser(user);
			eventlog.setUpdatetime(course.getOpendate());
			if(qlist!=null&&qlist.size()>0)
				eventlog.setQlist(qlist);// 报名表单
			eventlogService.eventlogSaveOrUpdate(eventlog);
			
			//消息中心-消息
			Set<User> set = new HashSet<User>();
			set.add(course.getUser());
			NoticeMsg noticeMsg = new NoticeMsg();
			noticeMsg.setActiontype(NoticeActionTypeValue.SIGN.value);
			noticeMsg.setContents(user.getNickname()+"报名了你的课程"+course.getTitle());
			noticeMsg.setMsgtype(NoticeMsgTypeValue.COURSE.value);
			noticeMsg.setParam(course.getId());
			noticeMsg.setTags(set);
			noticeMsg.setIcon("loudspeaker");
			noticeMsgService.saveOrUpdate(noticeMsg);
			
			//push通知
			JSONObject msg_extras = new JSONObject();
			msg_extras.put("type", NoticeMsgTypeValue.COURSE.value);
			msg_extras.put("parameter", course.getId());
			msg_extras.put("icon", "loudspeaker");
			msg_extras.put("action", NoticeActionTypeValue.SIGN.value);
			pushService.push(course.getTitle() + "报名",
					course.getTitle(),msg_extras, Platform.android_ios(),
					new String[] { course.getUser().getId() },
					new String[] { "version4","userstart0" });
		}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public Course findBySkuid(final String skuid) {
		// TODO Auto-generated method stub
		Specification<Course> spec = new Specification<Course>() {
			
			@Override
			public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query,
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
	public Page<Course> getcourselist(Integer page, Integer size,
			final String listtype) {
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Course> spec = new Specification<Course>() {
			
			@Override
			public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query,
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
	public Page<Course> courselist(Integer currentPage, Integer pageSize,final String key) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(currentPage,pageSize,new Sort(Direction.DESC, "createdate"));
		Specification<Course> spec = new Specification<Course>() {
			
			@Override
			public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				if(key!=null){
					ps.add(cb.like(root.<String>get("title"), key));
				}
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec,pageRequest);
	}

}
