package cn.yi.gather.v11.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;

/**
 * 团队展示
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "indexteaminfo")
public class Indexteaminfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4023189275705018621L;
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@Column(name = "teamname")
	private String teamname="";
	
	@Column(name = "teamtitle",length=1000)
	private String teamtitle="";

	@Column(name = "teampeople")
	private Integer teampeople=0;
	
	@Column(name = "teamtype")
	private String teamtype="";
	
	@Column(name = "teamboss")
	private String teamboss="";
	
	@Transient
	private Long teamcreate;
	
	@Column(name = "teamcreate1")
	private Date teamcreate1=new Date();
	
	@Column(name = "teamgrowth",length = 2000)
	private String teamgrowth="";
	
	@Column(name = "teamimg")
	private String teamimg="";
	
	@Transient
	private Long joindatetimes;
	
	@Column(name = "joindatetimes1")
	private Date joindatetimes1=new Date();
	
	@ManyToMany(cascade = CascadeType.REFRESH,targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_indexteaminfo_label", joinColumns = { @JoinColumn(name = "indexteaminfo_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "label_id", referencedColumnName = "id") })
	private List<Labels> teamlables;
	
	@Column(name = "teamminim")
	private String teamminim="";

	public Indexteaminfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getTeamtitle() {
		return teamtitle;
	}

	public void setTeamtitle(String teamtitle) {
		this.teamtitle = teamtitle;
	}

	public Integer getTeampeople() {
		return teampeople;
	}

	public void setTeampeople(Integer teampeople) {
		this.teampeople = teampeople;
	}

	public String getTeamtype() {
		return teamtype;
	}

	public void setTeamtype(String teamtype) {
		this.teamtype = teamtype;
	}

	public String getTeamboss() {
		return teamboss;
	}

	public void setTeamboss(String teamboss) {
		this.teamboss = teamboss;
	}

	public Long getTeamcreate() {
		if(teamcreate1==null)
			return 0L;
		return teamcreate1.getTime();
	}

	public void setTeamcreate(Long teamcreate) {
		this.teamcreate = teamcreate;
	}

	public Date getTeamcreate1() {
		return teamcreate1;
	}

	public void setTeamcreate1(Date teamcreate1) {
		this.teamcreate1 = teamcreate1;
	}

	public String getTeamgrowth() {
		return teamgrowth;
	}

	public void setTeamgrowth(String teamgrowth) {
		this.teamgrowth = teamgrowth;
	}

	public String getTeamimg() {
		return teamimg;
	}

	public void setTeamimg(String teamimg) {
		this.teamimg = teamimg;
	}

	public Long getJoindatetimes() {
		if(joindatetimes1==null)
			return 0L;
		return joindatetimes1.getTime();
	}

	public void setJoindatetimes(Long joindatetimes) {
		this.joindatetimes = joindatetimes;
	}

	public Date getJoindatetimes1() {
		return joindatetimes1;
	}

	public void setJoindatetimes1(Date joindatetimes1) {
		this.joindatetimes1 = joindatetimes1;
	}

	public List<Labels> getTeamlables() {
		return teamlables;
	}

	public void setTeamlables(List<Labels> teamlables) {
		this.teamlables = teamlables;
	}

	public String getTeamminim() {
		return teamminim;
	}

	public void setTeamminim(String teamminim) {
		this.teamminim = teamminim;
	}
	
}
