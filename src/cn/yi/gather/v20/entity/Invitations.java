package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.org.apache.regexp.internal.recompile;
import com.tools.utils.RandomUtil;

/**
 * 邀请涵
 * @author Administrator
 *
 */
@Entity
@Table(name = "invitations")
public class Invitations implements Serializable{

	private static final long serialVersionUID = -8153560097298707660L;

	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@Column(name="title")
	private String title;//标题
	
	@Column(name="summary")
	private String summary="";//自我简介
	
	@Column(name="thingstexts")
	private String thingstexts="";//事项
	
	@Column(name="createtime")
	private Date createtime=new Date();//记录创建时间
	
	@Column(name="opentime")
	private Date opentime;//预定时间
	
	@Column(name="ifhavetime")
	private String ifhavetime="Y";//是否选择了预定时间
	
	@Column(name="address")
	private String address="";//地址
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//邀请者
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="inviteuser_id")
	private User inviteuser;//被邀请者
	
	@Column(name="coffee")
	private String coffee="N";//是否买咖啡
	
	@Column(name="answer")
	private String answer="N";//是否结束邀请  新加 S 表示正在聊天  Y是确定了见面
	
	@Column(name="cnum")
	private Integer cnum=0;//评论数（2人之间的对话）
	
	@Column(name="itype")
	private String itype="";//不填表示是邀请涵    sj 表示随机获取咖啡
	
	@Transient
	private Integer status;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getThingstexts() {
		return thingstexts;
	}

	public void setThingstexts(String thingstexts) {
		this.thingstexts = thingstexts;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getOpentime() {
		if(opentime==null){
			return new Date();
		}
		return opentime;
	}

	public void setOpentime(Date opentime) {
		this.opentime = opentime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getInviteuser() {
		return inviteuser;
	}

	public void setInviteuser(User inviteuser) {
		this.inviteuser = inviteuser;
	}

	public Invitations() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIfhavetime() {
		return ifhavetime;
	}

	public void setIfhavetime(String ifhavetime) {
		this.ifhavetime = ifhavetime;
	}

	public String getCoffee() {
		return coffee;
	}

	public void setCoffee(String coffee) {
		this.coffee = coffee;
	}

	public Integer getCnum() {
		return cnum;
	}

	public void setCnum(Integer cnum) {
		this.cnum = cnum;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getItype() {
		return itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public Integer getStatus() {
		Calendar c = Calendar.getInstance();
		Date today = c.getTime();
		c.add(Calendar.DATE, 1);
		Date tomorrow = c.getTime();
		if(today.after(opentime)){//已结束
			return -1;
		}else if (tomorrow.after(opentime)) {//即将开始，约定时间的前一天
			return 3;
		}else {//未开始
			return 1;
		}
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
