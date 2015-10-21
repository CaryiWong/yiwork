package cn.yi.gather.v20.editor.service;

import java.util.List;

import cn.yi.gather.v20.editor.entity.CommHtml;

public interface ICommHtmlService {

	public CommHtml saveOrUpdate(CommHtml commHtml);
	
	public CommHtml findById(String id);
	
	public List<CommHtml> findAll();
	
	public List<CommHtml> findByUserId(String uid);
	
}
