package cn.yi.gather.v20.editor.service;

import cn.yi.gather.v20.editor.entity.Htmlpagecode;

public interface IHtmlpagecodeService {

	public Htmlpagecode findById(String id);
	
	public Htmlpagecode save(Htmlpagecode htmlpagecode);
	
	public Htmlpagecode findByObjid(String objid);
	
}
