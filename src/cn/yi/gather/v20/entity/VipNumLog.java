package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import cn.yi.gather.v20.base.BaseEntity;

@Entity
@Table(name = "vip_num_log")
public class VipNumLog extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8908347261869188315L;
	
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "order_no",unique=true)
	private Integer orderNO;
	
	@Column(name = "vip_no",unique = true,nullable=false)
	private String vipNO;
	
	@Column(name = "status")
	private Boolean status;//使用状态，true：已使用，false:未使用

	public VipNumLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(Integer orderNO) {
		this.orderNO = orderNO;
	}

	public String getVipNO() {
		return vipNO;
	}

	public void setVipNO(String vipNO) {
		this.vipNO = vipNO;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
