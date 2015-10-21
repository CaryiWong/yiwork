package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "userotherinfo")
public class UserotherInfo implements Serializable{

	private static final long serialVersionUID = -4462926838100850329L;

	@Id
	@Column(name="id")
	private String id= System.currentTimeMillis()+ RandomUtil.getRandomeStringForLength(20); // 主键ID 唯一 非空
	
	@ManyToOne(cascade=CascadeType.PERSIST,targetEntity = Userother.class,fetch=FetchType.EAGER)
	@JoinColumn(name="userother_id")
	private Userother userother;//对应发问题选择项
	
	@Column(name="texts",length=2000)
	private String texts="";//内容

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Userother getUserother() {
		return userother;
	}

	public void setUserother(Userother userother) {
		this.userother = userother;
	}

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public UserotherInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj){
			return true;
		}
		if(!(obj instanceof UserotherInfo)){
			return false;
		}
		final UserotherInfo info = (UserotherInfo) obj;
		if(!info.getId().equals(getId())){
			return false;
		}
		return true;
	}
	
}
