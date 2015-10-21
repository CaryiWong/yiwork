package cn.yi.gather.v20.editor.service;

import java.util.List;

import cn.yi.gather.v20.editor.entity.Options;

public interface IOptionService {

	public Options saveOrupdate(Options options);
	
	public List<Options> getOptionList();
	
	public List<Options> getOptionList(List<String> ids);
	
	public Options findOptions(String id);
	
}
