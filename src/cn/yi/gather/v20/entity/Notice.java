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

import cn.yi.gather.v20.permission.entity.AdminUser;

import com.tools.utils.RandomUtil;

/**
 * 公告
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "notice")
public class Notice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2802018321111271536L;
	
	public static final String entityName = "notice";
	
	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.getRandomeStringForLength(20);
	
	@Column(name = "title")
	private String title = "";//标题
	
	@Column(name = "content")
	private String content = "";//内容
	
	@Column(name = "createtime")
	private Date createtime = new Date();//创建时间
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = AdminUser.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private AdminUser user;
	//private User user; // 操作者
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="workspace_id")
	private WorkSpaceInfo spaceInfo;// 所属空间信息
	

	public WorkSpaceInfo getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(WorkSpaceInfo spaceInfo) {
		this.spaceInfo = spaceInfo;
	}

	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}
	
}