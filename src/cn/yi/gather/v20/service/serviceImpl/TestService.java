package cn.yi.gather.v20.service.serviceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v11.dao.ActivityJoinRepository;
import cn.yi.gather.v11.entity.ActivityJoin;
import cn.yi.gather.v20.dao.ActivityRepository;
import cn.yi.gather.v20.dao.ApplyvipRepository;
import cn.yi.gather.v20.dao.AppversionRepository;
import cn.yi.gather.v20.dao.CommentRepository;
import cn.yi.gather.v20.dao.DcommentRepository;
import cn.yi.gather.v20.dao.DemandsRepository;
import cn.yi.gather.v20.dao.EventlogRepository;
import cn.yi.gather.v20.dao.FeedbackRepository;
import cn.yi.gather.v20.dao.FocusRepository;
import cn.yi.gather.v20.dao.IndexorderbyinfoRepository;
import cn.yi.gather.v20.dao.IndexteaminfoRepository;
import cn.yi.gather.v20.dao.IndexuserinfoRepository;
import cn.yi.gather.v20.dao.JoinapplicationRepository;
import cn.yi.gather.v20.dao.PushlogRepository;
import cn.yi.gather.v20.dao.ResetpasswordRepository;
import cn.yi.gather.v20.dao.ReviewcontentRepository;
import cn.yi.gather.v20.dao.SignLogRepository;
import cn.yi.gather.v20.dao.SpaceshowRepository;
import cn.yi.gather.v20.dao.UserFocusRepository;
import cn.yi.gather.v20.dao.UserRepository;
import cn.yi.gather.v20.dao.VipNumLogRepository;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.entity.Comment;
import cn.yi.gather.v20.entity.Dcomment;
import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Feedback;
import cn.yi.gather.v20.entity.Focus;
import cn.yi.gather.v20.entity.Indexorderbyinfo;
import cn.yi.gather.v20.entity.Indexteaminfo;
import cn.yi.gather.v20.entity.Indexuserinfo;
import cn.yi.gather.v20.entity.Joinapplication;
import cn.yi.gather.v20.entity.Pushlog;
import cn.yi.gather.v20.entity.Resetpassword;
import cn.yi.gather.v20.entity.Reviewcontent;
import cn.yi.gather.v20.entity.SignLog;
import cn.yi.gather.v20.entity.Spaceshow;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserFocus;
import cn.yi.gather.v20.entity.VipNumLog;
import cn.yi.gather.v20.service.ITestService;
import cn.yi.gather.v20.service.IUserService;

@Service("testServiceV20")
public class TestService implements ITestService{

//	@Resource(name = "userServiceV20")
//	private IUserService userService;
//	
//	@Resource(name = "userRepositoryV20")
//	private UserRepository userRepository;
	
