package cn.yi.gather.v22.yg100.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 阳光100  微信登录用户
 * @author YuanBao
 *
 */
@Entity
@Table(name = "weixin_user")
public class WxUser implements Serializable{
	private static final long serialVersionUID = 2159035893383768432L;

	@Id
	@Column(name="id")
	private String id="wx"+System.currentTimeMillis();
	
	@Column(name="tel")
	private String tel="";//绑定验证后手机
	
	@Column(name="nickname")
	private String nickname="";//微信妮称
	
	@Column(name="headimgurl")
	private String headimgurl="";//微信头像
	
	@Column(name="openid")
	private String openid="";
	
	@Column(name="unionid")
	private String unionid="";
	
	@Column(name="createtime")
	private Date createtime=new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public WxUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
