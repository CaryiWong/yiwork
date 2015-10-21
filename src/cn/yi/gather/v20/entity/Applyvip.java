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

import org.springframework.format.annotation.DateTimeFormat;

import com.tools.utils.RandomUtil;

/**
 * 会员申请表
 * @author Lee.J.Eric
 * @time 2014年6月10日下午2:57:24
 */
@Entity
@Table(name = "applyvip")
public class Applyvip implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3361736690143950978L;
	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20);//唯一码(对应user的id)
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "nickname")
	private String nickname; // 妮称
	
	@Column(name = "sex")
	private Integer sex=0;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinColumn(name="industry_id")
	private Labels industry;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinColumn(name="job_id")
	private Labels job;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "birthday")
	private Date birthday=new Date();
	
	@Column(name = "constellation")
	private String constellation="";
	
	
	@Column(name = "createdate")
	private Date createdate=new Date();
	
	@Column(name = "idnum")
	private String idnum="";
	
	@Column(name = "icnum_type")
	private String icnum_type="ID";// ID 身份证   HKM 港澳通行证   TW 台湾通行证   PP 护照
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "jobyear")
	private Integer jobyear=0;
	
//	@Column(name = "interests")
//	private String interests="";
	
	@Column(name="actnum")
	private Integer actnum=0;
	
	@Column(name = "jobdemand")
	private Integer jobdemand=0;
	
	@Column(name = "channel")
	private Integer channel=0;
	
	@Column(name = "understand")
	private String understand="";
	
	@Column(name = "result")
	private String result="";
	
	@Column(name = "proposal")
	private String proposal="";
	
	@Column(name = "acttype")
	private String acttype="";
	
	@Column(name = "actname")
	private String actname="";
	
	@Column(name ="duties")
	private String duties="";//职位(填写的)
	
	@Column(name = "introduction",length = 2000)
	private String introduction="";//个人介绍

	@Column(name ="province")
	private String province="";//所在省份
	
	@Column(name ="city")
	private String city="";  //所在城市
	
	
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

	
	
	public Applyvip() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Labels getIndustry() {
		return industry;
	}

	public void setIndustry(Labels industry) {
		this.industry = industry;
	}

	public Labels getJob() {
		return job;
	}

	public void setJob(Labels job) {
		this.job = job;
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


	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
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

	public Integer getJobyear() {
		return jobyear;
	}

	public void setJobyear(Integer jobyear) {
		this.jobyear = jobyear;
	}

//	public String getInterests() {
//		return interests;
//	}
//
//	public void setInterests(String interests) {
//		this.interests = interests;
//	}

	public Integer getActnum() {
		return actnum;
	}

	public void setActnum(Integer actnum) {
		this.actnum = actnum;
	}

	public Integer getJobdemand() {
		return jobdemand;
	}

	public void setJobdemand(Integer jobdemand) {
		this.jobdemand = jobdemand;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getUnderstand() {
		return understand;
	}

	public void setUnderstand(String understand) {
		this.understand = understand;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getProposal() {
		return proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
	}

	public String getActtype() {
		return acttype;
	}

	public void setActtype(String acttype) {
		this.acttype = acttype;
	}

	public String getActname() {
		return actname;
	}

	public void setActname(String actname) {
		this.actname = actname;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIcnum_type() {
		return icnum_type;
	}

	public void setIcnum_type(String icnum_type) {
		this.icnum_type = icnum_type;
	}
	
}
