package cn.yi.gather.v11.service.serviceImpl;


import java.util.ArrayList;
import java.util.List;

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

import cn.yi.gather.v11.dao.CommentRepository;
import cn.yi.gather.v11.entity.Activity;
import cn.yi.gather.v11.entity.Comment;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.ICommentServiceV2;

@Service("commentServiceV2")
public class CommentServiceV2 implements ICommentServiceV2{

	@Resource(name = "commentRepositoryV2")
	private CommentRepository commentdao;
	
	@Override
	public Comment commentSaveOrUpdate(Comment comment) {
		return commentdao.save(comment);
	}
	@Override
	public void commentDel(String id) {
		commentdao.delete(id);
	}
	@Override
	public Comment getCommentByID(String id) {
		return commentdao.findOne(id);
	}
	@Override
	public List<Comment> getCommentListByPid(String pid) {
		if(StringUtils.isBlank(pid))
			return new ArrayList<Comment>();
		return commentdao.findByPid(pid);
	}
	/**
	 * 
	 * @param page 页码
	 * @param size 每页大小
	 * @param orderby -1 时间倒序 1 时间正序
	 * @param listtype 0 根据活动拿评论  1 根据用户拿评论
	 * @param id   
	 * @return
	 */
	@Override
	public Page<Comment> commentFind(Integer page,Integer size,Integer orderby,final Integer listtype,final String id) {
		PageRequest pageRequest = new PageRequest(page, size);
		if(orderby==1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "createdate1"));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		}
		Specification<Comment> spec = new Specification<Comment>() {
			@Override
			public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				Predicate p0 =null;
				//Predicate p1=cb.isNull(root.<String>get("pid"));
				Predicate p1=cb.equal(root.<String>get("pid"), "");
				if(listtype==0){
					p0=cb.equal(root.<Activity>get("activity").get("id"), id);
				}else{
					p0=cb.equal(root.<User>get("user").get("id"), id);
				}
				query.where(cb.and(p0,p1));
				return query.getRestriction();
			}
		};
		return commentdao.findAll(spec, pageRequest);
	}
}
