package cn.yi.gather.v20.service;

import java.sql.Connection;
import java.util.List;
import java.sql.Date;

import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserAccount;
import cn.yi.gather.v20.entity.UserAccountLog;

public interface IUserAccountService {

	/**
	 * 查看账户情况
	 * 
	 * @param conn
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public List<UserAccount> getUserAccount(Connection conn, String user_id) throws Exception;
	
	/**
	 * 查看账户情况
	 * @param user_id
	 * @return
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 上午10:55:04
	 */
	public List<UserAccount> getUserAccount(String user_id) throws Exception;
	
	/**
	 * 创建账户
	 * 
	 * @param conn
	 * @param user_id
	 * @throws Exception
	 */
	public void createAccount(Connection conn, String user_id) throws Exception;
	
	/**
	 * 冻结
	 * @param conn
	 * @param user_id
	 * @throws Exception
	 */
	public void freezeAccount(Connection conn, String user_id) throws Exception;
	
	/**
	 * 解冻
	 * @param conn
	 * @param user_id
	 * @throws Exception
	 */
	public void unfreezeAccount(Connection conn, String user_id) throws Exception;
	
	/**
	 * 执行充值
	 * @param conn
	 * @param user_id
	 * @param money
	 * @param order_id
	 * @param alipay_trade_no
	 * @throws Exception
	 */
	public void doDeposit(Connection conn, String user_id, Double money, String order_id, String alipay_trade_no, String memo) throws Exception;

	/**
	 * 获取用户账户的流水记录，分页
	 * 
	 * @param conn
	 * @param user_id
	 * @param page
	 * @param page_size
	 * @return
	 * @throws Exception
	 */
	public com.common.Page<UserAccountLog> getUserAccountLogForPage(Connection conn, String user_id, Date start_date, Date end_date, Integer page, Integer page_size) throws Exception;
	
	/**
	 * 修改用户账号余额
	 * 
	 * @param conn
	 * @param user_id
	 * @param money_type
	 * @param action_type
	 * @param money
	 * @param reason
	 * @param user
	 * @throws Exception
	 */
	public void editBalance(Connection conn, String user_id, Integer money_type, Integer action_type, Double money, String reason, User admin) throws Exception;

	public UserAccount findByMoneyTypeAndUserId(Integer type,String userId);
	
	public UserAccount saveOrUpdate(UserAccount entity);
	
	public void saveOrUpdate(List<UserAccount> entities);
	
	/**
	 * 用户消费
	 * @param money
	 * @param userId
	 * @param moneyType
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 上午11:28:54
	 */
	public void userAccountConsume(Double money,String userId,Integer moneyType) throws Exception;
	
	/**
	 * 执行充值
	 * @param payment
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 下午12:09:06
	 */
	public void doDeposit(Payment payment) throws Exception;
}
