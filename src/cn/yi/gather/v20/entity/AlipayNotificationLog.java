package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 支付宝通知
 * 
 * @author Li Ming
 * @time 2014年8月27日下午2:13:09
 */
@Entity
@Table(name = "alipay_notification_log")
public class AlipayNotificationLog implements Serializable {

	/**
	 * 支付宝通知
	 */
	private static final long serialVersionUID = 28012768449013L;

	// 支付宝订单号
	@Id
	@Column(name = "notify_id")
	private String notifyId;
	
	// 通知时间
	@Column(name = "notify_time")
	private Timestamp notifyTime;
	
	// 通知类型
	@Column(name = "notify_type")
	private String notifyType;
	
	// 支付宝的订单号
	@Column(name="alipay_trade_no")
	private String alipayTradeNo;
	
	// 一起的订单号
	@Column(name = "order_id")
	private String orderId;
	
	// 商品名称
	@Column(name = "subject")
	private String subject;
	
	// 支付类型
	@Column(name = "payment_type")
	private String paymentType;
	
	// 交易状态
	@Column(name = "trade_status")
	private String tradeStatus;
	
	// 交易状态
	@Column(name = "refund_status")
	private String refundStatus;
		
	// 买家支付宝用户号
	@Column(name = "buyer_id")
	private String buyerId;
	
	// 买家支付宝账号
	@Column(name = "buyer_email")
	private String buyerEmail;
	
	// 卖家支付宝用户号
	@Column(name = "seller_id")
	private String sellerId;
		
	// 卖家支付宝账号
	@Column(name = "seller_email")
	private String sellerEmail;
		
	// 交易金额
	@Column(name = "total_fee",columnDefinition="double(10,2) default '0.00'")
	private Double totalFee;
	
	// 购买数量
	@Column(name = "quantity")
	private Integer quantity;
	
	// 商品单价
	@Column(name = "price",columnDefinition="double(10,2) default '0.00'")
	private Double price;
	
	// 商品描述
	@Column(name = "body")
	private String body;
		
	// 交易创建时间
	@Column(name = "gmt_create")
	private Timestamp gmtCreate;
	
	// 交易付款时间
	@Column(name = "gmt_payment")
	private Timestamp gmtPayment;
	
	// 交易关闭时间
	@Column(name = "gmt_close")
	private Timestamp gmtClose;
	
	// 卖家退款时间
	@Column(name = "gmt_refund")
	private Timestamp gmtRefund;
	
	// 网银流水
	@Column(name = "bank_seq_no")
	private String bankSeqNo;
	
	// 错误代码
	@Column(name = "error_code")
	private String errorCode;
	
	/**
	 *  支付渠道 组合信息
	 *  支付渠道，譬如：支付宝余额(BALANCE), 信用卡(CREDIT_PAY), 现金(CASH), ...
	 */
	@Column(name = "out_channel_type")
	private String outChannelType;
	
	// 支付金额 组合信息
	@Column(name = "out_channel_amount")
	private String outChannelAmount;
	
	// 该交易支付时实际使用的银行渠道, 例如: 工商银行(ICBC), 招商银行(CMB), ...
	@Column(name = "out_channel_inst")
	private String outChannelInst;
	
	
	public AlipayNotificationLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	
	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}

	public void setAlipayTradeNo(String alipayTradeNo) {
		this.alipayTradeNo = alipayTradeNo;
	}

	public Timestamp getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Timestamp notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getTradeStatus() {
		return tradeStatus;
	}
	
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public String getRefundStatus() {
		return refundStatus;
	}
	
	public void setRefundStatus(String status) {
		this.refundStatus = status;
	}
	
	public String getBuyerId() {
		return buyerId;
	}
	
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getBuyerEmail() {
		return buyerEmail;
	}
	
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	
	public String getSellerId() {
		return sellerId;
	}
	
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	public String getSellerEmail() {
		return sellerEmail;
	}
	
	public void setSellerEmail(String email) {
		this.sellerEmail = email;
	}
	
	public Double getTotalFee() {
		return totalFee;
	}
	
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public Timestamp getGmtCreate() {
		return gmtCreate;
	}
	
	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	public Timestamp getGmtPayment() {
		return gmtPayment;
	}
	
	public void setGmtPayment(Timestamp gmtPayment) {
		this.gmtPayment = gmtPayment;
	}
	
	public Timestamp getGmtClose() {
		return gmtClose;
	}
	
	public void setGmtClose(Timestamp gmtClose) {
		this.gmtClose = gmtClose;
	}
	
	public Timestamp getGmtRefund() {
		return gmtRefund;
	}
	
	public void setGmtRefund(Timestamp gmtRefund) {
		this.gmtRefund = gmtRefund;
	}
	
	public String getBankSeqNo() {
		return this.bankSeqNo;
	}
	
	public void setBankSeqNo(String bankSeqNo) {
		this.bankSeqNo = bankSeqNo;
	}
	
	public String getErorCode() {
		return this.errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getOutChannelType() {
		return this.outChannelType;
	}
	
	public void setOutChannelType(String outChannelType) {
		this.outChannelType = outChannelType;
	}
	
	public String getOutChannelAmount() {
		return this.outChannelAmount;
	}
	
	public void setOutChannelAmount(String out_channel_amount) {
		this.outChannelAmount = outChannelAmount;
	}
	
	public String getOutChannelInst() {
		return this.outChannelInst;
	}
	
	public void setOutChannelInst(String outChannelInst) {
		this.outChannelInst = outChannelInst;
	}
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("notify_id=");
		buf.append(notifyId);
		buf.append(", notify_time=");
		buf.append(notifyTime);
		buf.append(", notify_type=");
		buf.append(notifyType);
		buf.append(", alipay_trade_no=");
		buf.append(alipayTradeNo);
		buf.append(", order_id=");
		buf.append(orderId);
		buf.append(", subject=");
		buf.append(subject);
		buf.append(", payment_type=");
		buf.append(paymentType);
		buf.append(", trade_status=");
		buf.append(tradeStatus);
		buf.append(", refund_status=");
		buf.append(refundStatus);
		buf.append(", buyer_id=");
		buf.append(buyerId);
		buf.append(", buyer_email=");
		buf.append(buyerEmail);
		buf.append(", seller_id=");
		buf.append(sellerId);
		buf.append(", seller_email=");
		buf.append(sellerEmail);
		buf.append(", total_fee=");
		buf.append(totalFee);
		buf.append(", quantity=");
		buf.append(quantity);
		buf.append(", price=");
		buf.append(price);
		buf.append(", body=");
		buf.append(body);
		buf.append(", gmt_create=");
		buf.append(gmtCreate);
		buf.append(", gmt_payment=");
		buf.append(gmtPayment);
		buf.append(", gmt_close=");
		buf.append(gmtClose);
		buf.append(", gmt_refund=");
		buf.append(gmtRefund);
		buf.append(", bank_seq_no=");
		buf.append(bankSeqNo);
		buf.append(",error_code=");
		buf.append(errorCode);
		buf.append(", out_channel_type=");
		buf.append(outChannelType);
		buf.append(", out_channel_amount=");
		buf.append(outChannelAmount);
		buf.append(", out_channel_inst=");
		buf.append(outChannelInst);
		
		return buf.toString();
	}
}
