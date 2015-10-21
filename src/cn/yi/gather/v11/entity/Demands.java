package cn.yi.gather.v11.entity;

import java.io.Serializable;
import java.util.Date;
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

@Entity
@Table(name = "demands")
public class Demands implements Serializable{

	private static final long serialVersionUID = 9207774321321587345L;
	
	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//主键ID
	
	@Column(name="title")
	private String title;//标题
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User publishuser;//发布者
	
	@Column(name="publishdate0")
	private Date publishdate0=new Date();//发布时间
	
	@Transient
	private Long publishdate;
	
	@Column(name="texts",length=4000)
	private String texts;//需求内容
	
	@Column(name="status")
	private Integer status;// 需求状态 0 需要解决  1  正在解决  2 解决完成
	
	@Column(name="demandstype")
	private Integer demandstype=0;//0  寻人  1 视频制作  2 发起活动  3 其他
	
	@ManyToMany(cascade=CascadeType.REFRESH,targetEntity=Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name="ref_dem_make_label",joinColumns={@JoinColumn(name="demands_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="labels_id",referencedColumnName="id")})
	private List<Labels> makelabel;//视频制作类型
	
	@ManyToMany(cascade=CascadeType.REFRESH,targetEntity=Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name="ref_dem_form_label",joinColumns={@JoinColumn(name="demands_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="labels_id",referencedColumnName="id")})
	private List<Labels> formlabel;//形式
	
	@ManyToMany(cascade=CascadeType.REFRESH,targetEntity=Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name="ref_dem_people_label",joinColumns={@JoinColumn(name="demands_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="labels_id",referencedColumnName="id")})
	private List<Labels> peoplelabel;//面向人群
	
	@ManyToMany(cascade=CascadeType.REFRESH,targetEntity=Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name="ref_dem_find_label",joinColumns={@JoinColumn(name="demands_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="labels_id",referencedColumnName="id")})
	private List<Labels> findlabel;//寻人类型
	
	@ManyToMany(cascade=CascadeType.REFRESH,targetEntity=Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name="ref_dem_label",joinColumns={@JoinColumn(name="demands_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="labels_id",referencedColumnName="id")})
	private List<Labels> label;//领域
	
	@Column(name="plandatetime")
	private Date plandatetime =new Date();//计划时间
	
	@Transient
	private Long plandatetime1;
	
	@Column(name="hopepeoples")
	private Integer hopepeoples=0;//希望人数
	
	@Column(name="ischeck")
	private Integer ischeck = 0;//待审核
	
	@Column(name="cover")
	private String cover = "";//封面
	
	@Column(name="mediaurl")
	private String mediaurl = "";//视频地址
	
	@Column(name="isneed")
	private Integer isneed=0;//0 提供资源   1 请求资源
	
	@Column(name="focusnum")
	private Integer focusnum=0;//需求关注数
	
/*	@ManyToMany(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinTable(name="ref_dem_focus_user",joinColumns={@JoinColumn(name="demands_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName="id")})
	private Set<User> focususer;//关注者列表
*/	
	@Column(name="isdel")
	private Integer isdel=0;//0 未删除  1 取消任务
	
	@Column(name="isok")
	private Integer isok=0;//0 未解答  1 已解答
	
	@Column(name="onsale")
	private Integer onsale=0;//上架(0)/下架(1)

	@Column(name="onsaledate") //上下架操作时间
	private Date onsaledate=new Date();
	
	@Transient
	private Long onsaledate1;
	
	
	public  Demands() {
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

	public Long getPublishdate() {
		if(publishdate0!=null){
			return publishdate0.getTime();
		}
		return 0l;
	}

	public void setPublishdate(Long publishdate) {
		this.publishdate = publishdate;
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

	public Integer getDemandstype() {
		return demandstype;
	}

	public void setDemandstype(Integer demandstype) {
		this.demandstype = demandstype;
	}

	public List<Labels> getMakelabel() {
		return makelabel;
	}

	public void setMakelabel(List<Labels> makelabel) {
		this.makelabel = makelabel;
	}

	public List<Labels> getFormlabel() {
		return formlabel;
	}

	public void setFormlabel(List<Labels> formlabel) {
		this.formlabel = formlabel;
	}

	public List<Labels> getPeoplelabel() {
		return peoplelabel;
	}

	public void setPeoplelabel(List<Labels> peoplelabel) {
		this.peoplelabel = peoplelabel;
	}

	public List<Labels> getFindlabel() {
		return findlabel;
	}

	public void setFindlabel(List<Labels> findlabel) {
		this.findlabel = findlabel;
	}

	public Date getPlandatetime() {
		return plandatetime;
	}

	public void setPlandatetime(Date plandatetime) {
		this.plandatetime = plandatetime;
	}

	public Long getPlandatetime1() {
		if(plandatetime!=null){
			return plandatetime.getTime();
		}
		return 0l;
	}

	public void setPlandatetime1(Long plandatetime1) {
		this.plandatetime1 = plandatetime1;
	}

	public Integer getHopepeoples() {
		return hopepeoples;
	}

	public void setHopepeoples(Integer hopepeoples) {
		this.hopepeoples = hopepeoples;
	}

	public Integer getIscheck() {
		return ischeck;
	}

	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getIsneed() {
		return isneed;
	}

	public void setIsneed(Integer isneed) {
		this.isneed = isneed;
	}

	public Integer getFocusnum() {
		return focusnum;
	}

	public void setFocusnum(Integer focusnum) {
		this.focusnum = focusnum;
	}

	/*public Set<User> getFocususer() {
		return focususer;
	}

	public void setFocususer(Set<User> focususer) {
		this.focususer = focususer;
	}*/

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

	public List<Labels> getLabel() {
		return label;
	}

	public void setLabel(List<Labels> label) {
		this.label = label;
	}

	public String getMediaurl() {
		return mediaurl;
	}

	public void setMediaurl(String mediaurl) {
		this.mediaurl = mediaurl;
	}

	public Integer getOnsale() {
		if(onsale==null){
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

	public Long getOnsaledate1() {
		if(onsaledate!=null){
			return onsaledate.getTime();
		}
		return 0l;
	}

	public void setOnsaledate1(Long onsaledate1) {
		this.onsaledate1 = onsaledate1;
	}
	
}
