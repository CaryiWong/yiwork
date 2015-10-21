package cn.yi.gather.v11.service;

import java.util.List;

import cn.yi.gather.v11.entity.Imglog;


/**
 * 
 * @author Lee.J.Eric
 * @time 2014年5月29日下午5:23:36
 */
public interface IImglogServiceV2 {

	/**
	 * 新增一个需要生成缩略图的记录
	 * @param url  图片源
	 * @param type 类型 0 用户  1活动封面  2 活动详情 3评论附件图 4个人展示，5团队展示 ,6空间展示              
	 * @return
	 */
	public int createImgLog(String url,int type);
	
	/**
	 * 拿需要生成缩略图的 
	 * @param type 类型 0 用户  1活动封面  2 活动详情 3评论附件图 4个人展示，5团队展示，6空间展示
	 * @return
	 */
	public List<Imglog> getListByType(int type,int size);
	
	/**
	 * 生成完成 修改对象
	 * @param imglog
	 */
	public void updateImglog(Imglog imglog);
}
