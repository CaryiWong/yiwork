package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;

/**
 * 商品实例表
 * 
 * @author Li Ming
 * @time 2014年8月27日下午2:13:09
 */
@Entity
@Table(name = "item_instance")
public class ItemInstance implements Serializable {

	public static enum ItemInstanceStatus {
		UNDELIVERED("未发货", 0),
		CANCELED("取消发货，自动销毁", 1),
		UNUSED("未使用", 2),
		USED("已使用", 3),
		EXPIRED("已过期", 4),
		DESTROYED("被销毁", 5);
		
		private String name;
        private int code;
        
        private ItemInstanceStatus(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (ItemInstanceStatus s : ItemInstanceStatus.values()) {
                if (s.getCode() == code) {
                    return s.name;
                }
            }
            return null;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
	};
	
	public static enum ItemInstanceTypeName {
		COFFEE("咖啡券", "coffee"),
		SHARECOFFEE("分享咖啡券", "sharecoffee"),
		TIMECOFFEE("限时咖啡券", "timecoffee"),
		INN("订房券", "inn"),
		ACTIVITY("活动券", "activity");
		
		private String name;
        private String code;
        
        private ItemInstanceTypeName(String name, String code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(String code) {
            for (ItemInstanceTypeName s : ItemInstanceTypeName.values()) {
                if (s.getCode().equals(code)) {
                    return s.name;
                }
            }
            return null;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	};

	
	private static final long serialVersionUID = 3160122832150193L;

	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, targetEntity=SKU.class)
	@JoinColumn(name="sku_id")
	private SKU sku;
	
	@Column(name="order_id")
	private String orderId;
	
	@Column(name="user_id")
	private String userId;//购买者
	
	@Transient
	private User user;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "sale_price",columnDefinition="double(10,2) default '0.00'")
	private Double salePrice;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "modify_time")
	private Timestamp modifyTime;
	
	@Column(name="coupon_number", unique=true)
	private String couponnumber=RandomUtil.getRandomeStringFornum(10);//劵号  1.19
	
	@Column(name="receiveuser_id")
	private String receiveuserId=userId;//接收者/拥有者 (当对应商品为赠品咖啡劵时 这个接收者为第二人，其他全部默认为购买者)
	
	@Column(name = "end_time")
	private Date endTime;//过期时间
	
	@Column(name = "effective")
	private Integer effective = 0;//时效性，0：无时效，1有时效
	
	@Column(name = "buynum")
	private Integer buynum = 0;//购买数量（1人的数量）
	
	@Column(name = "buypeople")
	private Integer buypeople = 0;//购买人数
	
	@Column(name = "openid")
	private String openid = "";//微信OPENid
	
	@Column(name = "tel")
	private String tel = "";//电话
	
	@Column(name="showname")
	private String showname="";//2.2版  显示名字 （ 咖啡卷   分享咖啡卷   限时咖啡卷   订房卷  xxx活动卷）
	
	@Column(name="showplatform")
	private String showplatform="";//2.2版  显示使用平台 （一起咖啡    阳光100）
	
	@Column(name="itemtype")
	private String itemtype="";//coffee   sharecoffee    timecoffee  inn   activity   对应显示名字来
	
	/*update item_instance set showname='咖啡券'  where  sku_id='1-1419494727163';
	update  item_instance  set showplatform='一起咖啡'  where sku_id='1-1419494727163';
	update  item_instance  set itemtype='coffee'  where sku_id='1-1419494727163';

	update item_instance set showname='限时咖啡券'  where sku_id='1-1419494728888' and receiveuser_id!=user_id;
	update  item_instance  set showplatform='一起咖啡'  where sku_id='1-1419494728888' and receiveuser_id!=user_id;
	update  item_instance  set itemtype='timecoffee'  where sku_id='1-1419494728888' and receiveuser_id!=user_id;

	update item_instance set showname='分享咖啡券'  where sku_id='1-1419494728888' and receiveuser_id=user_id;
	update  item_instance  set showplatform='一起咖啡'  where sku_id='1-1419494728888' and receiveuser_id=user_id;
	update  item_instance  set itemtype='sharecoffee'  where sku_id='1-1419494728888' and receiveuser_id=user_id;*/

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getShowplatform() {
		return showplatform;
	}

	public void setShowplatform(String showplatform) {
		this.showplatform = showplatform;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getReceiveuserId() {
		return receiveuserId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setReceiveuserId(String receiveuserId) {
		this.receiveuserId = receiveuserId;
	}

	public static boolean isValidStatus(int status) {
		if (status == ItemInstanceStatus.UNDELIVERED.getCode() ||
				status == ItemInstanceStatus.UNUSED.getCode())
			return true;
		return false;			
	}
	
	public ItemInstance() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getCouponnumber() {
		return couponnumber;
	}

	public void setCouponnumber(String couponnumber) {
		this.couponnumber = couponnumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SKU getSku() {
		return sku;
	}

	public void setSku(SKU sku) {
		this.sku = sku;
	}
	
	public Integer getStatus() {
		return status;
	}

	@Transient
	public String getStatusName() {
		return ItemInstanceStatus.getName(status);
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getEffective() {
		return effective;
	}

	public void setEffective(Integer effective) {
		this.effective = effective;
	}

	public Integer getBuynum() {
		if(this.buynum==null){
			this.buynum=0;
		}
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
	
}
