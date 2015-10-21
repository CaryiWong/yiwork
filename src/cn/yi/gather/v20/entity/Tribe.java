package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "tribe")
public class Tribe implements Serializable {

	private static final long serialVersionUID = 8204982202913504270L;

	public Tribe() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis()
			+ RandomUtil.getRandomeStringForLength(20);// 唯一码;
	
	@Column(name = "name")
	private String name;// 姓名

	@Column(name = "phone_number")
	private String phone_number; // 手机号

	@Column(name = "email")
	private String email; // Email地址

	@Column(name = "is_member")
	private Integer is_member = 0; // 是否一起的会员 0是 1 不是

	@Column(name = "tribe_name")
	private String tribe_name; // 社群名

	@Column(name = "tribe_introduction")
	private String tribe_introduction; // 社群介绍

	@Column(name = "plan")
	private String plan; // 社群发展规划
	
	@Column(name = "creator_introduction")
	private String creator_introduction; // 发起人／发起团队的介绍

	@Column(name = "reason")
	private String reason; // 为什么想加入社群共建计划

	@Column(name="createdate")
	private Date createdate=new Date();// 时间
	
	@Column(name="ip")
	private String ip;// ip

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIs_member() {
		return is_member;
	}

	public void setIs_member(Integer is_member) {
		this.is_member = is_member;
	}

	public String getTribe_name() {
		return tribe_name;
	}

	public void setTribe_name(String tribe_name) {
		this.tribe_name = tribe_name;
	}

	public String getTribe_introduction() {
		return tribe_introduction;
	}

	public void setTribe_introduction(String tribe_introduction) {
		this.tribe_introduction = tribe_introduction;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getCreator_introduction() {
		return creator_introduction;
	}

	public void setCreator_introduction(String creator_introduction) {
		this.creator_introduction = creator_introduction;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
