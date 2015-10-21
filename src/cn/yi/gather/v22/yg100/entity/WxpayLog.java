package cn.yi.gather.v22.yg100.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微信支付 log 表
 * @author YuanBao
 *
 */
@Entity
@Table(name = "wxpay_log")
public class WxpayLog implements Serializable{
	private static final long serialVersionUID = -975898569997624104L;

	@Id
	@Column(name="id")
	private String id="wxlog"+System.currentTimeMillis();
	
	@Column(name="orderid")
	private String orderid;//对应订单ID
	
	@Column(name="wxorderid")
	private String wxorderid;//微信订单ID
	
	@Column(name="money")
	private String money;//单位分  实际交易金额
	
	@Column(name="createtime")
	private String createtime;
	
	@Column(name="openid")
	private String openid;
	
	@Column(name="tel")
	private String tel;
	
	@Column(name="wxnickname")
	private String wxnickname;
	
	@Column(name="ifgzhu")
	private String ifgzhu;// Y 已关注公众号  N 没关注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getWxorderid() {
		return wxorderid;
	}

	public void setWxorderid(String wxorderid) {
		this.wxorderid = wxorderid;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}


	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWxnickname() {
		return wxnickname;
	}

	public void setWxnickname(String wxnickname) {
		this.wxnickname = wxnickname;
	}

	public String getIfgzhu() {
		return ifgzhu;
	}

	public void setIfgzhu(String ifgzhu) {
		this.ifgzhu = ifgzhu;
	}

	public WxpayLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
