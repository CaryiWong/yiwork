package cn.yi.gather.v20.editor.service;

import java.util.List;

import cn.yi.gather.v20.editor.entity.Template;

public interface ITemplateService {

	public Template saveOrupdate(Template template);
	
	public Template findTemplate(String id);

	public List<Template> findTemplateList();
	
	public List<Template> findTemplateList(List<String> ids);
	
	public List<Template> findTemplateList(String pid);
}
