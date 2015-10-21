package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Applyvip;

@Component("applyvipRepositoryV20")
public interface ApplyvipRepository extends JpaRepository<Applyvip, String>{

}
