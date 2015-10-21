package cn.yi.gather.v22.yg100.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wx_ticket")
public class WxTicket implements Serializable{

	private static final long serialVersionUID = 7115758748210100699L;

	@Id
	@Column(name="appid")
	private String appid="";
	
	@Column(name="access_token")
	private String accesstoken="";
	
	@Column(name="js_api_ticket")
	private String jsapiticket="";
	
	@Column(name="update_time")
	private Date updatetime=new Date();

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public String getJsapiticket() {
		return jsapiticket;
	}

	public void setJsapiticket(String jsapiticket) {
		this.jsapiticket = jsapiticket;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public WxTicket() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
