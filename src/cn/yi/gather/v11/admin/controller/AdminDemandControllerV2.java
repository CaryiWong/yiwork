package cn.yi.gather.v11.admin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v11.entity.Demands;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.service.IClasssortServiceV2;
import cn.yi.gather.v11.service.IDemandsServiceV2;
import cn.yi.gather.v11.service.IJPushServiceV2;
import cn.yi.gather.v11.service.ILabelsServiceV2;

import com.common.Jr;
import com.tools.utils.JSONUtil;

@Controller("adminDemandControllerV2")
@RequestMapping(value="v2/admin/demand")
public class AdminDemandControllerV2 {

	private static Logger log = Logger.getLogger(AdminDemandControllerV2.class);
	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;
	
	@Resource(name =  "classsortServiceV2")
	private IClasssortServiceV2 classsortServiceV2;
	
	@Resource(name = "demandsServiceV2")
	private IDemandsServiceV2 demandsServiceV2;
	
	@Resource(name = "jPushServiceV2")
	private IJPushServiceV2 pushServiceV2;
	
	/**
	 * 需求列表
	 * @param modelMap
	 * @param page
	 * @param status
	 * @param keyword  搜索关键字
	 * @param groups
	 * @param demandtype  -1全部，0  寻人  1 视频制作  2 发起活动  3 其他  4视频以外的
	 * @param ischeck
	 * @return
	 */
	@RequestMapping(value="demandlist")
	public ModelAndView demandList(ModelMap modelMap,com.common.Page<Demands> page,Integer status,String keyword,String groups,Integer demandtype,Integer ischeck){
		Page<Demands> list = demandsServiceV2.getDemandsByAdmin(page.getCurrentPage(),page.getPageSize(),status, demandtype, keyword,groups,ischeck);
		page.setCurrentCount(list.getNumber());
		page.setPageSize(list.getSize());
		page.setResult(list.getContent());
		page.setTotalCount((int)list.getTotalElements());
		List<Labels> labels = labelsServiceV2.getLabelsByType(0);
		modelMap.put("page", page);
		modelMap.put("status", status);
		modelMap.put("keyword", keyword);
		modelMap.put("labelList", groups);
		modelMap.put("labels", labels);
		modelMap.put("demandtype", demandtype);
		modelMap.put("ischeck", ischeck);
		return new ModelAndView("admin/demand/demandlist");
	}
	
	/**
	 * 查看需求
	 * @param modelMap
	 * @param demandid
	 * @return
	 */
	@RequestMapping(value="updatedemand")
	public ModelAndView updateDemand(ModelMap modelMap,String demandid){
		try {
			Demands demand = demandsServiceV2.getDemandinfo(demandid);
			List<Labels> labels = labelsServiceV2.getLabelsByType(0);
			List<Labels> forms = labelsServiceV2.getLabelsByType(1);
			List<Labels> groups = labelsServiceV2.getLabelsByType(2);
			List<Labels> makes = labelsServiceV2.getLabelsByType(3);
			List<Labels> finds = labelsServiceV2.getLabelsByType(4);
			List<Long> labelList = new ArrayList<Long>();
			List<Long> findList = new ArrayList<Long>();
			List<Long> formList = new ArrayList<Long>();
			List<Long> makeList = new ArrayList<Long>();
			List<Long> groupList = new ArrayList<Long>();
			List<Labels> label = demand.getLabel();
			for (Labels la : label) {
				labelList.add(la.getId());
			}
			for (Labels la : forms) {
				formList.add(la.getId());
			}
			for (Labels la : finds) {
				findList.add(la.getId());
			}
			for (Labels la : makes) {
				makeList.add(la.getId());
			}
			for (Labels la : groups) {
				groupList.add(la.getId());
			}
			modelMap.put("demand", demand);
			modelMap.put("labels", labels);
			modelMap.put("labelList", labelList);
			modelMap.put("forms", forms);
			modelMap.put("formList", formList);
			modelMap.put("groups", groups);
			modelMap.put("groupList", groupList);
			modelMap.put("makes", makes);
			modelMap.put("makeList", makeList);
			modelMap.put("finds", finds);
			modelMap.put("findList", findList);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查看需求出错------"+e);
			return null;
		}
		return new ModelAndView("admin/demand/updatedemand");
	}
	
