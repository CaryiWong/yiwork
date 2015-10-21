package cn.yi.gather.v22.yg100.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 阳光100 特别订单 
 * @author YuanBao
 * 
 */
@Entity
@Table(name = "yg_order_info")
public class OrderInfo implements Serializable{

	private static final long serialVersionUID = -4212783285443812120L;

	@Id
	@Column(name="id")
	private String id="ygorder"+System.currentTimeMillis();
	
	@Column(name="user_id")
	private String userid="";// 微信用户/一起用户
	
	@Column(name="user_type")
	private String usertype="wx";// wx/yq 
	
	@Column(name="user_name")
	private String username="";// 微信妮称/ 一起用户妮称
	
	@Column(name="user_img")
	private String userimg="";// 图像地址 绝对地址
	
	@Column(name="create_time")
	private Date createtime=new Date();
	
	@Column(name="update_time")
	private Date updatetime=new Date();
	
	@Column(name="cancel_time")
	private Date canceltime;//预计过期时间 有效期   对应验证码有效期
	
	@Column(name="total_price")
	private Double totalprice=0.0;//预计到账金额 (订单金额*（1-0.02） 扣除第三方手续费)
	
	@Column(name="order_price")
	private Double orderprice=0.0;//订单价格
	
	/** 订单提交(待确定 submit)  订单确定(待付款 sure)  订单成功(待消费 success)  订单取消（订单作废 cancel）  */
	@Column(name="order_status")
	private String orderstatus="submit";//订单状态  
	
	@Column(name="order_vcode")
	private String ordervcode="";//验证码 (订单确定时会发验证码给用户)
	
	@Column(name="tel")
	private String tel="";
	
	@Column(name="openid")
	private String openid="";
	
	@Column(name="memo",length=10000)
	private String memo="";//备注
	
	@Column(name="timestamp")
	private String timestamp="";//微信支付 JSAPI 需要打包参数  时间戳
	
	@Column(name="nonce_str")
	private String nonce_str="";//微信支付 JSAPI 需要打包参数  随机码
	
	@Column(name="finalpackage")
	private String finalpackage="";//微信支付 JSAPI 需要打包参数  {}
	
	@Column(name="finalsign")
	private String finalsign="";//微信支付 JSAPI 需要打包参数  签名
	
	@Column(name = "buynum")
	private Integer buynum = 0;//购买数量（1人的数量）
	
	@Column(name = "buypeople")
	private Integer buypeople = 0;//购买人数
	
	@Column(name = "people_name")
	private String peoplename;//联系人称呼
	
	@Column(name = "check_in_time")
	private Date checkintime;//入住时间
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserimg() {
		return userimg;
	}

	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public Double getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(Double orderprice) {
		this.orderprice = orderprice;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrdervcode() {
		return ordervcode;
	}

	public void setOrdervcode(String ordervcode) {
		this.ordervcode = ordervcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getFinalpackage() {
		return finalpackage;
	}

	public void setFinalpackage(String finalpackage) {
		this.finalpackage = finalpackage;
	}

	public String getFinalsign() {
		return finalsign;
	}

	public void setFinalsign(String finalsign) {
		this.finalsign = finalsign;
	}

	public Integer getBuynum() {
		return buynum;
	}

	public void setBuynum(Integer buynum) {
		this.buynum = buynum;
	}

	public Integer getBuypeople() {
		return buypeople;
	}

	public void setBuypeople(Integer buypeople) {
		this.buypeople = buypeople;
	}

	public String getPeoplename() {
		return peoplename;
	}

	public void setPeoplename(String peoplename) {
		this.peoplename = peoplename;
	}

	public Date getCheckintime() {
		return checkintime;
	}

	public void setCheckintime(Date checkintime) {
		this.checkintime = checkintime;
	}

	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	
}
