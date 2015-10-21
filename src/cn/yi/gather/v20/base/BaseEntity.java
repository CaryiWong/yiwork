package cn.yi.gather.v20.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import com.tools.utils.RandomUtil;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20);
	
	@Column(name = "create_time")
	private Date createTime = new Date();

	@Column(name = "update_time")
	private Date updateTime = new Date();

	@Version
	@Column(name = "version")
	private Long version = 1L;
	
	@PreUpdate
	public void preUpdate(){
		updateTime = new Date();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