	@Resource(name = "vipNumLogRepositoryV20")
	private VipNumLogRepository vipNumLogRepository;
	
//	@Resource(name = "userRepositoryV2")
//	private cn.yi.gather.v11.dao.UserRepository userRepository2;
//
//	@Resource(name = "userFocusRepositoryV20")
//	private UserFocusRepository userFocusRepository;
//	@Resource(name = "userFocusRepositoryV2")
//	private cn.yi.gather.v11.dao.UserFocusRepository userFocusRepository2;
//	
//	@Resource(name = "spaceshowRepositoryV20")
//	private SpaceshowRepository spaceshowRepository;
//	@Resource(name = "spaceshowRepositoryV2")
//	private cn.yi.gather.v11.dao.SpaceshowRepository spaceshowRepository2;
//	
//	@Resource(name = "signLogRepositoryV20")
//	private SignLogRepository signLogRepository;
//	@Resource(name = "signLogRepositoryV2")
//	private cn.yi.gather.v11.dao.SignLogRepository signLogRepository2;
//	
//	@Resource(name = "reviewcontentRepositoryV20")
//	private ReviewcontentRepository reviewcontentRepository;
//	@Resource(name = "reviewcontentRepositoryV2")
//	private cn.yi.gather.v11.dao.ReviewcontentRepository reviewcontentRepository2;
//	
//	@Resource(name = "resetpasswordRepositoryV20")
//	private ResetpasswordRepository resetpasswordRepository;
//	@Resource(name = "resetpasswordRepositoryV2")
//	private cn.yi.gather.v11.dao.ResetpasswordRepository resetpasswordRepository2;
//	
//	@Resource(name = "pushlogRepositoryV20")
//	private PushlogRepository pushlogRepository;
//	@Resource(name = "pushlogRepositoryV2")
//	private cn.yi.gather.v11.dao.PushlogRepository pushlogRepository2;
//	
//	@Resource(name = "joinapplicationRepositoryV20")
//	private JoinapplicationRepository joinapplicationRepository;
//	@Resource(name = "joinapplicationRepositoryV2")
//	private cn.yi.gather.v11.dao.JoinapplicationRepository joinapplicationRepository2;
//	
//	@Resource(name = "indexuserinfoRepositoryV20")
//	private IndexuserinfoRepository indexuserinfoRepository;
//	@Resource(name = "indexuserinfoRepositoryV2")
//	private cn.yi.gather.v11.dao.IndexuserinfoRepository indexuserinfoRepository2;
//	
//	@Resource(name = "indexteaminfoRepositoryV20")
//	private IndexteaminfoRepository indexteaminfoRepository;
//	@Resource(name = "indexteaminfoRepositoryV2")
//	private cn.yi.gather.v11.dao.IndexteaminfoRepository indexteaminfoRepository2;
//	
//	@Resource(name = "indexorderbyinfoRepositoryV20")
//	private IndexorderbyinfoRepository indexorderbyinfoRepository;
//	@Resource(name = "indexorderbyinfoRepositoryV2")
//	private cn.yi.gather.v11.dao.IndexorderbyinfoRepository indexorderbyinfoRepository2;
//	
//	@Resource(name = "demandsRepositoryV20")
//	private DemandsRepository demandsRepository;
//	@Resource(name = "demandsRepositoryV2")
//	private cn.yi.gather.v11.dao.DemandsRepository demandsRepository2;
//	
//	@Resource(name = "focusRepositoryV20")
//	private FocusRepository focusRepository;
//	@Resource(name = "focusRepositoryV2")
//	private cn.yi.gather.v11.dao.FocusRepository focusRepository2;
//	
//	@Resource(name = "feedbackRepositoryV20")
//	private FeedbackRepository feedbackRepository;
//	@Resource(name = "feedbackRepositoryV2")
//	private cn.yi.gather.v11.dao.FeedbackRepository feedbackRepository2;
//	
//	@Resource(name = "dcommentRepositoryV20")
//	private DcommentRepository dcommentRepository;
//	@Resource(name = "dcommentRepositoryV2")
//	private cn.yi.gather.v11.dao.DcommentRepository dcommentRepository2;
//	
//	@Resource(name = "appversionRepositoryV20")
//	private AppversionRepository appversionRepository;
//	@Resource(name = "appversionRepositoryV2")
//	private cn.yi.gather.v11.dao.AppversionRepository appversionRepository2;
//	
//	@Resource(name = "applyvipRepositoryV20")
//	private ApplyvipRepository applyvipRepository;
//	@Resource(name = "applyvipRepositoryV2")
//	private cn.yi.gather.v11.dao.ApplyvipRepository applyvipRepository2;
//	
//	@Resource(name = "activityRepositoryV20")
//	private ActivityRepository activityRepository;
//	@Resource(name = "activityRepositoryV2")
//	private cn.yi.gather.v11.dao.ActivityRepository activityRepository2;
//	
//	@Resource(name = "commentRepositoryV20")
//	private CommentRepository commentRepository;
//	@Resource(name = "commentRepositoryV2")
//	private cn.yi.gather.v11.dao.CommentRepository commentRepository2;
//	
//	@Resource(name = "eventlogRepositoryV20")
//	private EventlogRepository eventlogRepository;
//	@Resource(name = "activityJoinRepositoryV2")
//	private ActivityJoinRepository activityJoinRepository;
	
