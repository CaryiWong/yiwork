package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "eventlog")
public class Eventlog implements Serializable{

	private static final long serialVersionUID = 6646631555587043178L;
	
	public static enum EventTypeValue{
		ACTIVITY("activity"),
		GATHERING("gathering"),
		COURSE("course"),
		NOTICE("notice"),
		INVITE("invite"),
		ARTICLE("article");
		public String value;

		EventTypeValue(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user; // 操作者
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Activity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="activity_id")
	private Activity activity;//活动
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Gathering.class,fetch=FetchType.EAGER)
	@JoinColumn(name="gathering_id")
	private Gathering gathering;//小活动
	
	//还有 课程 公告
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Notice.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "notice_id")
	private Notice notice;//社区公告
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Course.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Invitations.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "invitation_id")
	private Invitations invitation;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Article.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "article_id")
	private Article article;//文章
	
	@Column(name="acttype")
	private String acttype="";// 事件类型  activity  活动  || gathering  小活动 || course 课程 ||notice 公告 ||invite邀请
	
	@Column(name="eventtype")
	private String eventtype="";// LOG 类型 create:创建  join:参加  sign:报名  focus:关注 review:回顾||invite邀请
	
	@Column(name="createtime")
	private Date createtime=new Date(); //记录产生时间 jointype=0 1 2  时排序使用
	
	@Column(name="updatetime")
	private Date updatetime=new Date();//记录产生时间  jointype=3时 为排序时间字段
	
	@Column(name="informationanswer")
	private String informationanswer="";//报名信息答案 “”，“”，“”，“” 格式
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = Question.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_eventlog_question", joinColumns = { @JoinColumn(name = "eventlog_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "question_id", referencedColumnName = "id") })
	private List<Question> qlist=new ArrayList<Question>();

	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = City.class,fetch=FetchType.EAGER)
	@JoinColumn(name="city_id")
	private City city; // 操作者
	
	public Eventlog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Gathering getGathering() {
		return gathering;
	}

	public void setGathering(Gathering gathering) {
		this.gathering = gathering;
	}

	public String getActtype() {
		return acttype;
	}

	public void setActtype(String acttype) {
		this.acttype = acttype;
	}

	public String getEventtype() {
		return eventtype;
	}

	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getInformationanswer() {
		return informationanswer;
	}

	public void setInformationanswer(String informationanswer) {
		this.informationanswer = informationanswer;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Invitations getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitations invitation) {
		this.invitation = invitation;
	}

	public List<Question> getQlist() {
		return qlist;
	}

	public void setQlist(List<Question> qlist) {
		this.qlist = qlist;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
