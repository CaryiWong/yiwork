package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "spaceaddress")
public class Spaceaddress implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4455979432650133106L;

	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@Column(name = "zname")
	private String zname="";
	
	@Column(name = "ename")
	private String ename="";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZname() {
		return zname;
	}

	public void setZname(String zname) {
		this.zname = zname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	public Spaceaddress(){
		
	}
	
}
