package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Reviewcontent;
@Component("reviewcontentRepositoryV2")
public interface ReviewcontentRepository extends JpaRepository<Reviewcontent, String>{

}
