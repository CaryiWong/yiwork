package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.YiQuestion;

@Component("yiQeustionRepositoryV20")
public interface YiQeustionRepository extends JpaRepository<YiQuestion, String>,JpaSpecificationExecutor<YiQuestion>{

}
