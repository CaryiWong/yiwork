package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "yqservice_info")
public class YqServiceInfo implements Serializable{
	private static final long serialVersionUID = -205035613835207628L;
	
	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20);//ID
	
	@Column(name="name")
	private String name;//服务名
	
	@Column(name="introduction")
	private String introduction="";//简介/标语/口号等
	
	@Column(name="city")
	private String city="";//城市
	
	@Column(name="titleimg")
	private String titleimg="";//封面图
	
	@Column(name="contexturl")
	private String contexturl="";// 详情/服务介绍  URL  相对地址
	
	@Column(name="createtime")
	private Date createtime=new Date();//创建时间
	
	@Column(name="isdel")
	private Integer isdel=0;//是否删除   默认0 没删除
	
	@Column(name="isshow")
	private Integer isshow=0;//是否显示/上架/审核 等  默认0 显示
	
	@Column(name="isbanner")
	private Integer isbanner=0;//0 显示  1 不显示
	
	@Column(name="email")
	private String email="";//邮箱

	public YqServiceInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YqServiceInfo(String id, String name, String introduction,
			String city, String titleimg, String contexturl, Date createtime,
			Integer isdel, Integer isshow) {
		super();
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.city = city;
		this.titleimg = titleimg;
		this.contexturl = contexturl;
		this.createtime = createtime;
		this.isdel = isdel;
		this.isshow = isshow;
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTitleimg() {
		return titleimg;
	}

	public void setTitleimg(String titleimg) {
		this.titleimg = titleimg;
	}

	public String getContexturl() {
		return contexturl;
	}

	public void setContexturl(String contexturl) {
		this.contexturl = contexturl;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}

	public Integer getIsbanner() {
		return isbanner;
	}

	public void setIsbanner(Integer isbanner) {
		this.isbanner = isbanner;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
