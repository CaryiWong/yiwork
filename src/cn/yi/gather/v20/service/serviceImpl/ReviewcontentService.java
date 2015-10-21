package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.ReviewcontentRepository;
import cn.yi.gather.v20.entity.Reviewcontent;
import cn.yi.gather.v20.service.IReviewcontentService;

@Service("reviewcontentServiceV20")
public class ReviewcontentService implements IReviewcontentService{
	@Resource(name = "reviewcontentRepositoryV20")
	private ReviewcontentRepository repository;
	
	@Override
	public Reviewcontent reviewcontentSaveOrUpdate(Reviewcontent reviewcontent) {
		// TODO Auto-generated method stub
		return repository.save(reviewcontent);
	}

	@Override
	public Reviewcontent findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
	
	

}
