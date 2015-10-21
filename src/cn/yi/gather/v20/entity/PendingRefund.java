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
 * 待审核的退款表
 * 
 * @author Li Ming
 * @time 2014年9月2日下午1:10:00
 */
@Entity
@Table(name = "pending_refund")
public class PendingRefund implements Serializable {

	public static enum RefundStatus {
		PENDING("等待审核", 0),
		REJECTED("审核不通过，不予退款", 1),
		FINISHED("已退款", 2);
		
		private String name;
        private int code;
        
        private RefundStatus(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (RefundStatus s : RefundStatus.values()) {
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

	private static final long serialVersionUID = -2256345673445732L;

	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(5);//唯一码;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "modify_time")
	private Timestamp modifyTime;
	
	@Column(name = "buyer_id")
	private String buyerId;
	
	@Column(name = "buyer_email")
	private String buyerEmail;
	
	@Column(name = "alipay_trade_no")
	private String alipayTradeNo;
	
	@Column(name = "order_id")
	private String orderId;
		
	@Column(name = "user_id")
	private String userId;
	
	@Transient
	private String userNickname;
	
	@Column(name = "bank_seq_no")
	private String bankSeqNo;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "money",columnDefinition="double(10,2) default '0.00'")
	private Double money;
	
	@Column(name = "memo")
	private String memo;
	
	public PendingRefund() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp date_time) {
		this.createTime = date_time;
	}
	
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp date_time) {
		this.modifyTime = date_time;
	}

	public Integer getStatus() {
		return status;
	}

	public String getStatusName() {
		return RefundStatus.getName(status);
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
	
	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}
	
	public void setAlipayTradeNo(String alipay_trade_no) {
		this.alipayTradeNo = alipay_trade_no;
	}
	
	public String getBankSeqNo() {
		return bankSeqNo;
	}
	
	public void setBankSeqNo(String bank_seq_no) {
		this.bankSeqNo = bank_seq_no;
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
	
	@Transient
	public String getUserNickname() {
		return userNickname;
	}
	
	@Transient
	public void setUserNickname(String nickname) {
		this.userNickname = nickname;
	}
	public Double getMoney() {
		return money;
	}
	
	public void setMoney(Double money) {
		this.money = money;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
