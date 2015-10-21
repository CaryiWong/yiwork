package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.yi.gather.v20.entity.UserAccount.MoneyType;

import com.tools.utils.RandomUtil;

/**
 * 支付单
 * 
 * @author Li Ming
 * @time 2014年9月4日下午2:09:00
 */
@Entity
@Table(name = "payment")
public class Payment implements Serializable {
	public static enum PaymentType {
		YIGATHER("一起社区内的虚拟货币", 1),
		ALIPAY("支付宝", 2),
		UNIONPAY("银联", 3);
		
		private String name;
        private int code;
        
        private PaymentType(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (PaymentType s : PaymentType.values()) {
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

	public static enum PaymentStatus {
		NEW("已收到，未处理", 0),
		RECORDED("已入账", 1),
		PREPARE_TO_REFUND("等待退款审核", 2),
		REJECT_TO_REFUND("退款审核不通过", 3),
		REFUNDED("已退款", 4);
		
		private String name;
        private int code;
        
        private PaymentStatus(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (PaymentStatus s : PaymentStatus.values()) {
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
	
	private static final long serialVersionUID = 33628687246349013L;

	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(5); // 主键ID 唯一 非空

	// 支付宝交易号
	@Column(name = "alipay_trade_no")
	private String alipayTradeNo;
	
	// 关联的OrderID
	@Column(name = "order_id")
	private String orderId;
	
	// 所属的userID
	@Column(name = "user_id")
	private String userId;
		
	// 交易状态
	@Column(name = "status")
	private Integer status;
	
	// 买家支付宝用户号
	@Column(name = "buyer_id")
	private String buyerId;
	
	// 买家支付宝账号
	@Column(name = "buyer_email")
	private String buyerEmail;
	
	// 支付方式
	@Column(name = "payment_type")
	private Integer paymentType;
		
	// 支付的金额种类
	@Column(name = "money_type")
	private Integer moneyType;
		
	// 支付的金额
	@Column(name = "money",columnDefinition="double(10,2) default '0.00'")
	private Double money;
	
	// 创建时间
	@Column(name = "create_time")
	private Timestamp createTime;
	
	// 最后修改时间
	@Column(name = "modify_time")
	private Timestamp modifyTime;
	
	// 网银流水
	@Column(name = "bank_seq_no")
	private String bankSeqNo;
	
	// 备注
	@Column(name = "memo")
	private String memo;
	
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}

	public void setAlipayTradeNo(String trade_no) {
		this.alipayTradeNo = trade_no;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String order_id) {
		this.orderId = order_id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String user_id) {
		this.userId = user_id;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	@Transient
	public String getStatusName() {
		return PaymentStatus.getName(this.status);
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getBuyerId() {
		return buyerId;
	}
	
	public void setBuyerId(String buyer_id) {
		this.buyerId = buyer_id;
	}
	
	public String getBuyerEmail() {
		return buyerEmail;
	}
	
	public void setBuyerEmail(String buyer_email) {
		this.buyerEmail = buyer_email;
	}
	
	public Double getMoney() {
		return money;
	}
	
	public void setMoney(Double money) {
		this.money = money;
	}
	
	public Integer getMoneyType() {
		return moneyType;
	}
	
	@Transient
	public String getMoneyTypeName() {
		return MoneyType.getName(moneyType);
	}
	
	public void setMoneyType(Integer money_type) {
		this.moneyType = money_type;
	}
	
	public Integer getPaymentType() {
		return paymentType;
	}
	
	@Transient
	public String getPaymentTypeName() {
		return PaymentType.getName(paymentType);
	}
	
	public void setPaymentType(Integer payment_type) {
		this.paymentType = payment_type;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Timestamp create_time) {
		this.createTime = create_time;
	}
	
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	
	public void setModifyTime(Timestamp modify_time) {
		this.modifyTime = modify_time;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getBankSeqNo() {
		return bankSeqNo;
	}
	
	public void setBankSeqNo(String bank_seq_no) {
		this.bankSeqNo = bank_seq_no;
	}
}