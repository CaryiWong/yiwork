package cn.yi.gather.v22.yg100.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 阳光100 商品实例
 * @author YuanBao
 *
 */
@Entity
@Table(name = "yg_iteminstance")
public class YgItemInstance implements Serializable{

	private static final long serialVersionUID = -5498147372031942017L;

	@Id
	@Column(name="id")
	private String id="yg_item"+System.currentTimeMillis();
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, targetEntity=GoodsInfo.class)
	@JoinColumn(name="good_id")
	private GoodsInfo goods;//哪个商品
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, targetEntity=OrderInfo.class)
	@JoinColumn(name="order_id")
	private OrderInfo orderInfo;//所属订单
	
	@Column(name="user_id")
	private String userid;//所属用户
	
	@Column(name="create_time")
	private Date createtime=new Date();
	
	@Column(name="update_time")
	private Date updatetime=new Date();
	
	@Column(name="coupon_number", unique=true)
	private String couponnumber=RandomUtil.getRandomeStringFornum(10);//劵号  1.19
	
	@Column(name="itemstatus")
	private String itemstatus;//实例状态    未发货 nosend  未使用 unused 已使用 uesed 过期 time 撤回 black
	
	@Column(name="memo",length=10000)
	private String memo="";//备注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GoodsInfo getGoods() {
		return goods;
	}

	public void setGoods(GoodsInfo goods) {
		this.goods = goods;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getCouponnumber() {
		return couponnumber;
	}

	public void setCouponnumber(String couponnumber) {
		this.couponnumber = couponnumber;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public YgItemInstance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getItemstatus() {
		return itemstatus;
	}

	public void setItemstatus(String itemstatus) {
		this.itemstatus = itemstatus;
	}
	
	
}
