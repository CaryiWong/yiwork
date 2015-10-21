package cn.yi.gather.v20.editor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.editor.entity.ObjectOptions;

@Component("objectOptionsRepositoryV20")
public interface ObjectOptionsRepository extends JpaRepository<ObjectOptions, String>,JpaSpecificationExecutor<ObjectOptions>{

	@Modifying
	@Query(value = "update ObjectOptions set objid =:objid , objtype=:objtype where objid =:lsid")
	public void updateObj(@Param("objid")String objid,@Param("objtype")String objtype,@Param("lsid")String lsid);
	
}
