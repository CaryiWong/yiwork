package cn.yi.gather.v11.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;

/**
 * 入驻申请
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "joinapplication")
public class Joinapplication implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2324572615318213958L;
	@Id
	@Column(name = "id")
	private String id= RandomUtil.getRandomeStringForLength(20)+System.currentTimeMillis();//唯一码
	
	@Column(name = "teamname")
	private String teamname="";
	
	@Column(name = "teamintroduce",length=1000)
	private String teamintroduce="";
	
	@Column(name = "teampeople")
	private Integer teampeople=0;
	
	@Column(name = "teamtype")
	private String teamtype="";
	
	@Transient
	private Long settleddate;
	
	@Column(name = "settleddate1")
	private Date settleddate1= new Date();
	
	@Transient
	private Long createdate=System.currentTimeMillis();
	
	@Column(name = "createdate1")
	private Date createdate1=new Date();

	public Joinapplication() {
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

	public String getTeamintroduce() {
		return teamintroduce;
	}

	public void setTeamintroduce(String teamintroduce) {
		this.teamintroduce = teamintroduce;
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

	public Long getSettleddate() {
		if(settleddate1==null)
			return 0L;
		return settleddate1.getTime();
	}

	public void setSettleddate(Long settleddate) {
		this.settleddate = settleddate;
	}

	public Date getSettleddate1() {
		return settleddate1;
	}

	public void setSettleddate1(Date settleddate1) {
		this.settleddate1 = settleddate1;
	}

	public Long getCreatedate() {
		if(createdate1==null)
			return 0L;
		return createdate1.getTime();
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
	
}
