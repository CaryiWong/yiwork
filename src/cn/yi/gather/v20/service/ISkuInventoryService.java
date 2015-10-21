package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.SkuInventory;

public interface ISkuInventoryService {
	
	public SkuInventory saveOrUpdate(SkuInventory entity);
	
	public SkuInventory findById(String id);

}
