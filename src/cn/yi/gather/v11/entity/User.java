package cn.yi.gather.v11.entity;

import java.io.Serializable;
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

import com.tools.utils.MD5Util;
import com.tools.utils.RandomUtil;

/**
 * 用戶表
 * 
 * @author Lee.J.Eric
 * @time 2014年5月28日下午1:57:37
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7399672531868821033L;

	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20); // 主键ID 唯一 非空

	@Column(name = "accounts")
	private String accounts; // 帐号
	
	@Column(name = "password")
	private String password; // 密码
	
	@Column(name = "nickname", unique = true)
	private String nickname; // 妮称
	
	@Column(name = "minimg")
	private String minimg=""; // 用户图像 小
	
	@Column(name = "maximg")
	private String maximg=""; // 用户展示图 大
	
	@Column(name = "unum",unique = true)
	private String unum; // 会员编号
	
	@Column(name = "introduction")
	private String introduction=""; // 用户简介
	
	@Column(name = "sex")
	private Integer sex = 0; // 性别
	
	@Column(name = "realname")
	private String realname=""; // 真实姓名
	
	@Column(name = "icnum")
	private String icnum=""; // 身份证号码
	
	@Column(name = "email",unique = true)
	private String email; // 注册邮箱
	//private String email0; // 常用联系邮箱 默认为注册邮箱
	
	@Column(name = "telnum",unique = true)
	private String telnum; // 注册电话
	//private String telnum0; // 常用联系电话 默认为注册电话
	
	@Column(name = "wechat")
	private String wechat=""; // 微信号
	@Transient
	private Integer age; // 年龄
	
	@Column(name = "birthday")
	private Date birthday=new Date(); // 生日（出生年月日）
	
	@Column(name = "constellation")
	private String constellation=""; // 星座
	
	@Column(name = "province")
	private String province=""; // 省
	
	@Column(name = "city")
	private String city=""; // 市
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Classsort.class,fetch=FetchType.EAGER)
	@JoinColumn(name="industry_id")
	private Classsort industry; // 行业
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Classsort.class,fetch=FetchType.EAGER)
	@JoinColumn(name="job_id")
	private Classsort job; // 职业
	
	@Column(name = "company")
	private String company=""; // 所属公司
	
	@Column(name = "companyurl")
	private String companyurl=""; // 公司网址
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_label_labels", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private List<Labels> label; // 所属领域标签
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_skill_labels", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private List<Labels> skilledlabel; // 擅长领域标签
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_interest_labels", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private List<Labels> interestlabel; // 感兴趣的领域
	
	@Column(name = "mypageurl")
	private String mypageurl=""; // 个人主页
	
	@Column(name = "favourite")
	private String favourite = ""; // 兴趣爱好
	
	@Transient
	private Long createdate; // 注册时间
	
	@Column(name = "createdate1")
	private Date createdate1 = new Date(); // 注册时间
	
	@Column(name = "myactnum")
	private Integer myactnum; // 发起活动数
	
	@Column(name = "joinactnum")
	private Integer joinactnum; // 参加活动数
	
	@Column(name = "checkactnum")
	private Integer checkactnum; // 待审核活动数
	
	@Column(name = "checkingactnum")
	private Integer checkingactnum; // 审核中活动数
	
	@Column(name = "rejectactnum")
	private Integer rejectactnum; // 驳回活动数
	
	@Column(name = "ifindex")
	private Integer ifindex; // 是否首页展示
	
	@Column(name = "ifspace")
	private Integer ifspace; // 是否空间展示
	
	@Column(name = "spacesettime")
	private Date spacesettime = new Date();//空间展示设置时间
	
	@Column (name = "ifvipshow")
	private Integer ifvipshow; // 是否会员展示
	
	@Column(name = "vipshowsettime")
	private Date vipshowsettime = new Date();//会员展示设置时间
	
	@Column(name = "root")
	private Integer root = 3; // 用户状态 0 系统 1 管理员 2 会员 3普通用户
	
	@Transient
	private Long vipdate; // 会员加入时间
	
	@Column(name ="vipdate1")
	private Date vipdate1 = new Date(); // 会员加入时间
	
	@Transient
	private Long vipenddate; // 会员到期时间
	
	@Column(name = "vipenddate1")
	private Date vipenddate1 = new Date(); // 会员到期时间
	
	@Column(name = "viplevel")
	private Integer viplevel; // 会员等级
	
	@Column(name = "virtualname")
	private String virtualname=""; // 社区虚拟身份
	
	@Column(name = "teamname")
	private String teamname = ""; // 团队名
	
	@Column(name = "teamintroduction",length=1500)
	private String teamintroduction=""; // 团队介绍
	
	@Column(name = "teamurl",length=1500)
	private String teamurl=""; // 团队URL
	
	@Column(name = "mediaurl",length=1500)
	private String mediaurl=""; // 视频链接
	
	@Column(name = "isdel")
	private Integer isdel = 0; // 是否删除
	
	@Column(name = "twonum")
	private String twonum;//二维码
	
	@Column(name = "pakageaddress",length=1500)
	private String pakageaddress="";//收件地址

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(Applyvip applyvip){
		this.id = applyvip.getId();
		this.email = applyvip.getEmail();
		this.nickname = applyvip.getName();
		this.password = MD5Util.encrypt(applyvip.getTel());
		this.realname = applyvip.getName();
		this.telnum = applyvip.getTel();
		this.icnum = applyvip.getIdnum();
		this.sex = applyvip.getSex();
		this.industry = applyvip.getIndustry();
		this.job = applyvip.getJob();
		this.favourite = applyvip.getInterests();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMinimg() {
		return minimg;
	}

	public void setMinimg(String minimg) {
		this.minimg = minimg;
	}

	public String getMaximg() {
		return maximg;
	}

	public void setMaximg(String maximg) {
		this.maximg = maximg;
	}

	public String getUnum() {
		return unum;
	}

	public void setUnum(String unum) {
		this.unum = unum;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIcnum() {
		return icnum;
	}

	public void setIcnum(String icnum) {
		this.icnum = icnum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public Integer getAge() {
		if(birthday!=null){
			Calendar c = Calendar.getInstance();
			int now = c.get(Calendar.YEAR);
			c.setTime(birthday);
			int birth = c.get(Calendar.YEAR);

			return now-birth;

		}else {
			return 0;
		}
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyurl() {
		return companyurl;
	}

	public void setCompanyurl(String companyurl) {
		this.companyurl = companyurl;
	}

	public List<Labels> getLabel() {
		return label;
	}

	public void setLabel(List<Labels> label) {
		this.label = label;
	}

	public List<Labels> getSkilledlabel() {
		return skilledlabel;
	}

	public void setSkilledlabel(List<Labels> skilledlabel) {
		this.skilledlabel = skilledlabel;
	}

	public List<Labels> getInterestlabel() {
		return interestlabel;
	}

	public void setInterestlabel(List<Labels> interestlabel) {
		this.interestlabel = interestlabel;
	}

	public String getMypageurl() {
		return mypageurl;
	}

	public void setMypageurl(String mypageurl) {
		this.mypageurl = mypageurl;
	}

	public String getFavourite() {
		return favourite;
	}

	public void setFavourite(String favourite) {
		this.favourite = favourite;
	}

	public Long getCreatedate() {
		if(createdate1!=null)
			return createdate1.getTime();
		else
			return 0L;
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

	public Integer getMyactnum() {
		return myactnum;
	}

	public void setMyactnum(Integer myactnum) {
		this.myactnum = myactnum;
	}

	public Integer getJoinactnum() {
		return joinactnum;
	}

	public void setJoinactnum(Integer joinactnum) {
		this.joinactnum = joinactnum;
	}

	public Integer getCheckactnum() {
		return checkactnum;
	}

	public void setCheckactnum(Integer checkactnum) {
		this.checkactnum = checkactnum;
	}

	public Integer getCheckingactnum() {
		return checkingactnum;
	}

	public void setCheckingactnum(Integer checkingactnum) {
		this.checkingactnum = checkingactnum;
	}

	public Integer getRejectactnum() {
		return rejectactnum;
	}

	public void setRejectactnum(Integer rejectactnum) {
		this.rejectactnum = rejectactnum;
	}

	public Integer getIfindex() {
		return ifindex;
	}

	public void setIfindex(Integer ifindex) {
		this.ifindex = ifindex;
	}

	public Integer getIfspace() {
		return ifspace;
	}

	public void setIfspace(Integer ifspace) {
		this.ifspace = ifspace;
	}

	public Integer getIfvipshow() {
		return ifvipshow;
	}

	public void setIfvipshow(Integer ifvipshow) {
		this.ifvipshow = ifvipshow;
	}

	public Integer getRoot() {
		return root;
	}

	public void setRoot(Integer root) {
		this.root = root;
	}

	public Long getVipdate() {
		if(vipdate1!=null)
			return vipdate1.getTime();
		else  
			return 0L;
	}

	public void setVipdate(Long vipdate) {
		this.vipdate = vipdate;
	}

	public Date getVipdate1() {
		return vipdate1;
	}

	public void setVipdate1(Date vipdate1) {
		this.vipdate1 = vipdate1;
	}

	public Long getVipenddate() {
		if(vipenddate1!=null)
			return vipenddate1.getTime();
		else  
			return 0L;
	}

	public void setVipenddate(Long vipenddate) {
		this.vipenddate = vipenddate;
	}

	public Date getVipenddate1() {
		return vipenddate1;
	}

	public void setVipenddate1(Date vipenddate1) {
		this.vipenddate1 = vipenddate1;
	}

	public Integer getViplevel() {
		return viplevel;
	}

	public void setViplevel(Integer viplevel) {
		this.viplevel = viplevel;
	}

	public String getVirtualname() {
		return virtualname;
	}

	public void setVirtualname(String virtualname) {
		this.virtualname = virtualname;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getTeamintroduction() {
		return teamintroduction;
	}

	public void setTeamintroduction(String teamintroduction) {
		this.teamintroduction = teamintroduction;
	}

	public String getTeamurl() {
		return teamurl;
	}

	public void setTeamurl(String teamurl) {
		this.teamurl = teamurl;
	}

	public String getMediaurl() {
		return mediaurl;
	}

	public void setMediaurl(String mediaurl) {
		this.mediaurl = mediaurl;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public String getTwonum() {
		return twonum;
	}

	public void setTwonum(String twonum) {
		this.twonum = twonum;
	}

	public Date getSpacesettime() {
		return spacesettime;
	}

	public void setSpacesettime(Date spacesettime) {
		this.spacesettime = spacesettime;
	}

	public Date getVipshowsettime() {
		return vipshowsettime;
	}

	public void setVipshowsettime(Date vipshowsettime) {
		this.vipshowsettime = vipshowsettime;
	}

	public String getPakageaddress() {
		return pakageaddress;
	}

	public void setPakageaddress(String pakageaddress) {
		this.pakageaddress = pakageaddress;
	}
	
}