	/**
	 * 更新需求
	 * @param modelMap
	 * @param demands
	 * @param areas
	 * @param plantime
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月29日 下午6:28:07
	 */
	@RequestMapping(value="savedemand")
	public ModelAndView saveDemand(ModelMap modelMap,Demands demands,Long[] areas,Long [] findid,Long[] formid,Long[] makeid,Long[] groupid,String plantime){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Long> labelList = new ArrayList<Long>();
		List<Long> findList = new ArrayList<Long>();
		List<Long> formList = new ArrayList<Long>();
		List<Long> makeList = new ArrayList<Long>();
		List<Long> groupList = new ArrayList<Long>();
		if (areas != null) {
			labelList = Arrays.asList(areas);
		}
		if(findid!=null){
			findList = Arrays.asList(findid);
		}
		if (formid != null) {
			formList = Arrays.asList(formid);
		}
		if(makeid!=null){
			makeList = Arrays.asList(makeid);
		}
		if(groupid!=null){
			groupList = Arrays.asList(groupid);
		}
		List<Labels> labels = labelsServiceV2.getLabelsByType(0);
		List<Labels> forms = labelsServiceV2.getLabelsByType(1);
		List<Labels> groups = labelsServiceV2.getLabelsByType(2);
		List<Labels> makes = labelsServiceV2.getLabelsByType(3);
		List<Labels> finds = labelsServiceV2.getLabelsByType(4);
		Demands demand = demandsServiceV2.getDemandinfo(demands.getId());
		List<Labels> myLabels = labelsServiceV2.getLabelsByList(labelList);
		if(demand != null){//更新
			demand.setTitle(demands.getTitle());
			demand.setTexts(demands.getTexts());
			if (labelList==null||labelList.size()<1||labelList.size()>3) {
				modelMap.put("tips", "标签数个数：1~3个");
			}else {
				demand.setLabel(myLabels);
				demand.setDemandstype(demands.getDemandstype());
				demand.setFindlabel(labelsServiceV2.getLabelsByList(findList));
				demand.setFormlabel(labelsServiceV2.getLabelsByList(formList));
				demand.setMakelabel(labelsServiceV2.getLabelsByList(makeList));
				demand.setPeoplelabel(labelsServiceV2.getLabelsByList(groupList));
				if(plantime!=null){
					try {
						demand.setPlandatetime(format.parse(plantime));
						demand.setPlandatetime1(format.parse(plantime).getTime());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				demand.setCover(demands.getCover());
				demand.setStatus(demands.getStatus()); 
				demand = demandsServiceV2.updateDemand(demand);
				modelMap.put("tips", "修改成功");
				modelMap.put("demand", demand);
			}
		}else {//新增
			if (labelList==null||labelList.size()<1||labelList.size()>3) {
				modelMap.put("tips", "标签数个数：1~3个");
			}else {
				demands.setLabel(myLabels);
				demands.setFindlabel(labelsServiceV2.getLabelsByList(findList));
				demands.setFormlabel(labelsServiceV2.getLabelsByList(formList));
				demands.setMakelabel(labelsServiceV2.getLabelsByList(makeList));
				demands.setPeoplelabel(labelsServiceV2.getLabelsByList(groupList));
				if(plantime!=null){
					try {
						demands.setPlandatetime(format.parse(plantime));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				demands = demandsServiceV2.createDemand(demands);
				modelMap.put("tips", "修改成功");
				modelMap.put("demand", demands);
			}
		}
		modelMap.put("labels", labels);
		modelMap.put("labelList", labelList);
		modelMap.put("forms", forms);
		modelMap.put("formList", formList);
		modelMap.put("groups", groups);
		modelMap.put("groupList", groupList);
		modelMap.put("makes", makes);
		modelMap.put("makeList", makeList);
		modelMap.put("finds", finds);
		modelMap.put("findList", findList);
		return new ModelAndView("admin/demand/updatedemand");
	}
	
	/**
	 * 审核需求
	 * @param response
	 * @param demands
	 * Lee.J.Eric
	 * 2014 2014年4月28日 下午2:15:38
	 */
	@RequestMapping(value="verifydemand")
	public void verifyDemand(HttpServletResponse response,Demands demands){
		Jr jr = new Jr();
		jr.setMethod("verifydemand");
		if(demands==null){
			jr.setCord(-1);
			jr.setMsg("参数非法");
		}else {
			Demands demands2 = demandsServiceV2.getDemandinfo(demands.getId());
			if(demands2==null){
				jr.setCord(1);
				jr.setMsg("无此需求");
			}else if (demands2.getDemandstype()==1&&!demands2.getCover().equals("")&&!demands.getCover().equals("")) {//视频制作
				jr.setCord(1);
				jr.setMsg("请上传视频封面");
			}else {
				//if(demands2.getDemandstype()==1&&!demands2.getMediaurl().equals(""))
					//demands2.setCover(demands.getCover());
				demands2.setIscheck(1);//审核标记
				demands2 = demandsServiceV2.updateDemand(demands2);
				if(demands2==null){
					jr.setCord(1);
					jr.setMsg("审核失败");
				}else {
					pushServiceV2.push(1, demands.getId(), demands.getTitle(), "新需求", Platform.android_ios(), new String[]{"root1","root2"}, new String[]{"version2"});
//					pushServiceV2.push("root1", "androidi,ios", "会员新需求:"+demands.getTitle(), 1);
					jr.setCord(0);
					jr.setMsg("审核成功");
				}
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
	 * 跳转到新增需求页面
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月29日 下午5:55:45
	 */
	@RequestMapping(value="adddemand")
	public ModelAndView adddemand(ModelMap modelMap){
		List<Labels> labels = labelsServiceV2.getLabelsByType(0);
		List<Labels> forms = labelsServiceV2.getLabelsByType(1);
		List<Labels> groups = labelsServiceV2.getLabelsByType(2);
		List<Labels> makes = labelsServiceV2.getLabelsByType(3);
		List<Labels> finds = labelsServiceV2.getLabelsByType(4);
		modelMap.put("labels", labels);
		modelMap.put("forms", forms);
		modelMap.put("groups", groups);
		modelMap.put("makes", makes);
		modelMap.put("finds", finds);
		return new ModelAndView("admin/demand/addDemand");
	}
	
	/**
	 *  需求上下架
	 * @param response
	 * @param demands     id  onsale 必须滴  onsale =0 上架  onsale=1 下架
	 */
	@RequestMapping(value="onsaleDemand")
	public void onsaleDemand(HttpServletResponse response,Demands demands){
		Jr jr = new Jr();
		jr.setMethod("onsaleDemand");
		if(demands==null){
			jr.setCord(-1);
			jr.setMsg("参数非法");
		}else {
			Demands demands2 = demandsServiceV2.getDemandinfo(demands.getId());
			if(demands2==null){
				jr.setCord(1);
				jr.setMsg("无此需求");
			}else {
				if(demands.getOnsale()!=null){
					demands2.setOnsale(demands.getOnsale());
					demands2.setOnsaledate(new Date());
				}
				demands2 = demandsServiceV2.updateDemand(demands2);
				if(demands2==null){
					jr.setCord(1);
					jr.setMsg("上架失败");
				}else {
					jr.setCord(0);
					jr.setMsg("上架成功");
				}
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
