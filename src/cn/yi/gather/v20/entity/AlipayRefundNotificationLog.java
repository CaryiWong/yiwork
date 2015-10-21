package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 支付宝退款通知
 * 
 * @author Li Ming
 * @time 2014年8月27日下午2:13:09
 */
@Entity
@Table(name = "alipay_refund_notification_log")
public class AlipayRefundNotificationLog implements Serializable {

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
	
	// 退款批次号
	@Column(name="batch_no")
	private String batchNo;
	
	// 退款成功总数
	@Column(name = "success_num")
	private String successNum;
	
	// 退款结果明细
	@Column(name = "result_details")
	private String resultDetails;
	
		
	public AlipayRefundNotificationLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notify_id) {
		this.notifyId = notify_id;
	}
	
	public Timestamp getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Timestamp notify_time) {
		this.notifyTime = notify_time;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notify_type) {
		this.notifyType = notify_type;
	}

	public String getBatchNo() {
		return batchNo;
	}
	
	public void setBatchNo(String batch_no) {
		this.batchNo = batch_no;
	}
	
	public String getSuccessNum() {
		return successNum;
	}
	
	public void setSuccessNum(String success_num) {
		this.successNum = success_num;
	}
	
	public String getResultDetails() {
		return resultDetails;
	}
	
	public void setResultDetails(String result_details) {
		this.resultDetails = result_details;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("notify_id=");
		buf.append(notifyId);
		buf.append(", notify_time=");
		buf.append(notifyTime);
		buf.append(", notify_type=");
		buf.append(notifyType);
		buf.append(", batch_no=");
		buf.append(batchNo);
		buf.append(", success_num=");
		buf.append(successNum);
		buf.append(", result_details=");
		buf.append(resultDetails);
		
		return buf.toString();
	}
}
