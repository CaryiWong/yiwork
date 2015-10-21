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
 * 商品入库/发货记录表
 * 
 * @author Li Ming
 * @time 2014年8月28日下午6:20:00
 */
@Entity
@Table(name = "yigather_item_log")
public class YigatherItemInventoryLog implements Serializable {

	public static enum OpType {
		INCOME("入库", 1),
		DELIVERY("出库", 2);
		
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
	
	private static final long serialVersionUID = -38403522098165923L;

	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(5);//唯一码
	
	@Column(name="sku_id")
	private String skuId;
	
	@Column(name="amount")
	private Integer amount;
	
	@Column(name="order_id")
	private String orderId;
	
	@Column(name="operator_id")
	private String operatorId;
	
	@Transient
	private String operatorName;
	
	@Column(name = "date_time")
	private Timestamp dateTime;
	
	@Column(name = "memo")
	private String memo;
	
	@Column(name = "op_type")
	private Integer opType;
	
	public YigatherItemInventoryLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String sku_id) {
		this.skuId = sku_id;
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String order_id) {
		this.orderId = order_id;
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

	public Integer getOpType() {
		return opType;
	}

	@Transient
	public String getOpTypeName() {
		return OpType.getName(this.opType);
	}
	
	public void setOpType(Integer op_type) {
		this.opType = op_type;
	}
}
