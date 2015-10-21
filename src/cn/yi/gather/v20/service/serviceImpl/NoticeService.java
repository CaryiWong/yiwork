package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.EventlogRepository;
import cn.yi.gather.v20.dao.NoticeRepository;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Notice;
import cn.yi.gather.v20.entity.Sysnetwork;
import cn.yi.gather.v20.service.IEventlogService;
import cn.yi.gather.v20.service.INoticeService;

@Service("noticeServiceV20")
public class NoticeService implements INoticeService{
	
	@Resource(name = "noticeRepositoryV20")
	private NoticeRepository repository;
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;
	
	@Transactional
	@Override
	public Notice noticeSaveOrUpdate(Notice notice) {
		// TODO Auto-generated method stub
		notice = repository.save(notice);
		Eventlog eventlog = new Eventlog();
		eventlog.setActtype("notice");
		eventlog.setEventtype("create");
		eventlog.setNotice(notice);
		eventlog.setUpdatetime(notice.getCreatetime());
		//eventlog.setUser(notice.getUser());
		eventlog.setUpdatetime(notice.getCreatetime());
		//设置城市8.24
		eventlog.setCity(notice.getSpaceInfo().getSpacecity());
	
		eventlogService.eventlogSaveOrUpdate(eventlog);
		
		return findByid(notice.getId());
	}

	@Transactional
	@Override
	public void deleteNotice(String id) {
		// TODO Auto-generated method stub
		eventlogService.deleteByNotice(id);
		repository.delete(id);
	}

	@Override
	public Notice findByid(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
	
	@Override
	public com.common.Page<Notice> getNoticeForPage(Integer page, Integer size,
			final String keyword) {
		// TODO Auto-generated method stub
		com.common.Page<Notice> result = new com.common.Page<Notice>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createtime"));
		Specification<Notice> spec = new Specification<Notice>() {

			@Override
			public Predicate toPredicate(Root<Notice> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = null;
				if(StringUtils.isNotBlank(keyword)){
					p0 = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
				}else {
					p0 = cb.isNotNull(root.<String>get("id"));
				}
				query.where(p0);
				return query.getRestriction();
			}
		};
		Page<Notice> list = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount(list.getNumberOfElements());
		result.setTotalPage(list.getTotalPages());
		return result;
	}

}
