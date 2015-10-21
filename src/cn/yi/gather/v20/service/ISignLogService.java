package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.SignLog;

public interface ISignLogService {

	public SignLog signLogSaveOrUpdate(SignLog signLog);
	
	/**
	 * 用户签到/签出记录
	 * @param userid 用户id
	 * @param signtype 签到in/签出out
	 * @return true:签到/签出成功,false:签到/签出失败
	 * Lee.J.Eric
	 * 2014年6月30日 下午3:19:22
	 */
	public Boolean userSignLog(String userid,String signtype,String gps);
	
	/**
	 * 统计当前用户当月签到
	 * @param userid
	 */
	public void signNumByMoth(String userid);
}
