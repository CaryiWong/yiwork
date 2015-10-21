package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Spaceshow;
@Component("spaceshowRepositoryV20")
public interface SpaceshowRepository extends JpaRepository<Spaceshow, String>{

}
