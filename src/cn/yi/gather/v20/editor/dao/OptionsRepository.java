package cn.yi.gather.v20.editor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.editor.entity.Options;

@Component("optionsRepositoryV20")
public interface OptionsRepository extends JpaRepository<Options, String>{

	public List<Options> findByIdIn(List<String> ids);
	
}
