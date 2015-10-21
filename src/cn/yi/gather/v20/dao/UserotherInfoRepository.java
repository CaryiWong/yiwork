package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.UserotherInfo;
@Component("userotherInfoRepositoryV20")
public interface UserotherInfoRepository extends JpaRepository<UserotherInfo, String>{

	
}
