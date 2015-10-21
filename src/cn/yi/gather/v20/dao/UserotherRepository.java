package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Userother;
@Component("userotherRepositoryV20")
public interface UserotherRepository extends JpaRepository<Userother, String>{

}
