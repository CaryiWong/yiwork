package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 会员关注(人脉-名片夹，交情)
 * @author Lee.J.Eric
 * @time 2014年9月26日 下午4:30:53
 */
@Entity
@Table(name = "userfocus")
public class UserFocus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3036951166372353741L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@OneToOne(cascade=CascadeType.PERSIST,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="who_id")
	private User who;//被关注的会员ID
	
	@OneToOne(cascade=CascadeType.PERSIST,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="me_id")
	private User me;//自己的会员ID
	
	@Column(name="createdate")
	private Date createdate = new Date();//创建时间
	
	@Column(name = "relation")
	private Integer relation= 0;//0:收藏，1:交情
	
	@Column(name = "updatedate")
	private Date updatedate = new Date();//更新时间

	public UserFocus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public User getWho() {
		return who;
	}

	public void setWho(User who) {
		this.who = who;
	}

	public User getMe() {
		return me;
	}

	public void setMe(User me) {
		this.me = me;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj){
			return true;
		} else if(!(obj instanceof UserFocus)){
			return false;
		}
		final UserFocus uf = (UserFocus)obj;
		if(!getId().equals(uf.getId())){
			return false;
		}
		if (!getWho().equals(uf.getWho())) {
			return false;
		}
		if(!getMe().equals(uf.getMe())){
			return false;
		}
		return true;
	}
	
}
