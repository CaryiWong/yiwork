package cn.yi.gather.v11.service;

public interface IEmailServiceV2 {

	/**
	 * 
	 * @param email
	 * @param key
	 * @param url
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午5:23:56
	 */
	public Boolean applyResetPassword(String email,String key,String url);
}
