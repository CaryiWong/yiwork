package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.Userother;

public interface IUserotherService {

	public Userother saveOrUpdateUserother(Userother userother);
	
	public Userother findUserother(String id);
	
	public List<Userother> findAllUserothers();
}
