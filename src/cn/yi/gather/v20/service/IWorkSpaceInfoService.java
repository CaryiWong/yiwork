package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.WorkSpaceInfo;

public interface IWorkSpaceInfoService {

	public WorkSpaceInfo saveOrUpdate(WorkSpaceInfo info);
	
	public WorkSpaceInfo find(String id);
	
	public List<WorkSpaceInfo> findAll();
	
	public List<WorkSpaceInfo> findAll(Integer spacestatus);
	
	public WorkSpaceInfo findByCode(String code);
	
	public com.common.Page<WorkSpaceInfo> workspaceList(Integer page,Integer size,Integer status,String keyword);
	
}
