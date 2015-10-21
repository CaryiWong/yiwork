package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.EmailTemplate;

public interface IEmailTemplateService {
	
	public EmailTemplate findById(String id);
	
	public EmailTemplate saveOrUpdate(EmailTemplate entity);
	
	public EmailTemplate findByCode(String code);

}
