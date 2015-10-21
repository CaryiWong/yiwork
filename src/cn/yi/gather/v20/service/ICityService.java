package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.City;

public interface ICityService {

	public City save(City city);
	
	public List<City> findAll();
	
	public City findById(String id);
	
}
