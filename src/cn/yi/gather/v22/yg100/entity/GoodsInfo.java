package cn.yi.gather.v22.yg100.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.yi.gather.v22.yg100.dao.GoodsInfoRepository;

/**
 * 阳光100 特别商品
 * @author YuanBao
 *
 */
@Entity
@Table(name = "goodsinfo")
public class GoodsInfo  implements Serializable{

	private static final long serialVersionUID = 4442649891599272150L;

	@Id
	@Column(name="id")
	private String id;//ID
	
	@Column(name="name")
	private String name;// 标题
	
	@Column(name="title")
	private String title;// 广告语 标题的说明
	
	@Column(name="price")
	private Double price;//商品单价(1人/1天)
	
	@Column(name="titleimg",length=10000)
	private String titleimg;//商品主图 (兼容多张)
	
	@Column(name="titleimg_link")
	private String titleimglink="";//商品主图 对应链接
	
	@Transient
	private Map<String, String> imgs;//锚：图片
	
	@Column(name="contexturl")
	private String contexturl;//详情介绍URL
	
	@Column(name="createtime")
	private Date createtime;//创建时间
	
	@Column(name="service_tel")
	private String servicetel;//客服联系电话
	
	public GoodsInfo() {
		
	}

	public String getServicetel() {
		return servicetel;
	}

	public void setServicetel(String servicetel) {
		this.servicetel = servicetel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitleimg() {
		return titleimg;
	}

	public void setTitleimg(String titleimg) {
		this.titleimg = titleimg;
	}

	public String getContexturl() {
		return contexturl;
	}

	public void setContexturl(String contexturl) {
		this.contexturl = contexturl;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleimglink() {
		return titleimglink;
	}

	public void setTitleimglink(String titleimglink) {
		this.titleimglink = titleimglink;
	}

	public Map<String, String> getImgs() {
		imgs=new HashMap<String, String>();
		if(titleimg!=null&&titleimglink!=null){
			String[] karr=titleimglink.split(",");
			String[] varr=titleimg.split(",");
			if(karr!=null&&varr!=null){
				for (int i = 0; i <karr.length; i++) {
					imgs.put(karr[i], varr[i]);
				}
			}
		}
		return imgs;
	}

}
