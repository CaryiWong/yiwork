package cn.yi.gather.v20.service.serviceImpl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.SkuInventoryRepository;
import cn.yi.gather.v20.entity.SkuInventory;
import cn.yi.gather.v20.service.ISkuInventoryService;

/**
 * 商品库存
 * @author Lee.J.Eric
 * @time 2015年1月2日 上午11:26:08
 */
@Service("skuInventoryServiceV20")
public class SkuInventoryService implements ISkuInventoryService{

	@Resource(name="skuInventoryRepositoryV20")
	private SkuInventoryRepository repository;

	@Override
	public SkuInventory saveOrUpdate(SkuInventory entity) {
		// TODO Auto-generated method stub
//		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
		return repository.save(entity);
	}

	@Override
	public SkuInventory findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
	
}
