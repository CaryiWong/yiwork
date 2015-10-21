package cn.yi.gather.v20.editor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table( name ="template")
public class Template implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2201619313715468883L;
	
	@Id
	@Column(name="id")
	private String id="temp"+System.currentTimeMillis();
	
	@Column(name="zname")
	private String zname="";
	
	@Column(name="ename")
	private String ename="";
	
	@ManyToMany(cascade = CascadeType.REFRESH,targetEntity = Options.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_temp_options", joinColumns = { @JoinColumn(name = "temp_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "option_id", referencedColumnName = "id") })
	private List<Options> options=new ArrayList<Options>();
	
	@Column(name="optionsstor",length=5000)
	private String optionsstor="";   //模板所选 选项的ID,ID,ID,ID
	
	@Column(name="createtime")
	private Date createtime=new Date();
	
	@Column(name="templatenum")
	private Integer templatenum=0;  //当前模板在上级模板的排序   一般大模板用0  其子模板从1 开始排
	
	@Column(name="pid")
	private String pid=id;
	
	@Column(name="tempsx")
	private String tempsx="";//模板对应的属性
	
	@Column(name="temptype")
	private String temptype="";//模板类型
	
	@Column(name="optionszdysx",length=50000)
	private String optionszdysx="";   //每个选项 在当前模板的 自定义属性    A的属性,B的属性,C的属性 对应 optionsstor  没有自定义属性的 用 “”
	
	@Transient
	private List<String> optionszdysxlist=new ArrayList<String>();
	
	@Transient
	private List<Template> childtemplates=new ArrayList<Template>();

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

	public List<Options> getOptions() {
		return options;
	}

	public void setOptions(List<Options> options) {
		this.options = options;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<Template> getChildtemplates() {
		return childtemplates;
	}

	public void setChildtemplates(List<Template> childtemplates) {
		this.childtemplates = childtemplates;
	}
	
	public Template() {
		
	}

	public String getOptionsstor() {
		return optionsstor;
	}

	public void setOptionsstor(String optionsstor) {
		this.optionsstor = optionsstor;
	}

	public Integer getTemplatenum() {
		return templatenum;
	}

	public void setTemplatenum(Integer templatenum) {
		this.templatenum = templatenum;
	}

	public String getTempsx() {
		return tempsx;
	}

	public void setTempsx(String tempsx) {
		this.tempsx = tempsx;
	}

	public String getOptionszdysx() {
		return optionszdysx;
	}

	public void setOptionszdysx(String optionszdysx) {
		this.optionszdysx = optionszdysx;
	}

	public List<String> getOptionszdysxlist() {
		this.optionszdysxlist=new ArrayList<String>();
		if(optionszdysx!=null&&optionszdysx.length()>0){
			String[] os=optionszdysx.split(",");
			optionszdysxlist=Arrays.asList(os);
		}
		return optionszdysxlist;
	}

	public void setOptionszdysxlist(List<String> optionszdysxlist) {
		this.optionszdysxlist = optionszdysxlist;
	}

	public String getTemptype() {
		return temptype;
	}

	public void setTemptype(String temptype) {
		this.temptype = temptype;
	}

	
}
