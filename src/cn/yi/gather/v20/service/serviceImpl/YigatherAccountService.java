package cn.yi.gather.v20.service.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.PendingRefund;
import cn.yi.gather.v20.entity.YiGatherAccountLog;
import cn.yi.gather.v20.service.IYigatherAccountService;


@Service("yigatherAccountServiceV20")
public class YigatherAccountService implements IYigatherAccountService{
	
	@Override
	public com.common.Page<YiGatherAccountLog> getYiGatherAccountLogForPage(Connection conn, Date start_date, Date end_date, Integer page, Integer page_size) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<YiGatherAccountLog> array_list = new ArrayList<YiGatherAccountLog>();
		int total_count = 0;
		try {
			pstat = conn.prepareStatement("SELECT COUNT(*) as total_count FROM yigather_account_log WHERE date_time>=? AND date_time<=date_add(?, interval 1 day)");
			pstat.setDate(1, start_date);
			pstat.setDate(2, end_date);
			result = pstat.executeQuery();
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
			
			StringBuffer sql_buf = new StringBuffer();
			sql_buf.append("SELECT yigather_account_log.id, associated_user_id, u1.nickname as user_nickname,");
			sql_buf.append(" op_type, order_id, payment_type, money_type, money, yigather_account_log.subject, operator_id, u2.nickname as operator_name, memo, date_time ");
			sql_buf.append(" FROM yigather_account_log LEFT JOIN user u1 ON yigather_account_log.associated_user_id=u1.id");
			sql_buf.append(" 	LEFT JOIN user u2 ON yigather_account_log.operator_id=u2.id ");
			sql_buf.append(" WHERE date_time>=? AND date_time<=date_add(?, interval 1 day)");
			sql_buf.append(" ORDER BY date_time ASC");
			sql_buf.append(" LIMIT ?, ?");
			pstat = conn.prepareStatement(sql_buf.toString());
			pstat.setDate(1, start_date);
			pstat.setDate(2, end_date);
			int offset = 0;
			if (page > 1) {
				offset = (page-1) * page_size;
			}
			pstat.setInt(3, offset);
			pstat.setInt(4, page_size);
			result = pstat.executeQuery();
			while (result.next()) {
				YiGatherAccountLog record = new YiGatherAccountLog();
				record.setId(result.getString("id"));
				record.setOpType(result.getInt("op_type"));
				record.setAssociatedUserId(result.getString("associated_user_id"));
				record.setAssociatedUserNickname(result.getString("user_nickname"));
				record.setOrderId(result.getString("order_id"));
				record.setPaymentType(result.getInt("payment_type"));
				record.setMoneyType(result.getInt("money_type"));
				record.setMoney(result.getDouble("money"));
				record.setSubject(result.getString("subject"));
				record.setOperatorId(result.getString("operator_id"));
				record.setOperatorName(result.getString("operator_name"));
				record.setMemo(result.getString("memo"));
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
		
		com.common.Page<YiGatherAccountLog> paged_result = new com.common.Page<YiGatherAccountLog>();
		paged_result.setCurrentPage(page);
		paged_result.setPageSize(page_size);
		paged_result.setCurrentCount(array_list.size());
		paged_result.setTotalCount(total_count);
		paged_result.setResult(array_list);
		return paged_result;		
	}

	@Override
	public com.common.Page<AlipayNotificationLog> getAlipayNotificationLogForPage(Connection conn, Date start_date, Date end_date, Integer page, Integer page_size) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<AlipayNotificationLog> array_list = new ArrayList<AlipayNotificationLog>();
		int total_count = 0;
		try {
			pstat = conn.prepareStatement("SELECT COUNT(*) as total_count FROM alipay_notification_log WHERE notify_time>=? AND notify_time<=date_add(?, interval 1 day)");
			pstat.setDate(1, start_date);
			pstat.setDate(2, end_date);
			result = pstat.executeQuery();
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
			
			StringBuffer sql_buf = new StringBuffer();
			sql_buf.append("SELECT notify_id, notify_time, payment_type, alipay_trade_no, buyer_email, bank_seq_no, total_fee, order_id ");
			sql_buf.append(" FROM  alipay_notification_log");
			sql_buf.append(" WHERE notify_time>=? AND notify_time<=date_add(?, interval 1 day)");
			sql_buf.append(" ORDER BY notify_time ASC");
			sql_buf.append(" LIMIT ?, ?");
			pstat = conn.prepareStatement(sql_buf.toString());
			pstat.setDate(1, start_date);
			pstat.setDate(2, end_date);
			int offset = 0;
			if (page > 1) {
				offset = (page-1) * page_size;
			}
			pstat.setInt(3, offset);
			pstat.setInt(4, page_size);
			result = pstat.executeQuery();
			while (result.next()) {
				AlipayNotificationLog record = new AlipayNotificationLog();
				record.setNotifyId(result.getString("notify_id"));
				record.setNotifyTime(result.getTimestamp("notify_time"));
				record.setPaymentType(result.getString("payment_type"));
				record.setAlipayTradeNo(result.getString("alipay_trade_no"));
				record.setBuyerEmail(result.getString("buyer_email"));
				record.setBankSeqNo(result.getString("bank_seq_no"));
				record.setTotalFee(result.getDouble("total_fee"));
				record.setOrderId(result.getString("order_id"));
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
		
		com.common.Page<AlipayNotificationLog> paged_result = new com.common.Page<AlipayNotificationLog>();
		paged_result.setCurrentPage(page);
		paged_result.setPageSize(page_size);
		paged_result.setCurrentCount(array_list.size());
		paged_result.setTotalCount(total_count);
		paged_result.setResult(array_list);
		return paged_result;
	}
	
	@Override
	public com.common.Page<PendingRefund> getPendingRefundForPage(Connection conn, Integer page, Integer page_size) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<PendingRefund> array_list = new ArrayList<PendingRefund>();
		int total_count = 0;
		try {
			pstat = conn.prepareStatement("SELECT COUNT(*) as total_count FROM pending_refund");
			result = pstat.executeQuery();
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
			
			pstat = conn.prepareStatement(
					"SELECT pending_refund.id, alipay_trade_no, buyer_email, buyer_id, bank_seq_no, create_time, modify_time, user_id, user.nickname, order_id, pending_refund.status, money, memo "
					+ " FROM pending_refund LEFT JOIN user ON pending_refund.user_id=user.id"
					+ " ORDER BY modify_time ASC"
					+ " LIMIT ?, ?");
			int offset = 0;
			if (page > 1) {
				offset = (page-1) * page_size;
			}
			pstat.setInt(1, offset);
			pstat.setInt(2, page_size);			
			result = pstat.executeQuery();
			while (result.next()) {
				PendingRefund record = new PendingRefund();
				record.setId(result.getString("id"));
				record.setAlipayTradeNo(result.getString("alipay_trade_no"));
				record.setBuyerEmail(result.getString("buyer_email"));
				record.setBuyerId(result.getString("buyer_id"));
				record.setBankSeqNo(result.getString("bank_seq_no"));
				record.setCreateTime(result.getTimestamp("create_time"));
				record.setModifyTime(result.getTimestamp("modify_time"));
				record.setOrderId(result.getString("order_id"));
				record.setUserId(result.getString("user_id"));
				record.setUserNickname(result.getString("nickname"));
				record.setStatus(result.getInt("status"));
				record.setMoney(result.getDouble("money"));
				record.setMemo(result.getString("Memo"));
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
		
		com.common.Page<PendingRefund> paged_result = new com.common.Page<PendingRefund>();
		paged_result.setCurrentPage(page);
		paged_result.setPageSize(page_size);
		paged_result.setCurrentCount(array_list.size());
		paged_result.setTotalCount(total_count);
		paged_result.setResult(array_list);
		return paged_result;
	}
	
}
