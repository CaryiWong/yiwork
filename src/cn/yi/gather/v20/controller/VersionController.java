package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.service.IAppversionService;

import com.common.Jr;
import com.common.Page;
import com.tools.utils.JSONUtil;

/**
 * 应用版本
 * @author Lee.J.Eric
 *
 */
@Controller("versionControllerV20")
@RequestMapping(value = "v20/version")
public class VersionController {
	
	@Resource(name = "appversionServiceV20")
	private IAppversionService appversionService;
	
	/**
	 * 根据平台类型获取最新的版本
	 * @param response
	 * @param platform
	 * @param ver 当前应用版本值
	 * @param ver 当前应用版本号
	 * Lee.J.Eric
	 * 2014年6月19日 下午4:55:23
	 */
	@RequestMapping(value="checkupdate")
	public void checkUpdate(HttpServletResponse response,String platform,Integer ver,String version){
		Jr jr = new Jr();
		jr.setMethod("checkupdate");
		if(platform==null||platform.isEmpty()){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Appversion appversion = appversionService.getLastedAppversionByPlatform(platform.toLowerCase(),ver,version);
			if(appversion==null){
				jr.setCord(1);
				jr.setMsg("获取失败");
			}else {
				jr.setCord(0);
				jr.setData(appversion);
				jr.setMsg("获取成功");
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到创建新版本页面
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 下午4:55:16
	 */
	@RequestMapping(value="newversion")
	public ModelAndView newVersion(){
		return new ModelAndView("admin/display/newVersion");
	}
	
	/**
	 * 创建新版本
	 * @param response
	 * @param appversion
	 * Lee.J.Eric
	 * 2014 2014年5月1日 下午4:37:58
	 */
	@RequestMapping(value="createnewversion")
	public void createNewVersion(HttpServletResponse response,Appversion appversion){
		Jr jr = new Jr();
		jr.setMethod("createnewversion");
		Appversion dbversion = appversionService.getLastedAppversionByPlatform(appversion.getPlatform(),appversion.getVer(),appversion.getVersion());
		if(appversion.getVersion().equals("")){
			jr.setCord(1);
			jr.setMsg("版本号错误");
		}else if (appversion.getUrl().equals("")) {
			jr.setCord(1);
			jr.setMsg("应用下载地址不能为空");
		}else if(dbversion!=null&&appversion.getVer()<=dbversion.getVer()){
			jr.setCord(1);
			jr.setMsg("版本错误：已存在版本:"+dbversion.getVersion()+"(version),"+dbversion.getVer()+"(ver)");
		}else {
			try {
				if(dbversion!= null){
					if(appversion.getVersion().compareToIgnoreCase(dbversion.getVersion())==0||appversion.getVersion().compareToIgnoreCase(dbversion.getVersion())<0){
						jr.setCord(1);
						jr.setMsg("版本错误：已存在版本:"+dbversion.getVersion()+"(version)");
					}else {
						appversion = appversionService.appversionSaveOrUpdate(appversion);
						if(appversion==null){
							jr.setCord(1);
							jr.setMsg("版本创建失败");
						}else {
							jr.setCord(0);
							jr.setMsg("版本创建成功");
							jr.setData(appversion);
						}
					}
				}else {
					appversion = appversionService.appversionSaveOrUpdate(appversion);
					if(appversion==null){
						jr.setCord(1);
						jr.setMsg("版本创建失败");
					}else {
						jr.setCord(0);
						jr.setMsg("版本创建成功");
						jr.setData(appversion);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				jr.setCord(1);
				jr.setMsg("版本错误，格式应该为浮点数");
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取版本列表
	 * @param response
	 * @param page
	 * @param size
	 * Lee.J.Eric
	 * 2014年6月19日 下午4:57:58
	 */
	@RequestMapping(value="getversionlist")
	public void getVersionList(HttpServletResponse response,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("getversionlist");
		if(page==null||page<0||size==null||size<0){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			List<Appversion> list = appversionService.getList(page, size);
			if(list==null||list.isEmpty()){
				jr.setCord(1);
				jr.setMsg("获取失败");
			}else {
				jr.setCord(0);
				jr.setMsg("获取成功");
				jr.setData(list);
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 版本列表
	 * @param modelMap
	 * @param page
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 下午5:11:00
	 */
	@RequestMapping(value="versionlist")
	public ModelAndView getVersionList(ModelMap modelMap,Page<Appversion> page){
		page = appversionService.versionList(page);
		modelMap.put("page", page);
		return new ModelAndView("admin/display/versionList");
	}
}
