package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.entity.Joinapplication;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.Spaceshow;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.service.IAlipayService;
import cn.yi.gather.v20.service.IApplyvipService;
import cn.yi.gather.v20.service.IAppversionService;
import cn.yi.gather.v20.service.IJoinapplicationService;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.ISmsInfoService;
import cn.yi.gather.v20.service.ISpaceshowService;
import cn.yi.gather.v20.service.IUserService;

import com.common.Jr;
import com.common.R;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("smsControllerV20")
@RequestMapping(value="v20/sms")
public class SmsController {
	@Resource(name = "smsinfoServiceV20")
	private ISmsInfoService smsService;
	
	/**
	 * 获取验证码
	 * @param telnum  手机号码
	 */
	@RequestMapping(value="sendvalidatesms")
	public void sendSms(HttpServletResponse response,String telnum,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("sendvalidatesms");
		int result=-1; 
		
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");  
		Matcher m=null;
		if(telnum!=null){
			m = p.matcher(telnum);  
		}
		
		
		if(telnum==null||telnum.isEmpty() || !m.matches()){
			jr.setCord(-1);
			jr.setMsg("telnum不符合要求");
		}else {
			result =smsService.sendSms(telnum,request);
			jr.setCord(result);
			switch (result) {
			case -1:
				jr.setMsg("号码格式错误");
				break;
			case 0:
				jr.setMsg("验证码发送成功"+R.Sms.VALIDATIONHOURS+"小时内有效");
				break;
			case 201:
				jr.setMsg("今天发送次数已满 如有需要请致电客服");
				break;
			case 202:
				jr.setMsg("你的验证码已发送,如未收到请"+R.Sms.VALIDATIONINTERVAL+"分钟后重试 ");
				break;
			case 203:
				jr.setMsg("平台今日发送量已满 请联系工作人员");
				break;
			case 204:
				jr.setMsg("该IP超限 请明日再试");
				break;
				
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
	 * 识别验证码
	 * @param telnum  手机号码
	 * @param code    验证码
	 */
	@RequestMapping(value="validatecode")
	public void validateCode(HttpServletResponse response,String telnum,HttpServletRequest request,String code){
		Jr jr = new Jr();
		jr.setMethod("validatecode");
		
		int result=0;
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");  
		Matcher m = p.matcher(telnum);  
		
		if(telnum==null||telnum.isEmpty() || !m.matches()){
			jr.setCord(-1);
			jr.setMsg("telnum不符合要求");
		}else if(code==null || code.equals("") ||code.length()>10){
			jr.setCord(-2);
			jr.setMsg("code不符合要求");
		}else{
			result=smsService.validateCodeByTel(telnum, code);
			jr.setCord(result);
			
			switch (result) {
			case 0:
				jr.setMsg("验证成功");
				break;
			case -1:
				jr.setMsg("号码格式错误");
				break;
			case -2:
				jr.setMsg("验证码不能为空");
				break;
			case -3:
				jr.setMsg("你还没有获取验证码");
				break;
			case -4:
				jr.setMsg("验证码已过期 请重新获取");
				break;
			case 1:
				jr.setMsg("验证码或者手机号错误 请重试");
				break;
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
	
	
	public static void main(String[] args) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");  
		String mobiles="156221901600";
		Matcher m = p.matcher(mobiles);  
		System.out.println(m.matches());  
	}
}
