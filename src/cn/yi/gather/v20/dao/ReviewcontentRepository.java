package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Reviewcontent;
@Component("reviewcontentRepositoryV20")
public interface ReviewcontentRepository extends JpaRepository<Reviewcontent, String>{

}
