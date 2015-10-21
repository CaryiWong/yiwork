package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Indexorderbyinfo;
@Component("indexorderbyinfoRepositoryV20")
public interface IndexorderbyinfoRepository extends JpaRepository<Indexorderbyinfo, String> {

	public Indexorderbyinfo findByIdtype(Integer idtype);
}
