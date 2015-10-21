package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Demands;

@Component("demandsRepositoryV20")
public interface DemandsRepository extends JpaRepository<Demands, String>,JpaSpecificationExecutor<Demands>{

}
