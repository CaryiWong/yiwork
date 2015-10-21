package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.yi.gather.v20.entity.Payment.PaymentType;
import cn.yi.gather.v20.entity.UserAccount.MoneyType;

import com.tools.utils.RandomUtil;

/**
 * 用户账户充值记录
 */
@Entity
@Table(name = "user_account_log")
public class UserAccountLog implements Serializable {

	private static final long serialVersionUID = -17453460923840653L;

	public static enum UserAccountOpType {
		INCOME("收入", 1),
		EXPENDITURE("支出", 2);
		
		private String name;
        private int code;
        
        private UserAccountOpType(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (UserAccountOpType s : UserAccountOpType.values()) {
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
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(5);//唯一码
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="op_type")
	private Integer opType;
	
	@Column(name="payment_type")
	private Integer paymentType;
	
	@Column(name="money_type")
	private Integer moneyType;
	
	@Column(name="money",columnDefinition="double(10,2) default '0.00'")
	private Double money;
	
	@Column(name="memo")
	private String memo;
	
	@Column(name="date_time")
	private Timestamp dateTime;
	
	@Column(name="order_id")
	private String orderId;
	
	public UserAccountLog() {
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
	
	@Transient
	public String getOpTypeName() {
		return UserAccountOpType.getName(this.opType);
	}

	public void setOpType(Integer op_type) {
		this.opType = op_type;
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

	public Integer getMoneyType() {
		return moneyType;
	}

	@Transient
	public String getMoneyTypeName() {
		return MoneyType.getName(this.moneyType);
	}
	
	public void setMoneyType(Integer money_type) {
		this.moneyType = money_type;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String order_id) {
		this.orderId = order_id;
	}
	
}
