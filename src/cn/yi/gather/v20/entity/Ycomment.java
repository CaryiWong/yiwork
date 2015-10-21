package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 点评     用户点评空间服务
 * @author Administrator
 *
 */
@Entity
@Table(name="ycomment")
public class Ycomment implements Serializable{

	private static final long serialVersionUID = 2987337813120840936L;

	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+ RandomUtil.getRandomeStringForLength(20);//ID 主键
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//点评者
	
	@Column(name="text",length=800)
	private String text="";//点评内容
	
	@Column(name="createtime")
	private Date createtime=new Date();//点评时间
	
	@Column(name="ytype")
	private String ytype="z";// 踩  c/ 赞 z
	
	@Column(name="coffeenum")
	private Integer coffeenum=0;// 咖啡数  默认0  没送咖啡

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getYtype() {
		return ytype;
	}

	public void setYtype(String ytype) {
		this.ytype = ytype;
	}

	public Integer getCoffeenum() {
		return coffeenum;
	}

	public void setCoffeenum(Integer coffeenum) {
		this.coffeenum = coffeenum;
	}
	
	public Ycomment(){
		
	}
	
}
