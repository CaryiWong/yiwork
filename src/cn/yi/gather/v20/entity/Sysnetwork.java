package cn.yi.gather.v20.entity;

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
	
	@Column(name = "createdate")
	private Date createdate=new Date();
	
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

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
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
