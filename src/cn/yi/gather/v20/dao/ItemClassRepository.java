package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.ItemClass;

/**
 * 商品大类
 */
@Component("itemClassRepositoryV20")
public interface ItemClassRepository extends JpaRepository<ItemClass, Long>{
	
	public ItemClass findById(Long item_class_id);

	@Query("select o from ItemClass o where status=:status")
	public List<ItemClass> findByStatus(@Param("status") int status);
	
	public ItemClass findByCode(String code);
}
