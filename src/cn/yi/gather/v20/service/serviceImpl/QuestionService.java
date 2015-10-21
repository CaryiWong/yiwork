package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.QuestionRepository;
import cn.yi.gather.v20.entity.Question;
import cn.yi.gather.v20.service.IQuestionService;

@Service("questionServiceV20")
public class QuestionService implements IQuestionService{
	
	@Resource(name="questionRepositoryV20" )
	private QuestionRepository repository;

	@Override
	public Question save(Question question) {
		repository.save(question);
		return findById(question.getId());
	}

	@Override
	public Question findById(String id) {
		return repository.findOne(id);
	}

}
