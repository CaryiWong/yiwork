package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v11.entity.Demands;
import cn.yi.gather.v11.entity.Focus;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IDemandsServiceV2;
import cn.yi.gather.v11.service.IFocusServiceV2;
import cn.yi.gather.v11.service.IUserServiceV2;

import com.common.Jr;
import com.tools.utils.JSONUtil;

@Controller("focusControllerV2")
@RequestMapping(value="v2/demandfocus")
public class FocusControllerV2 {

	@Resource(name = "focusServiceV2")
	private IFocusServiceV2 focusServiceV2;
	
	@Resource(name= "userServiceV2")
	private IUserServiceV2 userServiceV2;
	
	@Resource(name = "demandsServiceV2")
	private IDemandsServiceV2 demandsServiceV2;
	
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
		focus = focusServiceV2.saveFocus(focus);
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
		User user=userServiceV2.findById(userid);
		Demands demands=demandsServiceV2.getDemandinfo(demandid);
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
			focusServiceV2.delFocus(user, demands);
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
			Page<Focus> list=focusServiceV2.findFocusBy(page, size, uid, demandid, -1);
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
