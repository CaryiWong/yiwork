package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * now-信息
 * @author Lee.J.Eric
 * @time 2014年11月28日 下午3:36:48
 */
@Entity
@Table(name = "current_msg")
public class CurrentMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3053441700897767837L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@Column(name = "contents",length=2000)
	private String contents = "";//内容
	
	@Column(name = "createdate")
	private Date createdate = new Date();

	public CurrentMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	

}
