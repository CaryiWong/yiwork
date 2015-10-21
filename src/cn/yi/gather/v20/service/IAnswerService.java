package cn.yi.gather.v20.service;


import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Answer;

public interface IAnswerService {

	public Answer save(Answer answer);
	
	public Answer findById(String id);
	
	/**
	 * 通过活动ID /课程 ID 等获取 用户提交的问卷列表
	 * @param objid
	 * @param idtype
	 * @return
	 */
	public Page<Answer> findByObjid(Integer page,Integer size, String objid,String idtype);

}
