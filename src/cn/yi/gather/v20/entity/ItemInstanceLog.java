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
 * 商品实例LOG
 * 
 * @author Li Ming
 * @time 2014年9月5日下午4:10:00
 */
@Entity
@Table(name = "item_instance_log")
public class ItemInstanceLog implements Serializable {

	public static enum OpType {
		CREATE("创建", 1),
		DELIVER("发货", 2),
		CANCEL("取消发货", 3),
		TRANSFER("转移属主", 4),
		USE("使用", 5),
		DESTROY("销毁", 6);		
		
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

	private static final long serialVersionUID = 765182094332463L;

	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20); // 主键ID 唯一 非空

	@Column(name="item_instance_id")
	private String itemInstanceId;
	
	@Column(name="op_type")
	private Integer opType;
	
	@Column(name="sku_id")
	private String skuId;

	@Column(name="order_id")
	private String orderId;
	
	@Column(name="user_id")
	private String userId;
	
	@Transient
	private String userNickname;
	
	@Column(name="date_time")
	private Timestamp dateTime;
	
	@Column(name="memo")
	private String memo;
	
	public ItemInstanceLog() {
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

	@Transient
	public String getUserNickname() {
		return userNickname;
	}

	@Transient
	public void setUserNickname(String nickname) {
		this.userNickname = nickname;
	}

	public String getItemInstanceId() {
		return itemInstanceId;
	}

	public void setItemInstanceId(String item_instance_id) {
		this.itemInstanceId = item_instance_id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String order_id) {
		this.orderId = order_id;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String sku_id) {
		this.skuId = sku_id;
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

}
