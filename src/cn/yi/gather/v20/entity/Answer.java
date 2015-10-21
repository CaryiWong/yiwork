package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.yi.gather.v20.base.BaseEntity;


@Entity
@Table(name = "answer")
public class Answer extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3062959827591621997L;
	
	@Column(name="objid")
	private String objid="";
	
	@Column(name="type")
	private String objtype="";
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToMany(cascade = CascadeType.REFRESH, targetEntity = Question.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ref_answer_question", joinColumns = { @JoinColumn(name = "answer_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "question_id", referencedColumnName = "id") })
	private List<Question> qlist=new ArrayList<Question>();

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Question> getQlist() {
		return qlist;
	}

	public void setQlist(List<Question> qlist) {
		this.qlist = qlist;
	}
	
	public  Answer() {
		
	}
}
