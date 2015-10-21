package cn.yi.gather.v11.service;

import cn.yi.gather.v11.entity.SignLog;

public interface ISignLogServiceV2 {

	public SignLog signLogSaveOrUpdate(SignLog signLog);
	
	/**
	 * 用户签到/签出记录
	 * @param userid 用户id
	 * @param signtype 签到in/签出out
	 * @return true:签到/签出成功,false:签到/签出失败
	 * Lee.J.Eric
	 * 2014年6月30日 下午3:19:22
	 */
	public Boolean userSignLog(String userid,String signtype);
}
