package cn.yi.gather.v11.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.ReviewcontentRepository;
import cn.yi.gather.v11.entity.Reviewcontent;
import cn.yi.gather.v11.service.IReviewcontentServiceV2;

@Service("reviewcontentServiceV2")
public class ReviewcontentServiceV2 implements IReviewcontentServiceV2{
	
	@Resource(name = "reviewcontentRepositoryV2")
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
