package cn.yi.gather.v20.editor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.editor.entity.Template;

@Component("templateRepositoryV20")
public interface TemplateRepository extends JpaRepository<Template, String>,JpaSpecificationExecutor<Template>{

	public List<Template> findByPid(String pid);
	
	public List<Template> findByIdIn(List<String> ids);
	
}
