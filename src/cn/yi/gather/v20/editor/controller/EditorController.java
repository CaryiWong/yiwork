package cn.yi.gather.v20.editor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.editor.comm.CreateHtml;
import cn.yi.gather.v20.editor.entity.Htmlpagecode;
import cn.yi.gather.v20.editor.entity.ObjectOptions;
import cn.yi.gather.v20.editor.entity.Options;
import cn.yi.gather.v20.editor.entity.Template;
import cn.yi.gather.v20.editor.service.IHtmlpagecodeService;
import cn.yi.gather.v20.editor.service.IObjectOptionsService;
import cn.yi.gather.v20.editor.service.IOptionService;
import cn.yi.gather.v20.editor.service.ITemplateService;
import cn.yi.gather.v20.entity.Activity;


@Controller("editorControllerV20")
@RequestMapping(value="v20/editor")
public class EditorController {

	@Resource(name="optionServiceV20")
	private IOptionService optionService; //选项
	
	@Resource(name="templateServiceV20")
	private ITemplateService templateService;//模板
	
	@Resource(name="objectOptionsServiceV20")
	private IObjectOptionsService objectOptionsService;//记录
	
	@Resource(name="htmlpagecodeServiceV20")
	private IHtmlpagecodeService htmlpagecodeService;//页面代码
	
	
	/**
	 * 新增一个模板选项
	 * @param response
	 * @param options
	 */
	@RequestMapping(value="addoptions")
	public void addoptions(HttpServletResponse response,Options options,String type) {
		Jr jr = new Jr();
		jr.setMethod("addoptions");
		options=optionService.saveOrupdate(options);
		if(options!=null){
			jr.setCord(0);
			jr.setMsg("保存成功");
		}else{
			jr.setCord(-1);
		}
		jr.setData(options);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取选项列表（新建模板需要）
	 * @param response
	 */
	@RequestMapping(value="getlistoptions")
	public void getlistoptions(HttpServletResponse response,String type) {
		Jr jr = new Jr();
		jr.setMethod("getlistoptions");
		jr.setData(optionService.getOptionList());
		jr.setCord(0);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 新建模板
	 * @param response
	 * @param template
	 * @param optionids
	 */
	@RequestMapping(value="addtemplate")
	public void addtemplate(HttpServletResponse response,Template template,String type) {
		Jr jr = new Jr();
		jr.setMethod("addtemplate");
		if(template.getPid()==null||template.getPid().length()<1){
			template.setPid(template.getId());
		}
		String optionids=template.getOptionsstor();
		if(optionids!=null&&optionids.length()>0){
			String[] oids=optionids.split(",");
			List<String> ids=new ArrayList<String>();
			for (int i=0;i<oids.length;i++) {
				ids.add(oids[i]);
			}
			List<Options> list=optionService.getOptionList(ids);
			template.setOptions(list);
		}
		template=templateService.saveOrupdate(template);
		jr.setData(template);
		jr.setCord(0);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取单个模板（保函其子模板）
	 * @param response
	 * @param id
	 */
	@RequestMapping(value="gettemplate")
	public void gettemplate(HttpServletResponse response,String id,String type) {
		Jr jr = new Jr();
		jr.setMethod("gettemplate");
		Template template=templateService.findTemplate(id);
		if (template!=null) {
			List<Template> list = templateService.findTemplateList(id);
			template.setChildtemplates(list);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Template temp=(Template)list.get(i);
					temp.setChildtemplates(templateService.findTemplateList(((Template)list.get(i)).getId()));
				}
				
			}
		}
		jr.setData(template);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存单个选项内容    当objid 无值时  默认开启一组新的选项内容   同属一个对象的  objid赋值第一次系统返回的值
	 * @param response
	 * @param type
	 * @param options
	 */
	@RequestMapping(value="saveobjectoptions")
	public void saveobjectoptions(HttpServletResponse response,String type,ObjectOptions options) {
		Jr jr = new Jr();
		jr.setMethod("saveobjectoptions");
		if(options.getOptions()==null||options.getTemplate()==null||options.getOptions().getId()==null||options.getTemplate().getId()==null){
			jr.setCord(1);
			jr.setMsg("非法传参");
		}
		
		options=objectOptionsService.saveOrupdate(options);
		if(options!=null){
			jr.setCord(0);
			jr.setData(options);
			jr.setMsg("保存成功");
		}else{
			jr.setCord(-1);
			jr.setMsg("保存失败");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@RequestMapping(value="getobjectbyparam")
	public void getobjectbyparam(HttpServletResponse response,String type,String objid,String objtype,String tmpid,Integer page,Integer size) {
		Jr jr = new Jr();
		jr.setMethod("getobjectbyparam");
		Page<ObjectOptions> list=objectOptionsService.objectlistbyparam(objid, objtype, tmpid, page, size);
		if(list!=null){
			jr.setCord(0);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
			jr.setMsg("成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 创建h5 或者图文html
	 * @param request
	 * @param response
	 * @param htmlpagecode (objid,htmltype,pagecode)
	 */
	@RequestMapping(value="createhtmlpage")
	public void createhtmlpage(HttpServletRequest request,HttpServletResponse response,Htmlpagecode htmlpagecode){
		Jr jr = new Jr();
		jr.setMethod("createhtmlpage");
		CreateHtml createHtml=new CreateHtml(htmlpagecode.getPagecode(),request.getServletContext().getRealPath("/"),htmlpagecode.getHtmltype());
		//防重复入库
		Htmlpagecode h=htmlpagecodeService.findByObjid(htmlpagecode.getObjid());
		if(h!=null){
			h.setPageurl(createHtml.getUrl_path());
			htmlpagecodeService.save(h);
		}else{
			htmlpagecode.setPageurl(createHtml.getUrl_path());
			htmlpagecodeService.save(htmlpagecode);
		}
		jr.setData(createHtml.getUrl_path());
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 主要是新增表单 绑定
	 * @param request
	 * @param response
	 * @param htmlpagecode (objid,pagecode)
	 */
	@RequestMapping(value="updatehtmlpage")
	public void updatehtmlpage(HttpServletRequest request,HttpServletResponse response,Htmlpagecode htmlpagecode){
		Jr jr = new Jr();
		jr.setMethod("updatehtmlpage");
		Htmlpagecode h=htmlpagecodeService.findByObjid(htmlpagecode.getObjid());
		if(htmlpagecode.getPagecode()!=null&&htmlpagecode.getPagecode().length()>1){
			h.setPagecode(h.getPagecode()+htmlpagecode.getPagecode());
			h=htmlpagecodeService.save(h);
		}
		CreateHtml createHtml=new CreateHtml(h.getPagecode(),request.getServletContext().getRealPath("/"),h.getHtmltype(),"update",h.getPageurl());
		jr.setData(createHtml.getUrl_path());
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
