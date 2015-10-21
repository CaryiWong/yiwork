package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "visit")
public class Visit implements Serializable {

	private static final long serialVersionUID = 8204982202913504229L;

	public Visit() {
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

	@Column(name = "organization_name")
	private String organization_name; // 公司／组织名称

	@Column(name = "organization_introduction")
	private String organization_introduction; // 公司／组织简介

	@Column(name = "num_of_visitors")
	private Integer num_of_visitors; // 参观人数
	
	@Column(name = "purpose")
	private String purpose; //参观目的

	@Column(name = "visit_date_time")
	private String visit_date_time; // 预计到访时间

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

	public String getOrganization_name() {
		return organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	public String getOrganization_introduction() {
		return organization_introduction;
	}

	public void setOrganization_introduction(String organization_introduction) {
		this.organization_introduction = organization_introduction;
	}

	public Integer getNum_of_visitors() {
		return num_of_visitors;
	}

	public void setNum_of_visitors(Integer num_of_visitors) {
		this.num_of_visitors = num_of_visitors;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getVisit_date_time() {
		return visit_date_time;
	}

	public void setVisit_date_time(String visit_date_time) {
		this.visit_date_time = visit_date_time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	 
}
