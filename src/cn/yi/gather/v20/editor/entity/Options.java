package cn.yi.gather.v20.editor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table( name ="options")
public class Options implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9051281537340721886L;
	
	@Id
	@Column(name="id")
	private String id="option"+System.currentTimeMillis();
	
	@Column(name="zname")
	private String zname="";
	
	@Column(name="ename")
	private String ename="";
	
	@Column(name="type")
	private String optiontype="text";// 文本：text   图片：img   单选：radio   多选：checkbox  按钮：button  链接：a
	
	@Column(name="options")
	private String options=""; // 当type 为选择类型时  预留的选择项
	
	@Transient
	private List<String> optionslist=new ArrayList<String>();

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


	public String getOptiontype() {
		return optiontype;
	}

	public void setOptiontype(String optiontype) {
		this.optiontype = optiontype;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public List<String> getOptionslist() {
		List<String> list=new ArrayList<String>();
		if(options!=null&&options.length()>0){
			String[] arr= options.split(",");
			list=Arrays.asList(arr);
		}
		return list;
	}

	public void setOptionslist(List<String> optionslist) {
		this.optionslist = optionslist;
	}
	
	public Options(){
		
	}

}
