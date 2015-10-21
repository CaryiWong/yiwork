package cn.yi.gather.v20.entity;

import cn.yi.gather.v20.application.AdminFilter;
import cn.yi.gather.v20.permission.entity.AdminUser;

import com.tools.utils.RandomUtil;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "activity")
public class Activity implements Serializable{

	private static final long serialVersionUID = 4136522081681L;
	
	public static final String entityName = "activity";
	
	//single 单一活动   set 活动集   sub子活动
	public static enum ActivityTypeValue{
		SINGLE("single"),
		SET("set"),
		SUB("sub");
		public String value;

		ActivityTypeValue(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@Column(name = "title")
	private String title="";//标题
	
	@Column (name="summary",length=20000)
	private String summary="";//简介
	
	@Column(name = "cover")
	private String cover="";//封面
	
	@Column(name = "createdate")
	private Date createdate=new Date();
	
	@Column(name = "publishdate")
	private Date publishdate=new Date();//发布日期
	
	@Column(name = "opendate")
	private Date opendate=new Date();//开启日期
	
	@Column(name = "enddate")
	private Date enddate=new Date();//结束日期
	
	@Column(name = "checktype")
	private Integer checktype=0;//审核状态 0 待审核 ，1 通过审核 ，2 审核失败
	
	@Column(name = "address")
	private String address="";//地点
	
	@Column(name = "cost")
	private Integer cost=0;//费用 0 免费 , 1 收费
	
	@ManyToMany(cascade = CascadeType.REFRESH,targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_label_act", joinColumns = { @JoinColumn(name = "activity_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private Set<Labels> label = new HashSet<Labels>();//标签 多对多labels
	
	@Column(name = "tel")
	private String tel="";//电话
	
	/*@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//发起者ID
*/	
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = AdminUser.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private AdminUser user;//发起者ID
	
	@Transient
	private Integer status;//状态，0:进行中，-１:结束，-２:回顾，1:未开启，2:new,3:即将开启
	
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
	
	@Column(name = "titleimg")
	private String titleimg="";//活动详情图
	
	@Column(name = "isbanner")
	private Integer isbanner=0;//banner显示，0：不显示，1显示
	
	@Column(name = "huiguurl",length=1000)
	private String huiguurl="";//回顾URL
	
	@Column(name = "price")
	private Integer price=0;//普通价格
	
	@Column(name = "vprice")
	private Integer vprice=0;//会员价格
	
	@Column(name = "acttype")
	private Integer acttype=0; //活动个性化类型  0 常规  1个性化
	
	@Column(name = "acttypetitle")
	private String  acttypetitle="支持";//个性化按钮文字
	
	@Column(name = "acttypeurl")
	private String  acttypeurl="";//个性化URL
	
	@Column(name = "onsale")
	private Integer onsale = 0;//活动上架(0)/下架(1)
	
	@Column(name = "isvip") 
	private Integer isvip = 0;//是否会员限制  默认0 所有人可见  1 只能会员

/* *********************************0630 S*************************************  */	
/* ****** update activity set pricetype=0 , minprice=price , maxprice=price ********  */	
	 
	
	@Column(name="pricetype")
	private Integer pricetype=0;//0 单一价格   1 自定义多价格
	
	@Column(name="minprice")
	private Integer minprice = 0;//最底价格
	
	@Column(name="maxprice")
	private Integer maxprice = 0;//最高价格
	
	@Column(name="pricekey")
	private String pricekey="";//自定义价格名字
	
	@Column(name="pricevalue")
	private String pricevalue="";//自定义价格值
	
	@Transient
	private List<String> pricekey1;//自定义价格名字
	
	@Transient
	private List<String> pricevalue1;//自定义价格值
	

/* *********************************0630 E*************************************  */	
	@Column(name="h5url")
	private String h5url="";//活动的H5 页面  报名/问卷 页地址 服务器相对地址

	@Column(name="imgtexturl",length=1000)
	private String imgtexturl="";//活动的图文 页面 //现在直接为微信编辑后绝对网址
	
	@Column(name="h5objid")
	private String h5objid="";//创建模板时候的临时ID
	
	@Column(name="imgtextobjid")
	private String imgtextobjid="";//创建模板时候的临时ID
	
	@Column(name = "skuid")
	private String skuid="";//商品编号
	
	@Column(name="activity_type")
	private String activityType="single";//single 单一活动   set 活动集   sub子活动
	
	@Column(name="pid")
	private String pid="";//子活动对应的活动集ID

	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="workspace_id")
	private WorkSpaceInfo spaceInfo;// 所属空间信息
	
	
	public WorkSpaceInfo getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(WorkSpaceInfo spaceInfo) {
		this.spaceInfo = spaceInfo;
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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
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
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		if(now.after(enddate)){
			if(huiguurl!=null&&!huiguurl.equals(""))
				return -2;//回顾
			return -1;
		}else if(now.after(opendate)&&now.before(enddate)) {
			return 0;//进行中
		}else {//未来
			c.add(Calendar.DATE, 1);
			Date now2 = c.getTime();//即将开启前一天
			c.setTime(createdate);
			c.add(Calendar.DATE, 3);
			Date now3 = c.getTime();//new:后三天
			if(now2.after(opendate)&&now.before(opendate)){
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

	public Set<Labels> getLabel() {
		return label;
	}

	public void setLabel(Set<Labels> label) {
		this.label = label;
	}


	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
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

//	public Set<Informations> getInformations() {
//		return informations;
//	}
//
//	public void setInformations(Set<Informations> informations) {
//		this.informations = informations;
//	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}

	public Date getOpendate() {
		return opendate;
	}

	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getVprice() {
		return vprice;
	}

	public void setVprice(Integer vprice) {
		this.vprice = vprice;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getIsvip() {
		return isvip;
	}

	public void setIsvip(Integer isvip) {
		this.isvip = isvip;
	}

//	public Set<Informations> getInformationsaft() {
//		return informationsaft;
//	}
//
//	public void setInformationsaft(Set<Informations> informationsaft) {
//		this.informationsaft = informationsaft;
//	}

}
