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

/**
 * 用户签到记录表
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "signlog")
public class SignLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 566139822176700159L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//签到/签出的用户
	
	@Column(name = "signtime")
	private Date signtime = new Date();//签到/签出时间
	
	@Column(name = "signtype")
	private String signtype;//类型：签到(in)/签出(out)
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="workspace_id")
	private WorkSpaceInfo spaceInfo;// 所属空间信息

	public SignLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SignLog(User user, String signtype) {
		super();
		this.user = user;
		this.signtype = signtype;
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

	public Date getSigntime() {
		return signtime;
	}

	public void setSigntime(Date signtime) {
		this.signtime = signtime;
	}

	public String getSigntype() {
		return signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}

	public WorkSpaceInfo getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(WorkSpaceInfo spaceInfo) {
		this.spaceInfo = spaceInfo;
	}
	
}
