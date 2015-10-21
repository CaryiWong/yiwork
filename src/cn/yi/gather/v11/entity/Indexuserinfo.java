package cn.yi.gather.v11.entity;

import java.io.Serializable;
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

import com.tools.utils.RandomUtil;

/**
 * 个人展示
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "indexuserinfo")
public class Indexuserinfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3386501747819274753L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@Column(name = "iusernickname")
	private String iusernickname="";
	
	@Column(name = "iuserimg")
	private String iuserimg="";
	
	@Column(name = "iusersummery",length = 2000)
	private String iusersummery="";
	
	@Column(name = "iuserjobinfo")
	private String iuserjobinfo="";
	
	@Column(name = "iusermaximg")
	private String iusermaximg="";
	
	@ManyToMany(cascade = CascadeType.REFRESH,targetEntity = Labels.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_indexuserinfo_label", joinColumns = { @JoinColumn(name = "indexuser_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "label_id", referencedColumnName = "id") })
	private List<Labels> iuserlables;

	public Indexuserinfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIusernickname() {
		return iusernickname;
	}

	public void setIusernickname(String iusernickname) {
		this.iusernickname = iusernickname;
	}

	public String getIuserimg() {
		return iuserimg;
	}

	public void setIuserimg(String iuserimg) {
		this.iuserimg = iuserimg;
	}

	public String getIusersummery() {
		return iusersummery;
	}

	public void setIusersummery(String iusersummery) {
		this.iusersummery = iusersummery;
	}

	public String getIuserjobinfo() {
		return iuserjobinfo;
	}

	public void setIuserjobinfo(String iuserjobinfo) {
		this.iuserjobinfo = iuserjobinfo;
	}

	public String getIusermaximg() {
		return iusermaximg;
	}

	public void setIusermaximg(String iusermaximg) {
		this.iusermaximg = iusermaximg;
	}

	public List<Labels> getIuserlables() {
		return iuserlables;
	}

	public void setIuserlables(List<Labels> iuserlables) {
		this.iuserlables = iuserlables;
	} 

}
