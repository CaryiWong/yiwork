package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.TeamInfo;

public interface ITeamService {

	public TeamInfo saveOrUpdate(TeamInfo teamInfo);
	
	public TeamInfo findById(String id);
	
	public Page<TeamInfo> findAll(Integer size,Integer page);
	
	public List<TeamInfo> findAll();
	
	public List<TeamInfo> findBanner();
	
}
