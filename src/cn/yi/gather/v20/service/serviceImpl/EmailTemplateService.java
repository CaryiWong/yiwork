package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.EmailTemplateRepository;
import cn.yi.gather.v20.entity.EmailTemplate;
import cn.yi.gather.v20.service.IEmailTemplateService;

@Service("emailTemplateServiceV20")
public class EmailTemplateService implements IEmailTemplateService{

	private static Logger log = Logger.getLogger(EmailTemplateService.class);
	
	@Resource(name = "emailTemplateRepositoryV20")
	private EmailTemplateRepository repository;

	@Override
	public EmailTemplate findById(String id){
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public EmailTemplate saveOrUpdate(EmailTemplate entity){
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public EmailTemplate findByCode(String code){
		// TODO Auto-generated method stub
		return repository.findByCode(code);
	}
	
	
	
}
