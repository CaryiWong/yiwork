package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "demands")
public class Demands implements Serializable {

	private static final long serialVersionUID = 9207774321321587345L;
	
	public static final String entityName = "demands";

	/**
	 * demandtype  使用   需求的类型
	 * @author ys
	 * 找人:findpeople   合作:cooperation  提问:askquestion
	 */
	public static enum DemandTypeValue{
		FINDPEOPLE("findpeople"),
		COOPERATION("cooperation"),
		ASKQUESTION("askquestion");
		public String value;
		DemandTypeValue(String value) {
			this.value=value;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	/**
	 * 需求有效期 提醒时间
	 * @author YS
	 *
	 */
	public static enum DemandDate{
		EFFECTIVE(15),
		REMIND(5);
		public int value;
		DemandDate(int value) {
			this.value=value;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
	}
	
	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis()
			+ RandomUtil.getRandomeStringForLength(20);// 主键ID

	@Column(name = "title")
	private String title;// 标题

	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User publishuser;// 发布者

	@Column(name = "publishdate0")
	private Date publishdate0 = new Date();// 发布时间

	@Column(name = "texts", length = 4000)
	private String texts;// 需求内容

	@Column(name = "status")
	private Integer status = 0;// 需求状态 0 需要解决 1 正在解决 2 解决完成  3删除

	@Column(name = "ischeck")
	private Integer ischeck = 0;// 待审核

	@Column(name = "isdel")
	private Integer isdel = 0;// 0 未删除 1 取消任务

	@Column(name = "isok")
	private Integer isok = 0;// 0 未解答 1 已解答

	@Column(name = "onsale")
	private Integer onsale = 0;// 上架(0)/下架(1)

	@Column(name = "onsaledate")
	// 上下架操作时间
	private Date onsaledate = new Date();

	/* 2014-09-04 YS */

	@Column(name = "commentnum")
	private Integer commentnum = 0; // 需求评论数统计 有评论的时候需要update 需求
	
	@Column(name="h5url")
	private String h5url="";//活动的H5 页面

	@Column(name="imgtexturl")
	private String imgtexturl="";//活动的图文 页面
	
	@Column(name="h5objid")
	private String h5objid="";//创建模板时候的临时ID
	
	@Column(name="imgtextobjid")
	private String imgtextobjid="";//创建模板时候的临时ID

	/* 2014-09-04 YS */
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_user_demand_praises",joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "demand_id", referencedColumnName = "id") })
	private Set<User> praises=new HashSet<User>();
	
	@Column(name="demand_type")
	private String demandtype;//需求类型
	
	@Column(name = "isdx")
	private Integer isdx = 0;// 0 未答谢 1 已答谢
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="workspace_id")
	private WorkSpaceInfo spaceInfo;// 所属空间信息
	
	
	public WorkSpaceInfo getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(WorkSpaceInfo spaceInfo) {
		this.spaceInfo = spaceInfo;
	}

	public Demands() {
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

	public User getPublishuser() {
		return publishuser;
	}

	public void setPublishuser(User publishuser) {
		this.publishuser = publishuser;
	}

	public Date getPublishdate0() {
		return publishdate0;
	}

	public void setPublishdate0(Date publishdate0) {
		this.publishdate0 = publishdate0;
	}

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIscheck() {
		return ischeck;
	}

	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Integer getIsok() {
		return isok;
	}

	public void setIsok(Integer isok) {
		this.isok = isok;
	}

	public Integer getOnsale() {
		if (onsale == null) {
			return 0;
		}
		return onsale;
	}

	public void setOnsale(Integer onsale) {
		this.onsale = onsale;
	}

	public Date getOnsaledate() {
		return onsaledate;
	}

	public void setOnsaledate(Date onsaledate) {
		this.onsaledate = onsaledate;
	}

	public Integer getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(Integer commentnum) {
		this.commentnum = commentnum;
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

	public Set<User> getPraises() {
		return praises;
	}

	public void setPraises(Set<User> praises) {
		this.praises = praises;
	}

	public String getDemandtype() {
		return demandtype;
	}

	public void setDemandtype(String demandtype) {
		this.demandtype = demandtype;
	}

	public Integer getIsdx() {
		return isdx;
	}

	public void setIsdx(Integer isdx) {
		this.isdx = isdx;
	}

}
