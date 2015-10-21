package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v11.entity.Dcomment;
import cn.yi.gather.v11.entity.Demands;
import cn.yi.gather.v11.entity.Focus;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IDcommentServiceV2;
import cn.yi.gather.v11.service.IDemandsServiceV2;
import cn.yi.gather.v11.service.IFocusServiceV2;
import cn.yi.gather.v11.service.ILabelsServiceV2;
import cn.yi.gather.v11.service.IUserServiceV2;

import com.common.Jr;
import com.tools.utils.JSONUtil;

@Controller("demandsControllerV2")
@RequestMapping(value="v2/demands")
public class DemandsControllerV2 {
	private static Logger log = Logger.getLogger(DemandsControllerV2.class);

	@Resource(name = "demandsServiceV2")
	private IDemandsServiceV2 demandsServiceV2;
	
	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;
	
	@Resource(name = "userServiceV2")
	private IUserServiceV2 userServiceV2;
	
	@Resource(name = "dcommentServiceV2")
	private IDcommentServiceV2 dcommentServiceV2;
	
	@Resource(name = "focusServiceV2")
	private IFocusServiceV2 focusServiceV2;

	/**
	 * 新增需求(资源)
	 * @param response
	 * @param type
	 * @param demands     demandstype 需求的类型    0  寻人  1 视频制作  2 发起活动  3 其他
	 * @param area 领域
	 * @param make  视频制作
	 * @param form  活动形式
	 * @param group 面向人群
	 * @param find  寻人
	 */
	@RequestMapping(value="createdemand")
	public void createDemand(HttpServletResponse response,Integer type,Demands demands,String area, String make,String form,String group,String find){
		Jr jr = new Jr();
		Boolean flag=true;
		jr.setMethod("createdemand");
		if(demands.getPublishuser()==null){
			jr.setCord(-1);
			jr.setMsg("用户ID为空");
			flag=false;
		}
		User user =userServiceV2.findById(demands.getPublishuser().getId());
		if(user==null){
			jr.setCord(8);
			jr.setMsg("用户不存在信息");
			flag=false;
		}else if(user.getRoot()<3){
			if(area!=null){
				demands.setLabel(labelsServiceV2.getLabelsByids(area));
			}else{
				jr.setCord(9);
				jr.setMsg("领域为空");
				flag=false;
			}
			if(demands.getDemandstype()<3){
				try {
					if(demands.getDemandstype()==0){//寻人
						if(find!=null){
							demands.setFindlabel(labelsServiceV2.getLabelsByids(find));
						}else{
							jr.setCord(7);
							jr.setMsg("寻人类型为空");
							flag=false;
						}
					}else if (demands.getDemandstype()==1) {//视频制作
						if(make!=null){
							demands.setMakelabel(labelsServiceV2.getLabelsByids(make));
						}else{
							jr.setCord(6);
							jr.setMsg("视频制作类型为空");
							flag=false;
						}
					}else if (demands.getDemandstype()==2) {//发起活动
						if(form!=null){
							demands.setFormlabel(labelsServiceV2.getLabelsByids(form));//形式
						}else{
							jr.setCord(5);
							jr.setMsg("活动形式为空");
							flag=false;
						}
						if(group!=null){
							demands.setPeoplelabel(labelsServiceV2.getLabelsByids(group));//面向人群
						}else{
							jr.setCord(4);
							jr.setMsg("面向人群为空");
							flag=false;
						}
					}
					if(flag){
						demands = demandsServiceV2.createDemand(demands);
						if(demands==null){
							jr.setCord(3);
							jr.setMsg("新建需求失败");
						}else {
							jr.setData(demands);
							jr.setCord(0);
							jr.setMsg("新建需求成功");
						}
					}
				} catch (Exception e) {
					log.error("新建需求失败");
					jr.setCord(-1);
					jr.setMsg("服务器异常");
				}
			}else {
				jr.setCord(2);
				jr.setMsg("功能尚未开放，敬请关注");
			}
		}else{
			jr.setCord(1);
			jr.setMsg("用户权限不够");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取需求详细信息
	 * @param response
	 * @param id
	 */
	@RequestMapping(value="viewdemand")
	public void viewDemand(HttpServletResponse response,String id){
		Jr jr = new Jr();
		jr.setMethod("viewdemand");
		if(id==null||id.isEmpty()){
			jr.setCord(-1);//非法传参
		}else {
			Demands demands = demandsServiceV2.getDemandinfo(id);
			if(demands==null){
				jr.setCord(1);
			}else {
				jr.setData(demands);
				jr.setCord(0);
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 需求信息修改
	 * @param response
	 * @param demands
	 * @param area
	 * @param make
	 * @param form
	 * @param group
	 * @param find
	 * @param userid
	 */
	@RequestMapping(value="updatedemand")
	public void updateDemand(HttpServletResponse response,Demands demands,String area, String make,String form,String group,String find,String userid){
		Jr jr = new Jr();
		jr.setMethod("updatedemand");
		Boolean flag=true;
		if(demands==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
			flag=false;
		}else {
			Demands demands2 = demandsServiceV2.getDemandinfo(demands.getId());
			if(demands2==null){
				jr.setCord(1);
				jr.setMsg("无此需求");
				flag=false;
			}else if (!demands2.getPublishuser().getId().equals(userid)) {
				jr.setCord(1);
				jr.setMsg("无权修改需求");
				flag=false;
			}else {
				if(area!=null){
					demands2.setLabel(labelsServiceV2.getLabelsByids(area));
				}else{
					jr.setCord(9);
					jr.setMsg("领域为空");
					flag=false;
				}
				if(demands2.getDemandstype()<3){
					try {
						if(demands2.getDemandstype()==0){//寻人
							if(find!=null){
								demands2.setFindlabel(labelsServiceV2.getLabelsByids(find));
							}else{
								jr.setCord(7);
								jr.setMsg("寻人类型为空");
								flag=false;
							}
						}else if (demands2.getDemandstype()==1) {//视频制作
							if(make!=null){
								demands2.setMakelabel(labelsServiceV2.getLabelsByids(make));
							}else{
								jr.setCord(6);
								jr.setMsg("视频制作类型为空");
								flag=false;
							}
						}else if (demands2.getDemandstype()==2) {//发起活动
							if(form!=null){
								demands2.setFormlabel(labelsServiceV2.getLabelsByids(form));//形式
							}else{
								jr.setCord(5);
								jr.setMsg("活动形式为空");
								flag=false;
							}
							if(group!=null){
								demands2.setPeoplelabel(labelsServiceV2.getLabelsByids(group));//面向人群
							}else{
								jr.setCord(4);
								jr.setMsg("面向人群为空");
								flag=false;
							}
						}
						if(!demands.getTexts().isEmpty()){
							demands2.setTexts(demands.getTexts());
						}else{
							jr.setCord(10);
							jr.setMsg("内容为空");
							flag=false;
						}
						if(!demands.getTitle().isEmpty()){
							demands2.setTitle(demands.getTitle());
						}else{
							jr.setCord(11);
							jr.setMsg("标题为空");
							flag=false;
						}
						if(demands.getHopepeoples()!=0){
							demands2.setHopepeoples(demands.getHopepeoples());	
						}
						if(flag){
							demands = demandsServiceV2.updateDemand(demands2);
							if(demands==null){
								jr.setCord(3);
								jr.setMsg("修改需求失败");
							}else {
								jr.setData(demands);
								jr.setCord(0);
								jr.setMsg("修改需求成功");
							}
						}
					} catch (Exception e) {
						log.error("新建需求失败");
						jr.setCord(-1);
						jr.setMsg("服务器异常");
					}
				}else {
					jr.setCord(2);
					jr.setMsg("功能尚未开放，敬请关注");
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

	/**
	 * 获取我的需求列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param userid
	 * @param status 需求状态,(负数全部) 0 需要解决 1 正在解决 2 解决完成
	 * @param flag 1:我发起，2我参与，3我关注
	 */
	@RequestMapping(value="getmydemandlist")
	public void getMyDemandList(HttpServletResponse response,Integer type,Integer page,Integer size,String userid,Integer status,Integer flag){
		Jr jr = new Jr();
		jr.setMethod("getmydemandlist");
		if(page==null||page<0||size==null||size<0||userid==null||flag==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else{
			User u=userServiceV2.findById(userid);
			if(u==null){
				jr.setCord(1);
				jr.setMsg("用户不存在");
			}
			if(flag==1){//我发起的需求
				Page<Demands> list = demandsServiceV2.getMyDemands(page, size, -1, userid);//暂时不过滤需求的状态   -1 时间倒序
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setData(list.getContent());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
			}
			if(flag==2){//我参与评论的需求
				Page<Dcomment> list=dcommentServiceV2.dcommentFindByUid(page, size, -1, userid);
				List<Dcomment> dList=list.getContent();
				List<Demands> demands=new ArrayList<Demands>();
				for (int i = 0; i < dList.size(); i++) {
					demands.add(dList.get(i).getDemands());
				}
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setData(demands);
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
			}
			if(flag==3){//我关注的
				Page<Focus> list=focusServiceV2.findFocusBy(page, size, userid, null, -1);
				List<Focus> fs=list.getContent();
				List<Demands> list2=new ArrayList<Demands>();
				if(fs!=null){
					for (Focus o:fs) {
						list2.add(o.getDemands());
					}
				}
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setData(list2);
				jr.setTotal(list.getTotalElements());
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
	 * 获取所有需求列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 */
	@RequestMapping(value="getdemandlist")
	public void getDemandList(HttpServletResponse response,Integer type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("getdemandlist");
		if(page==null||page<0||size==null||size<=0){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Page<Demands> list = demandsServiceV2.getAllDemands(page, size, -1);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 需求列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param listtype -1全部，0  寻人  1 视频制作  2 发起活动  3 其他  4视频以外的
	 * @param keyword 搜索关键字
	 * @param userid 用户id
	 */
	@RequestMapping(value="demandlist")
	public void demandList(HttpServletResponse response,Integer type,Integer page,Integer size,Integer listtype,String keyword,String userid,Integer orderby){
		Jr jr = new Jr();
		jr.setMethod("demandlist");
		if(listtype==null||page==null||page<0||size==null||size<0){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<Demands> list = demandsServiceV2.getDemandsBy(page, size, listtype, keyword, userid,orderby);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
