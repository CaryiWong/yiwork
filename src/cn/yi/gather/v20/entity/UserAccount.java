package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 用戶账户余额表
 * 
 * @author Li Ming
 * @time 2014年8月28日下午8:23:00
 */
@Entity
@Table(name = "user_account")
@IdClass(UserMoneyTypeUionPKID.class)
public class UserAccount implements Serializable {
	
	private static final long serialVersionUID = 517833447692340653L;

	public static enum MoneyType {
		RMB("人民币", 1),
		YIGATHER("一起社区虚拟币", 2);
		
		private String name;
        private int code;
        
        private MoneyType(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (MoneyType s : MoneyType.values()) {
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

	public static enum AccountStatus {
		NORMAL("正常", 0),
		FROZEN("已冻结", 1);
		
		private String name;
        private int code;
        
        private AccountStatus(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (AccountStatus s : AccountStatus.values()) {
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
	
	public static enum ActionType {
		ADD("增加", 1),
		MINUS("减少", 2);
		
		private String name;
        private int code;
        
        private ActionType(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (ActionType s : ActionType.values()) {
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
	@Column(name="userId")
	private String userId;
	
	@Id
	@Column(name="moneyType")
	private Integer moneyType;
	
	@Column(name="money",columnDefinition="double(10,2) default '0.00'")
	private Double money;
	
	@Column(name="create_time")
	private Timestamp createTime;
	
	@Column(name="modify_time")
	private Timestamp modifyTime;

	@Column(name="status")
	private Integer status;

	public UserAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String user_id) {
		this.userId = user_id;
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
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp time_stamp) {
		this.createTime = time_stamp;
	}
	
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp time_stamp) {
		this.modifyTime = time_stamp;
	}
	
	public Integer getStatus() {
		return status;
	}

	@Transient
	public String getStatusName() {
		return AccountStatus.getName(this.status);
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
