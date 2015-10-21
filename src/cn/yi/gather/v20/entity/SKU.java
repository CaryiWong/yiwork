package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.junit.ClassRule;


/**
 * 商品SKU表
 * 
 * @author Li Ming
 * @time 2014年8月27日下午2:13:09
 */
@Entity
@Table(name = "sku")
public class SKU implements Serializable {

	public static enum SKUStatus {
		SELLING("已上架", 0),
		NOTSELLING("已下架", 1),
		DELETED("已删除", 2);
		
		private String name;
        private int code;
        
        private SKUStatus(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int code) {
            for (SKUStatus s : SKUStatus.values()) {
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
	
	private static final long serialVersionUID = 1040527741761083L;

	@Id
	@Column(name = "id")
	private String id;
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, targetEntity=ItemClass.class)
	@JoinColumn(name="item_class_id")
	private ItemClass itemClass;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "default_price",columnDefinition="double(10,2) default '0.00'")
	private Double defaultPrice;
	
	// 会员价
	@Column(name = "member_price",columnDefinition="double(10,2) default '0.00'")
	private Double memberPrice;
	
	@Column(name = "code")
	private String code;//name的英文代码
	
	@Column(name = "multPrice")
	private String multPrice="";//多个价格
	
	@Column(name="coffeetype")
	private String coffeetype="";//咖啡的类型   normal 正常   giveaway 赠品(带时效性)  1.19
	
	@Column(name = "introduction")
	private String introduction="";//简介
	
	@Column(name = "titleImg")
	private String titleImg="";//商品封面图   绝对地址
	
	@Column(name = "contextUrl")
	private String contextUrl="";//商品详情URL  绝对地址
	
	
	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public String getContextUrl() {
		return contextUrl;
	}

	public void setContextUrl(String contextUrl) {
		this.contextUrl = contextUrl;
	}

	public String getCoffeetype() {
		return coffeetype;
	}

	public void setCoffeetype(String coffeetype) {
		this.coffeetype = coffeetype;
	}

	public SKU() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ItemClass getItemClass() {
		return itemClass;
	}

	public void setItemClass(ItemClass itemClass) {
		this.itemClass = itemClass;
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

	@Transient
	public String getStatusName() {
		return SKUStatus.getName(status);
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Double price) {
		this.defaultPrice = price;
	}
	
	public Double getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Double price) {
		this.memberPrice = price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMultPrice() {
		return multPrice;
	}

	public void setMultPrice(String multPrice) {
		this.multPrice = multPrice;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
