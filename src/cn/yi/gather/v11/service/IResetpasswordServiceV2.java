package cn.yi.gather.v11.service;

import cn.yi.gather.v11.entity.Resetpassword;

public interface IResetpasswordServiceV2 {

	/**
	 * 新增一个找回密码记录
	 * @param resetpassword
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午5:17:44
	 */
	public Resetpassword createResetpassword(Resetpassword resetpassword);
	
	/**
	 * 更新找回密码记录
	 * @param resetpassword
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午5:17:51
	 */
	public Resetpassword updateResetpassword(Resetpassword resetpassword);
	
	/**
	 * 获取单个找回密码记录
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午5:17:57
	 */
	public Resetpassword searchResetpassword(String id);
}
