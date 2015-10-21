package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Indexteaminfo;
@Component("indexteaminfoRepositoryV20")
public interface IndexteaminfoRepository extends
		JpaRepository<Indexteaminfo, String> {

}
