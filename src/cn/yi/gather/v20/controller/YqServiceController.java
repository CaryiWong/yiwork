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
import com.common.R;
import com.tools.utils.EmailEntity;
import com.tools.utils.EmailUtil;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.YqServiceInfo;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IYqServiceService;

@Controller("yqServiceController")
@RequestMapping(value="v20/yqservice")
public class YqServiceController {

	@Resource(name="yqServiceService")
	private IYqServiceService serviceService;
	
	@Resource(name="userServiceV20")
	private IUserService userService;
	
	/**
	 * 获取服务列表
	 * @param response
	 * @param type
	 * @param size
	 * @param page
	 */
	@RequestMapping(value="findallyqservice")
	public void findAllYqService(HttpServletResponse response,String type,Integer size,Integer page){
		Jr jr=new Jr();
		jr.setMethod("findallyqservice");
		Page<YqServiceInfo> p= serviceService.findAll(size, page);
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
	 * 获取单个服务详情
	 * @param response
	 * @param type
	 * @param id
	 */
	@RequestMapping(value="findyqservice")
	public void findYqService(HttpServletResponse response,String type,String id){
		Jr jr=new Jr();
		jr.setMethod("findyqservice");
		if(id!=null&&id.length()>0){
			jr.setData(serviceService.findById(id));
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
	 * 服务 banner
	 * @param response
	 * @param type
	 */
	@RequestMapping(value="findbanner")
	public void findBanner(HttpServletResponse response,String type){
		Jr jr=new Jr();
		jr.setMethod("findbanner");
		List<YqServiceInfo> p= serviceService.findBanner();
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
	
	/**
	 * 服务发送邮件
	 * @param response
	 * @param type
	 * @param id
	 * @param bodystr
	 */
	@RequestMapping(value="sendserviceemail")
	public void sendServiceEmail(HttpServletResponse response,String type,String id,String bodystr,String userid){
		Jr jr=new Jr();
		jr.setMethod("sendserviceemail");
		if(id!=null&&id.length()>0&&userid!=null&&userid.length()>0){
			YqServiceInfo serviceInfo=serviceService.findById(id);
			User user=userService.findById(userid);
			if(serviceInfo!=null){
				if(user!=null&&user.getRoot()<3){
					try{
						List<String> receivers = new ArrayList<String>();
						receivers.add(serviceInfo.getEmail());
						EmailUtil emailUtil=new EmailUtil();
						EmailEntity mail=new EmailEntity();
						mail.setCharset("utf8");
						mail.setHostName(R.Common.EMAIL_SMTP);
						mail.setAccount(R.Common.EMAIL_ACCOUNT);
						mail.setPassword(R.Common.EMAIL_PASSWORD);
						mail.setFromName("一起开工社区");
						mail.setReceivers(receivers);
						mail.setTLS(true);
						mail.setTitle("一起开工社区会员申请服务");
						String ss="你好，以下是申请使用贵公司/机构服务的";
						if(user.getSpaceInfo()!=null){
							ss+=user.getSpaceInfo().getSpacename();
						}
						ss+="会员信息，请知悉</br>会员号："+user.getUnum()+"</br>";
						mail.setContent(ss+bodystr+"</br>此邮件由系统自动发送，请勿回复");
						emailUtil.sendEmail(mail);
						jr.setCord(0);
						jr.setMsg("发送成功");
					}catch(Exception e){
						jr.setCord(-1);
						jr.setMsg("发送失败");
					}
				}else{
					jr.setCord(-1);
					jr.setMsg("会员不存在");
				}
			}else{
				jr.setCord(2);
				jr.setMsg("服务不存在");
			}
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
}
