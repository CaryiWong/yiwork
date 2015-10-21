package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.yi.gather.v11.entity.Indexorderbyinfo;
import cn.yi.gather.v11.entity.Indexteaminfo;
import cn.yi.gather.v11.entity.Indexuserinfo;
import cn.yi.gather.v11.service.IIndexorderbyinfoServiceV2;
import cn.yi.gather.v11.service.IIndexteaminfoServiceV2;
import cn.yi.gather.v11.service.IIndexuserinfoServiceV2;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

/**
 * 展示控制器
 * @author Lee.J.Eric
 *
 */
@Controller("displayControllerV2")
@RequestMapping(value = "v2/display")
public class DisplayControllerV2 {
	
	@Resource(name = "indexuserinfoServiceV2")
	private IIndexuserinfoServiceV2 indexuserinfoServiceV2;
	
	@Resource(name = "indexteaminfoServiceV2")
	private IIndexteaminfoServiceV2 indexteaminfoServiceV2;
	
	@Resource(name = "indexorderbyinfoServiceV2")
	private IIndexorderbyinfoServiceV2 indexorderbyinfoServiceV2;
	
	/**
	 * 个人展示列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * Lee.J.Eric
	 * 2014年6月19日 下午2:12:24
	 */
	@RequestMapping(value="gethugolist")
	public void getHugoList(HttpServletResponse response,Integer type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("gethugolist");
		if(page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
			jr.setMsg("参数错误");
		}else {
			try {
				Indexorderbyinfo indexorder = indexorderbyinfoServiceV2.findByType(0);
				List<String> orderList = indexorder.getIds();
				Integer end = (page*size+size)>orderList.size()?orderList.size():page*size+size;
				Integer index = page*size>end?end:page*size;
				List<Indexuserinfo> list = indexuserinfoServiceV2.getByIdList(orderList.subList(index, end));
				Integer total = orderList.size();
				Integer totalPage = total%size==0?total/size:total/size+1;
				jr.setPagenum(page+1);
				jr.setPagecount(totalPage);
				if(list!=null&&!list.isEmpty()){
					jr.setPagesum(list.size());
				}
				jr.setData(list);
				jr.setCord(0);
				jr.setMsg("获取成功");
			} catch (Exception e) {
				// TODO: handle exception
				jr.setCord(1);
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
	 * 团队展示列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * Lee.J.Eric
	 * 2014年6月19日 下午2:22:54
	 */
	@RequestMapping(value="getteamlist")
	public void getTeamList(HttpServletResponse response,Integer type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("getteamlist");
		if(page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
			jr.setMsg("参数错误");
		}else {
			try {
				Indexorderbyinfo indexorder = indexorderbyinfoServiceV2.findByType(1);
				List<String> orderList = indexorder.getIds();
				Integer end = (page*size+size)>orderList.size()?orderList.size():page*size+size;
				Integer index = page*size>end?end:page*size;
				List<Indexteaminfo> list = indexteaminfoServiceV2.findByIdList(orderList.subList(index, end));
				Integer total = orderList.size();
				Integer totalPage = total%size==0?total/size:total/size+1;
				jr.setPagenum(page+1);
				jr.setPagecount(totalPage);
				if(list!=null&&!list.isEmpty()){
					jr.setPagesum(list.size());
				}
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("teamname");
				set.add("teamtitle");
				set.add("teampeople");
				set.add("teamcreate");
				set.add("teamtype");
				set.add("teamgrowth");
				set.add("teamimg");
				set.add("joindatetimes");
				set.add("teamlables");
				set.add("teamminim");
				jr.setData(BeanUtil.getFieldValueMapForList(list, set));
				jr.setCord(0);
				jr.setMsg("获取成功");
			} catch (Exception e) {
				// TODO: handle exception
				jr.setCord(1);
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
	 * 保存首页排序列表
	 * @param response
	 * @param type
	 * @param ids
	 * @param idtype 0 用户  1团队
	 * Lee.J.Eric
	 * 2014年6月19日 下午2:27:47
	 */
	@RequestMapping(value="saveorderlist")
	public void saveOrderList(HttpServletResponse response,Integer type,@RequestParam(value="ids[]") String[] ids,Integer idtype){
		Jr jr = new Jr();
		jr.setMethod("saveorderlist");
		if(ids==null||idtype==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("参数错误");
		}else {
			String idString = "";
			if(ids!=null){
				for (int i = 0; i < ids.length; i++) {
					if(i==0)
						idString += ids[i];
					else
						idString += ","+ids[i];
				}
			}
			Indexorderbyinfo indexorder = indexorderbyinfoServiceV2.findByType(idtype);
			indexorder.setIdstring(idString);
			indexorder = indexorderbyinfoServiceV2.indexorderbyinfoSaveOrUpdate(indexorder);
			if(indexorder==null)
				jr.setCord(1);
			else 
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
	
	/**
	 * 根据关键字搜索个人展示
	 * @param response
	 * @param keyword 搜索关键字
	 * @param page 页码，从0开始
	 * @param size 页面大小
	 * @param type
	 * Lee.J.Eric
	 * 2014年6月19日 下午2:42:28
	 */
	@RequestMapping(value="searchhugo")
	public void searchHugo(HttpServletResponse response,String keyword,Integer page,Integer size,Integer type){
		Jr jr = new Jr();
		jr.setMethod("searchhugo");
		if(page==null||page<0||size==null||size<0||keyword==null||keyword.isEmpty()){
			jr.setCord(-1);//非法传参
		}else {
			Page<Indexuserinfo> list = indexuserinfoServiceV2.searchByKeyword(page, size, keyword);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setData2(keyword);
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
