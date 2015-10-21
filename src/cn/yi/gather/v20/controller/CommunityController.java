package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.CommunityInfo;
import cn.yi.gather.v20.service.ICommunityService;

/**
 * 社群 Controller
 * @author ybao
 *
 */
@Controller
@RequestMapping(value="v20/community")
public class CommunityController {

	@Resource(name="communityService")
	private ICommunityService communityService;
	
	/**
	 * 获取社群列表
	 * @param response
	 * @param type
	 * @param size
	 * @param page
	 */
	@RequestMapping(value="findallcommunity")
	public void findAllCommunity(HttpServletResponse response,String type,Integer size,Integer page){
		Jr jr=new Jr();
		jr.setMethod("findallcommunity");
		Page<CommunityInfo> p= communityService.findAll(size, page);
		jr.setPagenum(page+1);
		jr.setPagecount(p.getTotalPages());
		jr.setPagesum(p.getNumberOfElements());
		jr.setTotal(p.getTotalElements());
		jr.setData(p.getContent());
		jr.setCord(0);
		jr.setMsg("获取成功");
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取单个社群详情
	 * @param response
	 * @param type
	 * @param id
	 */
	@RequestMapping(value="findcommunity")
	public void findCommunity(HttpServletResponse response,String type,String id){
		Jr jr=new Jr();
		jr.setMethod("findcommunity");
		if(id!=null&&id.length()>0){
			jr.setData(communityService.findById(id));
			jr.setCord(0);
			jr.setMsg("获取成功");
		}else{
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 社群 banner
	 * @param response
	 * @param type
	 */
	@RequestMapping(value="findbanner")
	public void findBanner(HttpServletResponse response,String type){
		Jr jr=new Jr();
		jr.setMethod("findbanner");
		List<CommunityInfo> p= communityService.findBanner();
		jr.setData(p);
		jr.setCord(0);
		jr.setMsg("获取成功");
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
