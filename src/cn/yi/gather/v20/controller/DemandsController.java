package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import cn.yi.gather.v20.entity.Dcomment;
import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Focus;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IDcommentService;
import cn.yi.gather.v20.service.IDemandsService;
import cn.yi.gather.v20.service.IFocusService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.IUserService;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("demandsControllerV20")
@RequestMapping(value="v20/demands")
public class DemandsController {
	private static Logger log = Logger.getLogger(DemandsController.class);

	@Resource(name = "demandsServiceV20")
	private IDemandsService demandsService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "dcommentServiceV20")
	private IDcommentService dcommentService;
	
	@Resource(name = "focusServiceV20")
	private IFocusService focusService;
	
	/**
	 *  2.0 获取所有需求列表 只拿 时效性以内的 （最近30天）
	 * @param response
	 * @param type          访问端：    android  安卓   ios IOS   web 网页端
	 * @param page        	当前页：    首页 0
	 * @param size        	每页显示记录数
	 * @param listorderby   发布时间排序：asc 正序   desc 倒序
	 * 2014-09-04
	 */
	@RequestMapping(value="getdemandlist")
	public void getDemandList(HttpServletResponse response,String type,Integer page,Integer size,String listorderby){
		Jr jr = new Jr();
		jr.setMethod("getdemandlist");
		if(page==null||page<0||size==null||size<=0){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Page<Demands> list = demandsService.getAllDemands(page, size, listorderby);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
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
	
	/**
	 * 2.0   我的需求列表 我参加的需求列表   我发起的需求列表
	 * @param response
	 * @param type          访问端：    android  安卓   ios IOS   web 网页端
	 * @param page        	当前页：    首页 0
	 * @param size        	每页显示记录数
	 * @param userid        我的ID 当前用户ID
	 * @param status        需求状态：-1：全部     1：正在解决        2：解决完成     3：未上架   4：未审核  5：已取消/已关闭
	 * @param flag          1：我发起       2：我参与     
	 * @param listorderby   发布时间排序：asc 正序   desc 倒序
	 * 2014-09-04
	 */
	@RequestMapping(value="mydemandlist")
	public void mydemandlist(HttpServletResponse response,String type,Integer page,Integer size,String userid,Integer status,Integer flag,String listorderby){
		Jr jr = new Jr();
		jr.setMethod("mydemandlist");
		if(page==null||page<0||size==null||size<0||userid==null||flag==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else{
			User u=userService.findById(userid);
			if(u==null){
				jr.setCord(1);
				jr.setMsg("用户不存在");
			}
			Page<Demands> list = demandsService.myDemandList(page, size, userid, status, flag, listorderby);
			if(list!=null){
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setData(list.getContent());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
			}else{
				jr.setCord(2);
				jr.setMsg("信息错误");
			}
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
	 * 2.0   新增需求 创建需求
	 * @param response
	 * @param type          访问端：    android  安卓   ios IOS   web 网页端
	 * @param demands  需求实体(需求发起人  标题  内容)
	 * 2014-09-09
	 */
	@RequestMapping(value="savedemand")
	public void savedemand(HttpServletResponse response,String type,Demands demands){
		Jr jr = new Jr();
		jr.setMethod("savedemand");
		if(demands==null||demands.getTitle()==null||demands.getPublishuser()==null||demands.getTexts()==null){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		User user=userService.findById(demands.getPublishuser().getId());
		if(user==null){
			jr.setCord(1);
			jr.setMsg("用户不存在");
		}else if(user.getRoot()>2){
			jr.setCord(2);
			jr.setMsg("用户无权限");
		}
		//demands.setPublishdate0(new Date());
		user.setDemandsnum(user.getDemandsnum()+1);//用户需求发表数
		userService.userSaveOrUpdate(user);
		demands=demandsService.createDemand(demands);
		if(demands==null){
			jr.setCord(3);
			jr.setMsg("新建需求失败");
		}else {
			jr.setData(demands);
			jr.setCord(0);
			jr.setMsg("新建需求成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 2.0   获取需求详情 查看需求详情
	 * @param response
	 * @param type       访问端：    android  安卓   ios IOS   web 网页端
	 * @param demandsid  需求ID
	 * 2014-09-09
	 */
	@RequestMapping(value="finddemand")
	public void finddemand(HttpServletResponse response,String type,String demandsid){
		Jr jr = new Jr();
		jr.setMethod("finddemand");
		if(demandsid!=null&&demandsid.length()>5){
			jr.setData(demandsService.getDemandinfo(demandsid));
			jr.setCord(0);
			jr.setMsg("成功");
		}else{
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.0   标记需求解决
	 * @param response
	 * @param type       访问端：    android  安卓   ios IOS   web 网页端
	 * @param demandsid  需求ID
	 * @param userid     用户ID （检测是不是需求发起人）
	 * 2014-09-09
	 */
	@RequestMapping(value="demandisok")
	public void demandisok(HttpServletResponse response,String type,String demandsid,String userid){
		Jr jr = new Jr();
		jr.setMethod("demandisok");
		if(demandsid!=null&&demandsid.length()>5&&userid!=null&&userid.length()>5){
			Demands demands=demandsService.getDemandinfo(demandsid);
			if(demands!=null&&userid.equals(demands.getPublishuser().getId())){
				demands.setIsok(1);
				demands.setStatus(2);
				demands=demandsService.updateDemand(demands);
				jr.setData(demandsService.getDemandinfo(demandsid));
				jr.setCord(0);
				jr.setMsg("成功");
			}else{
				jr.setCord(1);
				jr.setMsg("非法传参");
			}
		}else{
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除需求
	 * @param response
	 * @param type       访问端：    android  安卓   ios IOS   web 网页端
	 * @param demandsid  需求ID
	 * @param userid     用户ID （检测是不是需求发起人）
	 * @author ys 2015-04-03
	 */
	@RequestMapping(value="deldemands")
	public void delDemands(HttpServletResponse response,String type,String demandsid,String userid) {
		Jr jr = new Jr();
		jr.setMethod("deldemands");
		if(demandsid!=null&&demandsid.length()>5&&userid!=null&&userid.length()>5){
			Demands demands=demandsService.getDemandinfo(demandsid);
			if(demands!=null&&userid.equals(demands.getPublishuser().getId())){
				demands.setIsdel(1);
				demands.setStatus(3);
				demands=demandsService.updateDemand(demands);
				//jr.setData(demandsService.getDemandinfo(demandsid));
				jr.setCord(0);
				jr.setMsg("成功");
			}else{
				jr.setCord(1);
				jr.setMsg("非法传参");
			}
		}else{
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 需求点赞
	 * @param response
	 * @param type
	 * @param demandsid  需求ID
	 * @param userid     操作用户
	 * @author ys  2015-04-07
	 */
	@RequestMapping(value="praisedemands")
	public void praiseDemands(HttpServletResponse response,String type,String demandsid,String userid) {
		Jr jr = new Jr();
		jr.setMethod("praisedemands");
		if(demandsid!=null&&demandsid.length()>0&&userid!=null&&userid.length()>0){
			Demands demands=demandsService.getDemandinfo(demandsid);
			User user=userService.findById(userid);
			if(demands!=null&&user!=null){
				Set<User> set=demands.getPraises();
				if(set==null)
					set=new HashSet<User>();
				if(!set.contains(user)){
					set.add(user);
					demands.setPraises(set);
					demands=demandsService.updateDemand(demands);
					jr.setCord(0);
					jr.setMsg("点赞成功");
					jr.setData(set.size());
				}else{
					jr.setCord(1);
					jr.setMsg("重复点赞");
				}
			}else{
				jr.setCord(1);
				jr.setMsg("用户或需求不存在");
			}
		}else{
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 搜索需求/ 按需求类型取需求
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param listorderby   发布时间排序：asc 正序   desc 倒序
	 * @param searchkey     搜索词
	 * @param demandtype    需求类型  找人:findpeople   合作:cooperation  提问:askquestion
	 * @param userid        当前用户
	 */
	@RequestMapping(value="searchdemandlist")
	public void searchdemandlist(HttpServletResponse response,String type,Integer page,Integer size,String listorderby,String searchkey,String demandtype,String userid){
		Jr jr = new Jr();
		jr.setMethod("searchdemandlist");
		if(page==null||page<0||size==null||size<=0||(searchkey==null&&demandtype==null)){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Page<Demands> list = demandsService.searchDemandList(page, size, listorderby, searchkey, demandtype);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
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
	
	/**
	 * 答谢需求
	 * @param response
	 * @param type       访问端：    android  安卓   ios IOS   web 网页端
	 * @param demandsid  需求ID
	 * @param userid     用户ID （检测是不是需求发起人）
	 * @author ys 2015-04-29
	 */
	@RequestMapping(value="dxdemand")
	public void dxdemand(HttpServletResponse response,String type,String demandsid,String userid) {
		Jr jr = new Jr();
		jr.setMethod("dxdemand");
		if(demandsid!=null&&demandsid.length()>5&&userid!=null&&userid.length()>5){
			Demands demands=demandsService.getDemandinfo(demandsid);
			if(demands!=null&&userid.equals(demands.getPublishuser().getId())){
				demands.setIsdx(1);
				demands=demandsService.updateDemand(demands);
				jr.setCord(0);
				jr.setMsg("成功");
			}else{
				jr.setCord(1);
				jr.setMsg("非法传参");
			}
		}else{
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
