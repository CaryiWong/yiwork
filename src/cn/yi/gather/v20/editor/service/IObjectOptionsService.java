package cn.yi.gather.v20.editor.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.editor.entity.ObjectOptions;

public interface IObjectOptionsService {

	public ObjectOptions saveOrupdate(ObjectOptions objectOptions);
	
	public ObjectOptions findObjectOptions(String id);
	
	public List<ObjectOptions> findAllObjectOptions();
	
	/**
	 * 根据模板获取生成的对象
	 * @param template_id
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<ObjectOptions> findObjectOptionsByTemplate(String template_id,Integer page,Integer size);
	
	/**
	 * 根据 对象类型 对象ID 获取当前对象的所有属性列表
	 * @param objid
	 * @param objtype
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<ObjectOptions> findObjectOptionsByObj(String objid, String objtype,Integer page,Integer size);
	
	/**
	 * 根据 对象临时ID 获取当前对象的所有属性列表
	 * @param objid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<ObjectOptions> findObjectOptionsByObj(String objid);
	
	/**
	 * 把临时对象 与 活动/课程 等  对象关联起来
	 * @param objid  活动/课程ID
	 * @param objtype  活动：activity   课程：coures
	 * @param lsid   临时对象ID
	 * @return
	 */
	public List<ObjectOptions> updateObj(String objid,String objtype,String lsid);
	
	/**
	 * 获取选项列表
	 * @param objid   根据对象ID
	 * @param objtype 根据对象类型
	 * @param tmpid   根据模板类型
	 * @param page    当前页  -1 不翻页
	 * @param size    每页大小
	 * @return 
	 */
	public Page<ObjectOptions> objectlistbyparam(String objid,String objtype,String tmpid,Integer page,Integer size);
	
}
