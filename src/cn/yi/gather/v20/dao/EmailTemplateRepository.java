package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.EmailTemplate;

@Component("emailTemplateRepositoryV20")
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String>{

	public EmailTemplate findByCode(String code);
}
