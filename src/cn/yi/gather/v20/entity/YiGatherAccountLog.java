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
@Table(name = "yigather_account_log")
public class YiGatherAccountLog implements Serializable {

	private static final long serialVersionUID = 17453460923848888L;

	public static enum AccountOpType {
		INCOME("收入", 1),
		EXPENDITURE("支出", 2);
		
		private String name;
        private int code;
        
        private AccountOpType(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (AccountOpType s : AccountOpType.values()) {
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
	
	@Column(name="associated_user_id")
	private String associatedUserId;
	
	@Transient
	private String associatedUserNickname;
	
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
		
	@Column(name="subject")
	private String subject;
	
	@Column(name="date_time")
	private Timestamp dateTime;
	
	@Column(name="order_id")
	private String orderId;
	
	@Column(name="operator_id")
	private String operatorId;
	
	@Transient
	private String operatorName;
	
	public YiGatherAccountLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getAssociatedUserId() {
		return associatedUserId;
	}

	public void setAssociatedUserId(String associated_user_id) {
		this.associatedUserId = associated_user_id;
	}

	@Transient
	public String getAssociatedUserNickname() {
		return associatedUserNickname;
	}

	@Transient
	public void setAssociatedUserNickname(String associated_user_nickname) {
		this.associatedUserNickname = associated_user_nickname;
	}

	public Integer getOpType() {
		return opType;
	}
	
	@Transient
	public String getOpTypeName() {
		return AccountOpType.getName(this.opType);
	}

	public void setOpType(Integer op_type) {
		this.opType = op_type;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer payment_type) {
		this.paymentType = payment_type;
	}
	
	@Transient
	public String getPaymentTypeName() {
		return PaymentType.getName(this.paymentType);
	}
	
	public Integer getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(Integer money_type) {
		this.moneyType = money_type;
	}
	
	@Transient
	public String getMoneyTypeName() {
		return MoneyType.getName(this.moneyType);
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
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operator_id) {
		this.operatorId = operator_id;
	}
	
	@Transient
	public String getOperatorName() {
		return operatorName;
	}
	
	@Transient
	public void setOperatorName(String operator_name) {
		this.operatorName = operator_name;
	}
}
