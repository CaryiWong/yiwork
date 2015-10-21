package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Spaceshow;
@Component("spaceshowRepositoryV2")
public interface SpaceshowRepository extends JpaRepository<Spaceshow, String>{

}
