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
import javax.persistence.Transient;

import cn.yi.gather.v20.entity.User.UserRoot;

import com.tools.utils.RandomUtil;

/**
 * 空间分点信息属性
 *
 */

@Entity
@Table(name = "workspace_info")
public class WorkSpaceInfo implements Serializable{

	public static enum SpaceStatus{
		NORMAL(1,"开通"),
		PAUSE(2,"暂停"),
		DISABLE(3,"禁用");
		private Integer code;
		private String name;
		private SpaceStatus(Integer code,String name){
			this.code=code;
			this.name=name;
		}
		public static String code(Integer code) {
			for (SpaceStatus s:SpaceStatus.values()) {
				if(s.code==code){
					return s.name;
				}
			}
			return null;
		}
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	private static final long serialVersionUID = 5984450603843146794L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(10);
	
	@Column(name = "space_name")
	private String spacename="";//分点名
	
	@Column(name = "space_code")
	private String spacecode="";//简写代号
	
	@Column(name = "space_address")
	private String spaceaddress="";//联系地址
	
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = City.class,fetch=FetchType.EAGER)
	@JoinColumn(name="space_city")
	private City spacecity;// 城市
	
	@Column(name = "space_gps")
	private String spacegps="";//GPS  x,y
	
	@Column(name = "space_tel")
	private String spacetel="";//联系电话
	
	@Column(name = "space_twonum")
	private String spacetwonum="";//微信二维码
	
	@Column(name = "space_weburl")
	private String spaceweburl="";//空间网站
	
	@Column(name = "space_logo")
	private String spacelogo="";//空间logo
	
	@Column(name = "space_status")
	private Integer spacestatus;//状态  
	
	@Column(name = "space_grade")
	private Integer spacegrade=2;//空间等级 /级别  一起 为 0 最高级  其他默认  2
	
	@Column(name = "createtime")
	private Date createtime=new Date();//创建时间
	
	@Transient
	private String spacestatusname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpacename() {
		return spacename;
	}

	public void setSpacename(String spacename) {
		this.spacename = spacename;
	}

	public String getSpacecode() {
		return spacecode;
	}

	public void setSpacecode(String spacecode) {
		this.spacecode = spacecode;
	}

	public String getSpaceaddress() {
		return spaceaddress;
	}

	public void setSpaceaddress(String spaceaddress) {
		this.spaceaddress = spaceaddress;
	}

	public String getSpacegps() {
		return spacegps;
	}

	public void setSpacegps(String spacegps) {
		this.spacegps = spacegps;
	}

	public String getSpacetel() {
		return spacetel;
	}

	public void setSpacetel(String spacetel) {
		this.spacetel = spacetel;
	}

	public String getSpacetwonum() {
		return spacetwonum;
	}

	public void setSpacetwonum(String spacetwonum) {
		this.spacetwonum = spacetwonum;
	}

	public Integer getSpacestatus() {
		return spacestatus;
	}

	public void setSpacestatus(Integer spacestatus) {
		this.spacestatus = spacestatus;
	}
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getSpacestatusname() {
		return SpaceStatus.code(this.spacestatus);
	}

	public WorkSpaceInfo(){
		
	}

	public String getSpaceweburl() {
		return spaceweburl;
	}

	public void setSpaceweburl(String spaceweburl) {
		this.spaceweburl = spaceweburl;
	}

	public String getSpacelogo() {
		return spacelogo;
	}

	public void setSpacelogo(String spacelogo) {
		this.spacelogo = spacelogo;
	}

	public Integer getSpacegrade() {
		return spacegrade;
	}

	public void setSpacegrade(Integer spacegrade) {
		this.spacegrade = spacegrade;
	}

	public void setSpacestatusname(String spacestatusname) {
		this.spacestatusname = spacestatusname;
	}

	public City getSpacecity() {
		return spacecity;
	}

	public void setSpacecity(City spacecity) {
		this.spacecity = spacecity;
	}

}
