package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.yi.gather.v20.base.BaseEntity;

/**
 * 喊话内容
 * @author Lee.J.Eric
 * @time 2015年1月12日 上午11:55:22
 */
@Table
@Entity(name = "shout_content")
public class ShoutContent extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7433169382420235416L;
	
	private String content = "";//喊话内容

	public ShoutContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
