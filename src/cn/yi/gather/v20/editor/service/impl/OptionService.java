package cn.yi.gather.v20.editor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.editor.dao.OptionsRepository;
import cn.yi.gather.v20.editor.entity.Options;
import cn.yi.gather.v20.editor.service.IOptionService;

/**
 * 模板所需选项
 * @author Administrator
 *
 */
@Service("optionServiceV20")
public class OptionService implements IOptionService{
	
	@Resource(name="optionsRepositoryV20")
	private OptionsRepository repository;
	

	@Override
	public Options saveOrupdate(Options options) {
		// TODO Auto-generated method stub
		return repository.save(options);
	}

	@Override
	public List<Options> getOptionList() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<Options> getOptionList(List<String> ids) {
		// TODO Auto-generated method stub
		return repository.findByIdIn(ids);
	}

	@Override
	public Options findOptions(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
