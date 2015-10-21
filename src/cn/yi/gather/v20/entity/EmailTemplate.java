package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yi.gather.v20.base.BaseEntity;

/**
 * 邮件模版
 * @author Lee.J.Eric
 * @time 2015年3月17日 下午2:36:45
 */
@Entity
@Table(name = "email_template")
public class EmailTemplate extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8885751965171569110L;
	
	@Column(name = "title")
	private String title = "";//标题
	
	@Column(name = "code")
	private String code = "";//模版识别代码
	
	@Column(name = "contents",length = 20000)
	private String contents="";//内容

	public EmailTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
