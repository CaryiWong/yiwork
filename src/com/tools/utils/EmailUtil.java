package com.tools.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

public class EmailUtil {
	private static Logger log = Logger.getLogger(EmailUtil.class);

	public void sendEmail(List<EmailEntity> emails) {
		for (EmailEntity email : emails) {
			sendEmail(email);
		}
	}

	public void sendEmail(EmailEntity[] emails) {
		for (EmailEntity email : emails) {
			sendEmail(email);
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param email
	 */
	public void sendEmail(EmailEntity email) {
		send(email);
	}

	private void send(EmailEntity mail) {
		HtmlEmail email = new HtmlEmail();
		email.setTLS(mail.getTLS());
		email.setHostName(mail.getHostName());// 发送账户(我方)smtp.qq.com
		email.setAuthentication(mail.getAccount(), mail.getPassword());// 邮箱账号和密码
		try {
			for (int i = 0; i < mail.getReceivers().size(); i++) {
				email.addTo(mail.getReceivers().get(i));
			}
			email.setFrom(mail.getAccount(), mail.getFromName()); // 发送方账户 我方
			email.setSubject(mail.getTitle()); // 标题
			email.setCharset(mail.getCharset()); // 设置Charset
			email.setHtmlMsg(mail.getContent()); // 内容
			if (mail.getAttachments() != null) {
				for (EmailAttachment attachment : mail.getAttachments()) {
					email.attach(attachment);
				}
			}
			email.setSentDate(new Date());
			email.send();
			log.info("邮件发送成功: from " + mail.getAccount());
			System.out.println("邮件发送成功: from " + mail.getAccount());
		} catch (EmailException e) {
			e.printStackTrace();
			log.error("邮件发送失败: from " + mail.getAccount());
			System.out.println("邮件发送失败: from " + mail.getAccount());
		}
	}

}
