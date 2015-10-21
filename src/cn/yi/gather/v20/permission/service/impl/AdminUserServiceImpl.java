package cn.yi.gather.v20.permission.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.Co_working_spaceRepository;
import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.permission.dao.AdminUserRepository;
import cn.yi.gather.v20.permission.entity.AdminRole;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.permission.service.IAdminUserService;

@Service("adminUserService")
public class AdminUserServiceImpl implements IAdminUserService {
	@Resource(name = "adminUserRepositoryV20")
	private AdminUserRepository repository;

	@Override
	public AdminUser SaveOrUpdate(AdminUser obj) {
		// TODO Auto-generated method stub
		return repository.save(obj);
	}

	@Override
	public AdminUser findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<AdminUser> getPage(Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size, new Sort(
				Direction.DESC, "createtime"));
		return repository.findAll(pageRequest);
	}

	@Override
	public com.common.Page<AdminUser> adminUserList(
			com.common.Page<AdminUser> page) {
		// TODO Auto-generated method stub
		Page<AdminUser> result = getPage(page.getCurrentPage(),
				page.getPageSize());
		page.setTotalCount(result.getNumberOfElements());
		page.setResult(result.getContent());
		return page;
	}

	/**
	 * 查询所有未删除的管理员列表
	 */
	public List<AdminUser> selectAllUser() {
		List<AdminUser> list = repository.findAll();
		List<AdminUser> result = new ArrayList<AdminUser>();
		for (AdminUser role : list) { // 0未删除 1删除
			if (role.getIsdel() != 1) {
				result.add(role);
			}
		}
		return result;
	}
	
	@Override
	public AdminUser findByusernameAndPWD(String password, String username) {
		// TODO Auto-generated method stub
		return repository.findByusernameAndPWD(password, username);
	}
}
