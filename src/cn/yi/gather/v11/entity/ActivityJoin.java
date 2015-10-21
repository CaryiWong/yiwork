package cn.yi.gather.v11.entity;

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

import com.tools.utils.RandomUtil;

/**
 * 活动报名记录表
 * @author Lee.J.Eric
 * @time 2014年6月6日下午2:42:48
 */
@Entity
@Table(name = "activityjoin")
public class ActivityJoin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3871265286547394310L;
	
	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.get32RandomUUID();
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//报名用户
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Activity.class,fetch=FetchType.EAGER)
	@JoinColumn(name="activity_id")
	private Activity activity;//报名活动
	
	@Column(name = "name")
	private String name = "";//姓名
	
	@Column(name = "sex")
	private Integer sex = 0;//性别，0男，1女
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Classsort.class,fetch=FetchType.EAGER)
	@JoinColumn(name="industry_id")
	private Classsort industry;//行业
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Classsort.class,fetch=FetchType.EAGER)
	@JoinColumn(name="job_id")
	private Classsort job;//职业
	
	@Column(name = "tel")
	private String tel = "";//电话
	
	@Column(name = "email")
	private String email = "";//邮箱
	
	@Column(name = "vipflag")
	private Integer vipflag = 0;//0非会员，1会员
	
	@Column(name = "reason")
	private String reason = "";//参加活动的理由
	
	@Column(name = "attendflag")
	private Integer attendflag = 0;//出席标志
	
	@Column(name = "owner")
	private Integer owner = 0;//活动发起人，0否，1是
	
	@Column(name = "createtime")
	private Date createtime = new Date();//报名时间
	
/* *********************************0630 S*************************************  */	
/* ********* update activityjoin set pricetype=0  *************  */		
	
	@Column(name="pricetype")
	private Integer pricetype=0;//0 单一价格   1 自定义多价格
	
	@Column(name="pricekey")
	private String pricekey="";//价格名字
	
	@Column(name="pricevalue")
	private Integer pricevalue=0;//价格值
/* *********************************0630 E*************************************  */	
	
	public ActivityJoin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Classsort getIndustry() {
		return industry;
	}

	public void setIndustry(Classsort industry) {
		this.industry = industry;
	}

	public Classsort getJob() {
		return job;
	}

	public void setJob(Classsort job) {
		this.job = job;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getVipflag() {
		return vipflag;
	}

	public void setVipflag(Integer vipflag) {
		this.vipflag = vipflag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getAttendflag() {
		return attendflag;
	}

	public void setAttendflag(Integer attendflag) {
		this.attendflag = attendflag;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getPricetype() {
		return pricetype;
	}

	public void setPricetype(Integer pricetype) {
		this.pricetype = pricetype;
	}

	public String getPricekey() {
		return pricekey;
	}

	public void setPricekey(String pricekey) {
		this.pricekey = pricekey;
	}

	public Integer getPricevalue() {
		return pricevalue;
	}

	public void setPricevalue(Integer pricevalue) {
		this.pricevalue = pricevalue;
	}

	
	
}
