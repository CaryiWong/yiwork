package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.WorkSpaceInfo;
import cn.yi.gather.v20.entity.WorkSpaceInfo.SpaceStatus;
import cn.yi.gather.v20.service.IWorkSpaceInfoService;

@Controller("workSpaceInfoController")
@RequestMapping(value="v20/workspace")
public class WorkSpaceInfoController {

	@Resource(name="workSpaceInfoService")
	private IWorkSpaceInfoService spaceInfoService;
	
	/**
	 * 获取所有空间集合
	 * @param response
	 * @param type
	 */
	@RequestMapping(value="getallworkspace")
	public void getAllWorkSpace(HttpServletResponse response,String type){
		Jr jr = new Jr();
		jr.setMethod("getallworkspace");
		try {
			List<WorkSpaceInfo> list = spaceInfoService.findAll(SpaceStatus.NORMAL.getCode());
			jr.setData(list);
			jr.setCord(0);
			try {
				JSONUtil jsonUtil = new JSONUtil();
				jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}
}
