package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.yi.gather.v20.base.BaseEntity;

/**
 * 问小Yi
 * @author Lee.J.Eric
 * @time 2014年12月18日 下午2:45:03
 */
@Entity
@Table(name = "yi_question")
public class YiQuestion extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5485478110563815826L;
	
	public static final String entityName = "yiquestion";
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;

	@Column(name = "contents",length = 5000)
	private String contents = "";

	@Column(name = "msg_type")
	private Integer msgtype = 0;//类型：0提问，1回复
	
	@Column(name="parent_id")
	private String parent="";
	
	@OneToMany(cascade = CascadeType.REFRESH, targetEntity = YiQuestion.class,fetch=FetchType.EAGER)
	@JoinTable(name = "ref_yiquestion_answer",joinColumns = { @JoinColumn(name = "qeution_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "answer_id", referencedColumnName = "id") })
	private List<YiQuestion> answer = new ArrayList<YiQuestion>();
	
	public YiQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContents() {
		try {
			contents = URLDecoder.decode(contents, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	}

	public void setContents(String contents) {
		try {
			this.contents = URLEncoder.encode(contents, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(Integer msgtype) {
		this.msgtype = msgtype;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<YiQuestion> getAnswer() {
		return answer;
	}

	public void setAnswer(List<YiQuestion> answer) {
		this.answer = answer;
	}

}
