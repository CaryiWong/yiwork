package cn.yi.gather.v20.admin.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.IUserService;


@Controller("adminLabelsControllerV20")
@RequestMapping(value = "v20/admin/labels")
public class AdminLabelsController {

	@Resource(name="labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name="userServiceV20")
	private IUserService userService;
	
	
	/**
	 * 修改单个标签值
	 * @param modelMap
	 * @param labels
	 * @return
	 */
	@RequestMapping(value = "updatelabels")
	public ModelAndView updateLabels(ModelMap modelMap,String lbtype,Long labid,String labzname) {
		Labels labels=labelsService.findByID(labid);
		labels.setZname(labzname);
		labels=labelsService.saveOrUpdate(labels);
		return getAllLabels(modelMap, lbtype, labels.getPid(), labels.getId(), labels.getZname(), null);
	}
	
	/**
	 * 新增单个标签
	 * @param modelMap
	 * @param labels
	 * @return
	 */
	@RequestMapping(value = "addlabels")
	public ModelAndView addLabels(ModelMap modelMap,String ntype,Long npid,String nzname,String nename) {
		Labels labels=new Labels();
		labels.setEname(nename);
		labels.setZname(nzname);
		labels.setHot(0);
		labels.setIsdel(0);
		if(npid==null){
			npid=0l;
		}
		labels.setPid(npid);
		labels.setLabeltype(ntype);
		labels.setId(System.currentTimeMillis());
		labels=labelsService.saveOrUpdate(labels);
		if(npid==0l){
			return getAllLabels(modelMap, ntype, labels.getPid(), null, null, null);
		}else{
			return getAllLabels(modelMap, ntype, labels.getPid(), labels.getId(), labels.getZname(), null);
		}
	}
	
	/**
	 * 删除单个标签 取消用户对应的标签
	 * @param modelMap
	 * @param labels
	 * @return
	 */
	@RequestMapping(value = "dellabels")
	public ModelAndView delLabels(ModelMap modelMap,String labtype,Long lablid) {
		userService.updateLabelsAllUsers(lablid, 0, null, labtype);
		Labels label=labelsService.findByID(lablid);
		Long pid=0l;
		if(label.getPid()!=0l){
			pid=label.getPid();
		}
		label.setIsdel(1);
		labelsService.saveOrUpdate(label);
		return getAllLabels(modelMap, labtype, pid, null, null, null);
	}
	
	/**
	 * 显示标签/查看标签的关联用户
	 * @param modelMap
	 * @param labeltype
	 * @param pid
	 * @param id
	 * @param zname
	 * @return
	 */
	@RequestMapping(value = "labels")
	public ModelAndView getAllLabels(ModelMap modelMap,String labeltype,Long pid,Long id,String zname,String uid) {
		Page<Labels> page=labelsService.labelList(labeltype, null, 0, 500);
		modelMap.put("list", page.getContent());
		List<User> ulist=new ArrayList<User>();
		if(id!=null&&id!=1l){//查询使用该标签的用户
			ulist=userService.findUserBylabel(id, labeltype);
		}
		modelMap.put("ulist", ulist);
		modelMap.put("labeltype", labeltype);
		modelMap.put("pid", pid);
		modelMap.put("id", id);
		modelMap.put("zname", zname);
		modelMap.put("uid", uid);
		return new ModelAndView("admin/labels/labels",modelMap);
	}
	
	/**
	 * 用户批量取消该标签、修改为其他
	 * @param modelMap
	 * @param labeltype
	 * @param pid
	 * @param id
	 * @param zname
	 * @return
	 */
	@RequestMapping(value = "updateuserlabelby")
	public ModelAndView updateUserLabelBy(ModelMap modelMap,String ltype,Long laid,Integer ifqt) {
		Labels labels=labelsService.findByID(laid);
		Long pid=0l;
		Labels lqt=null;
		if(ltype.equals("job")){
			Labels l1=labelsService.findByLabeltypeAndZnameAndPid(ltype, "其他", pid);
			if(l1!=null){
				lqt=labelsService.findByLabeltypeAndZnameAndPid(ltype, "其他", l1.getId());
			}
		}else if(ltype.equals("focus")){
			lqt=labelsService.findByLabeltypeAndZnameAndPid(ltype, "其他", pid);
		}else if(ltype.equals("favourite")){
			Labels l1=labelsService.findByLabeltypeAndZnameAndPid(ltype, "其他", pid);
			if(l1!=null){
				lqt=labelsService.findByLabeltypeAndZnameAndPid(ltype, "其他", l1.getId());
			}
		}
		
		if(lqt==null){
			userService.updateLabelsAllUsers(laid, 0, null, ltype);
		}else{
			userService.updateLabelsAllUsers(laid, ifqt, lqt.getId(), ltype);
		}
		return getAllLabels(modelMap, ltype, labels.getPid(), laid, labels.getZname(), null);
	}
	
	/**
	 * 取消单个用户的单个标签
	 * @param modelMap
	 * @param labeltype
	 * @param pid
	 * @param id
	 * @param zname
	 * @return
	 */
	@RequestMapping(value = "updateuserlabelone")
	public ModelAndView updateUserLabelOne(ModelMap modelMap,String labeltype,Long lid,String uid) {
		User user=userService.findById(uid);
		Labels labels=labelsService.findByID(lid);
		if(user!=null){ 
			Set<Labels> set=null;
			if(labeltype.equals("job")){
				set=user.getJob();
			}else if(labeltype.equals("focus")){
				set=user.getFocus();
			}else if(labeltype.equals("favourite")){
				set=user.getFavourite();
			}
			Set<Labels> set2=new HashSet<Labels>();
			if(set!=null){
				for (Labels l2 : set) {
					if(!l2.getId().equals(lid)){
						set2.add(l2);
					}
				}
			}
			if(labeltype.equals("job")){
				user.setJob(set2);
			}else if(labeltype.equals("focus")){
				user.setFocus(set2);
			}else if(labeltype.equals("favourite")){
				user.setFavourite(set2);
			}
			userService.userSaveOrUpdate(user);
		}
		return getAllLabels(modelMap, labeltype, labels.getPid(), lid, labels.getZname(), uid);
	}
	
	/**
	 * 标签迁移
	 * @param modelMap
	 * @param fav_pid
	 * @param foc_id
	 * @param fav_id
	 * @return
	 */
	@RequestMapping(value = "qylabels")
	public ModelAndView qyLabels(ModelMap modelMap,Long fav_pid,Long foc_id,Long fav_id) {
		Page<Labels> page=labelsService.labelList(null, null, 0, 500);
		modelMap.put("list", page.getContent());
		List<User> ulist=new ArrayList<User>();
		if(fav_id!=null&&fav_id!=1l){//查询使用该标签的用户
			ulist=userService.findUserBylabel(fav_id, "favourite");
		}
		modelMap.put("ulist", ulist);
		/*List<User> ulist2=new ArrayList<User>();
		if(foc_id!=null&&foc_id!=1l){//查询使用该标签的用户
			ulist2=userService.findUserBylabel(foc_id, "focus");
		}
		modelMap.put("ulist2", ulist2);*/
		modelMap.put("fav_pid", fav_pid);
		modelMap.put("foc_id", foc_id);
		modelMap.put("fav_id", fav_id);
		return new ModelAndView("admin/labels/qylabels",modelMap);
	}
	

	/**
	 * 标签迁移
	 * @param modelMap
	 * @param old_fav_pid
	 * @param new_focus_id
	 * @param old_fav_id
	 * @param u_id
	 * @param ifdel
	 * @return
	 */
	@RequestMapping(value = "updateonefouce")
	public ModelAndView updateonefouce(ModelMap modelMap,Long old_fav_pid,Long new_focus_id,Long old_fav_id,String u_id,Integer ifdel) {
		User user=userService.findById(u_id);
		Labels labels=labelsService.findByID(new_focus_id);
		if(user!=null){ 
			Set<Labels> set=user.getFocus();
			if(ifdel==0){
				Set<Labels> set2=new HashSet<Labels>();
				if(set!=null){
					for (Labels l2 : set) {
						if(!l2.getId().equals(new_focus_id)){
							set2.add(l2);
						}
					}
				}
				user.setFocus(set2);
			}else{
				if(set!=null){
					set.add(labels);
					user.setFocus(set);
				}else{
					user.setFocus(new HashSet<Labels>());
				}
			}
			userService.userSaveOrUpdate(user);
		}
		return qyLabels(modelMap, old_fav_pid, new_focus_id, old_fav_id);
	}
}

