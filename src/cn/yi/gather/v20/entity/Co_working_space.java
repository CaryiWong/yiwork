package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "co_working_space")
public class Co_working_space implements Serializable {

	private static final long serialVersionUID = 8204982202913504271L;

	public Co_working_space() {
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

	@Column(name = "space_name")
	private String space_name; // 空间名称

	@Column(name = "city")
	private String city; // 空间所在的城市

	@Column(name = "space_introduction")
	private String space_introduction; // 空间介绍

	@Column(name = "idea")
	private String idea; // 合作想法

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

	public String getSpace_name() {
		return space_name;
	}

	public void setSpace_name(String space_name) {
		this.space_name = space_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSpace_introduction() {
		return space_introduction;
	}

	public void setSpace_introduction(String space_introduction) {
		this.space_introduction = space_introduction;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 

 
}
