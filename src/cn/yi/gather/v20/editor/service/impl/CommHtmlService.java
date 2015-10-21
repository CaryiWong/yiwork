package cn.yi.gather.v20.editor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.editor.dao.CommHtmlRepository;
import cn.yi.gather.v20.editor.entity.CommHtml;
import cn.yi.gather.v20.editor.service.ICommHtmlService;

@Service("commHtmlService")
public class CommHtmlService implements ICommHtmlService{

	@Resource(name="commHtmlRepository")
	private CommHtmlRepository repository;
	
	@Override
	public CommHtml saveOrUpdate(CommHtml commHtml) {
		// TODO Auto-generated method stub
		return repository.save(commHtml);
	}

	@Override
	public CommHtml findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<CommHtml> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<CommHtml> findByUserId(String uid) {
		// TODO Auto-generated method stub
		return repository.findByUserid(uid);
	}

}
