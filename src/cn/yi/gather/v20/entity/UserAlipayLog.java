package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 用户账户充值记录
 */
@Entity
@Table(name = "user_alipay_log")
public class UserAlipayLog implements Serializable {

	private static final long serialVersionUID = -17453460923840666L;

	public static enum OpType {
		INCOME("收入", 1),
		EXPENDITURE("支出", 2);
		
		private String name;
        private int code;
        
        private OpType(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (OpType s : OpType.values()) {
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

	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(10);//唯一码
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="op_type")
	private Integer opType;
	
	@Column(name="money",columnDefinition="double(10,2) default '0.00'")
	private Double money;
	
	@Column(name="alipay_trade_no")
	private String alipayTradeNo;
	
	@Column(name="memo")
	private String memo;
	
	@Column(name="date_time")
	private Timestamp dateTime;
	
	@Column(name="order_id")
	private String orderId;
	
	@Column(name="buyer_id")
	private String buyerId;
	
	@Column(name="buyer_email")
	private String buyerEmail;
	
	public UserAlipayLog() {
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

	public void setUserId(String user_id) {
		this.userId = user_id;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer op_type) {
		this.opType = op_type;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp date_time) {
		this.dateTime = date_time;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}

	public void setAlipayTradeNo(String alipay_trade_no) {
		this.alipayTradeNo = alipay_trade_no;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String order_id) {
		this.orderId = order_id;
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

}
