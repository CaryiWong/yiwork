package cn.yi.gather.v20.service.serviceImpl;

import java.sql.Connection;
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
import javax.swing.text.html.StyleSheet.ListPainter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.PaymentRepository;
import cn.yi.gather.v20.dao.PendingRefundRepository;
import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.AlipayRefundNotificationLog;
import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.PendingRefund;
import cn.yi.gather.v20.entity.PendingRefund.RefundStatus;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.IPaymentService;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 支付宝通知
 */
@Service("paymentServiceV20")
public class PaymentService implements IPaymentService{
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "paymentRepositoryV20")
	private PaymentRepository repository;
	
	@Resource(name = "pendingRefundRepositoryV20")
	private PendingRefundRepository pendingRefundRepository;
	
	@Override
	public boolean isDuplicatedAlipayNotification(Connection conn, AlipayNotificationLog alipay_notification) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT notify_id FROM alipay_notification_log WHERE alipay_trade_no=? AND notify_id!=?");
			pstat.setString(1, alipay_notification.getAlipayTradeNo());
			pstat.setString(2, alipay_notification.getNotifyId());
			result = pstat.executeQuery();			
			if (result.next()) {
				return true;
			}
			return false;
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
	public void logAlipayNotification(Connection conn, AlipayNotificationLog alipay_notification) throws Exception {
		PreparedStatement pstat = null;
		try {
			pstat = conn.prepareStatement(
					"INSERT alipay_notification_log(notify_id, alipay_trade_no, body, buyer_id, buyer_email,"
					+ " gmt_create, gmt_payment, notify_time, notify_type, order_id, payment_type, price,"
					+ " quantity, subject, total_fee, trade_status)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstat.setString(1, alipay_notification.getNotifyId());
			pstat.setString(2, alipay_notification.getAlipayTradeNo());
			pstat.setString(3, alipay_notification.getBody());
			pstat.setString(4, alipay_notification.getBuyerId());
			pstat.setString(5, alipay_notification.getBuyerEmail());
			pstat.setTimestamp(6, alipay_notification.getGmtCreate());
			pstat.setTimestamp(7, alipay_notification.getGmtPayment());
			pstat.setTimestamp(8, alipay_notification.getNotifyTime());
			pstat.setString(9, alipay_notification.getNotifyType());
			pstat.setString(10,  alipay_notification.getOrderId());
			pstat.setString(11, alipay_notification.getPaymentType());
			pstat.setDouble(12, alipay_notification.getPrice());
			pstat.setInt(13, alipay_notification.getQuantity());
			pstat.setString(14,  alipay_notification.getSubject());
			pstat.setDouble(15, alipay_notification.getTotalFee());
			pstat.setString(16, alipay_notification.getTradeStatus());
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
	
	@Override
	public void logAlipayRefundNotification(Connection conn, AlipayRefundNotificationLog alipay_refund_notification) throws Exception {
		PreparedStatement pstat = null;
		try {
			pstat = conn.prepareStatement(
					"INSERT alipay_refund_notification_log(notify_id, notify_type, notify_time,"
					+ " batch_no, success_num, result_details)"
					+ " VALUES(?,?,?,?,?,?)");
			pstat.setString(1, alipay_refund_notification.getNotifyId());
			pstat.setString(2, alipay_refund_notification.getNotifyType());
			pstat.setTimestamp(3, alipay_refund_notification.getNotifyTime());
			pstat.setString(4, alipay_refund_notification.getBatchNo());
			pstat.setString(5, alipay_refund_notification.getSuccessNum());
			pstat.setString(6, alipay_refund_notification.getResultDetails());
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
	
	@Override
	public void savePayment(Connection conn, Payment payment) throws Exception {
		PreparedStatement pstat = null;
		try {
			pstat = conn.prepareStatement(
					"INSERT INTO payment(id, user_id, alipay_trade_no, order_id, status, payment_type, money_type, money, buyer_id, buyer_email, create_time, modify_time)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,NOW(),NOW())");
			pstat.setString(1, payment.getId());
			pstat.setString(2, payment.getUserId());
			pstat.setString(3, payment.getAlipayTradeNo());
			pstat.setString(4, payment.getOrderId());
			pstat.setInt(5, payment.getStatus());
			pstat.setInt(6, payment.getPaymentType());
			pstat.setInt(7, payment.getMoneyType());
			pstat.setDouble(8, payment.getMoney());
			pstat.setString(9, payment.getBuyerId());
			pstat.setString(10, payment.getBuyerEmail());
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
	
	@Override
	public void setPaymentStatusByAlipayTradeNo(Connection conn, String trade_no, int status) throws Exception {
		PreparedStatement pstat = null;
		try {
			pstat = conn.prepareStatement("UPDATE payment SET status=?, modify_time=NOW() WHERE alipay_trade_no=?");
			pstat.setInt(1, status);
			pstat.setString(2, trade_no);
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
	
	@Override
	public void setPaymentStatus(Connection conn, String payment_id, int status) throws Exception {
		PreparedStatement pstat = null;
		try {
			pstat = conn.prepareStatement("UPDATE payment SET status=?, modify_time=NOW() WHERE id=?");
			pstat.setInt(1, status);
			pstat.setString(2, payment_id);
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
	
	@Override
	public void setPaymentStatus(String payment_id, int status)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			Payment payment = findById(payment_id);
			payment.setStatus(status);
			payment.setModifyTime(new Timestamp(System.currentTimeMillis()));
			saveOrUpdate(payment);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public Payment findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Integer getPaymentStatusByAlipayTradeNo(Connection conn, String trade_no) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT status FROM payment WHERE alipay_trade_no=?");
			pstat.setString(1, trade_no);
			result = pstat.executeQuery();
			if (result.next()) {
				return result.getInt("status");
			}
			return null;
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
	public Integer getPaymentStatus(Connection conn, String payment_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT status FROM payment WHERE id=?");
			pstat.setString(1, payment_id);
			result = pstat.executeQuery();
			if (result.next()) {
				return result.getInt("status");
			}
			return null;
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
	public void prepareToRefund(Connection conn, String user_id, Double money, String order_id, String alipay_trade_no, String buyer_id, String buyer_email, String bank_seq_no, String memo) throws Exception {
		PreparedStatement pstat = null;
		try {
			PendingRefund pending_refund = new PendingRefund();
			pstat = conn.prepareStatement(
					"INSERT INTO pending_refund(id, alipay_trade_no, buyer_id, buyer_email, bank_seq_no, order_id, user_id, money, status, memo, create_time, modify_time)" +
					"VALUES(?,?,?,?,?,?,?,?,?,?,NOW(),NOW())");
			pstat.setString(1, pending_refund.getId());
			pstat.setString(2, alipay_trade_no);
			pstat.setString(3, buyer_id);
			pstat.setString(4, buyer_email);
			pstat.setString(5, bank_seq_no);
			pstat.setString(6, order_id);
			pstat.setString(7, user_id);
			pstat.setDouble(8, money);
			pstat.setInt(9, RefundStatus.PENDING.getCode());
			pstat.setString(10, memo);
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

	@Override
	public void prepareToRefund(Payment payment,String memo) throws Exception {
		// TODO Auto-generated method stub
		try {
			PendingRefund pending_refund = new PendingRefund();
			pending_refund.setAlipayTradeNo(payment.getAlipayTradeNo());
			pending_refund.setBuyerId(payment.getBuyerId());
			pending_refund.setBuyerEmail(payment.getBuyerEmail());;
			pending_refund.setBankSeqNo(payment.getBankSeqNo());
			pending_refund.setOrderId(payment.getOrderId());
			pending_refund.setUserId(payment.getUserId());
			pending_refund.setMoney(payment.getMoney());
			pending_refund.setStatus(RefundStatus.PENDING.getCode());
			pending_refund.setMemo(memo);
			pending_refund.setCreateTime(new Timestamp(System.currentTimeMillis()));
			pending_refund.setModifyTime(new Timestamp(System.currentTimeMillis()));
			pendingRefundRepository.save(pending_refund);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	@Override
	public Payment saveOrUpdate(Payment entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<Payment> findByOrderIdAndStatus(final String orderId,final Integer stauts) {
		// TODO Auto-generated method stub
		Specification<Payment> spec = new Specification<Payment>() {
			
			@Override
			public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("orderId"), orderId));
				if(stauts!=null){
					ps.add(cb.equal(root.<Integer>get("status"), stauts));
				}
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}
	
	
}
