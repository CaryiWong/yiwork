package cn.yi.gather.v22.yg100.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v22.yg100.entity.GoodsInfo;

@Component("goodsInfoRepository")
public interface GoodsInfoRepository extends JpaRepository<GoodsInfo, String>{

}
