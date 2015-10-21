package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 小活动
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "gathering")
public class Gathering implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2441513104649275615L;
	
	public static final String entityName = "gathering";
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@Column(name = "title")
	private String title="";//标题、主题
	
	@Column (name="summary",length=20000)
	private String summary="";//简介、介绍
	
	@Column(name = "createdate")
	private Date createdate=new Date();

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "opendate")
	private Date opendate=new Date();

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "enddate")
	private Date enddate = new Date();
	
	@Column(name = "address")
	private String address="";//地点
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//发起者ID
	
	@Column(name = "isdel")
	private Integer isdel = 0;//删除标记----0:正常，1：删除
	
	@Column(name = "deltext")
	private String deltext = "";//删除备注
	
	@Column(name = "style")
	private String style = "";//风格(分享时需要带风格)
	
	@Transient
	private Integer status;//状态，0:进行中，-1:结束，1:未开启，2:new,3:即将开启
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="workspace_id")
	private WorkSpaceInfo spaceInfo;// 所属空间信息
	

	public WorkSpaceInfo getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(WorkSpaceInfo spaceInfo) {
		this.spaceInfo = spaceInfo;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	@Column(name="h5url")
	private String h5url="";//活动的H5 页面

	@Column(name="imgtexturl")
	private String imgtexturl="";//活动的图文 页面
	
	@Column(name="h5objid")
	private String h5objid="";//创建模板时候的临时ID
	
	@Column(name="imgtextobjid")
	private String imgtextobjid="";//创建模板时候的临时ID

	public Gathering() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getOpendate() {
		return opendate;
	}

	public void setOpendate(Date opendate) {
		this.opendate = opendate;
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

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public String getDeltext() {
		return deltext;
	}

	public void setDeltext(String deltext) {
		this.deltext = deltext;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getStatus() {
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		c.setTime(opendate);
		c.add(Calendar.HOUR, 3);
		Date enddate = c.getTime();
		if(now.after(enddate)){
			return -1;//结束
		}else if(now.after(opendate)&&now.before(enddate)) {
			return 0;//进行中
		}else {//未来
			c.add(Calendar.DATE, -1);
			Date now2 = c.getTime();//即将开启前一天
			c.setTime(createdate);
			c.add(Calendar.DATE, 3);
			Date now3 = c.getTime();//new:后三天
			if(now.after(now2)&&now.before(opendate)){
				return 3;//即将开启
			}else if (now.before(now3)) {
				return 2;//new
			}else {
				return 1;//未开启
			}
		}
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getH5url() {
		return h5url;
	}

	public void setH5url(String h5url) {
		this.h5url = h5url;
	}

	public String getImgtexturl() {
		return imgtexturl;
	}

	public void setImgtexturl(String imgtexturl) {
		this.imgtexturl = imgtexturl;
	}

	public String getH5objid() {
		return h5objid;
	}

	public void setH5objid(String h5objid) {
		this.h5objid = h5objid;
	}

	public String getImgtextobjid() {
		return imgtextobjid;
	}

	public void setImgtextobjid(String imgtextobjid) {
		this.imgtextobjid = imgtextobjid;
	}

//	public Set<User> getSignuser() {
//		return signuser;
//	}
//
//	public void setSignuser(Set<User> signuser) {
//		this.signuser = signuser;
//	}

//	public Set<Gallery> getGallery() {
//		return gallery;
//	}
//
//	public void setGallery(Set<Gallery> gallery) {
//		this.gallery = gallery;
//	}

	
}
