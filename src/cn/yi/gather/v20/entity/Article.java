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

import cn.yi.gather.v20.permission.entity.AdminUser;

import com.tools.utils.RandomUtil;

/**
 * 文章 （动态里面所有文章类型的）
 * @author YS
 *
 */
@Entity
@Table(name = "article")
public class Article implements Serializable{

	private static final long serialVersionUID = -359723997538580670L;

	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);
	
	@Column(name="title")
	private String title="";  // 标题
	
	@Column(name="cover_img")
	private String coverimg=""; //封面图
	
	@Column(name="link_url",length=1000)
	private String linkurl="";  //外链
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = AdminUser.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private AdminUser user;  //创建者
	
	@Column(name="createdate")
	private Date createdate=new Date();// 时间
	
	@Column(name="ischeck")
	private Integer ischeck=0;  //是否审核   0 待审核 ，1 通过审核 ，2 审核失败
	
	@Column(name="isshelves")
	private Integer isshelves=0;  //是否上架  /活动上架(0)/下架(1)
	
	@Column(name="isdel")
	private Integer isdel=0;  //是否删除
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="workspace_id")
	private WorkSpaceInfo spaceInfo;// 所属空间信息
	

	public WorkSpaceInfo getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(WorkSpaceInfo spaceInfo) {
		this.spaceInfo = spaceInfo;
	}

	public Article() {
		super();
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

	public String getCoverimg() {
		return coverimg;
	}

	public void setCoverimg(String coverimg) {
		this.coverimg = coverimg;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getIscheck() {
		return ischeck;
	}

	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}

	public Integer getIsshelves() {
		return isshelves;
	}

	public void setIsshelves(Integer isshelves) {
		this.isshelves = isshelves;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
}
