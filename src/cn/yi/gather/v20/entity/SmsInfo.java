package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.tools.utils.RandomUtil;

/**
 * 短信实体 
 * @author kcm
 * @time  7.10
 *
 */
@Entity
@Table(name = "smsinfo")
public class SmsInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3361736690143950908L;
	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20);//唯一码(对应user的id)
	
	@Column(name = "msgid")  //msgid
	private String msgid;
	
	@Column(name = "receivenum")  //接收号码
	private String receivenum;
	
	public String getMsgid() {
		return msgid;
	}


	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}


	@Column(name = "validatecode")
	private String validacode; // 验证码
	
	@Column(name = "smscontent")
	private String smscontent;  //短信全部内容
	
	@Column(name = "validatestatus")  
	private Integer validatestatus=0;   //验证状态  默认0没有验证过  1验证成功
	
	@Column(name = "sendstatus")  
	private Integer sendstatus=0;   //验证状态  默认0提交成功  1发送成功(是否维护这个状态？如何维护？)
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "getdate")
	private Date getdate=new Date();  //获取验证的时间
	 
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "senddate")
	private Date senddate=new Date();  //短信发送的时间
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "validatetime")
	private Date validatetime=null;  //短信发送的时间
	
	@Column(name = "ipaddress")
	private String ipaddress="";  //ip地址
	
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getReceivenum() {
		return receivenum;
	}

	public Date getValidatetime() {
		return validatetime;
	}


	public void setValidatetime(Date validatetime) {
		this.validatetime = validatetime;
	}

	public void setReceivenum(String receivenum) {
		this.receivenum = receivenum;
	}


	public String getValidacode() {
		return validacode;
	}


	public void setValidacode(String validacode) {
		this.validacode = validacode;
	}


	public String getSmscontent() {
		return smscontent;
	}


	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}


	public Integer getValidatestatus() {
		return validatestatus;
	}


	public void setValidatestatus(Integer validatestatus) {
		this.validatestatus = validatestatus;
	}


	public Integer getSendstatus() {
		return sendstatus;
	}


	public void setSendstatus(Integer sendstatus) {
		this.sendstatus = sendstatus;
	}


	public Date getGetdate() {
		return getdate;
	}


	public void setGetdate(Date getdate) {
		this.getdate = getdate;
	}


	public Date getSenddate() {
		return senddate;
	}


	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}


	public String getIpaddress() {
		return ipaddress;
	}


	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}



	 
	
	
}
