package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 一起社区的商品存货量
 * 
 * @author Li Ming
 * @time 2014年8月27日下午2:13:09
 */
@Entity
@Table(name = "sku_inventory")
public class SkuInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 672501868921033L;

	@Id
	@Column(name="sku_id")
	private String id;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "is_unlimited")
	private Boolean isUnlimited;//true(1)无限，false(0)有限
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "modify_time")
	private Timestamp modifyTime;
	
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp timestamp) {
		this.createTime = timestamp;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp timestamp) {
		this.modifyTime = timestamp;
	}

	public SkuInventory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Boolean getIsUnlimited() {
		return isUnlimited;
	}

	public void setIsUnlimited(Boolean isUnlimited) {
		this.isUnlimited = isUnlimited;
	}
	
}
