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
import javax.persistence.Transient;

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
	
	@Column(name = "sex")
	private Integer sex=0;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Classsort.class,fetch=FetchType.EAGER)
	@JoinColumn(name="industry_id")
	private Classsort industry;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Classsort.class,fetch=FetchType.EAGER)
	@JoinColumn(name="job_id")
	private Classsort job;
	
	@Transient
	private Long birthday;
	
	@Column(name = "birthday1")
	private Date birthday1=new Date();
	
	@Column(name = "constellation")
	private String constellation="";
	
	@Transient
	private Long createdate;
	
	@Column(name = "createdate1")
	private Date createdate1=new Date();
	
	@Column(name = "idnum",unique=true)
	private String idnum="";
	
	@Column(name = "tel",unique = true)
	private String tel;
	
	@Column(name = "email",unique = true)
	private String email;
	
	@Column(name = "jobyear")
	private Integer jobyear=0;
	
	@Column(name = "interests")
	private String interests="";
	
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

	public Long getBirthday() {
		if(birthday1!=null)
			return birthday1.getTime();
		return 0L;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday1() {
		return birthday1;
	}

	public void setBirthday1(Date birthday1) {
		this.birthday1 = birthday1;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public Long getCreatedate() {
		if(createdate1!=null)
			return createdate1.getTime();
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

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

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
	
}
