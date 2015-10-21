package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "gallery")
public class Gallery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2834727335505631338L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@Column(name = "img")
	private String img="";//图片访问名
	
	@Column(name = "flag")
	private Integer flag = 0;//图片所属类型，0 小活动，1 活动，2 课程

	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Gathering.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "gathering_id")
	private Gathering gathering;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Activity.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "activity_id")
	private Activity activity;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Course.class,fetch=FetchType.EAGER)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//图片上传者
	
	@Column(name = "createtime")
	private Date createtime = new Date();
	
	@Column(name = "isdel")
	private Integer isdel = 0;//删除标记，0正常，1删除

	public Gallery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Gathering getGathering() {
		return gathering;
	}

	public void setGathering(Gathering gathering) {
		this.gathering = gathering;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	// 重写equals方法
		public boolean equals(Object other) {
			if (this == other) {
				return true;// 如果引用地址相同，即引用的是同一个对象，就返回true
			}

			if (!(other instanceof Gallery)) { // 如果other不是Gallery类的实例，返回false
				return false;
			}

			final Gallery gallery = (Gallery) other;
			if (!getId().equals(gallery.getId())) // id值不同，返回false
				return false;
			return true;
		}

		// 重写hashCode()方法
		public int hashCode() {
			return getId().hashCode()+getImg().hashCode();
		}
}
