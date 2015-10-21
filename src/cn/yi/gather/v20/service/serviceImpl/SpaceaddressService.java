package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.SpaceaddressRepository;
import cn.yi.gather.v20.entity.Spaceaddress;
import cn.yi.gather.v20.service.ISpaceaddressService;

@Service("spaceaddressServiceV20")
public class SpaceaddressService implements ISpaceaddressService{

	@Resource(name = "spaceaddressRepositoryV20")
	private SpaceaddressRepository repository;
	
	@Override
	public Spaceaddress saveOrupdate(Spaceaddress spaceaddress) {
		// TODO Auto-generated method stub
		return repository.save(spaceaddress);
	}

	@Override
	public List<Spaceaddress> spaceaddresslist() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
