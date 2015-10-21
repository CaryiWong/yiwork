package cn.yi.gather.v11.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
@Entity
@Table(name = "activity")
public class Activity implements Serializable{

	private static final long serialVersionUID = 4136522081681L;
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@Column(name = "title")
	private String title="";//标题
	
	@Column (name="summary",length=20000)
	private String summary="";//简介
	
	@Column(name = "img")
	private String img="";//封面
	
	@Transient
	private Long createdate;//创建时间
	
	@Column(name = "createdate1")
	private Date createdate1=new Date();
	
	@Transient
	private Long publishdate;//发布时间
	
	@Column(name = "publishdate1")
	private Date publishdate1=new Date();
	
	@Transient
	private Long opendate;//开启时间
	
	@Column(name = "opendate1")
	private Date opendate1=new Date();
	
	@Transient
	private Long enddate;//结束时间
	
	@Column(name = "enddate1")
	private Date enddate1=new Date();
	
	@Column(name = "checktype")
	private Integer checktype=0;//审核状态 0 待审核 ，1 通过审核 ，2 审核失败
	
	@Column(name = "address")
	private String address="";//地点
	
	@Column(name = "cost")
	private Integer cost=0;//费用 0 免费 , 1 收费
	
	@ManyToMany(cascade = CascadeType.REFRESH,targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_label_act", joinColumns = { @JoinColumn(name = "activity_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private List<Labels> label;//标签 多对多labels
	
	@Column(name = "tel")
	private String tel="";//电话
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//发起者ID
	
	@Column(name = "status")
	private Integer status=0;//活动状态
	
	@Column(name = "maxnum")
	private Integer maxnum=0;//最高人数
	
	@Column(name = "signnum")
	private Integer signnum=0;//报名人数
	
	@Column(name = "joinnum")
	private Integer joinnum=0;//参加人数
	
	@Column(name = "commentnum")
	private Integer commentnum=0;//评论数
	
	@Column(name = "goodnum")
	private Integer goodnum=0;//点赞数
	
	@Column(name = "sharenum")
	private Integer sharenum=0;//分享数
	
	@Column(name = "imgsnum")
	private Integer imgsnum=0;//图片数量
	
	@Column(name = "imgurls",length=2000)
	private String imgurls;//图片列表  文件名,文件名
	
	@Transient
	private Set<String> imgs;//图片列表  文件名,文件名
	
	/*@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_user_join_act", joinColumns = { @JoinColumn(name = "activity_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private Set<User> joinpeoples;//参加人员列表 多对多user
*/	
	/*@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_user_sign_act", joinColumns = { @JoinColumn(name = "activity_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private Set<User> signpeoples;//报名人员列表 多对多user
*/	
	@Column(name = "titleimg")
	private String titleimg="";//活动详情图
	
	@Column(name = "isgood")
	private Integer isgood=0;//是否为推荐
	
	@Column(name = "isbanner")
	private Integer isbanner=0;//banner显示，0：不显示，1显示
	
	@Column(name = "huiguurl")
	private String huiguurl="";//回顾URL
	
	@Column(name = "price")
	private Integer price=0;//门票价格
	
	@Column(name = "acttype")
	private Integer acttype=0; //活动个性化类型  0 常规  1个性化
	
	@Column(name = "acttypetitle")
	private String  acttypetitle="支持";//个性化按钮文字
	
	@Column(name = "acttypeurl")
	private String  acttypeurl="";//个性化URL
	
	@Column(name = "onsale")
	private Integer onsale = 0;//活动上架(0)/下架(1)

/* *********************************0630 S*************************************  */	
/* ****** update activity set pricetype=0 , minprice=price , maxprice=price ********  */	
	 
	
	@Column(name="pricetype")
	private Integer pricetype=0;//0 单一价格   1 自定义多价格
	
	@Column(name="minprice")
	private Integer minprice;//最底价格
	
	@Column(name="maxprice")
	private Integer maxprice;//最高价格
	
	@Column(name="pricekey")
	private String pricekey="";//自定义价格名字
	
	@Column(name="pricevalue")
	private String pricevalue="";//自定义价格值
	
	@Transient
	private List<String> pricekey1;//自定义价格名字
	
	@Transient
	private List<String> pricevalue1;//自定义价格值

/* *********************************0630 E*************************************  */	
	
	
	public Activity() {
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Long getCreatedate() {
		if(createdate1!=null){
			return createdate1.getTime();
		}
		return 0l;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	public Date getCreatedate1() {
		return createdate1;
	}

	public void setCreatedate1(Date createdate1) {
		this.createdate1 = createdate1;
	}

	public Long getPublishdate() {
		if(publishdate1!=null){
			return publishdate1.getTime();
		}
		return 0l;
	}

	public void setPublishdate(Long publishdate) {
		this.publishdate = publishdate;
	}

	public Date getPublishdate1() {
		return publishdate1;
	}

	public void setPublishdate1(Date publishdate1) {
		this.publishdate1 = publishdate1;
	}

	public Long getOpendate() {
		if(opendate1!=null){
			return opendate1.getTime();
		}
		return 0l;
	}

	public void setOpendate(Long opendate) {
		this.opendate = opendate;
	}

	public Date getOpendate1() {
		return opendate1;
	}

	public void setOpendate1(Date opendate1) {
		this.opendate1 = opendate1;
	}

	public Long getEnddate() {
		if(enddate1!=null){
			return enddate1.getTime();
		}
		return 0l;
	}

	public void setEnddate(Long enddate) {
		this.enddate = enddate;
	}

	public Date getEnddate1() {
		return enddate1;
	}

	public void setEnddate1(Date enddate1) {
		this.enddate1 = enddate1;
	}

	public Integer getChecktype() {
		return checktype;
	}

	public void setChecktype(Integer checktype) {
		this.checktype = checktype;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}


	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMaxnum() {
		return maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	public Integer getSignnum() {
		return signnum;
	}

	public void setSignnum(Integer signnum) {
		this.signnum = signnum;
	}

	public Integer getJoinnum() {
		return joinnum;
	}

	public void setJoinnum(Integer joinnum) {
		this.joinnum = joinnum;
	}

	public Integer getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(Integer commentnum) {
		this.commentnum = commentnum;
	}

	public Integer getGoodnum() {
		return goodnum;
	}

	public void setGoodnum(Integer goodnum) {
		this.goodnum = goodnum;
	}

	public Integer getSharenum() {
		return sharenum;
	}

	public void setSharenum(Integer sharenum) {
		this.sharenum = sharenum;
	}

	public Integer getImgsnum() {
		return imgsnum;
	}

	public void setImgsnum(Integer imgsnum) {
		this.imgsnum = imgsnum;
	}

	public String getTitleimg() {
		return titleimg;
	}

	public void setTitleimg(String titleimg) {
		this.titleimg = titleimg;
	}

	public Integer getIsgood() {
		return isgood;
	}

	public void setIsgood(Integer isgood) {
		this.isgood = isgood;
	}

	public Integer getIsbanner() {
		return isbanner;
	}

	public void setIsbanner(Integer isbanner) {
		this.isbanner = isbanner;
	}

	public String getHuiguurl() {
		return huiguurl;
	}

	public void setHuiguurl(String huiguurl) {
		this.huiguurl = huiguurl;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getActtype() {
		return acttype;
	}

	public void setActtype(Integer acttype) {
		this.acttype = acttype;
	}

	public String getActtypetitle() {
		return acttypetitle;
	}

	public void setActtypetitle(String acttypetitle) {
		this.acttypetitle = acttypetitle;
	}

	public String getActtypeurl() {
		return acttypeurl;
	}

	public void setActtypeurl(String acttypeurl) {
		this.acttypeurl = acttypeurl;
	}

	public Integer getOnsale() {
		return onsale;
	}

	public void setOnsale(Integer onsale) {
		this.onsale = onsale;
	}

	public List<Labels> getLabel() {
		return label;
	}

	public void setLabel(List<Labels> label) {
		this.label = label;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getImgurls() {
		return imgurls;
	}

	public void setImgurls(String imgurls) {
		this.imgurls = imgurls;
	}

	public Set<String> getImgs() {
		Set<String> set = new HashSet<String>();
		if(imgurls!=null){
			String[] ar = imgurls.split(",");
			if(ar!=null&&ar.length>0){
				Collections.addAll(set, ar);
			}
		}
		return set;
		/*if(imgurls!=null&&imgurls.length()>0){
			String[] ar=imgurls.split(",");
			if(ar!=null&&ar.length>0){
				return Arrays.asList(ar);
			}
		}
		return new ArrayList<String>();*/
	}

	public void setImgs(Set<String> imgs) {
		this.imgs = imgs;
	}

	public Integer getPricetype() {
		return pricetype;
	}

	public void setPricetype(Integer pricetype) {
		this.pricetype = pricetype;
	}

	public Integer getMinprice() {
		return minprice;
	}

	public void setMinprice(Integer minprice) {
		this.minprice = minprice;
	}

	public Integer getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(Integer maxprice) {
		this.maxprice = maxprice;
	}

	public String getPricekey() {
		return pricekey;
	}

	public void setPricekey(String pricekey) {
		this.pricekey = pricekey;
	}

	public String getPricevalue() {
		return pricevalue;
	}

	public void setPricevalue(String pricevalue) {
		this.pricevalue = pricevalue;
	}

	public List<String> getPricekey1() {
		List<String> pricekey1=new ArrayList<String>();
		if(pricekey!=null&&pricekey.length()>1){
			String[] sarr=pricekey.split(",");
			pricekey1=Arrays.asList(sarr);
		}
		return pricekey1;
	}

	public void setPricekey1(List<String> pricekey1) {
		this.pricekey1 = pricekey1;
	}

	public List<String> getPricevalue1() {
		List<String> pricevalue1=new ArrayList<String>();
		if(pricevalue!=null&&pricevalue.length()>1){
			String[] sarr=pricevalue.split(",");
			pricevalue1=Arrays.asList(sarr);
		}
		return pricevalue1;
	}

	public void setPricevalue1(List<String> pricevalue1) {
		this.pricevalue1 = pricevalue1;
	}

	/*public Set<User> getJoinpeoples() {
		return joinpeoples;
	}

	public void setJoinpeoples(Set<User> joinpeoples) {
		this.joinpeoples = joinpeoples;
	}

	public Set<User> getSignpeoples() {
		return signpeoples;
	}

	public void setSignpeoples(Set<User> signpeoples) {
		this.signpeoples = signpeoples;
	}*/

	
}
