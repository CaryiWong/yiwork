package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Course;

@Component("courseRepositoryV20")
public interface CourseRepository extends JpaRepository<Course, String>,JpaSpecificationExecutor<Course>{

}
