package cn.yi.gather.v20.service; 
import javax.servlet.http.HttpServletRequest;

import com.common.Jr;

import cn.yi.gather.v20.entity.SmsInfo;



public interface ISmsInfoService {

	public SmsInfo SaveOrUpdate(SmsInfo obj);
	
	public SmsInfo findById(String id);
	
	public Integer sendSms(String telnum,HttpServletRequest request);
	
	 
	/**
	 * 根据手机号 获取今天的下发的短信总条数
	 * @param telnum
	 * @param type
	 * 1 获取手机号今天的全部
	 * 2 获取手机号在有效期内待验证的个数
	 * 3 获取今天总台总的发送量(是否超标)
	 * 4 查看IP一天的发送量 
	 * @return
	 */
	public Integer getTodayCountSms(String telnum,Integer type);
	
	public Integer validateCodeByTel(String temlnum,String code);
	
	public Jr send(String telnum,HttpServletRequest request);
	 
}
