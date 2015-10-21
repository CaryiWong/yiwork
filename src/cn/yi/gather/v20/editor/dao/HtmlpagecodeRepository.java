package cn.yi.gather.v20.editor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.editor.entity.Htmlpagecode;

@Component("htmlpagecodeRepositoryV20")
public interface HtmlpagecodeRepository extends JpaRepository<Htmlpagecode, String>,JpaSpecificationExecutor<Htmlpagecode>{

	public Htmlpagecode findByObjid(String objid);
}
