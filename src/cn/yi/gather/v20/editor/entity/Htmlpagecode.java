package cn.yi.gather.v20.editor.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name ="htmlpagecode")
public class Htmlpagecode implements Serializable{

	private static final long serialVersionUID = 4116097744501454395L;

	@Id
	@Column(name="id")
	private String id="code"+System.currentTimeMillis();
	
	@Column(name="objid")
	private String objid="";
	
	@Column(name="htmltype")
	private String htmltype="";//  h5 or imgtext
	
	@Column(name="pagecode",length=20000)
	private String pagecode;
	
	@Column(name="createtime")
	private Date createtime=new Date();
	
	@Column(name="pageurl")
	private String pageurl="";

	public String getPageurl() {
		return pageurl;
	}

	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getHtmltype() {
		return htmltype;
	}

	public void setHtmltype(String htmltype) {
		this.htmltype = htmltype;
	}

	public String getPagecode() {
		return pagecode;
	}

	public void setPagecode(String pagecode) {
		this.pagecode = pagecode;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public Htmlpagecode(){
		
	}
	
}
