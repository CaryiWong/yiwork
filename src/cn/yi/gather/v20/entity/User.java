package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Calendar;
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
	
	public static enum UserRoot{
		SYS("系统",0),
		MANAGER("管理员",1),
		VIP("会员",2),
		OVREDUE("过期会员",3),
		REGISTER("注册用户",4);
		
		private String name;
		private Integer code;
		
		private UserRoot(String name, Integer code){
			this.name = name;
            this.code = code;
		}
		
		private static String getName(Integer code){
			for (UserRoot ur : UserRoot.values()) {
				 if (ur.getCode() == code) {
	                    return ur.name;
	             }
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}
		
	}

	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis()
			+ RandomUtil.getRandomeStringForLength(20); // 主键ID 唯一 非空


	@Column(name = "password")
	private String password; // 密码

	@Column(name = "nickname", unique = true)
	private String nickname; // 妮称

	@Column(name = "minimg")
	private String minimg = ""; // 用户图像  _160X160  _320X320  _640X640  3种规格

	@Column(name = "maximg")
	private String maximg = ""; // 用户展示图 目前运用于网页

	@Column(name = "unum", unique = true)
	private String unum; // 会员编号

	@Column(name = "introduction",length=2000)
	private String introduction = ""; // 用户简介

	@Column(name = "sex")
	private Integer sex = 0; // 性别

	@Column(name = "realname")
	private String realname = ""; // 真实姓名

	@Column(name = "icnum")
	private String icnum = ""; // 身份证号码
	
	@Column(name = "icnum_type")
	private String icnum_type="ID";// ID 身份证   HKM 港澳通行证   TW 台湾通行证   PP 护照

	@Column(name = "email", unique = true)
	private String email; // 注册邮箱
	// private String email0; // 常用联系邮箱 默认为注册邮箱

	@Column(name = "telnum")
	private String telnum; // 注册电话
	// private String telnum0; // 常用联系电话 默认为注册电话

	@Column(name = "wechat")
	private String wechat = ""; // 微信号
	@Transient
	private Integer age; // 年龄

	@Column(name = "birthday")
	private Date birthday = new Date(); // 生日（出生年月日）

	@Column(name = "constellation")
	private String constellation = ""; // 星座

	@Column(name = "province")
	private String province = ""; // 省

	@Column(name = "city")
	private String city = ""; // 市

	@Column(name = "company")
	private String company = ""; // 所属公司

	@Column(name = "companyurl")
	private String companyurl = ""; // 公司网址

	@Column(name = "mypageurl")
	private String mypageurl = ""; // 个人主页
	
	@Column(name = "createdate")
	private Date createdate = new Date(); // 注册时间

	@Column(name = "myactnum")
	private Integer myactnum=0; // 发起活动数

	@Column(name = "joinactnum")
	private Integer joinactnum=0; // 参加活动数

	@Column(name = "checkactnum")
	private Integer checkactnum=0; // 待审核活动数

	@Column(name = "checkingactnum")
	private Integer checkingactnum=0; // 审核中活动数

	@Column(name = "rejectactnum")
	private Integer rejectactnum=0; // 驳回活动数

	@Column(name = "ifindex")
	private Integer ifindex=0; // 是否首页展示

	@Column(name = "ifspace")
	private Integer ifspace=0; // 是否空间展示

	@Column(name = "spacesettime")
	private Date spacesettime = new Date();// 空间展示设置时间

	@Column(name = "ifvipshow")
	private Integer ifvipshow = 0; // 是否会员展示

	@Column(name = "vipshowsettime")
	private Date vipshowsettime = new Date();// 会员展示设置时间

	@Column(name = "root")
	private Integer root = 4; // 用户状态 0 系统 1 管理员 2 会员 3过期用户 4注册用户

	@Column(name = "vipdate")
	private Date vipdate = new Date(); // 会员加入时间

	@Column(name = "vipenddate")
	private Date vipenddate = new Date(); // 会员到期时间

	@Column(name = "viplevel")
	private Integer viplevel=-1; // 会员等级

	@Column(name = "virtualname")
	private String virtualname = ""; // 社区虚拟身份

	@Column(name = "teamname")
	private String teamname = ""; // 团队名

	@Column(name = "teamintroduction",length=1500)
	private String teamintroduction = ""; // 团队介绍

	@Column(name = "teamurl",length=1500)
	private String teamurl = ""; // 团队URL

	@Column(name = "mediaurl",length=1500)
	private String mediaurl = ""; // 视频链接

	@Column(name = "isdel")
	private Integer isdel = 0; // 是否删除

	@Column(name = "twonum")
	private String twonum="";// 二维码

	@Column(name = "pakageaddress")
	private String pakageaddress = "";// 收件地址

	/** @Time 2014-8-28 Ys */

	@Column(name = "regaddressnum")
	private String regaddressnum=""; // 注册地点代码

	@Column(name = "regtgnum")
	private String regtgnum="";// 注册推广代码

	@Column(name = "userstart")
	private Integer userstart=0;// 用户状态 0 求勾搭 1 暂时不勾搭
	
	@Column(name="demandsnum")
	private Integer demandsnum=0;//发布需求数
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = Labels.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_user_job",joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private Set<Labels> job=new HashSet<Labels>(); // 职业

	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = Labels.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_user_favourite",joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private Set<Labels> favourite=new HashSet<Labels>(); // 兴趣爱好
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = Labels.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_user_focus",joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "labels_id", referencedColumnName = "id") })
	private Set<Labels> focus=new HashSet<Labels>();//关注领域
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = UserotherInfo.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_user_other_info",joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "userotherinfo_id", referencedColumnName = "id") })
	private Set<UserotherInfo> userothers=new HashSet<UserotherInfo>();//用户附加信息项
	
	@Column(name="signnum")
	private Integer signnum=0;//当月签到数
	
	/** @Time 2014-8-28 Ys */

	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="workspace_id")
	private WorkSpaceInfo spaceInfo;// 所属空间信息
	
	public WorkSpaceInfo getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(WorkSpaceInfo spaceInfo) {
		this.spaceInfo = spaceInfo;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Applyvip applyvip) {
		this.id = applyvip.getId();
		this.email = applyvip.getEmail();
		this.nickname = applyvip.getNickname();
		this.password = MD5Util.encrypt(applyvip.getTel());
		this.realname = applyvip.getName();
		this.telnum = applyvip.getTel();
		this.icnum = applyvip.getIdnum();
		this.icnum_type=applyvip.getIcnum_type();
		this.birthday = applyvip.getBirthday();
		this.sex = applyvip.getSex();
		this.introduction = applyvip.getIntroduction();
		Set<Labels> la = new HashSet<Labels>();
		la.add(applyvip.getJob());
		this.job = la;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return "";
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		if(nickname==null)
			return "";
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
		if(unum==null)
			return "";
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
		if(email==null)
			return "";
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelnum() {
		if(telnum==null)
			return "";
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
		age = 0;
		if (birthday != null) {
			Calendar cal = Calendar.getInstance();
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
			cal.setTime(birthday);
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
			age = yearNow - yearBirth;
			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					// monthNow==monthBirth 
					if (dayOfMonthNow < dayOfMonthBirth) {
						age--;
					}
				} else {
					// monthNow>monthBirth 
					age--;
				}
			}
		} 
		return age;
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

	public String getMypageurl() {
		return mypageurl;
	}

	public void setMypageurl(String mypageurl) {
		this.mypageurl = mypageurl;
	}


	public Set<Labels> getJob() {
		return job;
	}

	public void setJob(Set<Labels> job) {
		this.job = job;
	}

	public Set<Labels> getFavourite() {
		return favourite;
	}

	public void setFavourite(Set<Labels> favourite) {
		this.favourite = favourite;
	}


	public Set<Labels> getFocus() {
		return focus;
	}

	public void setFocus(Set<Labels> focus) {
		this.focus = focus;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
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

	public Date getVipdate() {
		return vipdate;
	}

	public void setVipdate(Date vipdate) {
		this.vipdate = vipdate;
	}

	public Date getVipenddate() {
		return vipenddate;
	}

	public void setVipenddate(Date vipenddate) {
		this.vipenddate = vipenddate;
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

	public String getRegaddressnum() {
		return regaddressnum;
	}

	public void setRegaddressnum(String regaddressnum) {
		this.regaddressnum = regaddressnum;
	}

	public String getRegtgnum() {
		return regtgnum;
	}

	public void setRegtgnum(String regtgnum) {
		this.regtgnum = regtgnum;
	}

	public Integer getUserstart() {
		return userstart;
	}

	public void setUserstart(Integer userstart) {
		this.userstart = userstart;
	}

	public Integer getDemandsnum() {
		return demandsnum;
	}

	public void setDemandsnum(Integer demandsnum) {
		this.demandsnum = demandsnum;
	}

	// 重写equals方法
	public boolean equals(Object other) {
		if (this == other) {
			return true;// 如果引用地址相同，即引用的是同一个对象，就返回true
		}

		if (!(other instanceof User)) { // 如果other不是User类的实例，返回false
			return false;
		}

		final User user = (User) other;
		if (!getId().equals(user.getId())) // id值不同，返回false
			return false;
		if (!getTelnum().equals(user.getTelnum()))//
			return false;
		if (!getNickname().equals(user.getNickname()))
			return false;
		return true;
	}

	// 重写hashCode()方法
	public int hashCode() {
		return getId().hashCode();
	}

	public Set<UserotherInfo> getUserothers() {
		return userothers;
	}

	public void setUserothers(Set<UserotherInfo> userothers) {
		this.userothers = userothers;
	}

	public String getIcnum_type() {
		return icnum_type;
	}

	public void setIcnum_type(String icnum_type) {
		this.icnum_type = icnum_type;
	}

	public Integer getSignnum() {
		return signnum;
	}

	public void setSignnum(Integer signnum) {
		this.signnum = signnum;
	}

	
	
}
