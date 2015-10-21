package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Indexteaminfo;
@Component("indexteaminfoRepositoryV2")
public interface IndexteaminfoRepository extends
		JpaRepository<Indexteaminfo, String> {

}