	@Transactional
	@Override
	public void importData() {
		// TODO Auto-generated method stub
//		import_user();//用户
		
		//编辑器 部分 所有表+ label表+商品表  用户附加信息表 用户表部分数据确实默认值
		//import_demands();//需求
		/*import_user_focus();//用户关注
		import_spaceshow();//空间区域展示
		import_signlog();//签到记录
		import_reviewcontent();//回顾内容
		import_resetpwd();//重置密码
		import_pushlog();//推送记录
		import_joinapp();//入驻申请
		import_indexuser();//首页用户展示
		import_indexteam();//首页团队展示
		import_indexorder();//首页展示排序
		import_demands();//需求
		import_focus();//需求关注
		import_feedback();//意见反馈
		import_dcomment();//需求评论
		import_appversion();//应用版本
		import_applyvip();//会员申请
		import_activity();//活动
		import_comment();//活动评论
		import_activityjoin();//活动报名
*/	}
	
//	private void import_activityjoin(){
//		List<Eventlog> target = new ArrayList<Eventlog>();
//		List<ActivityJoin> list = activityJoinRepository.findAll();
//		for (ActivityJoin a : list) {
//			Eventlog e = new Eventlog();
//			Activity activity = new Activity();
//			activity.setId(a.getActivity().getId());
//			e.setActivity(activity);
//			e.setActtype("activity");
//			e.setCreatetime(a.getCreatetime());
//			e.setEventtype("sign");
//			e.setId(a.getId());
//			e.setUpdatetime(a.getCreatetime());
//			if(a.getUser()!=null){
//				User user = new User();
//				user.setId(a.getUser().getId());
//				e.setUser(user);
//				target.add(e);
//			}
//		}
//		eventlogRepository.save(target);
//	}
//	
//	private void import_comment(){
//		List<Comment> target = new ArrayList<Comment>();
//		List<cn.yi.gather.v11.entity.Comment> list = commentRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Comment c : list) {
//			Comment ct = new Comment();
//			Activity activity = new Activity();
//			activity.setId(c.getActivity().getId());
//			ct.setActivity(activity);
//			ct.setCreatedate(c.getCreatedate1());
//			ct.setId(c.getId());
//			ct.setPid(c.getPid());
//			ct.setTexts(c.getTexts());
//			User user = new User();
//			user.setId(c.getUser().getId());
//			ct.setUser(user);
//			User receive = new User();
//			receive.setId(c.getActivity().getUser().getId());
//			ct.setReceive(receive);
//			target.add(ct);
//		}
//		commentRepository.save(target);
//	}
//	
//	private void import_activity(){
//		List<Activity> target = new ArrayList<Activity>();
//		List<cn.yi.gather.v11.entity.Activity> list = activityRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Activity a : list) {
//			Activity ac = new Activity();
//			ac.setActtype(a.getActtype());
//			ac.setActtypetitle(a.getActtypetitle());
//			ac.setActtypeurl(a.getActtypeurl());
//			ac.setAddress(a.getAddress());
//			ac.setChecktype(a.getChecktype());
//			ac.setCommentnum(a.getCommentnum());
//			ac.setCost(a.getCost());
//			ac.setCover(a.getImg());
//			ac.setCreatedate(a.getCreatedate1());
//			ac.setEnddate(a.getEnddate1());
//			ac.setGoodnum(a.getGoodnum());
//			ac.setId(a.getId());
//			ac.setHuiguurl(a.getHuiguurl());
//			ac.setImgsnum(a.getImgsnum());
//			ac.setIsbanner(a.getIsbanner());
//			ac.setJoinnum(a.getJoinnum());
//			ac.setMaxnum(a.getMaxnum());
//			ac.setMaxprice(a.getMaxprice());
//			ac.setMinprice(a.getMinprice());
//			ac.setOnsale(a.getOnsale());
//			ac.setOpendate(a.getOpendate1());
//			ac.setPrice(a.getPrice());
//			ac.setPricekey(a.getPricekey());
//			ac.setPricetype(a.getPricetype());
//			ac.setPricevalue(a.getPricevalue());
//			ac.setPublishdate(a.getPublishdate1());
//			ac.setSharenum(a.getSharenum());
//			ac.setSignnum(a.getSignnum());
//			ac.setStatus(a.getStatus());
//			ac.setSummary(a.getSummary());
//			ac.setTel(a.getTel());
//			ac.setTitle(a.getTitle());
//			ac.setTitleimg(a.getTitleimg());
//			User user = new User();
//			user.setId(a.getUser().getId());
//			ac.setUser(user);
//			target.add(ac);
//		}
//		activityRepository.save(target);
//	}
//	
//	private void import_applyvip(){
//		List<Applyvip> target = new ArrayList<Applyvip>();
//		List<cn.yi.gather.v11.entity.Applyvip> list = applyvipRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Applyvip a : list) {
//			Applyvip av = new Applyvip();
//			av.setActname(a.getActname());
//			av.setActnum(a.getActnum());
//			av.setActtype(a.getActtype());
//			av.setBirthday(a.getBirthday1());
//			av.setChannel(a.getChannel());
//			av.setConstellation(a.getConstellation());
//			av.setCreatedate1(a.getCreatedate1());
//			av.setDuties(a.getDuties());
//			av.setEmail(a.getEmail());
//			av.setId(a.getId());
//			av.setIdnum(a.getIdnum());
//			av.setIntroduction(a.getInterests());
////			av.setInterests(a.getInterests());
//			av.setJobdemand(a.getJobdemand());
//			av.setJobyear(a.getJobyear());
//			av.setName(a.getName());
//			av.setProposal(a.getProposal());
//			av.setResult(a.getResult());
//			av.setSex(a.getSex());
//			av.setTel(a.getTel());
//			av.setUnderstand(a.getUnderstand());
//			target.add(av);
//		}
//		applyvipRepository.save(target);
//	}
//	
//	private void import_appversion(){
//		List<Appversion> target = new ArrayList<Appversion>();
//		List<cn.yi.gather.v11.entity.Appversion> list = appversionRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Appversion a : list) {
//			Appversion av = new Appversion();
//			av.setCreatedate(a.getCreatedate());
//			av.setDescription(a.getDescription());
//			av.setForce(a.getForce());
//			av.setId(a.getId());
//			av.setPlatform(a.getPlatform());
//			av.setUrl(a.getUrl());
//			av.setVer(a.getVer());
//			av.setVersion(a.getVersion());
//			target.add(av);
//		}
//		appversionRepository.save(target);
//		
//	}
//	
// 	private void import_dcomment(){
//		List<Dcomment> target = new ArrayList<Dcomment>();
//		List<cn.yi.gather.v11.entity.Dcomment> list = dcommentRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Dcomment d : list) {
//			Dcomment dc = new Dcomment();
//			dc.setCreatedate1(d.getCreatedate1());
//			Demands demands = new Demands();
//			demands.setId(d.getDemands().getId());
//			dc.setDemands(demands);
//			dc.setId(d.getId());
//			dc.setMediatype(d.getMediatype());
//			dc.setMediaurl0(d.getMediaurl0());
//			dc.setPid(d.getPid());
//			dc.setTexts(d.getTexts());
//			User user = new User();
//			user.setId(d.getReceive().getId());
//			dc.setReceive(user);
//			User user2 = new User();
//			user2.setId(d.getUser().getId());
//			target.add(dc);
//		}
//		dcommentRepository.save(target);
//	}
//	
//	private void import_feedback(){
//		List<Feedback> target = new ArrayList<Feedback>();
//		List<cn.yi.gather.v11.entity.Feedback> list = feedbackRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Feedback f : list) {
//			Feedback fb = new Feedback();
//			fb.setContent(f.getContent());
//			fb.setCreatedate(f.getCreatedate());
//			fb.setId(f.getId());
//			User user = new User();
//			user.setId(f.getUser().getId());
//			fb.setUser(user);
//			target.add(fb);
//		}
//		feedbackRepository.save(target);
//	}
//	
//	private void import_focus(){
//		List<Focus> target = new ArrayList<Focus>();
//		List<cn.yi.gather.v11.entity.Focus> list = focusRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Focus f : list) {
//			Focus fo = new Focus();
//			fo.setCreatedate(f.getCreatedate1());
//			Demands d = new Demands();
//			d.setId(f.getDemands().getId());
//			fo.setDemands(d);
//			User user = new User();
//			user.setId(f.getUser().getId());
//			fo.setUser(user);
//			target.add(fo);
//		}
//		focusRepository.save(target);
//	}
//	
//	private void import_demands(){
//		List<Demands> target = new ArrayList<Demands>();
//		List<cn.yi.gather.v11.entity.Demands> list = demandsRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Demands d : list) {
//			Demands ds = new Demands();
//			ds.setId(d.getId());
//			ds.setIscheck(d.getIscheck());
//			ds.setIsdel(d.getIsdel());
//			ds.setIsok(d.getIsok());
//			ds.setOnsale(d.getOnsale());
//			ds.setOnsaledate(d.getOnsaledate());
//			ds.setPublishdate0(d.getPublishdate0());
//			ds.setStatus(d.getStatus());
//			ds.setTexts(d.getTexts());
//			ds.setTitle(d.getTitle());
//			User user = new User();
//			user.setId(d.getPublishuser().getId());
//			ds.setPublishuser(user);
//			target.add(ds);
//		}
//		demandsRepository.save(target);
//	}
//	
//	private void import_indexorder(){
//		List<Indexorderbyinfo> target = new ArrayList<Indexorderbyinfo>();
//		List<cn.yi.gather.v11.entity.Indexorderbyinfo> list = indexorderbyinfoRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Indexorderbyinfo i : list) {
//			Indexorderbyinfo io = new Indexorderbyinfo();
//			io.setCreatedate(i.getCreatedate());
//			io.setId(i.getId());
//			io.setIdstring(i.getIdstring());
//			io.setIdtype(i.getIdtype());
//			target.add(io);
//		}
//		indexorderbyinfoRepository.save(target);
//	}
//	
//	private void import_indexteam(){
//		List<Indexteaminfo> target = new ArrayList<Indexteaminfo>();
//		List<cn.yi.gather.v11.entity.Indexteaminfo> list = indexteaminfoRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Indexteaminfo i : list) {
//			Indexteaminfo it = new Indexteaminfo();
//			it.setId(i.getId());
//			it.setJoindatetimes(i.getJoindatetimes1());
//			it.setTeamboss(i.getTeamboss());
//			it.setTeamcreate(i.getTeamcreate1());
//			it.setTeamgrowth(i.getTeamgrowth());
//			it.setTeamimg(i.getTeamimg());
//			it.setTeamminim(i.getTeamminim());
//			it.setTeamname(i.getTeamname());
//			it.setTeampeople(i.getTeampeople());
//			it.setTeamtitle(i.getTeamtitle());
//			it.setTeamtype(i.getTeamtype());
//			target.add(it);
//		}
//		indexteaminfoRepository.save(target);
//	}
//
//	private void import_indexuser(){
//		List<Indexuserinfo> target = new ArrayList<Indexuserinfo>();
//		List<cn.yi.gather.v11.entity.Indexuserinfo> list = indexuserinfoRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Indexuserinfo i : list) {
//			Indexuserinfo iu = new Indexuserinfo();
//			iu.setId(i.getId());
//			iu.setIuserimg(i.getIuserimg());
//			iu.setIuserjobinfo(i.getIuserjobinfo());
//			iu.setIusermaximg(iu.getIusermaximg());
//			iu.setIusernickname(iu.getIusernickname());
//			iu.setIusersummery(i.getIusersummery());
//			target.add(iu);
//		}
//		indexuserinfoRepository.save(target);
//	}
//	
//	private void import_joinapp(){
//		List<Joinapplication> target = new ArrayList<Joinapplication>();
//		List<cn.yi.gather.v11.entity.Joinapplication> list = joinapplicationRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Joinapplication j : list) {
//			Joinapplication ja = new Joinapplication();
//			ja.setCreatedate(j.getCreatedate1());
//			ja.setId(j.getId());
//			ja.setSettleddate(j.getSettleddate1());
//			ja.setTeamintroduce(j.getTeamintroduce());
//			ja.setTeamname(j.getTeamname());
//			ja.setTeampeople(j.getTeampeople());
//			ja.setTeamtype(j.getTeamtype());
//			target.add(ja);
//		}
//		joinapplicationRepository.save(target);
//	}
//	
//	private void import_pushlog(){
//		List<Pushlog> target = new ArrayList<Pushlog>();
//		List<cn.yi.gather.v11.entity.Pushlog> list = pushlogRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Pushlog p : list) {
//			Pushlog pl = new Pushlog();
//			pl.setCreatetime(p.getCreatetime());
//			pl.setId(p.getId());
//			pl.setTag(p.getTag());
//			pl.setTitle(p.getTitle());
//			target.add(pl);
//		}
//		pushlogRepository.save(target);
//	}
//	
//	private void import_resetpwd(){
//		List<Resetpassword> target = new ArrayList<Resetpassword>();
//		List<cn.yi.gather.v11.entity.Resetpassword> list = resetpasswordRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Resetpassword r : list) {
//			Resetpassword rp = new Resetpassword();
//			rp.setCreatedate(r.getCreatedate());
//			rp.setFlag(r.getFlag());
//			rp.setId(r.getId());
//			rp.setUser(r.getUser());
//			target.add(rp);
//		}
//		resetpasswordRepository.save(target);
//	}
//	
//	private void import_reviewcontent(){
//		List<Reviewcontent> target = new ArrayList<Reviewcontent>();
//		List<cn.yi.gather.v11.entity.Reviewcontent> list = reviewcontentRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Reviewcontent r : list) {
//			Reviewcontent rc = new Reviewcontent();
//			rc.setContent(r.getContent());
//			rc.setId(r.getId());
//			target.add(rc);
//		}
//		reviewcontentRepository.save(target);
//	}
//	
//	private void import_signlog(){
//		List<SignLog> target = new ArrayList<SignLog>();
//		List<cn.yi.gather.v11.entity.SignLog> list = signLogRepository2.findAll();
//		for (cn.yi.gather.v11.entity.SignLog sl : list) {
//			SignLog signLog = new SignLog();
//			signLog.setId(sl.getId());
//			signLog.setSigntime(sl.getSigntime());
//			signLog.setSigntype(sl.getSigntype());
//			User user = new User();
//			user.setId(sl.getUser().getId());
//			signLog.setUser(user);
//			target.add(signLog);
//		}
//		signLogRepository.save(target);
//	}
//
//	private void import_spaceshow(){
//		List<Spaceshow> target = new ArrayList<Spaceshow>();
//		List<cn.yi.gather.v11.entity.Spaceshow> list = spaceshowRepository2.findAll();
//		for (cn.yi.gather.v11.entity.Spaceshow ss : list) {
//			Spaceshow spaceshow = new Spaceshow();
//			spaceshow.setId(ss.getId());
//			spaceshow.setImage(ss.getImage());
//			spaceshow.setImages(ss.getImages());
//			spaceshow.setName(ss.getName());
//			spaceshow.setTitle(ss.getTitle());
//			target.add(spaceshow);
//		}
//		spaceshowRepository.save(target);
//	}
//
//	private void import_user_focus(){
//		List<UserFocus> target = new ArrayList<UserFocus>();
//		List<cn.yi.gather.v11.entity.UserFocus> list = userFocusRepository2.findAll();
//		for (cn.yi.gather.v11.entity.UserFocus uf : list) {
//			UserFocus focus = new UserFocus();
//			focus.setCreatedate(uf.getCreatedate());
//			focus.setId(uf.getId());
//			User user = new User();
//			user.setId(uf.getMe().getId());
//			focus.setMe(user);
//			user.setId(uf.getWho().getId());
//			focus.setWho(user);
//			focus.setRelation(0);
//			target.add(focus);
//		}
//		userFocusRepository.save(target);
//	}
//
//	private void import_user(){
//		List<User> target = new ArrayList<User>();
//		List<VipNumLog> numLogs = new ArrayList<VipNumLog>();
//		List<cn.yi.gather.v11.entity.User> list = userRepository2.findAll();
//		for (cn.yi.gather.v11.entity.User u : list) {
//			if(u.getRoot()<3){//普通用户数据不迁移
//				User user = new User();
//				user.setBirthday(u.getBirthday());
//				user.setCheckactnum(u.getCheckactnum()==null?0:u.getCheckactnum());
//				user.setCheckingactnum(u.getCheckingactnum()==null?0:u.getCheckingactnum());
//				user.setCity(u.getCity());
//				user.setCompany(u.getCompany());
//				user.setCompanyurl(u.getCompanyurl());
//				user.setConstellation(u.getConstellation());
//				user.setCreatedate(u.getCreatedate1());
//				user.setEmail(u.getEmail());
//				user.setIcnum(u.getIcnum());
//				user.setId(u.getId());
//				user.setIfindex(u.getIfindex()==null?0:u.getIfindex());
//				user.setIfspace(u.getIfspace()==null?0:u.getIfspace());
//				user.setIfvipshow(u.getIfvipshow()==null?0:u.getIfvipshow());
//				user.setIntroduction(u.getIntroduction());
//				user.setIsdel(u.getIsdel());
//				user.setJoinactnum(u.getJoinactnum());
//				user.setMaximg(u.getMaximg());
//				user.setMediaurl(u.getMediaurl());
//				user.setMinimg(u.getMinimg());
//				user.setMyactnum(u.getMyactnum());
//				user.setMypageurl(u.getMypageurl());
//				user.setNickname(u.getNickname());
//				user.setPakageaddress(u.getPakageaddress());
//				user.setPassword(u.getPassword());
//				user.setProvince(u.getProvince());
//				user.setRealname(u.getRealname());
//				user.setRoot(u.getRoot());
//				user.setSex(u.getSex());
//				user.setSpacesettime(u.getSpacesettime());
//				user.setTeamintroduction(u.getTeamintroduction());
//				user.setTeamname(u.getTeamname());
//				user.setTeamurl(u.getTeamurl());
//				user.setTelnum(u.getTelnum());
//				user.setTwonum(u.getTwonum()==null?"":u.getTwonum());
//				user.setUnum(u.getUnum());
//				user.setVipdate(u.getVipdate1());
//				user.setVipenddate(u.getVipenddate1());
//				user.setViplevel(u.getViplevel());
//				user.setVirtualname(u.getVirtualname());
//				user.setWechat(u.getWechat());
//				target.add(user);
//				//会员号使用记录
//				if(user.getRoot()<3&&u.getUnum()!=null){
//					VipNumLog vnLog = new VipNumLog();
//					vnLog.setVipNO(u.getUnum());
//					vnLog.setStatus(true);//true：已使用，false:未使用
//					vnLog.setOrderNO(Integer.valueOf(u.getUnum()));
//					numLogs.add(vnLog);
//				}
//			}
//		}
//		userRepository.save(target);	
//		vipNumLogRepository.save(numLogs);
//	}
	
