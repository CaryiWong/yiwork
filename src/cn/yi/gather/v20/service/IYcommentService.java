package cn.yi.gather.v20.service;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Ycomment;

public interface IYcommentService {

	/**
	 * 发表新点评
	 * @param ycomment
	 * @return
	 */
	public Ycomment save(Ycomment ycomment);
	
	/**
	 * 分页获取点评列表
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Ycomment> getYcommentList(Integer page, Integer size);
	
	/**
	 * 统计 点赞数   踩点数   咖啡数
	 * @param type  z 赞    /  c 踩    /coffee 咖啡
	 * @return
	 */
	public Integer getCountNum(String type);
	
	/**
	 * 获取当点评
	 * @param id
	 * @return
	 */
	public Ycomment findById(String id);
	
}
