package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 用户附加信息选项表
 * @author Administrator
 *
 */
@Entity
@Table(name = "userother")
public class Userother implements Serializable{

	private static final long serialVersionUID = 8973945562849831286L;
	
	@Id
	@Column(name="id")
	private String id= System.currentTimeMillis()+ RandomUtil.getRandomeStringForLength(20); // 主键ID 唯一 非空
	
	@Column(name="ztitle")
	private String ztitle="";//标题文字
	
	@Column(name="etitle")
	private String etitle="";//标题文字
	
	@Column(name="infotype")
	private String infotype="txt";//内容类型  txt 文本  url 链接  img 图片

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZtitle() {
		return ztitle;
	}

	public void setZtitle(String ztitle) {
		this.ztitle = ztitle;
	}

	public String getEtitle() {
		return etitle;
	}

	public void setEtitle(String etitle) {
		this.etitle = etitle;
	}


	public String getInfotype() {
		return infotype;
	}

	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}

	public Userother() {
		super();
		// TODO Auto-generated constructor stub
	}

}
