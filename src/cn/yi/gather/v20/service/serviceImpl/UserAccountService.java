package cn.yi.gather.v20.service.serviceImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.UserAccountRepository;
import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.Payment.PaymentType;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserAccount;
import cn.yi.gather.v20.entity.UserAccount.AccountStatus;
import cn.yi.gather.v20.entity.UserAccount.ActionType;
import cn.yi.gather.v20.entity.UserAccount.MoneyType;
import cn.yi.gather.v20.entity.UserAccountLog;
import cn.yi.gather.v20.entity.UserAccountLog.UserAccountOpType;
import cn.yi.gather.v20.entity.YiGatherAccountLog;
import cn.yi.gather.v20.entity.YiGatherAccountLog.AccountOpType;
import cn.yi.gather.v20.service.IUserAccountLogService;
import cn.yi.gather.v20.service.IUserAccountService;
import cn.yi.gather.v20.service.IYiGatherAccountLogService;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 支付宝通知
 */
@Service("userAccountServiceV20")
public class UserAccountService implements IUserAccountService{
	
	private static Logger log = Logger.getLogger(UserAccountService.class);
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	@Resource(name = "userAccountRepositoryV20")
	private UserAccountRepository repository;
	
	@Resource(name = "userAccountLogServiceV20")
	private IUserAccountLogService userAccountLogService;
	
