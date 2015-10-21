package cn.yi.gather.v11.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Feedback;
@Component("feedbackRepositoryV2")
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
	
	
	public Page<Feedback> findPageByContentContainingIgnoreCase(Pageable pageable,String content);

}
