package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 2.2 社群
 * @author ybao
 * @see  2015-07-20 
 */
@Entity
@Table(name = "community_info")
public class CommunityInfo implements Serializable{
	private static final long serialVersionUID = -2928193429718475747L;

	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20);//ID
	
	@Column(name="zname")
	private String zname;//中文社群名
	
	@Column(name="ename")
	private String ename="";//英文社群名
	
	@Column(name="introduction")
	private String introduction="";//简介/标语/口号等
	
	@Column(name="logo")
	private String logo="";
	
	@Column(name="titleimg")
	private String titleimg="";//封面图
	
	@Column(name="contexturl")
	private String contexturl="";// 详情/社群介绍  URL  相对地址
	
	@Column(name="city")
	private String city="";//城市
	
	@Column(name="tel")
	private String tel="";//联系电话
	
	@Column(name="address")
	private String address="";//联系地址
	
	@Column(name="weburl")
	private String weburl="";//社群自己的网站/网页URL  绝对地址 支持外链
	
	@Column(name="createtime")
	private Date createtime=new Date();//创建时间
	
	@Column(name="isdel")
	private Integer isdel=0;//是否删除   默认0 没删除
	
	@Column(name="isshow")
	private Integer isshow=0;//是否显示/上架/审核 等  默认0 显示
	
	@Column(name="isbanner")
	private Integer isbanner=0;//0 显示  1 不显示

	public CommunityInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommunityInfo(String id, String zname, String ename, String introduction,
			String logo, String titleimg, String contexturl, String city,
			String tel, String address, String weburl, Date createtime,
			Integer isdel, Integer isshow) {
		super();
		this.id = id;
		this.zname = zname;
		this.ename = ename;
		this.introduction = introduction;
		this.logo = logo;
		this.titleimg = titleimg;
		this.contexturl = contexturl;
		this.city = city;
		this.tel = tel;
		this.address = address;
		this.weburl = weburl;
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

	public String getZname() {
		return zname;
	}

	public void setZname(String zname) {
		this.zname = zname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
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
	
	
	
}
