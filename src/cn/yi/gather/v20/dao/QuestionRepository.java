package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Question;

@Component("questionRepositoryV20")
public interface QuestionRepository extends JpaRepository<Question, String>,JpaSpecificationExecutor<Question>{

}
