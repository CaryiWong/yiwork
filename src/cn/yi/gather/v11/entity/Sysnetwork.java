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
 * 广播内容
 * @author Lee.J.Eric
 * @time 2014年6月11日上午11:46:17
 */
@Entity
@Table(name = "sysnetwork")
public class Sysnetwork implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 619851008941459695L;
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "texts",length=2000)
	private String texts;
	
	@Transient
	private Long createdate;
	
	@Column(name = "createdate1")
	private Date createdate1=new Date();
	
	@Column(name = "isdel")
	private Integer isdel=0; 
	
	@Column(name="istype")
	private Integer istype=0;//0：系统，1需求，2活动，3会员

	public Sysnetwork() {
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

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public Long getCreatedate() {
		if(this.createdate1!=null)
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

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Integer getIstype() {
		return istype;
	}

	public void setIstype(Integer istype) {
		this.istype = istype;
	}
	
}
