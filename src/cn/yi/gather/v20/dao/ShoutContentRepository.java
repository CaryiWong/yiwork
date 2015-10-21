package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.ShoutContent;

@Component("shoutContentRepositoryV20")
public interface ShoutContentRepository extends JpaRepository<ShoutContent, String>{

}
