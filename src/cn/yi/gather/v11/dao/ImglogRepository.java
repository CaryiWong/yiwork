package cn.yi.gather.v11.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Imglog;
@Component("imglogRepositoryV2")
public interface ImglogRepository extends JpaRepository<Imglog, String> {

	@Query(value = "select o from Imglog o where type = ? and starts=0 limit ?", nativeQuery = true)
	public List<Imglog> getListByType(int type, int size);

}
