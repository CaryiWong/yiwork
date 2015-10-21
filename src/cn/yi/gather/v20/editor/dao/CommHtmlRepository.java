package cn.yi.gather.v20.editor.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.editor.entity.CommHtml;

@Component("commHtmlRepository")
public interface CommHtmlRepository extends JpaRepository<CommHtml, String>,JpaSpecificationExecutor<CommHtml>{

	public List<CommHtml> findByUserid(String uid);
}
