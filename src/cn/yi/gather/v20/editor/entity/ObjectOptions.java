package cn.yi.gather.v20.editor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table( name ="objectoptions")
public class ObjectOptions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2343254689878866778L;
	
	@Id
	@Column(name="id")
	private String id="an"+System.currentTimeMillis();
	
	@ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Options.class,fetch=FetchType.EAGER)
	@JoinColumn(name="options_id")
	private Options options;
	
	@ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Template.class,fetch=FetchType.EAGER)
	@JoinColumn(name="template_id")
	private Template template;
	
	@Column(name="texts",length=20000)
	private String texts="";
	
	@Column(name="createtime")
	private Date createtime=new Date();
	
	@Column(name="csstype")
	private String csstype="1";
	
	@Column(name="objid")
	private String objid="ls"+System.currentTimeMillis();// 对应活动ID 或者 课程ID 等 
	
	@Column(name="objtype")
	private String objtype=""; //对象类型 活动：activity  课程：coures
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCsstype() {
		return csstype;
	}

	public void setCsstype(String csstype) {
		this.csstype = csstype;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	public ObjectOptions() {
		
	}

}
