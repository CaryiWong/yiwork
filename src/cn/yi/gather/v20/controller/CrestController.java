package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.Answer;
import cn.yi.gather.v20.entity.Co_working_space;
import cn.yi.gather.v20.entity.Enterprise;
import cn.yi.gather.v20.entity.Question;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Tribe_partner;
import cn.yi.gather.v20.entity.Visit;
import cn.yi.gather.v20.service.IAnswerService;
import cn.yi.gather.v20.service.ICo_working_spaceService;
import cn.yi.gather.v20.service.IEnterpriseService;
import cn.yi.gather.v20.service.IQuestionService;
import cn.yi.gather.v20.service.ITribeService;
import cn.yi.gather.v20.service.ITribe_partnerService;
import cn.yi.gather.v20.service.IVisitService;

@Controller("tribeControllerV20")
@RequestMapping(value="v20/crest")  //问卷控制类
public class CrestController {

	@Resource (name="tribeServiceV20")
	private ITribeService tribeService;
	
	@Resource (name="enterpriseServiceV20")
	private IEnterpriseService enterpriseService;
	
	@Resource (name="co_working_spaceServiceV20")
	private ICo_working_spaceService co_working_spaceService;
	
	@Resource (name="tribe_partnerServiceServiceV20")
	private ITribe_partnerService tribe_partnerService;
	
	@Resource (name="visitServiceV20")
	private IVisitService visitService;
	
	
	
	@RequestMapping(value="tribe",method = RequestMethod.POST)
	@ResponseBody
	public void saveTribe(HttpServletResponse response,Tribe tribe,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("tribe");
		if(tribe.getName()==null||tribe.getEmail()==null||tribe.getPhone_number()==null||tribe.getIs_member()==null||tribe.getTribe_name()==null||tribe.getTribe_introduction()==null||tribe.getPlan()==null||tribe.getCreator_introduction()==null||tribe.getReason()==null){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			tribe.setIp(this.getRemoteIpAddr(request));
			Tribe t= tribeService.tribeSaveOrUpdate(tribe);
			jr.setCord(0);
			jr.setData(t.getId());
			jr.setMethod("保存成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="enterprise",method = RequestMethod.POST)
	@ResponseBody
	public void saveEnterprise(HttpServletResponse response,Enterprise enterprise,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("enterprise");
		if(enterprise.getName()==null||enterprise.getEmail()==null||enterprise.getPhone_number()==null||enterprise.getIs_member()==null||enterprise.getEnterprise_name()==null||enterprise.getProject_introduction()==null||enterprise.getNeed_service()==null){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			enterprise.setIp(this.getRemoteIpAddr(request));
			Enterprise en= enterpriseService.SaveOrUpdate(enterprise);
			jr.setCord(0);
			jr.setData(en.getId());
			jr.setMethod("保存成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="co_working_space",method = RequestMethod.POST)
	@ResponseBody
	public void saveCo(HttpServletResponse response,Co_working_space co,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("co_working_space");
		if(co.getName()==null||co.getEmail()==null||co.getPhone_number()==null||co.getIs_member()==null||co.getSpace_name()==null||co.getCity()==null||co.getSpace_introduction()==null||co.getIdea()==null){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			co.setIp(this.getRemoteIpAddr(request));
			Co_working_space co1= co_working_spaceService.SaveOrUpdate(co);
			jr.setCord(0);
			jr.setData(co1.getId());
			jr.setMethod("保存成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="tribe_partner",method = RequestMethod.POST)
	@ResponseBody
	public void saveTribe_partner(HttpServletResponse response,Tribe_partner tribe,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("tribe_partner");
		if(tribe.getName()==null||tribe.getEmail()==null||tribe.getPhone_number()==null||tribe.getIs_member()==null||tribe.getOrganization_name()==null||tribe.getProduct_name()==null||tribe.getProduct_introduction()==null||tribe.getIdea()==null){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			tribe.setIp(this.getRemoteIpAddr(request));
			Tribe_partner tr=tribe_partnerService.SaveOrUpdate(tribe);
			jr.setCord(0);
			jr.setData(tr.getId());
			jr.setMethod("保存成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="visit",method = RequestMethod.POST)
	@ResponseBody
	public void saveVisit(HttpServletResponse response,Visit visit,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("visit");
		if(visit.getName()==null||visit.getEmail()==null||visit.getPhone_number()==null||visit.getIs_member()==null||visit.getOrganization_name()==null||visit.getOrganization_introduction()==null||visit.getNum_of_visitors()==null||visit.getPurpose()==null||visit.getVisit_date_time()==null){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			visit.setIp(this.getRemoteIpAddr(request));
			Visit vi=visitService.SaveOrUpdate(visit);
			jr.setCord(0);
			jr.setData(vi.getId());
			jr.setMethod("保存成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取真实的客户IP 屏蔽代理
	 * @param request
	 * @return
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
}
