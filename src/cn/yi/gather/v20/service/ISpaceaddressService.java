package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.Spaceaddress;

public interface ISpaceaddressService {

	public Spaceaddress saveOrupdate(Spaceaddress spaceaddress);
	
	public List<Spaceaddress> spaceaddresslist();
	
}
