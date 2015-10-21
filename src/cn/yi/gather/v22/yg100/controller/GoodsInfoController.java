package cn.yi.gather.v22.yg100.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v22.yg100.entity.GoodsInfo;
import cn.yi.gather.v22.yg100.service.IGoodsInfoService;

/**
 * v 1.2 9.10
 * @author Administrator
 *
 */
@Controller("goodsInfoController")
@RequestMapping(value="/yg/goods")
public class GoodsInfoController {

	@Resource(name="goodsInfoService")
	private IGoodsInfoService goodsInfoService;
	
	/**
	 * 获取商品信息
	 * @param response
	 */
	@RequestMapping(value = "getyginn")
	public void getYgInn(HttpServletResponse response){
		Jr jr = new Jr();
		jr.setMethod("getyginn");
		List<GoodsInfo> list=goodsInfoService.findAll();
		if(list!=null){
			jr.setData(list.get(0));
			jr.setCord(0);
		}
		jr.setCord(0);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
