package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Indexorderbyinfo;
@Component("indexorderbyinfoRepositoryV2")
public interface IndexorderbyinfoRepository extends JpaRepository<Indexorderbyinfo, String> {

	public Indexorderbyinfo findByIdtype(Integer idtype);
}
