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
import javax.persistence.Version;

import com.tools.utils.RandomUtil;

/**
 * 相似用户(副)
 * @author YS
 * 2015-04-15
 */
@Entity
@Table(name = "similaruserfb")
public class SimilarUserFB implements Serializable{

	private static final long serialVersionUID = 4988447613749432616L;

	@Id
	@Column(name="id")
	private String id;//与主表一样
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_a_id")
	private User usera;
	
	@Column(name="similar")
	private Double similar=0.0;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_b_id")
	private User userb;
	
	@Column(name="updatetime")
	private Date updatetime;
	
	@Version
	@Column(name="version")
	private Integer version;
	
	
	public SimilarUserFB(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUsera() {
		return usera;
	}

	public void setUsera(User usera) {
		this.usera = usera;
	}

	public Double getSimilar() {
		return similar;
	}

	public void setSimilar(Double similar) {
		this.similar = similar;
	}

	public User getUserb() {
		return userb;
	}

	public void setUserb(User userb) {
		this.userb = userb;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
	
}
