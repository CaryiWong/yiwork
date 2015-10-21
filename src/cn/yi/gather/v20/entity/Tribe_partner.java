package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "tribe_partner")
public class Tribe_partner implements Serializable {

	private static final long serialVersionUID = 8204982202913504274L;

	public Tribe_partner() {
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

	@Column(name = "product_name")
	private String product_name; // 产品／服务名称

	@Column(name = "product_introduction")
	private String product_introduction; // 产品／服务简介

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

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_introduction() {
		return product_introduction;
	}

	public void setProduct_introduction(String product_introduction) {
		this.product_introduction = product_introduction;
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

	@Column(name = "idea")
	private String idea; //合作想法

}
