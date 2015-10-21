package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 应用版本
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "appversion")
public class Appversion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4034023550704191173L;
	@Id
	@Column(name ="id")
	private String id = System.currentTimeMillis() + RandomUtil.get32RandomUUID();
	
	@Column(name = "platform")
	private String platform = "";//平台类型
	
	@Column(name = "version")
	private String version = "1.0";//ios,android显示版本
	
	@Column(name = "ver")
	private Integer ver = 1;//android SDK版本
	
	@Column(name = "url")
	private String url = "";//应用下载地址
	
	@Column(name = "description",length = 2000)
	private String description = "";//更新描述
	
	@Column(name = "createdate")
	private Date createdate = new Date();
	
	@Column(name = "isforce")
	private String force = "n";//强制更新标记  y 强制更新  n 不强制更新

	public Appversion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getForce() {
		return force;
	}

	public void setForce(String force) {
		this.force = force;
	}
	
}
