package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;


/**
 * 商品大类表
 * 
 * @author Li Ming
 * @time 2014年8月27日下午2:13:09
 */
@Entity
@Table(name = "item_class")
public class ItemClass implements Serializable {
	public static enum ItemClassStatus {
		NORMAL("正常", 0),
		DELETED("已删除", 1);
		
		private String name;
        private int code;
        
        private ItemClassStatus(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (ItemClassStatus s : ItemClassStatus.values()) {
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

	private static final long serialVersionUID = 812904253841721033L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "code")
	private String code;//name的英文代码
	
	public ItemClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Transient
	public String getStatusName() {
		 return ItemClassStatus.getName(this.status); 
	}
}
