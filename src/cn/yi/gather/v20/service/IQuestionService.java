package cn.yi.gather.v20.service;


import cn.yi.gather.v20.entity.Question;

public interface IQuestionService {

	public Question save(Question question);
	
	public Question findById(String id);
	
}
