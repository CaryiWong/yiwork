package cn.yi.gather.v11.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.tools.utils.RandomUtil;

/**
 * 展示排序
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "indexorderbyinfo")
public class Indexorderbyinfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1021896122396380831L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@Transient
	private List<String> ids=new ArrayList<String>();
	
	@Column(name = "idstring",length = 10000)
	private String idstring = "";
	
	@Column(name = "idtype")
	private Integer idtype=0;//0:个人展示，1团队展示
	
	@Column(name = "createdate")
	private Date createdate=new Date();

	public Indexorderbyinfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getIds() {
		List<String> re = new ArrayList<String>();
		if(StringUtils.isNotBlank(idstring)){
			String[] a = idstring.split(",");
			re = Arrays.asList(a);
		}
		return re;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getIdstring() {
		return idstring;
	}

	public void setIdstring(String idstring) {
		this.idstring = idstring;
	}

	public Integer getIdtype() {
		return idtype;
	}

	public void setIdtype(Integer idtype) {
		this.idtype = idtype;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}
