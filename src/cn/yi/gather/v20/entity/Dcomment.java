package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "dcomment")
public class Dcomment implements Serializable{

	private static final long serialVersionUID = 445426510L;
	
	public static enum DCommentTypeValue{
		RECOMMEND("recommend"),
		NORMAL("normal"),
		REPLY("reply");
		public String value;

		DCommentTypeValue(String value) {
			this.value=value;
		}
		
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//评论人
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="receive_id")
	private User receive;//接受者
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=Demands.class,fetch=FetchType.EAGER)
	@JoinColumn(name="demands_id")
	private Demands demands;//被评论的需求
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="recommend_id")
	private User recommend;//被推荐的人
	
	@Column(name="texts",length=5000)
	private String texts="";//评论内容
	
	@Column(name="isdel")
	private Integer isdel=0;
	
	@Column(name="comment_type")
	private String commenttype="normal";//评论类型  recommend 推荐    normal 正常评论   reply 回复
	
	@Transient
	private Long createdate;
	
	@Column(name="createdate1")
	private Date createdate1=new Date();//评论时间
	
	@Column (name="pid")
	private String pid="";
	
	@Transient
	private Set<String> mediaurl;
	
	@Column(name="mediaurl0")
	private String mediaurl0;// 评论是附件列表 文件名,文件名
	
	@Column(name="mediatype")
	private Integer mediatype=-1;//-1无附件 0 文档，1 图片，2 压缩包
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_user_dcomment_praises",joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "dcomment_id", referencedColumnName = "id") })
	private Set<User> praises=new HashSet<User>();
	
	@Transient
	private Integer praisesnum;
	
	@Transient
	private List<Dcomment> replydcomments=new ArrayList<Dcomment>();
	
	
	public Dcomment() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Demands getDemands() {
		return demands;
	}


	public void setDemands(Demands demands) {
		this.demands = demands;
	}


	public String getTexts() {
		try {
			texts = URLDecoder.decode(texts, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texts;
	}


	public void setTexts(String texts) {
		try {
			this.texts = URLEncoder.encode(texts, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Long getCreatedate() {
		if(createdate1!=null){
			return createdate1.getTime();
		}
		return 0l;
	}


	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}


	public Date getCreatedate1() {
		return createdate1;
	}


	public void setCreatedate1(Date createdate1) {
		this.createdate1 = createdate1;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public Set<String> getMediaurl() {
		Set<String> set=new HashSet<String>();
		if(mediaurl0!=null){
			String[] ar=mediaurl0.split(",");
			if(ar!=null&&ar.length>0){
				Collections.addAll(set, ar);
			}
		}
		return set;
	}


	public void setMediaurl(Set<String> mediaurl) {
		this.mediaurl = mediaurl;
	}


	public String getMediaurl0() {
		return mediaurl0;
	}


	public void setMediaurl0(String mediaurl0) {
		this.mediaurl0 = mediaurl0;
	}


	public Integer getMediatype() {
		return mediatype;
	}


	public void setMediatype(Integer mediatype) {
		this.mediatype = mediatype;
	}


	public List<Dcomment> getReplydcomments() {
		return replydcomments;
	}


	public void setReplydcomments(List<Dcomment> replydcomments) {
		this.replydcomments = replydcomments;
	}


	public User getReceive() {
		return receive;
	}


	public void setReceive(User receive) {
		this.receive = receive;
	}


	public Integer getIsdel() {
		return isdel;
	}


	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}


	public User getRecommend() {
		return recommend;
	}


	public void setRecommend(User recommend) {
		this.recommend = recommend;
	}


	public String getCommenttype() {
		return commenttype;
	}


	public void setCommenttype(String commenttype) {
		this.commenttype = commenttype;
	}


	public Set<User> getPraises() {
		return praises;
	}


	public void setPraises(Set<User> praises) {
		this.praises = praises;
	}


	public Integer getPraisesnum() {
		if(praises!=null){
			return praises.size();
		}else{
			return 0;
		}
	}


	public void setPraisesnum(Integer praisesnum) {
		this.praisesnum = praisesnum;
	}
	
	
}
