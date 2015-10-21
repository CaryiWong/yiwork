package cn.yi.gather.v20.editor.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.editor.dao.HtmlpagecodeRepository;
import cn.yi.gather.v20.editor.entity.Htmlpagecode;
import cn.yi.gather.v20.editor.service.IHtmlpagecodeService;

@Service("htmlpagecodeServiceV20")
public class HtmlpagecodeService implements IHtmlpagecodeService{

	@Resource(name="htmlpagecodeRepositoryV20")
	private HtmlpagecodeRepository repository;
	
	@Override
	public Htmlpagecode findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Htmlpagecode save(Htmlpagecode htmlpagecode) {
		// TODO Auto-generated method stub
		return repository.save(htmlpagecode);
	}

	@Override
	public Htmlpagecode findByObjid(String objid) {
		return repository.findByObjid(objid);
	}

}
