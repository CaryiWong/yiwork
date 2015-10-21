package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Applyvip;
@Component("applyvipRepositoryV2")
public interface ApplyvipRepository extends JpaRepository<Applyvip, String>{

}
