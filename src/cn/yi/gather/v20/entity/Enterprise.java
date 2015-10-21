package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "enterprise")
public class Enterprise implements Serializable {

	private static final long serialVersionUID = 8204982202913504210L;

	public Enterprise() {
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

	@Column(name = "enterprise_name")
	private String enterprise_name; // 企业名

	@Column(name = "project_introduction")
	private String project_introduction; // 项目介绍

	@Column(name = "need_service")
	private String need_service; // 需要的服务(以逗号2隔开的字符串列表，例如：need_service=用户研究,商业模式梳理)

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

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getProject_introduction() {
		return project_introduction;
	}

	public void setProject_introduction(String project_introduction) {
		this.project_introduction = project_introduction;
	}

	public String getNeed_service() {
		return need_service;
	}

	public void setNeed_service(String need_service) {
		this.need_service = need_service;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
 

 

}
