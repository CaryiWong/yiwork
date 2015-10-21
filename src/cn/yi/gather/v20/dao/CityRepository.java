package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.City;

@Component("cityRepository")
public interface CityRepository extends JpaRepository<City, String>{

}
