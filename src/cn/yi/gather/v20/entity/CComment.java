package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

/**
 * 课程评论
 * @author Lee.J.Eric
 * @time 2014年10月14日 下午5:38:04
 */
@Table
@Entity(name = "ccomment")
public class CComment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8433059988922754364L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//评论人
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="receive_id")
	private User receiver;//接受者
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=Course.class,fetch=FetchType.EAGER)
	@JoinColumn(name="course_id")
	private Course course;//被评论的主体
	
	@Column(name="texts",length=5000)
	private String texts="";//评论内容
	
	@Column(name="createdate")
	private Date createdate=new Date();//评论时间
	
	@Column(name="isdel")
	private Integer isdel=0;

	public CComment() {
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

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getTexts() {
		try {
			texts = URLDecoder.decode(texts, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texts;
	}

	public void setTexts(String texts) {
		try {
			this.texts = URLEncoder.encode(texts, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

}
