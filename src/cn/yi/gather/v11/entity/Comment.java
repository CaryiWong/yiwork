package cn.yi.gather.v11.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "comment")
public class Comment implements Serializable{

	private static final long serialVersionUID = 444220L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//评论人
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=Activity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="activity_id")
	private Activity activity;//被评论的活动

	@Column(name="texts")
	private String texts="";//评论内容
	
	@Transient
	private Set<String> imgs;
	
	@Column(name="imgs0")
	private String imgs0;// 评论是附带的图片列表 文件名,文件名
	
	@Transient
	private Long createdate;
	
	@Column(name="createdate1")
	private Date createdate1=new Date();//评论时间
	
	@Column (name="pid")
	private String pid="";
	
	@Transient
	private List<Comment> replycomments=new ArrayList<Comment>();
	
	public Comment() {
		super();
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

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public Set<String> getImgs() {
		Set<String> set = new HashSet<String>();
		if(imgs0!=null){
			String[] ar = imgs0.split(",");
			if(ar!=null&&ar.length>0){
				Collections.addAll(set, ar);
			}
		}
		return set;
	}

	public void setImgs(Set<String> imgs) {
		this.imgs = imgs;
	}

	public String getImgs0() {
		return imgs0;
	}

	public void setImgs0(String imgs0) {
		this.imgs0 = imgs0;
	}

	public Long getCreatedate() {
		if(createdate1!=null){
			return createdate1.getTime();
		}else{
			return 0l;
		}
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	public Date getCreatedate1() {
		return createdate1;
	}

	public void setCreatedate1(Date createdate1) {
		this.createdate1 = createdate1;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<Comment> getReplycomments() {
		return replycomments;
	}

	public void setReplycomments(List<Comment> replycomments) {
		this.replycomments = replycomments;
	}
	
}
