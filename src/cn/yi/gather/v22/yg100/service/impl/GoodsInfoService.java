package cn.yi.gather.v22.yg100.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v22.yg100.dao.GoodsInfoRepository;
import cn.yi.gather.v22.yg100.entity.GoodsInfo;
import cn.yi.gather.v22.yg100.service.IGoodsInfoService;

@Service("goodsInfoService")
public class GoodsInfoService implements IGoodsInfoService{

	@Resource(name="goodsInfoRepository")
	private GoodsInfoRepository repository;
	
	@Override
	public GoodsInfo saveOrUpdate(GoodsInfo goodsInfo) {
		return repository.save(goodsInfo);
	}

	@Override
	public GoodsInfo findOne(String id) {
		return repository.findOne(id);
	}
	
	@Override
	public List<GoodsInfo> findAll() {
		return repository.findAll();
	}

}
