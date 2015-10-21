package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.yi.gather.v20.base.BaseEntity;

/**
 * 问卷调查表单 对应的问题对象
 * @author Administrator
 */
@Entity
@Table(name = "question")
public class Question extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -7896456330960818404L;
	
	@Column(name="num")
	private String num="";//问题的顺序
	
	@Column(name="questiontext")
	private String questiontext="";//问题
	
	@Column(name="questiontype")
	private String questiontype="text";// 文本：text   图片：img   单选：radio   多选：checkbox  按钮：button  链接：a
	
	@Column(name="questionoptions")
	private String questionoptions=""; // 当type 为选择类型时  预留的选择项
	
	@Column(name="answertext",length=2000)
	private String answertext=""; // 用户填的信息
	
	@Transient
	private List<String> questionoptionslist=new ArrayList<String>();

	
	public String getAnswertext() {
		return answertext;
	}

	public void setAnswertext(String answertext) {
		this.answertext = answertext;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getQuestiontext() {
		return questiontext;
	}

	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}

	public String getQuestiontype() {
		return questiontype;
	}

	public void setQuestiontype(String questiontype) {
		this.questiontype = questiontype;
	}

	public String getQuestionoptions() {
		return questionoptions;
	}

	public void setQuestionoptions(String questionoptions) {
		this.questionoptions = questionoptions;
	}

	public List<String> getQuestionoptionslist() {
		return questionoptionslist;
	}

	public void setQuestionoptionslist(List<String> questionoptionslist) {
		this.questionoptionslist = questionoptionslist;
	}
	
	public Question() {
		super();
	}


}
