package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.TeamRepository;
import cn.yi.gather.v20.entity.TeamInfo;
import cn.yi.gather.v20.service.ITeamService;

@Service("teamService")
public class TeamService implements ITeamService{

	@Resource(name="teamRepository")
	private TeamRepository repository;

	@Override
	public TeamInfo saveOrUpdate(TeamInfo teamInfo) {
		// TODO Auto-generated method stub
		return repository.save(teamInfo);
	}

	@Override
	public TeamInfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<TeamInfo> findAll(Integer size, Integer page) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size);
		return repository.findAll(pageRequest);
	}

	@Override
	public List<TeamInfo> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<TeamInfo> findBanner() {
		// TODO Auto-generated method stub
		return repository.findByIsbanner(1);
	}
	
}
