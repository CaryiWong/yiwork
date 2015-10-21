package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Focus;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IDemandsService;
import cn.yi.gather.v20.service.IFocusService;
import cn.yi.gather.v20.service.IUserService;

@Controller("focusControllerV20")
@RequestMapping(value="v20/demandfocus")
public class FocusController {

	@Resource(name = "focusServiceV20")
	private IFocusService focusService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "demandsServiceV20")
	private IDemandsService demandsService;
	
	/**
	 * 新增关注
	 * @param response
	 * @param focus
	 */
	@RequestMapping(value="newfocus")
	public void newFocus(HttpServletResponse response,Focus focus,Integer type){
		Jr jr = new Jr();
		jr.setMethod("newfocus");
		focus.setId(focus.getDemands().getId()+focus.getUser().getId());
		focus = focusService.saveFocus(focus);
		if(focus==null){
			jr.setCord(-1);
			jr.setMsg("关注失败");
		}else {
			jr.setData(focus);
			jr.setCord(0);
			jr.setMsg("关注成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  取消关注
	 * @param response
	 * @param userid
	 * @param demandid
	 * @param type
	 */
	@RequestMapping(value="canclefocus")
	public void cancleFocus(HttpServletResponse response,String userid,String demandid,Integer type){
		Jr jr = new Jr();
		Boolean f=true;
		jr.setMethod("canclefocus");
		User user=userService.findById(userid);
		Demands demands=demandsService.getDemandinfo(demandid);
		if(demands==null){
			jr.setCord(2);
			jr.setMsg("需求不存在");
			f=false;
		}
		if(user==null){
			jr.setCord(1);
			jr.setMsg("用户不存在");
			f=false;
		}
		if(f){
			focusService.delFocus(user, demands);
			jr.setCord(0);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取关注列表
	 * @param response
	 * @param demandid
	 * @param uid
	 * @param page
	 * @param size
	 */
	@RequestMapping(value="getfocuslist")
	public void getFocusList(HttpServletResponse response,String demandid,String uid ,Integer page,Integer size,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getfocuslist");
		if(demandid==null&&uid==null){
			jr.setCord(1);
			jr.setMsg("需求ID和用户ID不能同时为空");
		}else{
			Page<Focus> list=focusService.findFocusBy(page, size, uid, demandid, -1);
			List<User> us=new ArrayList<User>();
			if(list!=null){
				for(Focus o:list.getContent()){
					us.add(o.getUser());
				}
			}
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(us);
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
