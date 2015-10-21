package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.CityRepository;
import cn.yi.gather.v20.entity.City;
import cn.yi.gather.v20.service.ICityService;

@Service("cityService")
public class CityService implements ICityService{

	@Resource(name="cityRepository")
	private CityRepository repository;
	
	@Override
	public City save(City city) {
		// TODO Auto-generated method stub
		return repository.save(city);
	}

	@Override
	public List<City> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public City findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
