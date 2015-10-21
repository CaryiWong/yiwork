package cn.yi.gather.v20.entity;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 消息中心-消息表
 * @author Lee.J.Eric
 * @time 2014年12月2日 下午4:13:57
 */
@Entity
@Table(name = "notice_msg")
public class NoticeMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5935496235095671783L;

	/**
	 * 给msgtype 使用
	 * @author ys
	 * 消息类型 coffee:咖啡，activity:活动,gathering:小活动,demands:需求,invite:邀请,yiquestion:问小一,course:课程
	 */
	public static enum NoticeMsgTypeValue{
		COFFEE("coffee"),
		ACTIVITY("activity"),
		GATHERING("gathering"),
		DEMANDS("demands"),
		COURSE("course"),
		YIQUESTION("yiquestion"),
		INVITE("invite");
		
		public String value;
		NoticeMsgTypeValue(String value) {
			this.value=value;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	/**
	 * actiontype 使用
	 * @author ys
	 * 行为类型 sign:报名,review:回顾,cancle:取消,feedback:反馈,invite:邀请,share:分享,speak聊天,answer
	 */
	public static enum NoticeActionTypeValue{
		SIGN("sign"),
		REVIEW("review"),
		CANCLE("cancle"),
		FEEDBACK("feedback"),
		INVITE("invite"),
		SPEAK("speak"),
		ANSWER("answer"),
		SHARE("share");
		public String value;
		NoticeActionTypeValue(String value) {
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
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20);
	
	@Column(name = "msg_type")
	private String msgtype="";//消息类型，如:coffee:咖啡，activity:活动,invitation:邀请
	
	@Column(name = "contents",length=500)
	private String contents = "";//内容
	
	@Column(name = "icon")
	private String icon = "";//图标
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_notice_msg_tags",joinColumns = { @JoinColumn(name = "notice_msg_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private Set<User> tags = new HashSet<User>();//发送对象，用户id
	
	@Column(name = "createdate")
	private Date createdate = new Date();
	
	@Column(name = "actiontype")
	private String actiontype="";//行为类型 sign:报名，review:回顾，cancle:取消，feedback:反馈,invite:邀请,share:分享
	
	@Column(name = "param")
	private String param = "";//目标主体id
	
	@Column(name = "isread")
	private Integer isread=0;//方便查询列表时 把邀请函的对话归拢

	public NoticeMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Set<User> getTags() {
		return tags;
	}

	public void setTags(Set<User> tags) {
		this.tags = tags;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}
	
	
}
