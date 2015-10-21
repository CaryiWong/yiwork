package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;

/**
 * 订单表
 * 
 * @author Li Ming
 * @time 2014年8月28日下午5:52:00
 */
@Entity
@Table(name = "order_table")
public class Order implements Serializable {
	public static enum OrderType {
		DEPOSIT("虚拟币账号充值", 1),
		BUYING("购物", 2),
		MEMBERSHIP_RENEWAL("会员续费", 3);
		
		private String name;
        private int code;
        
        private OrderType(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (OrderType s : OrderType.values()) {
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

	public static enum OrderStatus {
		UNPAID("等待付款", 0),
		CONFIRMING("付款确认中", 1),
		CANCELED("已取消", 2),
		EXPIRED("交易超时", 3),
		END("已完成", 4);
		
		private String name;
        private int code;
        
        private OrderStatus(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (OrderStatus s : OrderStatus.values()) {
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
	
	private static final long serialVersionUID = -20053348234783L;

	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(5);//唯一码
	
	@Column(name="user_id")
	private String userId;
	
	@Transient
	private String userNickname;
	
	@Column(name="order_type")
	private Integer orderType;
		
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "modify_time")
	private Timestamp modifyTime;
	
	@Column(name = "total_price",columnDefinition="double(10,2) default '0.00'")
	private Double totalPrice=0.0;
	
	@Column(name = "paid_money",columnDefinition="double(10,2) default '0.00'")
	private Double paidMoney=0.0;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "deposit_money_type")
	private Integer depositMoneyType;
	
	@Column(name = "deposit_money",columnDefinition="double(10,2) default '0.00'")
	private Double depositMoney=0.0;
	
	@Column(name="jsontext",length=10000)
	private String jsontext="";  //订单创建前 需要带到订单处理结果里面的附加参数
	
	@Column(name = "memo")
	private String memo="";//备注
	
	@Column(name = "business_type")
	private String businessType="";//业务类型，activity,course,coffee,etc.
	
	@Column(name = "business_id")
	private String businessId="";//业务实体id
	
	@Column(name = "openid")
	private String openid="";//微信OPENID
	
	@Column(name = "tel")
	private String tel="";//微信下单  验证之后的有效手机号码
	
	@Column(name="businessTitle")
	private String businessTitle="";//业务名称
	
	@Transient
	private String couponnumber="";
	@Transient
	private String inntype="";
	
	@Column(name="timestamp")
	private String timestamp="";
	
	@Column(name="nonce_str")
	private String nonce_str="";
	
	@Column(name="finalpackage")
	private String finalpackage="";
	
	@Column(name="finalsign")
	private String finalsign="";
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Transient
	public String getUserNickname() {
		return userNickname;
	}
	
	@Transient
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	
	public Integer getOrderType() {
		return orderType;
	}

	@Transient
	public String getOrderTypeName() {
		return OrderType.getName(orderType);
	}
	
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
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
	
	public Integer getStatus() {
		return status;
	}

	@Transient
	public String getStatusName() {
		return OrderStatus.getName(status);
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double price) {
		this.totalPrice = price;
	}
	
	public Double getPaidMoney() {
		return paidMoney;
	}
	
	public void setPaidMoney(Double money) {
		this.paidMoney = money;
	}

	public String getJsontext() {
		return jsontext;
	}

	public void setJsontext(String jsontext) {
		this.jsontext = jsontext;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessTitle() {
		return businessTitle;
	}

	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle;
	}

	public Integer getDepositMoneyType() {
		return depositMoneyType;
	}

	public void setDepositMoneyType(Integer depositMoneyType) {
		this.depositMoneyType = depositMoneyType;
	}

	public Double getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(Double depositMoney) {
		this.depositMoney = depositMoney;
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

	public String getCouponnumber() {
		return couponnumber;
	}

	public void setCouponnumber(String couponnumber) {
		this.couponnumber = couponnumber;
	}

	public String getInntype() {
		if(inntype==null||inntype==""){
			inntype=OrderStatus.getName(status);
		}
		return inntype;
	}

	public void setInntype(String inntype) {
		this.inntype = inntype;
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
	
}
