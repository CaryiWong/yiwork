package cn.yi.gather.v20.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v20.entity.Indexorderbyinfo;
import cn.yi.gather.v20.entity.Indexteaminfo;
import cn.yi.gather.v20.entity.Indexuserinfo;
import cn.yi.gather.v20.entity.Joinapplication;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Spaceshow;
import cn.yi.gather.v20.service.IIndexorderbyinfoService;
import cn.yi.gather.v20.service.IIndexteaminfoService;
import cn.yi.gather.v20.service.IIndexuserinfoService;
import cn.yi.gather.v20.service.IJoinapplicationService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.ISpaceshowService;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("adminDisplayControllerV20")
@RequestMapping(value = "v20/admin/display")
public class AdminDisplayController {
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "indexuserinfoServiceV20")
	private IIndexuserinfoService indexuserinfoService;
	
	@Resource(name = "indexorderbyinfoServiceV20")
	private IIndexorderbyinfoService indexorderbyinfoService;
	
	@Resource(name = "indexteaminfoServiceV20")
	private IIndexteaminfoService indexteaminfoService;
	
	@Resource(name = "spaceshowServiceV20")
	private ISpaceshowService spaceshowService;
	
	@Resource(name = "joinapplicationServiceV20")
	private IJoinapplicationService joinapplicationService;
	
	
	/**
	 * hugo列表
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午3:35:26
	 */
	@RequestMapping(value="hugolist")
	public ModelAndView hugoList(){
		return new ModelAndView("admin/display/hugolist");
	}

	/**
	 * 入驻团队列表
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午3:35:42
	 */
	@RequestMapping(value="teamlist")
	public ModelAndView indexteaminfoList(){
		return new ModelAndView("admin/display/teamlist");
	}
	
	/**
	 * 新增hugo
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:39:34
	 */
	@RequestMapping(value="createhugo")
	public ModelAndView createHugo(ModelMap modelMap){
		///List<Labels> labels = labelsService.getLabelsByType(0);
		///modelMap.put("labels", labels);
		return new ModelAndView("admin/display/createhugo");
	}
	
	/**
	 * 保存hugo
	 * @param modelMap
	 * @param indexuser
	 * @param groups
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:39:26
	 */
	@RequestMapping(value="savehugo")
	public ModelAndView saveHugo(ModelMap modelMap,Indexuserinfo indexuser,Long[] groups){
		List<Long> labelList = new ArrayList<Long>();
		if(null != groups){
			labelList = Arrays.asList(groups);
		}else{
			labelList = null;
		}
		//List<Labels> labels = labelsService.getLabelsByType(0);
		//List<Labels> mylabel = labelsService.getLabelsByList(labelList);
		//indexuser.setIuserlables(mylabel);
		indexuser = indexuserinfoService.indexuserinfoSaveOrUpdate(indexuser); 
		Indexorderbyinfo indexorder = indexorderbyinfoService.findByType(0);
		String ids = indexorder.getIdstring();
		if(!ids.contains(indexuser.getId())){
			if(ids==null||ids.isEmpty())
				ids = indexuser.getId();
			else
				ids = indexuser.getId() + "," + ids;//默认把新增的排在最前
		}
		indexorder.setIdstring(ids);
		indexorder.setCreatedate(new Date());
		indexorderbyinfoService.indexorderbyinfoSaveOrUpdate(indexorder);
		//modelMap.put("labels", labels);
		//modelMap.put("labelList", labelList);
		modelMap.put("indexuser", indexuser);
		return new ModelAndView("admin/display/hugolist");
	}
	
	/**
	 * 更新hugo
	 * @param modelMap
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午5:32:50
	 */
	@RequestMapping(value="hugodetail")
	public ModelAndView hugoDetail(ModelMap modelMap,String id){
		try {
			List<Long> labelList = new ArrayList<Long>();
			Indexuserinfo indexuser = indexuserinfoService.findById(id);
			///List<Labels> labels = labelsService.getLabelsByType(0);
			///List<Labels> mylabel = indexuser.getIuserlables();
			///for (Labels label : mylabel) {
			///labelList.add(label.getId());
			///}
			///modelMap.put("labels", labels);
			///modelMap.put("labelList", labelList);
			modelMap.put("indexuser", indexuser);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("admin/display/updatehugo");
	}
	
	/**
	 * 更新hugo
	 * @param modelMap
	 * @param indexuser
	 * @param groups
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:39:19
	 */
	@RequestMapping(value="updatehugo")
	public ModelAndView updatehugo(ModelMap modelMap,Indexuserinfo indexuser,Long[] groups){
		List<Long> labelList = new ArrayList<Long>();
		if(null != groups){
			labelList = Arrays.asList(groups);
		}else{
			labelList = null;
		}
		//List<Labels> labels = labelsService.getLabelsByType(0);
		//List<Labels> mylabel = labelsService.getLabelsByList(labelList);
		//indexuser.setIuserlables(mylabel);
		indexuser = indexuserinfoService.indexuserinfoSaveOrUpdate(indexuser);
		if(indexuser!=null){
			//modelMap.put("labels", labels);
			modelMap.put("labelList", labelList);
			modelMap.put("indexuser", indexuser);
			return new ModelAndView("admin/display/hugolist");
		}else {
			return new ModelAndView("admin/display/updatehugo");
		}
	}
	
	/**
	 * 新增团队展示
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:39:12
	 */
	@RequestMapping(value="createteam")
	public ModelAndView createteam(ModelMap modelMap){
		List<Labels> labels = labelsService.getLabelsByType(0);
		modelMap.put("labels", labels);
		return new ModelAndView("admin/display/createteam");
	}
	
	/**
	 * 保存入驻团队
	 * @param modelMap
	 * @param indexteam
	 * @param groups
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:39:07
	 */
	@RequestMapping(value="saveteam")
	public ModelAndView saveTeam(ModelMap modelMap,Indexteaminfo indexteam,Long[] groups){
		List<Long> labelList = new ArrayList<Long>();
		if(null != groups){
			labelList = Arrays.asList(groups);
		}else{
			labelList = null;
		}
		List<Labels> labels = labelsService.getLabelsByType(0);
		List<Labels> mylabel = labelsService.getLabelsByList(labelList);
		indexteam.setTeamlables(mylabel);
		indexteam = indexteaminfoService.indexteaminfoSaveOrUpdate(indexteam);
		Indexorderbyinfo indexorder = indexorderbyinfoService.findByType(1);
		String ids = indexorder.getIdstring();
		if(!ids.contains(indexteam.getId())){
			if(ids==null||ids.isEmpty())
				ids = indexteam.getId();
			else
				ids = indexteam.getId() + "," + ids;//默认把新增的排在最前
		}
		indexorder.setIdstring(ids);
		indexorder.setCreatedate(new Date());
		indexorderbyinfoService.indexorderbyinfoSaveOrUpdate(indexorder);
		modelMap.put("labels", labels);
		modelMap.put("labelList", labelList);
		modelMap.put("indexteam", indexteam);
		return new ModelAndView("admin/display/teamlist");
	}
	
	/**
	 * 更新团队展示
	 * @param modelMap
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:39:01
	 */
	@RequestMapping(value="teaminfodetail")
	public ModelAndView teaminfoDetail(ModelMap modelMap,String id){
		try {
			List<Long> labelList = new ArrayList<Long>();
			Indexteaminfo teaminfo = indexteaminfoService.findById(id);
			List<Labels> labels = labelsService.getLabelsByType(0);
			List<Labels> mylabel = teaminfo.getTeamlables();
			for (Labels label : mylabel) {
				labelList.add(label.getId());
			}
			modelMap.put("labels", labels);
			modelMap.put("labelList", labelList);
			modelMap.put("teaminfo", teaminfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("admin/display/updateteaminfo");
	}
	
	/**
	 * 更新团队展示
	 * @param modelMap
	 * @param indexteam
	 * @param groups
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:38:55
	 */
	@RequestMapping(value="updateteaminfo")
	public ModelAndView updateTeaminfo(ModelMap modelMap,Indexteaminfo indexteam,Long[] groups){
		List<Long> labelList = new ArrayList<Long>();
		if(null != groups){
			labelList = Arrays.asList(groups);
		}else{
			labelList = null;
		}
		List<Labels> labels = labelsService.getLabelsByType(0);
		List<Labels> mylabel = labelsService.getLabelsByList(labelList);
		indexteam.setTeamlables(mylabel);
		indexteam = indexteaminfoService.indexteaminfoSaveOrUpdate(indexteam);
		if(indexteam!=null){
			//imglogService.createImgLog(indexteam.getTeamimg(), 5);//团队展示，type=5
			modelMap.put("labels", labels);
			modelMap.put("labelList", labelList);
			modelMap.put("indexteam", indexteam);
			return new ModelAndView("admin/display/teamlist");
		}else {
			return new ModelAndView("admin/display/updateteaminfo");
		}
	}
	
	/**
	 * 创建一个区域
	 * @param response
	 * @param spaceshow
	 * @param type
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:38:48
	 */
	@RequestMapping(value="createspaceshow")
	public void createSpaceshow(HttpServletResponse response,Spaceshow spaceshow,Integer type){
		Jr jr = new Jr();
		jr.setMethod("createspaceshow");
		if(spaceshow.getName().isEmpty()||spaceshow.getTitle().isEmpty()){
			jr.setCord(-1);//未命名名称和标题
			jr.setMsg("名称和标题不能为空");
		}else {
			spaceshow = spaceshowService.spaceshowSaveOrUpdate(spaceshow);
			if(spaceshow ==null){
				jr.setCord(1);
				jr.setMsg("保存失败");
			}else {
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("title");
				set.add("name");
				set.add("images");
				jr.setData(BeanUtil.getFieldValueMap(spaceshow,set));
				jr.setCord(0);
				jr.setMsg("保存成功");
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
	 * 更新区域图片信息
	 * @param response
	 * @param id
	 * @param img
	 * @param flag 更新标记，0添加图片，1删除图片
	 * Lee.J.Eric
	 * 2014年6月19日 下午12:11:52
	 */
	@RequestMapping(value="updatespaceshow")
	public void updateSpaceshow(HttpServletResponse response,String id,String img,Integer flag){
		Jr jr = new Jr();
		jr.setMethod("updatespaceshow");
		if(id==null||id.isEmpty()||img==null||img.isEmpty()||flag==null){
			jr.setCord(-1);//非法传参
		}else {
			Spaceshow spaceshow = spaceshowService.updateSpaceshow(id, img, flag);
			if(spaceshow==null){
				jr.setCord(1);
			}else {
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("title");
				set.add("name");
				set.add("images");
				jr.setData(BeanUtil.getFieldValueMap(spaceshow,set));
				jr.setCord(0);
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
	 * 空间区域设置
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 下午12:11:44
	 */
	@RequestMapping(value="showspace")
	public ModelAndView showspace(ModelMap modelMap){
		return new ModelAndView("admin/display/showspace");
	}
	
	/**
	 * 入驻申请列表
	 * @param modelMap
	 * @param page
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014年6月24日 下午2:20:01
	 */
	@RequestMapping(value = "joinapplicationlist")
	public ModelAndView joinapplicationList(ModelMap modelMap,com.common.Page<Joinapplication> page,String keyword){
		page = joinapplicationService.findJoinapplicationList(page.getCurrentPage(), page.getPageSize(), keyword);
		modelMap.put("page", page);
		modelMap.put("keyword", keyword);
		return new ModelAndView("admin/display/hosteamlist");
	}
	
	/**
	 * 入驻申请详情
	 * @param modelMap
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年6月25日 下午2:53:21
	 */
	@RequestMapping(value = "joinapplicationdetail")
	public ModelAndView joinapplicationDetail(ModelMap modelMap,String id){
		Joinapplication joinapplication = joinapplicationService.findById(id);
		modelMap.put("joinapplication", joinapplication);
		return new ModelAndView("admin/display/hosteamdetail");
	}

	/**
	 * 入驻申请成为团队展示
	 * @param modelMap
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年6月24日 下午3:05:01
	 */
	@RequestMapping(value="becometeam")
	public ModelAndView createteam(ModelMap modelMap,String id){
		Joinapplication joinapplication = joinapplicationService.findById(id);
		List<Labels> labels = labelsService.getLabelsByType(0);
		modelMap.put("labels", labels);
		modelMap.put("joinapplication", joinapplication);
		return new ModelAndView("admin/display/createteam");
	}
}
