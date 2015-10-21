package com.tools.utils;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;

/**
 * 邮件实体类
 * 
 * @author LeeJEric
 * 
 */
public class EmailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2233365882323348079L;

	private Boolean TLS;
	private String hostName;// 服务器名,e.g:smtp.qq.com,smtp.163.com
	private String account;// 发送方帐号
	private String password;// 发送方密码
	private String fromName;// 发送方名称
	private String title;
	private String charset;
	private String content;
	private List<String> receivers;// 接收方帐号
	private List<String> toNames;// 接收方名称

	private List<EmailAttachment> attachments;// 附件(注意：附件名称要带文件格式，中文要转码：windows下gbk->iso-8859-1)

	public EmailEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Boolean getTLS() {
		return TLS;
	}

	public void setTLS(Boolean tLS) {
		TLS = tLS;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}

	public List<String> getToNames() {
		return toNames;
	}

	public void setToNames(List<String> toNames) {
		this.toNames = toNames;
	}

	public List<EmailAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<EmailAttachment> attachments) {
		this.attachments = attachments;
	}

}
