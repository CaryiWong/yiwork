package cn.yi.gather.v20.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Course;

public interface ICourseService {
	
	/**
	 * 课程保存｜更新
	 * @param course
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年10月13日 下午3:55:36
	 */
	public Course courseSaveOrUpdate(Course course);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年10月13日 下午3:55:03
	 */
	public Course findById(String id);
	
	/**
	 * 根据开始时间和结束时间查询课程列表
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月1日 下午3:40:22
	 */
	public List<Course> findListOpendateBetween(Date beginDate,Date endDate);
	
	/**
	 * 报名课程后续处理
	 * @param userid
	 * @param questions
	 * @param courseid
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 下午6:19:13
	 */
	public void singCourse(String userid, String questions, String courseid)throws Exception;
	
	/**
	 * 根据商品编号查询
	 * @param skuid
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 下午6:29:01
	 */
	public Course findBySkuid(String skuid);
	/**
	 * 绑定课程
	 * @param page
	 * @param size
	 * @param listtype
	 * @return
	 */
	public Page<Course> getcourselist(Integer page,Integer size,String listtype);
	
	public Page<Course> courselist(Integer page,Integer size,String key);
}
