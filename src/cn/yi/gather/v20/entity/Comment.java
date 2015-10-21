package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

/**
 * 活动评论实体
 * @author Administrator
 *
 */
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
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="receive_id")
	private User receive;//接受者
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=Activity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="activity_id")
	private Activity activity;//被评论的活动

	@Column(name="texts",length=5000)
	private String texts="";//评论内容
		
	@Column(name="createdate")
	private Date createdate=new Date();//评论时间
	
	@Column (name="pid")
	private String pid=""; //暂未处理
	
	@Column(name="isdel")
	private Integer isdel=0;//是否标记删除
	
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

	public User getReceive() {
		return receive;
	}

	public void setReceive(User receive) {
		this.receive = receive;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
}
