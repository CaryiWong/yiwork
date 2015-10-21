package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.tools.utils.RandomUtil;

/**
 * 空间展示
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "spaceshow")
public class Spaceshow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3305718641541326298L;
	
	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() +RandomUtil.get32RandomUUID();
	
	@Column(name = "title")
	private String title = "";
	
	@Column(name = "name")
	private String name = "";
	
	@Transient
	private List<String> images = new ArrayList<String>();
	
	@Column(name = "image",length=20000)
	private String image = "";//数据表存放形式，逗号分隔，img,img,img

	public Spaceshow() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getImages() {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(image)){
			list = Arrays.asList(image.split(","));
		}
		return list;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}
