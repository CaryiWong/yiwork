package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

/**
 * 课程
 * @author Lee.J.Eric
 * @time 2014年10月11日 下午3:20:59
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7408766198749558414L;
	
	public static final String entityName = "course";
	
	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20);//主键
	
	@Column(name = "title")
	private String title="";//标题
	
	@Column(name = "opendate")
	private Date opendate = new Date();//开启时间
	
	@Column(name = "enddate")
	private Date enddate = new Date();//结束时间
	
	@Column(name = "createdate")
	private Date createdate = new Date();//创建时间

	@Column(name = "address")
	private String address="";//地点
	
	@Column(name = "maxnum")
	private Integer maxnum = 0;//最大人数
	
	@Column(name = "summary",length=20000)
	private String summary = "";//课程简介
	
	@Column(name = "teacher")
	private String teacher = "";//讲师
	
	@Column(name = "tel")
	private String tel="";//电话
	
	@Column(name = "cover")
	private String cover = "";//封面
	
	@Column(name = "titleimg")
	private String titleimg="";//课程详情图
	
	@Column(name = "isdel")
	private Integer isdel = 0;//删除标记，0正常，1删除
	
	@Column(name = "cost")
	private Integer cost=0;//费用 0 免费 , 1 收费
	
	@Column(name = "price")
	private Integer price=0;//普通价格
	
	@Column(name = "vprice")
	private Integer vprice=0;//会员价格
	
	@Column(name = "onsale")
	private Integer onsale = 0;//活动上架(0)/下架(1)
	
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
	
	@Transient
	private Integer status;//状态，0:进行中，-2 回顾 -1:结束，1:未开启，2:new,3:即将开启
	
	@Column(name="h5url")
	private String h5url="";//活动的H5 页面

	@Column(name="imgtexturl")
	private String imgtexturl="";//活动的图文 页面
	
	@Column(name="h5objid")
	private String h5objid="";//创建模板时候的临时ID
	
	@Column(name="imgtextobjid")
	private String imgtextobjid="";//创建模板时候的临时ID
	
	@Column(name = "skuid")
	private String skuid="";//商品编号
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//发起者ID
	
	@Column(name = "checktype")
	private Integer checktype=0;//审核状态 0 待审核 ，1 通过审核 ，2 审核失败
	
	@ManyToMany(cascade = CascadeType.REFRESH,targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_course_label", joinColumns = { @JoinColumn(name = "course_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private List<Labels> label = new ArrayList<Labels>();//标签 多对多labels
	
	@Column(name="huiguurl")
	private String huiguurl="";//回顾URL
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="workspace_id")
	private WorkSpaceInfo spaceInfo;// 所属空间信息
	
	
	public WorkSpaceInfo getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(WorkSpaceInfo spaceInfo) {
		this.spaceInfo = spaceInfo;
	}

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHuiguurl() {
		return huiguurl;
	}

	public void setHuiguurl(String huiguurl) {
		this.huiguurl = huiguurl;
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

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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

	public Integer getMaxnum() {
		return maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	public String getSummary() {
		try {
			summary = URLDecoder.decode(summary, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return summary;
	}

	public void setSummary(String summary) {
		try {
			this.summary = URLEncoder.encode(summary, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getVprice() {
		return vprice;
	}

	public void setVprice(Integer vprice) {
		this.vprice = vprice;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getStatus() {
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		if(now.after(enddate)){
			return -1;//结束
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTitleimg() {
		return titleimg;
	}

	public void setTitleimg(String titleimg) {
		this.titleimg = titleimg;
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

	public List<Labels> getLabel() {
		return label;
	}

	public void setLabel(List<Labels> label) {
		this.label = label;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getOnsale() {
		return onsale;
	}

	public void setOnsale(Integer onsale) {
		this.onsale = onsale;
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

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getChecktype() {
		return checktype;
	}

	public void setChecktype(Integer checktype) {
		this.checktype = checktype;
	}
	
}
