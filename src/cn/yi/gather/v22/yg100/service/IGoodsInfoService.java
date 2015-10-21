package cn.yi.gather.v22.yg100.service;

import java.util.List;

import cn.yi.gather.v22.yg100.entity.GoodsInfo;

public interface IGoodsInfoService {

	public GoodsInfo saveOrUpdate(GoodsInfo goodsInfo);
	
	public GoodsInfo findOne(String id);
	
	public List<GoodsInfo> findAll();
}
