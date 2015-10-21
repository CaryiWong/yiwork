package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.FeedbackRepository;
import cn.yi.gather.v20.entity.Feedback;
import cn.yi.gather.v20.service.IFeedbackService;

/**
 * 意见反馈
 * @author Lee.J.Eric
 * @time 2014年6月11日下午5:47:05
 */
@Service("feedbackServiceV20")
public class FeedbackService implements IFeedbackService{
	
	@Resource(name = "feedbackRepositoryV20")
	private FeedbackRepository repository;

	@Override
	public Feedback feedbackSaveOrUpdate(Feedback feedback) {
		// TODO Auto-generated method stub
		return repository.save(feedback);
	}

	@Override
	public com.common.Page<Feedback> getFeedbackForPage(Integer page, Integer size,
			String keyword) {
		// TODO Auto-generated method stub
		com.common.Page<Feedback> result = new com.common.Page<Feedback>();
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "createdate"));
		Page<Feedback> list = null;
		if(StringUtils.isNotBlank(keyword)){
			list = repository.findPageByContentContainingIgnoreCase(pageRequest, keyword);
		}else {
			list = repository.findAll(pageRequest);
		}
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount(list.getNumberOfElements());
		return result;
	}

}
