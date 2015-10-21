package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.SKU;

/**
 * 商品大类
 */
@Component("sKURepositoryV20")
public interface SKURepository extends JpaRepository<SKU, String>, JpaSpecificationExecutor<SKU>{
	
	public SKU findById(String id);
	
	@Query("select o from SKU o where itemClass_id=:item_class_id")
	public List<SKU> findByItemClassId(@Param("item_class_id") Long item_class_id);

	@Query(value="select defaultPrice from sku where id=:id", nativeQuery=true)
	public Integer getDefaultPriceById(@Param("id") String id);
	
	@Query("select o from SKU o where itemClass.name=:item_class_name and status=0")
	public List<SKU> findByItemClassName(@Param("item_class_name")String item_class_name);
}
