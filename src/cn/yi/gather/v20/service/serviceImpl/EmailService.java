package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.service.IEmailService;

import com.common.R;
import com.tools.utils.EmailEntity;
import com.tools.utils.EmailUtil;

@Service("emailServiceV20")
public class EmailService implements IEmailService{
	private static Logger log = Logger.getLogger(EmailService.class);

	@Override
	public Boolean applyResetPassword(String email, String key, String url) {
		// TODO Auto-generated method stub
		try {
			List<String> receivers = new ArrayList<String>();
			receivers.add(email);
			String content = "亲爱的用户，您好：<br/>";
			content += "请点击下面的链接重置您的密码<br/>";
			content += url;
			content += "?id="+key;
			EmailUtil emailUtil = new EmailUtil();
			EmailEntity mail = new EmailEntity();
			mail.setCharset("utf8");
			mail.setHostName(R.Common.EMAIL_SMTP);
			mail.setAccount(R.Common.EMAIL_ACCOUNT);
			mail.setPassword(R.Common.EMAIL_PASSWORD);
			mail.setFromName("小Yi");
			mail.setReceivers(receivers);
			mail.setTLS(true);
			mail.setTitle("重置申请");
			mail.setContent(content);
			emailUtil.sendEmail(mail);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("applyResetPassword---密码重置申请邮件发送失败"+e);
			return false;
		}
	}

}