	@Resource(name = "yiGatherAccountLogServiceV20")
	private IYiGatherAccountLogService yiGatherAccountLogService;
	
	
	@Override
	public List<UserAccount> getUserAccount(final String user_id) throws Exception {
		// TODO Auto-generated method stub
		Specification<UserAccount> spec = new Specification<UserAccount>() {
			
			@Override
			public Predicate toPredicate(Root<UserAccount> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("userId"), user_id));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Override
	public List<UserAccount> getUserAccount(Connection conn, String user_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			List<UserAccount> array_list = new ArrayList<UserAccount>();
			pstat = conn.prepareStatement("SELECT userId, moneyType, money, status, create_time, modify_time FROM user_account WHERE userId=?");
			pstat.setString(1, user_id);
			result = pstat.executeQuery();			
			if (result.next()) {
				UserAccount account = new UserAccount();
				account.setUserId(user_id);
				account.setMoneyType(result.getInt("moneyType"));
				account.setMoney(result.getDouble("money"));
				account.setStatus(result.getInt("status"));
				account.setCreateTime(result.getTimestamp("create_time"));
				account.setModifyTime(result.getTimestamp("modify_time"));
				array_list.add(account);
			}
			return array_list;
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	@Override
	public void createAccount(Connection conn, String user_id) throws Exception {
		PreparedStatement pstat = null;
		try {
			for (MoneyType m : MoneyType.values()) {
				if (m.getCode() == MoneyType.RMB.getCode()) {
					continue;
				}
                pstat = conn.prepareStatement(
						"INSERT user_account(userId, moneyType, money, status, create_time, modify_time)"
						+ " VALUES(?,?,?,?,NOW(),NOW(),?)");
				pstat.setString(1, user_id);
				pstat.setInt(2, m.getCode());
				pstat.setDouble(3, 0.0);
				pstat.setInt(4, AccountStatus.NORMAL.getCode());
				pstat.executeUpdate();
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	public void freezeAccount(Connection conn, String user_id) throws Exception {
		PreparedStatement pstat = null;
		try {
			pstat = conn.prepareStatement("UPDATE user_account SET status=? WHERE userId=?");
			pstat.setInt(1, AccountStatus.FROZEN.getCode());
			pstat.setString(2, user_id);
			pstat.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	public void unfreezeAccount(Connection conn, String user_id) throws Exception {
		PreparedStatement pstat = null;
		try {
			pstat = conn.prepareStatement("UPDATE user_account SET status=? WHERE userId=?");
			pstat.setInt(1, AccountStatus.NORMAL.getCode());
			pstat.setString(2, user_id);
			pstat.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	public void doDeposit(Connection conn, String user_id, Double money, String order_id, String alipay_trade_no, String memo) throws Exception{
		PreparedStatement pstat = null;
//		log.warn("doDeposit");
		try {
			pstat = conn.prepareStatement(
					"INSERT INTO user_account(userId, moneyType, money, create_time, modify_time,status)" +
					"VALUES(?,?,?,NOW(),NOW(),?)" +
					"ON DUPLICATE KEY UPDATE money=money+?, modify_time=NOW()");
			pstat.setString(1, user_id);
			pstat.setInt(2, MoneyType.YIGATHER.getCode());
			pstat.setDouble(3, money);
			pstat.setInt(4, AccountStatus.NORMAL.getCode());
			pstat.setDouble(5, money);
			pstat.executeUpdate();
//			log.warn("user_account");
			// 记录充值
			UserAccountLog record = new UserAccountLog();
			pstat = conn.prepareStatement("INSERT INTO user_account_log(id, order_id, user_id,"
					+ " op_type, money_type, money, memo, date_time)"
					+ " VALUES(?,?,?,?,?,?,?,NOW())");
			pstat.setString(1, record.getId());
			pstat.setString(2, order_id);
			pstat.setString(3, user_id);
			pstat.setInt(4, UserAccountOpType.INCOME.getCode());
			pstat.setInt(5, MoneyType.YIGATHER.getCode());
			pstat.setDouble(6, money);
			pstat.setString(7, "充值成功, 订单号"+order_id);
			pstat.executeUpdate();
			
//			log.warn("user_account_log");
			// 记录， 一起社区的账号， 付出虚拟币
			YiGatherAccountLog yi_log = new YiGatherAccountLog();
			pstat = conn.prepareStatement("INSERT INTO yigather_account_log(id, order_id, associated_user_id,"
					+ " op_type, money_type, money, date_time, memo, payment_type, subject)"
					+ " VALUES(?,?,?,?,?,?,NOW(),?,?,?)");
			pstat.setString(1, yi_log.getId());
			pstat.setString(2, order_id);
			pstat.setString(3, user_id);
			pstat.setInt(4, AccountOpType.EXPENDITURE.getCode());
			pstat.setInt(5, MoneyType.YIGATHER.getCode());
			pstat.setDouble(6, money);
			pstat.setString(7, "用户充值成功，订单号"+order_id);
			pstat.setInt(8, PaymentType.YIGATHER.getCode());
			pstat.setString(9, "用户充值");
			pstat.executeUpdate();
//			log.warn("yigather_account_log--");
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	@Override
	public com.common.Page<UserAccountLog> getUserAccountLogForPage(Connection conn, String user_id, Date start_date, Date end_date, Integer page, Integer page_size) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<UserAccountLog> array_list = new ArrayList<UserAccountLog>();
		int total_count = 0;
		try {
			pstat = conn.prepareStatement("SELECT COUNT(*) as total_count FROM user_account_log WHERE user_id=? AND date_time>=? AND date_time<=date_add(?, interval 1 day)");
			pstat.setString(1, user_id);
			pstat.setDate(2, start_date);
			pstat.setDate(3, end_date);
			result = pstat.executeQuery();
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
			
			pstat = conn.prepareStatement(
					"SELECT id, user_id, op_type, payment_type, money_type, money, order_id, memo, date_time "
					+ " FROM  user_account_log"
					+ " WHERE user_id=? AND date_time>=? AND date_time<=date_add(?, interval 1 day)"
					+ " ORDER BY date_time ASC"
					+ " LIMIT ?, ?");
			int offset = 0;
			if (page > 1) {
				offset = (page-1) * page_size;
			}
			pstat.setString(1, user_id);
			pstat.setDate(2, start_date);
			pstat.setDate(3, end_date);
			pstat.setInt(4, offset);
			pstat.setInt(5, page_size);			
			result = pstat.executeQuery();
			while (result.next()) {
				UserAccountLog record = new UserAccountLog();
				record.setId(result.getString("id"));
				record.setUserId(result.getString("user_id"));
				record.setOpType(result.getInt("op_type"));
				record.setPaymentType(result.getInt("payment_type"));
				record.setMoneyType(result.getInt("money_type"));
				record.setMoney(result.getDouble("money"));
				record.setOrderId(result.getString("order_id"));
				record.setMemo(result.getString("Memo"));
				record.setDateTime(result.getTimestamp("date_time"));
				array_list.add(record);
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
		
		com.common.Page<UserAccountLog> paged_result = new com.common.Page<UserAccountLog>();
		paged_result.setCurrentPage(page);
		paged_result.setPageSize(page_size);
		paged_result.setCurrentCount(array_list.size());
		paged_result.setTotalCount(total_count);
		paged_result.setResult(array_list);
		return paged_result;
	}

	@Override
	public void editBalance(Connection conn, String user_id, Integer money_type, Integer action_type, Double money, String reason, User admin) throws Exception {
		String action_type_name = ActionType.getName(action_type);
		if (action_type_name == null) {
			throw new Exception("未知的动作");
		}
		if (money <= 0) {
			throw new Exception("数额不正确");
		}
		if (admin == null) {
			throw new Exception("非管理员不能执行此操作");
		}
		if (reason == null || reason.length() == 0) {
			throw new Exception("事由必须填写");
		}
		
		if (ActionType.MINUS.getCode() == action_type) {
			money = -money;
		}
		
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement(
					"UPDATE user_account SET money=money+?, modify_time=NOW() WHERE userId=? AND moneyType=?");
			pstat.setDouble(1, money);
			pstat.setString(2, user_id);
			pstat.setInt(3, money_type);
			pstat.executeUpdate();
			
			pstat = conn.prepareStatement(
					"SELECT money FROM user_account WHERE userId=? AND moneyType=?");
			pstat.setString(1, user_id);
			pstat.setInt(2, money_type);
			result = pstat.executeQuery();
			if (result.next()) {
				if (result.getDouble("money") < 0.0) {
					throw new Exception("余额不足");
				}
			} else {
				throw new Exception("用户账户不存在");
			}
			
			// 记录充值
			UserAccountLog record = new UserAccountLog();
			pstat = conn.prepareStatement("INSERT INTO user_account_log(id, user_id,"
					+ " op_type, money_type, money, memo, date_time)"
					+ " VALUES(?,?,?,?,?,?,NOW())");
			pstat.setString(1, record.getId());
			pstat.setString(2, user_id);
			int user_account_op_type = UserAccountOpType.INCOME.getCode();
			if (action_type == ActionType.MINUS.getCode()) {
				user_account_op_type = UserAccountOpType.EXPENDITURE.getCode();
			}
			pstat.setInt(3, user_account_op_type);
			pstat.setInt(4, MoneyType.YIGATHER.getCode());
			pstat.setDouble(5, Math.abs(money));
			pstat.setString(6, "后台修改用户账户余额, 操作者:"+admin.getNickname()+"("+admin.getId()+"), 事由:"+reason);
			pstat.executeUpdate();
			
			// 记录， 一起社区的账号， 付出虚拟币
			YiGatherAccountLog yi_log = new YiGatherAccountLog();
			pstat = conn.prepareStatement("INSERT INTO yigather_account_log(id, associated_user_id,"
					+ " op_type, money_type, money, date_time, memo, payment_type, operator_id, subject)"
					+ " VALUES(?,?,?,?,?,NOW(),?,?,?,?)");
			pstat.setString(1, yi_log.getId());
			pstat.setString(2, user_id);
			int account_op_type = AccountOpType.EXPENDITURE.getCode();
			if (action_type == ActionType.MINUS.getCode()) {
				account_op_type = AccountOpType.INCOME.getCode();
			}
			pstat.setInt(3, account_op_type);
			pstat.setInt(4, MoneyType.YIGATHER.getCode());
			pstat.setDouble(5, Math.abs(money));
			pstat.setString(6, "用户"+user_id+"的账户余额变动:"+money+", 操作者:"+admin.getNickname()+"("+admin.getId()+"), 事由:"+reason);
			pstat.setInt(7, PaymentType.YIGATHER.getCode());
			pstat.setString(8, admin.getId());
			pstat.setString(9, "后台修改用户账户余额");
			pstat.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}

	@Override
	public UserAccount findByMoneyTypeAndUserId(final Integer moneyType,final String userId) {
		// TODO Auto-generated method stub
		Specification<UserAccount> spec = new Specification<UserAccount>() {
			
			@Override
			public Predicate toPredicate(Root<UserAccount> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("moneyType"), moneyType));
				ps.add(cb.equal(root.<Integer>get("userId"), userId));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findOne(spec);
	}


	@Override
	public UserAccount saveOrUpdate(UserAccount entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public void saveOrUpdate(List<UserAccount> entities) {
		// TODO Auto-generated method stub
		repository.save(entities);
	}

	@Override
	public void userAccountConsume(Double money, String userId,
			Integer moneyType) throws Exception{
		// TODO Auto-generated method stub
		try {
			UserAccount account = findByMoneyTypeAndUserId(moneyType, userId);
			if(account==null){
				throw new Exception("此用户账号不存在");
			}
			if(account.getMoney()<money){
				throw new Exception("账户余额不足");
			}
			account.setMoney(account.getMoney()-money);
			saveOrUpdate(account);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void doDeposit(Payment payment) throws Exception {
		// TODO Auto-generated method stub
		try {
			UserAccount account = findByMoneyTypeAndUserId(MoneyType.YIGATHER.getCode(), payment.getUserId());
			if(account==null){//此帐户不存在，新生成帐户
				account = new UserAccount();
				account.setUserId(payment.getUserId());
				account.setMoneyType(MoneyType.YIGATHER.getCode());
				account.setMoney(payment.getMoney());
				account.setCreateTime(new Timestamp(System.currentTimeMillis()));
				account.setModifyTime(new Timestamp(System.currentTimeMillis()));
				account.setStatus(AccountStatus.NORMAL.getCode());
			}else {
				account.setMoney(account.getMoney()+payment.getMoney());
			}
			saveOrUpdate(account);
			
			UserAccountLog record = new UserAccountLog();
			record.setOrderId(payment.getOrderId());
			record.setUserId(payment.getUserId());
			record.setOpType(UserAccountOpType.INCOME.getCode());
			record.setMoneyType(MoneyType.YIGATHER.getCode());
			record.setMoney(payment.getMoney());
			record.setMemo("充值成功, 订单号"+payment.getOrderId());
			record.setDateTime(new Timestamp(System.currentTimeMillis()));
			userAccountLogService.saveOrUpdate(record);
			
			// 记录， 一起社区的账号， 付出虚拟币
			YiGatherAccountLog yi_log = new YiGatherAccountLog();
			yi_log.setOrderId(payment.getOrderId());
			yi_log.setAssociatedUserId(payment.getUserId());
			yi_log.setOpType(AccountOpType.EXPENDITURE.getCode());
			yi_log.setMoneyType(MoneyType.YIGATHER.getCode());
			yi_log.setMoney(payment.getMoney());
			yi_log.setDateTime(new Timestamp(System.currentTimeMillis()));
			yi_log.setMemo("用户充值成功，订单号"+payment.getOrderId());
			yi_log.setPaymentType(PaymentType.YIGATHER.getCode());
			yi_log.setSubject("用户充值");
			yiGatherAccountLogService.saveOrUpdate(yi_log);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("doDeposit exception:"+e);
		}
		
	}
	
}
