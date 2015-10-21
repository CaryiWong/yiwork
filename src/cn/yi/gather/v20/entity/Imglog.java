package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "imglog")
public class Imglog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1953602835049L;
	
	@Id
	@Column(name = "id",unique=true)
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@Column(name = "imgurl")
	private String imgurl;
	
	@Column(name = "type")
	private Integer type;//int	0 用户头像  1活动封面  2 活动详情 3评论附件图，4个人展示，5团队展示
	
	@Column(name = "starts")
	private Integer starts=0;
	
	@Column(name = "createdate")
	private Long createdate=System.currentTimeMillis();
	
	@Column(name = "createdate1")
	private Date createdate1;
	
	@Column(name = "enddate")
	private Long enddate;
	
	@Column(name = "enddate1")
	private Date enddate1;
	
	public Imglog(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 图片源	imgurl	String	
	 * @return
	 */
	public String getImgurl() {
		return imgurl;
	}

	/**
	 * 图片源	imgurl	String	
	 * @param imgurl
	 */
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	/**
	 * 图片类型	type	int	0 用户  1活动封面  2 活动详情 3评论附件图 4个人展示，5团队展示
	 * @return
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 图片类型	type	int	0 用户  1活动封面  2 活动详情 3评论附件图 4个人展示，5团队展示
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 缩略图状态	starts	int	0 未生成  1 已生成
	 * @return
	 */
	public Integer getStarts() {
		return starts;
	}

	/**
	 * 缩略图状态	starts	int	0 未生成  1 已生成
	 * @param starts
	 */
	public void setStarts(Integer starts) {
		this.starts = starts;
	}

	/**
	 * 上传的时间	createdate	Long	
	 * @return
	 */
	public Long getCreatedate() {
		return createdate;
	}

	/**
	 * 上传的时间	createdate	Long	
	 * @param createdate
	 */
	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	/**
	 * 生成缩略图的时间	enddate	Long	
	 * @return
	 */
	public Long getEnddate() {
		return enddate;
	}

	/**
	 * 生成缩略图的时间	enddate	Long	
	 * @param enddate
	 */
	public void setEnddate(Long enddate) {
		this.enddate = enddate;
	}

	public Date getCreatedate1() {
		return createdate1;
	}

	public void setCreatedate1(Date createdate1) {
		this.createdate1 = createdate1;
	}

	public Date getEnddate1() {
		return enddate1;
	}

	public void setEnddate1(Date enddate1) {
		this.enddate1 = enddate1;
	}
}
