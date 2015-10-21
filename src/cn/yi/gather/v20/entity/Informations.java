package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "informations")
public class Informations implements Serializable{
	
	private static final long serialVersionUID = 8204982202913504270L;

	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@Column(name="optionname")
	private String optionname;//选项名称
	
	@Column(name="optiontype")
	private Integer optiontype=0;//选项类型 0 选择题  1问答题
	
	@Column(name="preanswer")
	private String preanswer;//预备答案
	
	@Column(name="informationstype")
	private Integer informationstype=0;//0 报名前收集相信项   1 问卷调查

	
	
	public Informations() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOptionname() {
		return optionname;
	}

	public void setOptionname(String optionname) {
		this.optionname = optionname;
	}

	public Integer getOptiontype() {
		return optiontype;
	}

	public void setOptiontype(Integer optiontype) {
		this.optiontype = optiontype;
	}

	public String getPreanswer() {
		return preanswer;
	}

	public void setPreanswer(String preanswer) {
		this.preanswer = preanswer;
	}

	public Integer getInformationstype() {
		return informationstype;
	}

	public void setInformationstype(Integer informationstype) {
		this.informationstype = informationstype;
	}
	
	
	
}