	public void vipNumLog(Integer min,Integer max){
		List<VipNumLog> numLogs = new ArrayList<VipNumLog>();
		for (int i = min; i < max; i++) {
			VipNumLog log = new VipNumLog();
			log.setOrderNO(i);
			String vipNo = String.valueOf(i);
			while (vipNo.length()<5) {
				vipNo = "0" + vipNo;
			}
			log.setVipNO(vipNo);
			log.setStatus(false);
			numLogs.add(log);
		}
		vipNumLogRepository.save(numLogs);
	}
	
	/*public static void main(String[] args) throws ParseException {
		Calendar c = Calendar.getInstance();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date opendate = dateFormat1.parse("2015-01-20 20:00:00");
		Date createdate=dateFormat1.parse("2015-01-20 10:00:00");
		Date enddate=dateFormat1.parse("2015-01-22 10:00:00");
		Date now = c.getTime();
		if(now.after(enddate)){
			
			System.out.println(-1);
		}else if(now.after(opendate)&&now.before(enddate)) {
			System.out.println(0);//进行中
		}else {//未来
			c.add(Calendar.DATE, 1);
			Date now2 = c.getTime();//即将开启前一天
			c.setTime(createdate);
			c.add(Calendar.DATE, 3);
			Date now3 = c.getTime();//new:后三天
			if(now2.after(opendate)&&now.before(opendate)){
				System.out.println(3); //即将开启
			}else if (now.before(now3)) {
				System.out.println(2);//new
			}else {
				System.out.println(1);//未开启
			}
			System.out.println("now:"+dateFormat1.format(now));
			System.out.println("now2:"+dateFormat1.format(now2));
			System.out.println("now3:"+dateFormat1.format(now3));
			System.out.println("opendate:"+dateFormat1.format(opendate));
			System.out.println("createdate:"+dateFormat1.format(createdate));
		}
	}*/
}
