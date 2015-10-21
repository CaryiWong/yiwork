package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Spaceaddress;
@Component("spaceaddressRepositoryV20")
public interface SpaceaddressRepository extends JpaRepository<Spaceaddress, String>{

}
